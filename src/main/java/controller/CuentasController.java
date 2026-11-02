/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import view.*;

/**
 *
 * @author argue
 */
public class CuentasController implements ActionListener {
    private Cuentas objVistas;
    private InicioSesion objInicio;
    private NuevoMedico objCrear;
    public CuentasController(Cuentas objVista){
        this.objVistas = objVista;
        
        objVistas.jButton1.addActionListener(this);
        objVistas.jButton2.addActionListener(this);
        
        objVistas.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == objVistas.jButton1){
            this.iniciarVentanaSesion();
        }
        
        if(e.getSource() == objVistas.jButton2){
            this.iniciarVentanaCrear();
        }
    }
    
    public void iniciarVentanaCrear(){
        objCrear = new NuevoMedico();
        objCrear.setVisible(true);
        objCrear.setSize(605,770);
        objCrear.setLocationRelativeTo(null);
        objVistas.dispose();
    }
    
    public void iniciarVentanaSesion(){
        objInicio = new InicioSesion();
        objInicio.setVisible(true);
        objInicio.setSize(499,337);
        objInicio.setLocationRelativeTo(null);
        objVistas.dispose();
    }
    
}
