/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author argue
 */
public class ConexionEsp32 {
   private static final int PUERTO = 5000;
   private static ConexionEsp32 objConexion;

   private final Map<Integer, Queue<String>> pendientes = new ConcurrentHashMap<>();
   private boolean activo = false;

    public static synchronized ConexionEsp32 getInstancia() {
        if (objConexion == null)
            objConexion = new ConexionEsp32();
        
        
        return objConexion;
    }

    public synchronized void iniciar() {
        if (activo) 
            return;
        activo = true;
        
        Thread hilo = new Thread(() -> {
            try (ServerSocket servidor = new ServerSocket(PUERTO)) {
                
                System.out.println("Servidor ESP32 escuchando en puerto " + PUERTO);
                
                while (true) {
                    Socket socket = servidor.accept();
                    atender(socket);
                }
                
            } catch (IOException e) {
                System.out.println("Error en servidor ESP32: " + e.getMessage());
                activo = false;
            }
            
        }, "servidor-esp32");
        
        hilo.setDaemon(true);
        hilo.start();
    }

    private void atender(Socket socket) {
        String señalEsp,aviso;
        int idPastillero;
        
        try (Socket objSocket = socket;
             BufferedReader in = new BufferedReader(new InputStreamReader(objSocket.getInputStream()));
             PrintWriter out = new PrintWriter(objSocket.getOutputStream(), true)) {

            objSocket.setSoTimeout(5000);
            señalEsp = in.readLine();
            
            if (señalEsp == null || !señalEsp.startsWith("HOLA;")) 
                return;

            idPastillero = Integer.parseInt(señalEsp.split(";")[1].trim());
            
            Queue<String> cola = pendientes.get(idPastillero);

            while (cola != null && (aviso = cola.poll()) != null) {
                out.println(aviso);
                System.out.println("Enviado al ESP32 " + idPastillero + ": " + aviso);
            }
            
            out.println("Fin de la conexion");

        } catch (IOException | NumberFormatException e) {
            System.out.println("Error de conexion ESP32: " + e.getMessage());
        }
    }

    public boolean enviarAsignacion(int idPastillero, int numeroCubiculo, int intervaloHoras) {
        return pendientes
            .computeIfAbsent(idPastillero, k -> new ConcurrentLinkedQueue<>())
            .add("ASIGNAR;" + numeroCubiculo + ";" + intervaloHoras);
    }
    
    public boolean enviarActualizacion(int idPastillero, int numeroCubiculo, int intervaloHoras) {
        return pendientes
        .computeIfAbsent(idPastillero, k -> new ConcurrentLinkedQueue<>())
        .add("ACTUALIZAR;" + numeroCubiculo + ";" + intervaloHoras);
    }
    
    public boolean enviarDesactivacion(int idPastillero, int numeroCubiculo) {
    return pendientes
        .computeIfAbsent(idPastillero, k -> new ConcurrentLinkedQueue<>())
        .add("DESACTIVAR;" + numeroCubiculo);
    }
}
