/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.*;
import model.*;
import view.*;
import DAO.*;
import java.time.LocalDate;
import javax.swing.*;
import java.util.*;

/**
 *
 * @author argue
 */
public class AsignacionCubiculoController implements ActionListener {
    private AsignacionCubiculo objVista;
    private Medicamento objMedicamento;
    private MenuPrincipal objMenu;
    private CubiculoDAO objDao;
    private AsignacionDAO objAsignacion;
    private Cubiculo objCubiculo;
    private Asignacion objAsignar;
    private List<Pastillero> objLista;
    private String cubiculoSeleccionado;
    private int intervaloHoras,numeroCubiculo,idPastillero;
    
    public AsignacionCubiculoController(AsignacionCubiculo objCubiculo,Medicamento objMedicamento,MenuPrincipal objMenu){
        this.objVista = objCubiculo;
        this.objMedicamento = objMedicamento;
        this.objMenu = objMenu;
        
        this.llenarPastillero();
        this.llenarCubiculos();
        
        objVista.jButton1.addActionListener(this);
        objVista.jButton2.addActionListener(this);
        objVista.jComboBox2.addActionListener(this);
        
        this.mostrarDatos();
        this.windowListener();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == objVista.jButton1){
            if(!this.guardarDatos()){
                return;
            }
        }
        
        if(e.getSource() == objVista.jButton2){
            this.iniciarVentanaMenu();
        }
        
        if(e.getSource() == objVista.jComboBox2){
            this.llenarCubiculos();
        }
    }
    
    public void mostrarDatos(){
        objVista.jTextField1.setText(String.valueOf(objMedicamento.getNombreMedicamento()));
        objVista.jTextField1.setEditable(false);
        objVista.jTextField2.setText(String.valueOf(objMedicamento.getMiligramos()));
        objVista.jTextField2.setEditable(false);
    }
    
    public boolean guardarDatos(){
        objAsignacion = new AsignacionDAO();
        idPastillero = objLista.get(objVista.jComboBox2.getSelectedIndex()).getIdPastillero();
        cubiculoSeleccionado = (String) objVista.jComboBox1.getSelectedItem();
        numeroCubiculo = Integer.parseInt(cubiculoSeleccionado.replace("Cubiculo ","").trim());
        intervaloHoras = (Integer) objVista.jSpinner1.getValue();
        
        if(this.buscarCubiculo(idPastillero, numeroCubiculo) == null){
            JOptionPane.showMessageDialog(null,"Este cubiculo no se encuentra en el pastillero");
            return false;
        }
        
        if(objAsignacion.cubiculoOcupado(objCubiculo.getIdCubiculo())){
            JOptionPane.showMessageDialog(null,"Este cubiculo ya esta ocupado");
            return false;
        }

        objAsignar = new Asignacion();
        objAsignar.setIntervaloHoras(intervaloHoras);
        objAsignar.setDosisMiligramos(objMedicamento.getMiligramos());
        objAsignar.setIdMedico(objMenu.getMedico().getIdMedico());
        objAsignar.setIdMedicamento(objMedicamento.getIdMedicamento());
        objAsignar.setIdCubiculo(objCubiculo.getIdCubiculo());
        objAsignar.setFechaInicio(LocalDate.now());
        objAsignar.setFechaFin(null);
        
        objAsignacion.registrar(objAsignar);
        
        this.guardarEsp32(numeroCubiculo, intervaloHoras);
        return true;
    }
    
    public Cubiculo buscarCubiculo(int id_pastillero,int numero_cubiculo){
        objDao = new CubiculoDAO();
        objCubiculo = new Cubiculo();
        
        objCubiculo = objDao.buscarPorIdPastilleroYNumeroCubiculo(id_pastillero, numeroCubiculo);
        
        return objCubiculo;
    }
    
    public void llenarPastillero(){
        objLista = new PastilleroDAO().listarPorMedico(objMenu.getMedico().getIdMedico());
        DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<>();
        
        for(Pastillero p: objLista){
            modelo.addElement(p.toString());
        }
        
        objVista.jComboBox2.setModel(modelo);
    }
    
    public void llenarCubiculos(){
        int cantidadPastilleros;
        int indice = objVista.jComboBox2.getSelectedIndex();
        DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<>();
        if(indice < 0){
            return;
        }
        
        cantidadPastilleros = objLista.get(indice).getCantidadCubiculo();
        for (int i = 1; i <= cantidadPastilleros; i++) {
            modelo.addElement("Cubiculo " + i);
        }
        
        objVista.jComboBox1.setModel(modelo);
    }
    
    public boolean guardarEsp32(int numeroCubiculo,int intervaloHoras){
        boolean enviar;
        enviar = ConexionEsp32.getInstancia().enviarAsignacion(idPastillero, numeroCubiculo, intervaloHoras);
            
        if(!enviar){
            JOptionPane.showMessageDialog(null,"Asignacion guardada, pero no se pudo enviar al pastillero");
            return false;
        }
        
        JOptionPane.showMessageDialog(null,"Asignacion guardad y enviada al pastillero");
        return true;
    }
    
    public void iniciarVentanaMenu(){
        objMenu.setVisible(true);
        objVista.dispose();
    }
    
    private void windowListener(){
        this.objVista.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent event){
                objMenu.setVisible(true);
            }
        });
        
    }
        
}
