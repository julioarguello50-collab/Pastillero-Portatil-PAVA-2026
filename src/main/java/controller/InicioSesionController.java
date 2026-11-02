/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.*;
import javax.swing.JOptionPane;
import view.*;
import model.*;
import DAO.*;
/**
 *
 * @author argue
 */
public class InicioSesionController implements ActionListener {
    private InicioSesion objVista;
    private Validador objValidador;
    private String[] arregloCadena;
    private Medico objMedico;
    private MedicoDAO objDao;
    private SeguridadContrasena objSeguridad;
    private MenuPrincipal objMenu;
    private Cuentas objCuenta;
    
    public InicioSesionController(InicioSesion objVista){
        this.objVista = objVista;
        objVista.jButton1.addActionListener(this);
        
        objValidador = new Validador();
        this.windowListener();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == objVista.jButton1){
            if(!this.comprobarCampos())
                return;
            
            if(!this.buscarCuentaMedico())
                return;
            
            objMenu = new MenuPrincipal(objMedico);
            objMenu.setVisible(true);
            objMenu.setSize(421,530);
        }
    }
    
    public boolean comprobarCampos(){
        arregloCadena = new String[2];
        
        arregloCadena[0] = objVista.jTextField1.getText();
        arregloCadena[1] = new String(objVista.jPasswordField1.getPassword());
        
        if(!objValidador.comprobarCamposTexto(arregloCadena)){
            JOptionPane.showMessageDialog(null,"Error faltan campos por rellenar");
            return false;
        }    
            
        return true;
    }
    
    public boolean buscarCuentaMedico(){
        String usuario = objVista.jTextField1.getText();
        String password = new String(objVista.jPasswordField1.getPassword());
        String hashGuardado;
        
        objMedico = new Medico();
        objDao = new MedicoDAO();
        objSeguridad = new SeguridadContrasena();
       
        objMedico = objDao.buscarUsuario(usuario);
        hashGuardado = objSeguridad.hashear(password);
        
        if(objMedico == null ||!hashGuardado.equals(objMedico.getPassword())){
            JOptionPane.showMessageDialog(null,"Usuario o contraseña incorrectos");
            return false;
        }
        
        return true;
    }
    
    private void windowListener(){
        this.objVista.addWindowListener(new java.awt.event.WindowAdapter(){
            @Override
            public void windowClosing(java.awt.event.WindowEvent event){
                objCuenta = new Cuentas();
                objCuenta.setVisible(true);
                objCuenta.setSize(432,530);
            }
        });
    }
    
}
