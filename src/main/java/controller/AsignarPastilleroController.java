/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.*;
import model.*;
import view.*;
import DAO.*;
import javax.swing.*;

/**
 *
 * @author argue
 */
public class AsignarPastilleroController implements ActionListener{
    private VentanaPastillero objVista;
    private MenuPrincipal objMenu;
    private Pastillero objPastillero;
    private PastilleroDAO objDAO;
    private CubiculoDAO objCubiculoDao;
    private Cubiculo objCubiculo;
    
    public AsignarPastilleroController(VentanaPastillero objVista,MenuPrincipal objMenu){
        this.objVista = objVista;
        this.objMenu = objMenu;
        
        objVista.jButton1.addActionListener(this);
        objVista.jButton2.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == objVista.jButton1){
            this.guardarDatos();
            JOptionPane.showMessageDialog(null,"Registro exitoso");
        }
        
        if(e.getSource() == objVista.jButton2){
            this.iniciarVentanaInicio();
        }
    }
    
    
    public void guardarDatos(){
        objPastillero = new Pastillero();
        objCubiculoDao = new CubiculoDAO();
        
        int cantidadCubiculos =(int) objVista.jSpinner1.getValue();
        int idPastillero;
        
        objPastillero.setCantidadCubiculo(cantidadCubiculos);
        objPastillero.setIdMedico(objMenu.getMedico().getIdMedico());
        
        if(!new PastilleroDAO().registrar(objPastillero)){
            JOptionPane.showMessageDialog(null,"No se pudo registrar el pastillero");
            return;
        }
        
        idPastillero = objPastillero.getIdMedico();
        objVista.jTextField1.setText(String.valueOf(idPastillero));
        
        for(int i = 1; i <= objPastillero.getCantidadCubiculo();i++){
            objCubiculo = new Cubiculo();
            objCubiculo.setNumeroCubiculo(i);
            objCubiculo.setIdPastillero(objPastillero.getIdPastillero());
            objCubiculoDao.registrar(objCubiculo);
        }
    }
    
    public void iniciarVentanaInicio(){
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
