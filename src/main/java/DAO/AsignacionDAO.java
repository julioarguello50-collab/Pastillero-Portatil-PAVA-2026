/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import java.sql.*;
import java.util.*;
import model.*;
import java.time.LocalDate;
/**
 *
 * @author argue
 */
public class AsignacionDAO implements CRUD<Asignacion> {
    private Asignacion objAsignacion;
    java.sql.Date fechaFin;
    @Override
    public boolean registrar(Asignacion objeto) {
        String sql = "INSERT INTO asignacion(dosis_miligramos,inntervalo_horas,fecha_inicio,fecha_fin,id_medico,id_medicamento,id_cubiculo)"
                + "values (?,?,?,?,?,?,?)";
        try(Connection conect = new Conexion().conectar();
                PreparedStatement ps = conect.prepareStatement(sql)){
                
                ps.setDouble(1,objeto.getDosisMiligramos());
                ps.setInt(2,objeto.getIntervaloHoras());
                ps.setDate(3,java.sql.Date.valueOf(objeto.getFechaInicio()));
                
                if(objeto.getFechaFin() == null){
                    ps.setNull(4,Types.DATE);
                }else{
                    ps.setDate(4,java.sql.Date.valueOf(objeto.getFechaFin()));    
                }
                
                ps.setInt(5,objeto.getIdMedico());
                ps.setInt(6,objeto.getIdMedicamento());
                ps.setInt(7,objeto.getIdCubiculo());
                
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
    public Asignacion buscarId(int id) {
        return null;//no olvidar
    }

    @Override
    public boolean update(Asignacion objeto) {
        String sql = "update asignacion set dosis_miligramos = ?,inntervalo_horas = ? where id_asignacion = ?";
        try(Connection conect = new Conexion().conectar();
                PreparedStatement ps = conect.prepareStatement(sql)){
            ps.setInt(3,objeto.getIdAsignacion());
            ps.setDouble(1, objeto.getDosisMiligramos());
            ps.setInt(2, objeto.getIntervaloHoras());
            
            return ps.executeUpdate() > 0;
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    } 

    @Override
    public boolean delete(int id) {
        String sql = "update asignacion set fecha_fin = ? where id_asignacion = ?";
        try(Connection conect = new Conexion().conectar();
                PreparedStatement ps = conect.prepareStatement(sql)){
            ps.setInt(2, id);
            ps.setDate(1,java.sql.Date.valueOf(LocalDate.now()));
            
            return ps.executeUpdate() > 0;
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    
    public Asignacion busquedaActivaCubiculo(int id){
        String sql = "select id_asignacion,dosis_miligramos,inntervalo_horas,fecha_inicio, "+
                "fecha_fin,id_medico,id_medicamento,id_cubiculo from asignacion where id_cubiculo = ? and fecha_fin is null";
        
        try(Connection conect = new Conexion().conectar();
                PreparedStatement ps = conect.prepareStatement(sql)){
            
            ps.setInt(1, id);
            try(ResultSet result = ps.executeQuery()){
                if(result.next()){
                    objAsignacion = new Asignacion();
                    objAsignacion.setIdAsignacion(result.getInt("id_asignacion"));
                    objAsignacion.setDosisMiligramos(result.getDouble("dosis_miligramos"));
                    objAsignacion.setIntervaloHoras(result.getInt("inntervalo_horas"));
                    objAsignacion.setFechaInicio(result.getDate("fecha_inicio").toLocalDate());
                    
                    fechaFin = result.getDate("fecha_fin");
                    if(fechaFin != null){
                        objAsignacion.setFechaFin(fechaFin.toLocalDate());
                    }
                    
                    objAsignacion.setIdMedico(result.getInt("id_medico"));
                    objAsignacion.setIdMedicamento(result.getInt("id_medicamento"));
                    objAsignacion.setIdCubiculo(result.getInt("id_cubiculo"));
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        
        return objAsignacion;
    }
    
    public boolean cubiculoOcupado(int idCubiculo){
        String sql = "select 1 from asignacion where id_cubiculo = ? and fecha_fin is null limit 1";
        try(Connection conect = new Conexion().conectar();
                PreparedStatement ps = conect.prepareStatement(sql)){
            ps.setInt(1, idCubiculo);
            
            try(ResultSet result = ps.executeQuery()){
                return result.next();
            }
            
        }catch(SQLException e){
            e.printStackTrace();
            return true;
        }
    }
    
}
