/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import java.sql.*;
import java.util.ArrayList;
import model.*;
/**
 *
 * @author argue
 */
public class MedicoDAO implements CRUD<Medico> {

    @Override
    public boolean registrar(Medico objetoMedico) {
        String sqlInsert = "INSERT INTO medico(nombre,ap_paterno,ap_materno,usuario,"
                + "fecha_nacimiento,carrera,cedula,contrasena) values(?,?,?,?,?,?,?,?)";
        try(Connection conectar = new Conexion().conectar();
              PreparedStatement ps = conectar.prepareStatement(sqlInsert)  ){
            
            ps.setString(1,objetoMedico.getNombre());
            ps.setString(2,objetoMedico.getApPaterno());
            ps.setString(3,objetoMedico.getApMaterno());
            ps.setString(4,objetoMedico.getUsuario());
            ps.setDate(5,Date.valueOf(objetoMedico.getFechaNacimiento()));
            ps.setString(6,objetoMedico.getCarrera());
            ps.setString(7,objetoMedico.getCedula());
            ps.setString(8,objetoMedico.getPassword());
            
            return ps.executeUpdate() > 0;
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ArrayList listar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Medico buscarId(int id) {
        String sql = "SELECT id_medico,nombre,ap_paterno,ap_materno,usuario,"
                + "fecha_nacimiento,carrera,cedula,contrasena FROM medico where id_medico = ?";
        
        try(Connection conect = new Conexion().conectar();
                PreparedStatement ps = conect.prepareStatement(sql)){
            
            ps.setInt(1, id);
            try(ResultSet resultado = ps.executeQuery()){
                if(resultado.next()){
                    Medico objMedico = new Medico();
                    objMedico.setIdMedico(resultado.getInt("id_medico"));
                    objMedico.setNombre(resultado.getString("nombre"));
                    objMedico.setApPaterno(resultado.getString("ap_paterno"));
                    objMedico.setApMaterno(resultado.getString("ap_materno"));
                    objMedico.setUsuario(resultado.getString("usuario"));
                    objMedico.setFechaNacimiento(resultado.getDate("fecha_nacimiento").toLocalDate());
                    objMedico.setCarrera(resultado.getString("carrera"));
                    objMedico.setCedula(resultado.getString("cedula"));
                    objMedico.setPassword(resultado.getString("contrasena"));
                    
                    return objMedico;
                }
            }
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(Medico objeto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public Medico buscarUsuario(String usuario){
        String sql = "SELECT id_medico,nombre,ap_paterno,ap_materno,usuario,"
                + "fecha_nacimiento,carrera,cedula,contrasena FROM medico where usuario = ?";
        
        
        try(Connection conect = new Conexion().conectar();
                PreparedStatement ps = conect.prepareStatement(sql)){
            
            ps.setString(1, usuario);
            try(ResultSet resultado = ps.executeQuery()){
                if(resultado.next()){
                    Medico objMedico = new Medico();
                    objMedico.setIdMedico(resultado.getInt("id_medico"));
                    objMedico.setNombre(resultado.getString("nombre"));
                    objMedico.setApPaterno(resultado.getString("ap_paterno"));
                    objMedico.setApMaterno(resultado.getString("ap_materno"));
                    objMedico.setUsuario(resultado.getString("usuario"));
                    objMedico.setFechaNacimiento(resultado.getDate("fecha_nacimiento").toLocalDate());
                    objMedico.setCarrera(resultado.getString("carrera"));
                    objMedico.setCedula(resultado.getString("cedula"));
                    objMedico.setPassword(resultado.getString("contrasena"));
                    
                    return objMedico;
                }
            }
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
   
    
}
