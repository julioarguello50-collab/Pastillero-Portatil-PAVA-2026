/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.*;
import view.*;
import model.*;
import DAO.*;
import javax.swing.*;

/**
 *
 * @author argue
 */
public class NuevoMedicamentoController implements ActionListener {
    private MenuPrincipal objMenu;
    private NuevoMedicamento objVista;
    private AsignacionCubiculo objCubiculo;
    private Medicamento objMedicamento;
    private Validador objValidador;
    private MedicamentoDAO objDao;
    
    private String[] arrayCadena,arrayNumeros;
    
    public NuevoMedicamentoController(NuevoMedicamento objVista,MenuPrincipal objMenu){
        this.objVista = objVista;
        this.objMenu = objMenu;
        
        objVista.jButton1.addActionListener(this);
        objVista.jButton2.addActionListener(this);
        
        objValidador = new Validador();
        
        this.windowListener();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.configurarComandos();
        if(e.getSource() == objVista.jButton1){
            if(!this.comprobarCampos())
                return;
            
            if(!this.ingresarDatos()){
                JOptionPane.showMessageDialog(null,"No se puedo registrar el medicamento");
                return;
            }
            
            this.limpiarCampos();
            this.iniciarVentanaCubiculo();
        }
        
        if(e.getSource() == objVista.jButton2){
            this.iniciarVentanaMenu();
        }
    }
    
    public boolean comprobarCampos(){
        arrayCadena = new String[2];
        arrayNumeros = new String[1];
        
        arrayCadena[0] = objVista.jTextField1.getText();
        arrayCadena[1] = objVista.jTextField2.getText();
        arrayNumeros[0] = objVista.jTextField3.getText();
        
        if(!objValidador.comprobarCamposTexto(arrayCadena))
            return false;
        
        if(!objValidador.comprobarCamposNumericos(arrayNumeros))
            return false;
        
        return true;
    }
    
    public boolean ingresarDatos(){
        objMedicamento = new Medicamento();
        objMedicamento.setNombreMedicamento(objVista.jTextField1.getText());
        objMedicamento.setTipoMedicamento(objVista.jTextField2.getText());
        objMedicamento.setMiligramos(Double.parseDouble(objVista.jTextField3.getText()));
        
        objDao = new MedicamentoDAO();
        return objDao.registrar(objMedicamento);
    }
    
    public void limpiarCampos(){
        objVista.jTextField1.setText("");
        objVista.jTextField2.setText("");
        objVista.jTextField3.setText("");
    }
    
    public void configurarComandos(){
        objVista.jButton1.setActionCommand("Cubiculo");
        objVista.jButton2.setActionCommand("Regresar");
    }
    
    public void iniciarVentanaCubiculo(){
        objCubiculo = new AsignacionCubiculo(objMenu,objMedicamento);
        objCubiculo.setVisible(true);
        objCubiculo.setSize(430,550);
        objCubiculo.setLocationRelativeTo(null);
        objVista.setVisible(false);
    }
    
    public void iniciarVentanaMenu(){
        objMenu.setVisible(true);
        objVista.setVisible(false);
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
