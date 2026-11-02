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
public class MedicamentoDAO implements CRUD<Medicamento> {

    @Override
    public boolean registrar(Medicamento objeto) {
        String sql = "INSERT INTO medicamento(nombre_medicamento,tipo_medicamento) values (?,?) returning id_medicamento";
        try(Connection conect = new Conexion().conectar();
                PreparedStatement ps = conect.prepareStatement(sql)){
            
            ps.setString(1,objeto.getNombreMedicamento());
            ps.setString(2,objeto.getTipoMedicamento());
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    objeto.setIdMedicamento(rs.getInt(1));
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
    public Medicamento buscarId(int id) {
        String sql = "SELECT id_medicamento,nombre_medicamento,tipo_medicamento FROM medicamento where id_medicamento = ?";
        
        try(Connection conect = new Conexion().conectar();
                PreparedStatement ps = conect.prepareStatement(sql)){
            
            ps.setInt(1,id);
            try(ResultSet resultado = ps.executeQuery()){
                if(resultado.next()){
                    Medicamento objMedicamento = new Medicamento();
                    objMedicamento.setIdMedicamento(resultado.getInt("id_medicamento"));
                    objMedicamento.setNombreMedicamento(resultado.getString("nombre_medicamento"));
                    objMedicamento.setTipoMedicamento(resultado.getString("tipo_medicamento"));
                    
                    return objMedicamento;
                }
            }
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(Medicamento objeto) {
        String sql = "update medicamento set nombre_medicamento = ?,tipo_medicamento = ? where id_medicamento = ?";
        try(Connection conect = new Conexion().conectar();
                PreparedStatement ps = conect.prepareStatement(sql)){
            
            ps.setInt(3,objeto.getIdMedicamento());
            ps.setString(1,objeto.getNombreMedicamento());
            ps.setString(2,objeto.getTipoMedicamento());
            
            return ps.executeUpdate() > 0;
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
