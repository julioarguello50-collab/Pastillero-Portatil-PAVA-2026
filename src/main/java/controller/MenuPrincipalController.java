package controller;
import java.awt.event.*;
import view.*;
import model.*;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author argue
 */
public class MenuPrincipalController implements ActionListener{
    MenuPrincipal objVista;
    NuevoMedicamento objMedicamento;
    ActualizarCubiculo objActualizar;
    DesactivarCubiculo objDesactivar;
    VentanaPastillero objPastillero;
    MostrarCubiculo objReporte;
    
    public MenuPrincipalController(MenuPrincipal objVista,Medico objMedico){
        this.objVista = objVista;
        
        objVista.jButton1.addActionListener(this);
        objVista.jButton2.addActionListener(this);
        objVista.jButton3.addActionListener(this);
        objVista.jButton4.addActionListener(this);
        objVista.jButton5.addActionListener(this);
        objVista.setMedico(objMedico);
        
        objVista.setLocationRelativeTo(null);
        this.asignarComandos();
    }

    @Override
    public void actionPerformed(ActionEvent e) {        
        switch(e.getActionCommand()){
            case "Medicamento":
                this.iniciarVentanaMedicamento();
            break;
            
            case "Actualizar":
                this.iniciarVentanaActualizar();
            break;
            
            case "Desactivar":
                this.iniciarVentanaDesactivar();
            break;
            
            case "Pastillero":
                this.iniciarVentanaPastillero();
            break;
            
            case "Reporte":
                this.inicarVentanaReporte();
            break;
            
            default: System.out.println("Opcion No valida"); break;
        }
    }
    
    public void asignarComandos(){
        this.objVista.jButton1.setActionCommand("Medicamento");
        this.objVista.jButton2.setActionCommand("Actualizar");
        this.objVista.jButton3.setActionCommand("Desactivar");
        this.objVista.jButton4.setActionCommand("Pastillero");
        this.objVista.jButton5.setActionCommand("Reporte");
    }
    
    public void iniciarVentanaMedicamento(){
        objMedicamento = new NuevoMedicamento(objVista);
        objMedicamento.setVisible(true);
        objMedicamento.setSize(430,550);
        objMedicamento.setLocationRelativeTo(null);
        objVista.setVisible(false);
    }
    
    public void iniciarVentanaActualizar(){
        objActualizar = new ActualizarCubiculo(objVista);
        objActualizar.setVisible(true);
        objActualizar.setSize(460,530);
        objActualizar.setLocationRelativeTo(null);
        objVista.setVisible(false);
    }
    
    public void iniciarVentanaDesactivar(){
        objDesactivar = new DesactivarCubiculo(objVista);
        objDesactivar.setVisible(true);
        objDesactivar.setSize(475,550);
        objDesactivar.setLocationRelativeTo(null);
        objVista.setVisible(false);
    }
    
    public void iniciarVentanaPastillero(){
        objPastillero = new VentanaPastillero(objVista);
        objPastillero.setVisible(true);
        objPastillero.setSize(420,530);
        objPastillero.setLocationRelativeTo(null);
        objVista.setVisible(false);
    }
    
    public void inicarVentanaReporte(){
        objReporte = new MostrarCubiculo(objVista);
        objReporte.setVisible(true);
        objReporte.setSize(438,580);
        objReporte.setLocationRelativeTo(null);
        objVista.setVisible(false);
    }
}
