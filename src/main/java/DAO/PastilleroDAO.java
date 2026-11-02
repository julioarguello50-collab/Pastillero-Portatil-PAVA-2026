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
public class PastilleroDAO implements CRUD<Pastillero> {
    private List objLista;
    private Pastillero objPastillero;
    @Override
    public boolean registrar(Pastillero objeto) {
        String sql = "insert into pastillero(cantidad_cubiculos,id_medico) values(?,?) returning id_pastillero";
        try(Connection conect = new Conexion().conectar();
                PreparedStatement ps = conect.prepareStatement(sql)){
            
            ps.setInt(1,objeto.getCantidadCubiculo());
            ps.setInt(2,objeto.getIdMedico());
            
            try(ResultSet result = ps.executeQuery()){
                if(result.next()){
                    objeto.setIdPastillero(result.getInt(1));
                    return true;
                }
            }
            
            return true;
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
    public Pastillero buscarId(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean update(Pastillero objeto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public List<Pastillero> listarPorMedico(int idMedico){
        objLista = new ArrayList<>();
        
        String sql = "select id_pastillero,cantidad_cubiculos,id_medico from pastillero where id_medico = ?";
        try(Connection conect = new Conexion().conectar();
                PreparedStatement ps = conect.prepareStatement(sql)){
            
            ps.setInt(1, idMedico);
            
            try(ResultSet result = ps.executeQuery()){
                    while(result.next()){
                        objPastillero = new Pastillero();
                        objPastillero.setIdPastillero(result.getInt("id_pastillero"));
                        objPastillero.setCantidadCubiculo(result.getInt("cantidad_cubiculos"));
                        objPastillero.setIdMedico(result.getInt("id_medico"));
                        
                        objLista.add(objPastillero);
                    }
                
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return objLista;
    }
    
}
