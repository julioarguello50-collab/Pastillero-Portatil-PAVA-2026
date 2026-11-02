/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.nio.charset.StandardCharsets;
import java.security.*;

/**
 *
 * @author argue
 */
public class SeguridadContrasena {
    public String hashear(String contrasena){
        try{
            MessageDigest mensaje = MessageDigest.getInstance("SHA-256");   
            byte[] hash = mensaje.digest(contrasena.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        }catch(NoSuchAlgorithmException e){
            throw new RuntimeException(e);
        }
    } 
    
    public boolean verificar(String contrasenaEscrita, String hashGuardado){
        return this.hashear(contrasenaEscrita).equalsIgnoreCase(hashGuardado);
    }
}
