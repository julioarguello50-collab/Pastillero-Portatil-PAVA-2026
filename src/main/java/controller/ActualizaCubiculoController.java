/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import model.*;
import view.*;
import DAO.*;

/**
 *
 * @author argue
 */
public class ActualizaCubiculoController implements ActionListener {
    private ActualizarCubiculo objVista;
    private MenuPrincipal objMenu;
    private PastilleroDAO objPastilleroD;
    private List<Pastillero> objListaPastillero;
    private Pastillero objPastillero;
    private Cubiculo objCubiculo;
    private Asignacion asignacionActual;
    private Medicamento medicamentoActual;
    private Validador objValidador;
    
    private int indicePastillero,cubiculo,idPastillero;
    private String cubiculoSeleccionado;
    public ActualizaCubiculoController(ActualizarCubiculo objVista,MenuPrincipal objMenu){
        this.objVista = objVista;
        this.objMenu = objMenu;
        objValidador = new Validador();
        
        this.llenarPastillero();
        this.llenarCubiculos();
        this.cargarValores();
        
        objVista.jButton2.addActionListener(this);
        objVista.jButton3.addActionListener(this);
        objVista.jComboBox3.addActionListener(this);
        objVista.jComboBox2.addActionListener(this);
        objVista.jButton3.setEnabled(true);
        
        this.windowListener();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == objVista.jComboBox3){
            this.llenarCubiculos();
           
            
            this.cargarValores();
        }
        
        if(e.getSource() == objVista.jComboBox2){
            this.cargarValores();
        }
        
        if(e.getSource() == objVista.jButton3){
            this.iniciarVentanaMenu();
        }
        
        if(e.getSource() == objVista.jButton2){
            this.actualizarDatos();
        }
    }
    
    //metodos para ingresar los nuevos datos
    
    public void actualizarDatos(){
        boolean actualizarMedicamento,actualizarAsignacion,enviar;
        if(asignacionActual == null || medicamentoActual == null){
            return;
        }
        
        if(!this.comprobarCampos())
            return;
        
        medicamentoActual.setNombreMedicamento(objVista.jTextField1.getText());
        medicamentoActual.setTipoMedicamento(objVista.jTextField2.getText());
        asignacionActual.setDosisMiligramos(Double.parseDouble(objVista.jTextField3.getText()));
        asignacionActual.setIntervaloHoras((Integer)objVista.jSpinner1.getValue());
        
        actualizarMedicamento = new MedicamentoDAO().update(medicamentoActual);
        actualizarAsignacion = new AsignacionDAO().update(asignacionActual);
        
        if(!actualizarMedicamento || !actualizarAsignacion){
            JOptionPane.showMessageDialog(null,"No se puedo ingresar la actualizacion en la bsd");
            return;
        }
        enviar = ConexionEsp32.getInstancia().enviarActualizacion(idPastillero,objCubiculo.getNumeroCubiculo(),(Integer)objVista.jSpinner1.getValue());
        
        if(!enviar){
            JOptionPane.showMessageDialog(null,"No se pudo conectar con el esp32");
        }
        
        JOptionPane.showMessageDialog(null,"Actualizacion existosa");
    }
    
    //metodos para buscar cubiculos e imprimir sus datos
    
    public void llenarPastillero(){
        objListaPastillero = new PastilleroDAO().listarPorMedico(objMenu.getMedico().getIdMedico());
        DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<>();
        
        for(Pastillero p: objListaPastillero){
            modelo.addElement(p.toString());
        }
        
        objVista.jComboBox3.setModel(modelo);
    }
    
    public void llenarCubiculos(){
        int indice = objVista.jComboBox3.getSelectedIndex();
        int cantidadPastilleros;
        
        DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<>();
        if(indice < 0){
            return;
        }
        
        cantidadPastilleros = objListaPastillero.get(indice).getCantidadCubiculo();
        
        for(int i = 1; i <= cantidadPastilleros;i++){
            modelo.addElement("Cubiculo "+ i);
        }
        
        objVista.jComboBox2.setModel(modelo);
    }
    
    public void cargarValores() {
        asignacionActual = null;
        medicamentoActual = null;

        objCubiculo = this.buscarCubiculo();
        if (objCubiculo == null) {
            this.limpiarCampos();
            return;
        }

        asignacionActual = new AsignacionDAO().busquedaActivaCubiculo(objCubiculo.getIdCubiculo());
        if (asignacionActual == null) {
            this.limpiarCampos();
            return;
        }

        medicamentoActual = new MedicamentoDAO().buscarId(asignacionActual.getIdMedicamento());
        if (medicamentoActual == null) {
            this.limpiarCampos();
            return;
        }

        objVista.jTextField1.setText(medicamentoActual.getNombreMedicamento());
        objVista.jTextField2.setText(medicamentoActual.getTipoMedicamento());
        objVista.jTextField3.setText(String.valueOf(asignacionActual.getDosisMiligramos()));
        objVista.jSpinner1.setValue(asignacionActual.getIntervaloHoras());
        objVista.jButton2.setEnabled(true);
    }   

    private Cubiculo buscarCubiculo() {
        int numero;
        int indice = objVista.jComboBox3.getSelectedIndex();
        String texto = (String) objVista.jComboBox2.getSelectedItem();
        if (indice < 0 || texto == null) {
            return null;
        }

        idPastillero = objListaPastillero.get(indice).getIdPastillero();
        numero = Integer.parseInt(texto.replace("Cubiculo ", "").trim());
        return new CubiculoDAO().buscarPorIdPastilleroYNumeroCubiculo(idPastillero, numero);
    }   
    
    //metodos extras para la ventana
    
    public void limpiarCampos(){
        objVista.jTextField1.setText("");
        objVista.jTextField2.setText("");
        objVista.jTextField3.setText("");
        objVista.jSpinner1.setValue(1);
        objVista.jButton2.setEnabled(false);
    }
    
    public boolean comprobarCampos(){
        String[] arregloCadena,arregloNumero;
        arregloCadena = new String[2];
        arregloNumero = new String[1];
        
        arregloCadena[0] = objVista.jTextField1.getText();
        arregloCadena[1] = objVista.jTextField2.getText();
        arregloNumero[0] = objVista.jTextField3.getText();

        if(!objValidador.comprobarCamposTexto(arregloCadena))
            return false;
                
        if(!objValidador.comprobarCamposNumericos(arregloNumero))
            return false;
        
        return true;
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
