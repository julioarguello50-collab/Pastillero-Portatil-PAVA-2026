/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.sql.*;
/**
 *
 * @author argue
 */
public class Conexion {
    private String URL = "jdbc:postgresql://localhost:5432/pastillero_portatil";
    private String usuario = "soporte";
    private String PASSWORD = "unach2026";
    
    public Connection conectar() throws SQLException{
        Connection conect;
        
        try{
            Class.forName("org.postgresql.Driver");
            conect = DriverManager.getConnection(URL,usuario,PASSWORD);
            
            System.out.println("Conexion exitosa");
        }catch(ClassNotFoundException e){
            throw new SQLException("Driver no encontrado: "+e.getMessage());
        }
        return conect;
    }
}
