/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import model.*;
import controller.*;
import view.*;
import DAO.*;
import java.time.LocalDate;
/**
 *
 * @author argue
 */
public class DesactivarCubiculoController implements ActionListener{
    private DesactivarCubiculo objVista;
    private MenuPrincipal objMenu;
    private List<Pastillero> objListaPastillero;
    private Asignacion asignacionActual;
    private Medicamento medicamentoActual;
    private Cubiculo objCubiculo;
    private AsignacionDAO objDao;
    
    private int idPastillero,numero;
    
    public DesactivarCubiculoController(DesactivarCubiculo objVista,MenuPrincipal objMenu){
        this.objVista = objVista;
        this.objMenu = objMenu;
        
        this.llenarPastillero();
        this.llenarCubiculos();
        this.cargarValores();
        
        objVista.jButton2.addActionListener(this);
        objVista.jButton3.addActionListener(this);
        objVista.jComboBox1.addActionListener(this);
        objVista.jComboBox2.addActionListener(this);
        
        this.windowListener();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == objVista.jComboBox2){
            this.llenarCubiculos();
            this.cargarValores();
        }
        
        if(e.getSource() == objVista.jComboBox1){
            this.cargarValores();
        }
        
        if(e.getSource() == objVista.jButton3){
            this.iniciarVentanaMenu();
        }
        
        if(e.getSource() == objVista.jButton2){
            this.desactivarCubiculo();
        }
    }
    
    public void llenarPastillero(){
        objListaPastillero = new PastilleroDAO().listarPorMedico(objMenu.getMedico().getIdMedico());
        DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<>();
        
        for(Pastillero p: objListaPastillero){
            modelo.addElement(p.toString());
        }
        
        objVista.jComboBox2.setModel(modelo);//del pastillero
    }
    
    public void llenarCubiculos(){
        int indice = objVista.jComboBox2.getSelectedIndex();
        int cantidadPastillero;
        
        DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<>();
        if(indice < 0){
            return;
        }
        
        cantidadPastillero = objListaPastillero.get(indice).getCantidadCubiculo();
        
        for(int i = 1; i <= cantidadPastillero;i++){
            modelo.addElement("Cubiculo "+i);
        }
        
        objVista.jComboBox1.setModel(modelo);
    }
    
    public void cargarValores(){
        asignacionActual = null;
        medicamentoActual = null;
      
        objCubiculo = this.buscarCubiculo();
        if(objCubiculo == null){
            this.limpiarCampos();
            return;
        }
        
        asignacionActual = new AsignacionDAO().busquedaActivaCubiculo(objCubiculo.getIdCubiculo());
        if(asignacionActual == null){
            this.limpiarCampos();
            return;
        }
        
        medicamentoActual = new MedicamentoDAO().buscarId(asignacionActual.getIdMedicamento());
        if(medicamentoActual == null){
            this.limpiarCampos();
            return;
        }
        
        objVista.jTextField1.setText(medicamentoActual.getNombreMedicamento());
        objVista.jTextField2.setText(medicamentoActual.getTipoMedicamento());
        objVista.jTextField3.setText(String.valueOf(asignacionActual.getDosisMiligramos()));
        objVista.jTextField4.setText(String.valueOf(asignacionActual.getIntervaloHoras()));
    }
    
    private Cubiculo buscarCubiculo(){
        int indice = objVista.jComboBox2.getSelectedIndex();
        String texto = (String) objVista.jComboBox1.getSelectedItem();
        
        if(indice < 0 || texto == null){
            return null;
        }
        
        idPastillero = objListaPastillero.get(indice).getIdPastillero();
        
        numero = Integer.parseInt(texto.replace("Cubiculo ", "").trim());
        
        return new CubiculoDAO().buscarPorIdPastilleroYNumeroCubiculo(idPastillero, numero);
    }
    
    
    public void desactivarCubiculo(){
        if(asignacionActual == null){
            JOptionPane.showMessageDialog(null, "Este cubiculo no tiene ninguna asignacion");
            return;
        }
        
        boolean band =  new AsignacionDAO().delete(asignacionActual.getIdAsignacion());
        boolean enviar = ConexionEsp32.getInstancia().enviarDesactivacion(idPastillero,numero);
        
        if(band && enviar){
            this.limpiarCampos();
           JOptionPane.showMessageDialog(null, "Cubiculo desactivado");
        }else{
            JOptionPane.showMessageDialog(null,"No se pudo desactivar el cubiculo");
        }
    }
    
    public void limpiarCampos(){
        objVista.jTextField1.setText("");
        objVista.jTextField2.setText("");
        objVista.jTextField3.setText("");
        objVista.jTextField4.setText("");
    }
    
    public void iniciarVentanaMenu(){
        objMenu.setVisible(true);   
        objVista.dispose();
    }
        
    private void windowListener(){
        this.objVista.addWindowListener(new java.awt.event.WindowAdapter(){
            @Override
            public void windowClosing(java.awt.event.WindowEvent envent){
                objMenu.setVisible(true);
            }
        });
    }
    
}
