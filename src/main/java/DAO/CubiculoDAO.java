/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import java.sql.*;
import java.util.*;
import model.*;

/**
 *
 * @author argue
 */
public class CubiculoDAO implements CRUD<Cubiculo> {
    private List objLista;
    private Cubiculo objCubiculo;
    @Override
    public boolean registrar(Cubiculo objeto) {
        String sql = "INSERT INTO cubiculo (numero_cubiculo,id_pastillero) values (?,?)";
        try(Connection conect = new Conexion().conectar();
                PreparedStatement ps = conect.prepareStatement(sql)){
           
            ps.setInt(1,objeto.getNumeroCubiculo());
            ps.setInt(2, objeto.getIdPastillero());
            
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
    public Cubiculo buscarId(int id) {
        String sql = "SELECT id_cubiculo,numero_cubiculo,id_pastillero from cubiculo where id_cubiculo = ?";
        
        try(Connection conect = new Conexion().conectar();
                PreparedStatement ps = conect.prepareStatement(sql)){
            
            ps.setInt(1,id);
            try(ResultSet result = ps.executeQuery()){
                if(result.next()){
                    Cubiculo objCubiculo = new Cubiculo();
                    
                    objCubiculo.setIdCubiculo(result.getInt("id_cubiculo"));
                    objCubiculo.setNumeroCubiculo(result.getInt("numero_cubiculo"));
                    objCubiculo.setIdPastillero(result.getInt("id_pastillero"));
                    
                    return objCubiculo;
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(Cubiculo objeto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public List<Cubiculo> listarPorPastillero(int id_pastillero){
        objLista = new ArrayList<>();
        
        String sql = "select id_cubiculo,numero_cubiculo,id_pastillero, from cubiculo where id_pastillero = ?";
        
        try(Connection conect = new Conexion().conectar();
                PreparedStatement ps = conect.prepareStatement(sql)){
                ps.setInt(1,id_pastillero);
                
                try(ResultSet result = ps.executeQuery()){
                    if(result.next()){
                        while(result.next()){
                            objCubiculo = new Cubiculo();
                            objCubiculo.setIdCubiculo(result.getInt("id_cubiculo"));
                            objCubiculo.setNumeroCubiculo(result.getInt("numero_cubiculo"));
                            objCubiculo.setIdPastillero(result.getInt("id_pastillero"));
                            
                            objLista.add(objCubiculo);
                        }
                    }
                }
        }catch(SQLException e){
            e.printStackTrace();
        }
        
        return objLista;
    }
    
    
    public Cubiculo buscarPorIdPastilleroYNumeroCubiculo(int id_pastillero,int numero_cubiculo){
        String sql = "select id_cubiculo,numero_cubiculo,id_pastillero from cubiculo where id_pastillero = ? and numero_cubiculo = ?";
        try(Connection conect = new Conexion().conectar();
                PreparedStatement ps = conect.prepareStatement(sql)){
                ps.setInt(1, id_pastillero);
                ps.setInt(2, numero_cubiculo);
                
                try(ResultSet result = ps.executeQuery()){
                    if(result.next()){
                        Cubiculo objCubiculo = new Cubiculo();
                        objCubiculo.setIdCubiculo(result.getInt("id_cubiculo"));
                        objCubiculo.setNumeroCubiculo(result.getInt("numero_cubiculo"));
                        objCubiculo.setIdPastillero(result.getInt("id_pastillero"));
                        
                        return objCubiculo;
                    }
                }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    
}
