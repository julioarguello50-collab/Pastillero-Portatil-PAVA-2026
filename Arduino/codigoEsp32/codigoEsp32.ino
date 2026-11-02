#include <Wire.h>
#include <Adafruit_PWMServoDriver.h>
#include "esp_sleep.h"
#include "comunicacion.h"

Adafruit_PWMServoDriver pca = Adafruit_PWMServoDriver(0x40);

// Servos en los canales 0, 2, 4, 6 y 8 del PCA9685 (cubiculos 1 a 5)
const int canales[] = {0, 2, 4, 6, 8};
const int PIN_BUZZER = 4;

#define PULSO_MIN 150
#define PULSO_MAX 600

const int ANGULO_CERRADO = 0;
const int ANGULO_ABIERTO = 120;
const unsigned long TIEMPO_SONIDO_MS = 5000;   // buzzer + servo abierto

// Frecuencias de los avisos (ajustalas a lo que mejor suene en tu buzzer)
const int FREQ_AVISO    = 2000;   // aviso de medicacion
const int FREQ_RECIBIDO = 3000;   // asignacion recibida

// 60 = cada "hora" de la app dura 1 minuto (modo prueba). Uso real: 3600
const uint32_t UNIDAD_SEG = 60;

// Cada cuantos segundos despierta como maximo para preguntarle a Java
const uint32_t CONSULTA_SEG = 30;

// Variables que sobreviven al deep sleep
RTC_DATA_ATTR uint32_t reloj = 0;
RTC_DATA_ATTR uint32_t ultimoSueno = 0;
RTC_DATA_ATTR uint32_t proxima[NUM_CUBICULOS]; 
RTC_DATA_ATTR uint32_t intervaloConocido[NUM_CUBICULOS];

int gradosAPulso(int g) {
  return map(g, 0, 180, PULSO_MIN, PULSO_MAX);
}

void moverServo(int canal, int g) {
  pca.setPWM(canal, 0, gradosAPulso(g));
}

void sonarBuzzer(unsigned long duracion) {

  unsigned long inicio = millis();

  while (millis() - inicio < duracion) {
    ledcWriteTone(PIN_BUZZER, FREQ_AVISO);
    delay(250);
    ledcWriteTone(PIN_BUZZER, 0);
    delay(250);
  }

  ledcWriteTone(PIN_BUZZER, 0);
}

void pitidoRecibido() {
  for (int i = 0; i < 3; i++) {
    ledcWriteTone(PIN_BUZZER, FREQ_RECIBIDO);
    delay(200);
    ledcWriteTone(PIN_BUZZER, 0);
    delay(150);
  }
}

void activarCasillero(int i) {
  Serial.printf("Activando cubiculo %d (canal %d)\n", i + 1, canales[i]);
  moverServo(canales[i], ANGULO_ABIERTO);
  sonarBuzzer(TIEMPO_SONIDO_MS);
  moverServo(canales[i], ANGULO_CERRADO);
  delay(600); 
}

void setup() {
  Serial.begin(115200);
  unsigned long t0 = millis();

  Wire.begin(21, 22);
  pca.begin();
  pca.setPWMFreq(50);
  delay(10);

  ledcAttach(PIN_BUZZER, 2000, 8);
  ledcWriteTone(PIN_BUZZER, 0);

  prefs.begin("pastillero", false);

  if (esp_sleep_get_wakeup_cause() != ESP_SLEEP_WAKEUP_TIMER) {
    // Primer arranque (pila nueva o codigo recien subido): se cuenta desde cero
    Serial.println("Primer arranque");
    reloj = 0;
    ultimoSueno = 0;
    for (int i = 0; i < NUM_CUBICULOS; i++) {
      proxima[i] = 0;
      intervaloConocido[i] = 0;
      moverServo(canales[i], ANGULO_CERRADO);
      delay(300);
    }
  } else {
    reloj += ultimoSueno;
    Serial.printf("Desperte, reloj = %u s\n", reloj);
  }

  consultarServidor();

  if (avisoRecibido) {
    pitidoRecibido();
    avisoRecibido = false;
  }

  mostrarResumen();

  for (int i = 0; i < NUM_CUBICULOS; i++) {
    int c = i + 1;
    bool activo = prefs.getBool(("act" + String(c)).c_str(), false);
    uint32_t horas = prefs.getUInt(("int" + String(c)).c_str(), 0);

    if (!activo || horas == 0) {
      proxima[i] = 0;
      intervaloConocido[i] = 0;
      continue;
    }

    if (proxima[i] == 0 || horas != intervaloConocido[i]) {
      intervaloConocido[i] = horas;
      proxima[i] = reloj + horas * UNIDAD_SEG;
    }

  }

  for (int i = 0; i < NUM_CUBICULOS; i++) {
    if (proxima[i] != 0 && reloj >= proxima[i]) {
      activarCasillero(i);
      proxima[i] += intervaloConocido[i] * UNIDAD_SEG;
    }
  }

  // 4) Sumar el tiempo despierto y calcular cuanto dormir
  reloj += (millis() - t0) / 1000;

  uint32_t espera = CONSULTA_SEG;

  for (int i = 0; i < NUM_CUBICULOS; i++) {
    if (proxima[i] != 0) {

      uint32_t falta = (proxima[i] > reloj) ? (proxima[i] - reloj) : 1;

      if (falta < espera) 
        espera = falta;

    }
  }
  ultimoSueno = espera;

  // 5) Apagar todo y dormir
  WiFi.disconnect(true);
  WiFi.mode(WIFI_OFF);

  for (int i = 0; i < NUM_CUBICULOS; i++) {
    pca.setPWM(canales[i], 0, 4096);
  }
  pca.sleep();

  Serial.printf("Durmiendo %u segundos\n", espera);
  Serial.flush();

  esp_sleep_enable_timer_wakeup((uint64_t)espera * 1000000ULL);
  esp_deep_sleep_start();
}

void loop() {
  // No se usa: el ESP32 duerme al final de setup()
}