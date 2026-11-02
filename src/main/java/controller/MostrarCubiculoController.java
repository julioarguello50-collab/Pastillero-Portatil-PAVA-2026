/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import model.*;
import view.*;
import DAO.*;
/**
 *
 * @author argue
 */
public class MostrarCubiculoController implements ActionListener{
    private MostrarCubiculo objVista;
    private MenuPrincipal objMenu;
    private List<Pastillero> objListaPastillero;
    private Asignacion asignacionActual;
    private Medicamento medicamentoActual;
    private Cubiculo objCubiculo;
    
    public MostrarCubiculoController(MostrarCubiculo objVista,MenuPrincipal objMenu){
        this.objVista = objVista;
        this.objMenu = objMenu;
        
        this.llenarPastillero();
        this.llenarCubiculos();
        this.cargarValores();
        
        objVista.jButton2.addActionListener(this);
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
        
        if(e.getSource() == objVista.jButton2){
            this.iniciarVentanaMenu();
        }
    }
    
    public void llenarPastillero(){
        objListaPastillero = new PastilleroDAO().listarPorMedico(objMenu.getMedico().getIdMedico());
        DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<>();
        
        for(Pastillero p: objListaPastillero){
            modelo.addElement(p.toString());
        }
        
        objVista.jComboBox2.setModel(modelo);
    }
    
    public void llenarCubiculos(){
        int indice = objVista.jComboBox2.getSelectedIndex();
        int cantidadPastilleros;
        
        DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<>();
        
        if(indice < 0){
            return;
        }
        
        cantidadPastilleros = objListaPastillero.get(indice).getCantidadCubiculo();
        
        for(int i = 1; i <= cantidadPastilleros;i++){
            modelo.addElement("Cubiculo "+i);
        }
        
        objVista.jComboBox1.setModel(modelo);
    }
    
    public void cargarValores(){
        medicamentoActual = null;
        asignacionActual = null;
        
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
        objVista.jTextField4.setText(String.valueOf(asignacionActual.getIdCubiculo()));
        objVista.jTextField5.setText(String.valueOf(asignacionActual.getIntervaloHoras()));
    }
    
    public Cubiculo buscarCubiculo(){
        int indice = objVista.jComboBox2.getSelectedIndex();
        String texto = (String) objVista.jComboBox1.getSelectedItem();
        int idPastillero,numero;
        
        if(indice < 0 || texto == null){
            return null;
        }
        
        idPastillero = objListaPastillero.get(indice).getIdPastillero();
        numero = Integer.parseInt(texto.replace("Cubiculo ","").trim());
        return new CubiculoDAO().buscarPorIdPastilleroYNumeroCubiculo(idPastillero, numero);
    }
    
    public void limpiarCampos(){
        objVista.jTextField1.setText("");
        objVista.jTextField2.setText("");
        objVista.jTextField3.setText("");
        objVista.jTextField4.setText("");
        objVista.jTextField5.setText("");
    }
    
    public void iniciarVentanaMenu(){
        objMenu.setVisible(true);
    }
    
    private void windowListener(){
        this.objVista.addWindowListener(new java.awt.event.WindowAdapter(){
            @Override
            public void windowClosing(java.awt.event.WindowEvent event){
            objMenu.setVisible(true);
            }
        });
    }
}
