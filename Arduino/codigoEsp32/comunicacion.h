#include <WiFi.h>
#include <Preferences.h>

const char* Wifi_casa   = "GALV12345";
const char* Wifi_contrasena   = "MamaMarina2025";
const char* IP_SERVIDOR = "192.168.1.244";   // IP de tu PC (ipconfig)
const uint16_t PUERTO   = 5000;
const int ID_PASTILLERO = 6;                // el id de este pastillero en la base
// ------------------------------
bool avisoRecibido = false;

const char *FIN_MENSAJE = "Fin de la conexion";

const int NUM_CUBICULOS = 5;
Preferences prefs;

bool conectarWiFi() {
  if (WiFi.status() == WL_CONNECTED) 
      return true;

  WiFi.mode(WIFI_STA);
  WiFi.begin(Wifi_casa, Wifi_contrasena);

  unsigned long inicio = millis();

  while (WiFi.status() != WL_CONNECTED && millis() - inicio < 15000) {
    delay(250);
  }

  return WiFi.status() == WL_CONNECTED;
}

void procesarLinea(String linea) {
  linea.trim();
  Serial.print("Recibido: ");
  Serial.println(linea);

  if (linea.startsWith("ASIGNAR;") || linea.startsWith("ACTUALIZAR;")) {
    int p1 = linea.indexOf(';');
    int p2 = linea.indexOf(';', p1 + 1);
    int cubiculo  = linea.substring(p1 + 1, p2).toInt();
    int intervalo = linea.substring(p2 + 1).toInt();

    if (cubiculo >= 1 && cubiculo <= NUM_CUBICULOS && intervalo > 0) {

      prefs.putUInt(("int" + String(cubiculo)).c_str(), intervalo);  // horas
      prefs.putBool(("act" + String(cubiculo)).c_str(), true);       // activo
      avisoRecibido = true;
      Serial.printf("Cubiculo %d asignado, cada %d horas\n", cubiculo, intervalo);
      
    } else {
      Serial.println("Asignacion invalida, se ignora");
    }

  }

  if(linea.startsWith("DESACTIVAR;")){
    int p1 = linea.indexOf(';');
    int cubiculo = linea.substring(p1 + 1).toInt();

    if(cubiculo >= 1 && cubiculo <= NUM_CUBICULOS){
      prefs.putBool(("act" + String(cubiculo)).c_str(),false);
      prefs.putUInt(("int" + String(cubiculo)).c_str(),0);
      avisoRecibido = true;
      Serial.printf("Cubiculo %d desactivado\n",cubiculo);
    }else{
      Serial.printf("Aviso de invalido, se ignora la peticion");
    }
  }

}

void consultarServidor() {

  if (!conectarWiFi()) {
    Serial.println("Sin WiFi");
    return;
  }

  WiFiClient cliente;

  if (!cliente.connect(IP_SERVIDOR, PUERTO)) {
    Serial.println("No se pudo conectar al servidor Java");
    return;
  }

  cliente.println("HOLA;" + String(ID_PASTILLERO));

  unsigned long inicio = millis();

  while (millis() - inicio < 5000) {

    if (cliente.available()) {

      String linea = cliente.readStringUntil('\n');
      linea.trim();

      if (linea == FIN_MENSAJE)
         break;
      
      if (linea.length() > 0) 
        procesarLinea(linea);

    } else if (!cliente.connected()) {
      break;
    }
    delay(10);
  }
  cliente.stop();
}

void mostrarResumen() {

  Serial.println("--- Cubiculos guardados ---");

  for (int i = 1; i <= NUM_CUBICULOS; i++) {

    bool activo = prefs.getBool(("act" + String(i)).c_str(), false);
    uint32_t horas = prefs.getUInt(("int" + String(i)).c_str(), 0);
    Serial.printf("Cubiculo %d: %s, intervalo %u h\n", i, activo ? "ACTIVO" : "libre", horas);

  }

}