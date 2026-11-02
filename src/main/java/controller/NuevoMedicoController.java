/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.time.LocalDate;
import java.time.ZoneId;
import javax.swing.JOptionPane;
import view.*;
import model.*;
import DAO.*;
/**
 *
 * @author argue
 */
public class NuevoMedicoController implements ActionListener {
    private NuevoMedico objVista;
    private InicioSesion objSesion;
    private Validador objValidador;
    private String[] arregloCadena;
    private Medico objMedico;
    private MedicoDAO objDao;
    private Date fecha;
    private Cuentas objMenu;
    public NuevoMedicoController(NuevoMedico objVista){
        this.objVista = objVista;
        
        objValidador = new Validador();
        
        this.objVista.jButton1.addActionListener(this);
        this.windowListener();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == objVista.jButton1){
            if(!this.comprobarCamposCadena())
                return;
            
            
            if(!this.comprobarFecha())
                return;
            
            if(!this.comprobarContrasena())
                return;
            
            if(!this.comprobarUsuario())
                return;
            
            if(this.ingresarDatos()){
            JOptionPane.showMessageDialog(null,"Registro exitoso");
                objVista.dispose();
                objSesion = new InicioSesion();
                objSesion.setSize(508,345);
                objSesion.setLocationRelativeTo(null);
            }else{
                JOptionPane.showMessageDialog(null,"No se pudo crear la cuenta");
            }
            
        }
    }
    
    public boolean comprobarCamposCadena(){
        arregloCadena = new String[7];
        arregloCadena[0] = objVista.jTextField1.getText();
        arregloCadena[1] = objVista.jTextField2.getText();
        arregloCadena[2] = objVista.jTextField3.getText();
        arregloCadena[3] = objVista.jTextField4.getText();
        arregloCadena[4] = objVista.jTextField5.getText();
        arregloCadena[5] = objVista.jTextField6.getText();
        arregloCadena[6] = objVista.jTextField7.getText();
        
        if(!objValidador.comprobarCamposTexto(arregloCadena)){
            JOptionPane.showMessageDialog(null,"Error faltan campos por rellenar");
            return false;
        }
        return true;
    }
    
    public boolean comprobarFecha(){
        Date fechaVista = (Date) objVista.jDateChooser1.getDate();
        
        if(fechaVista == null){
            JOptionPane.showMessageDialog(null,"Fecha no valida");
            return false;
        }
        
        LocalDate fecha = fechaVista.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        
        if(fecha.isAfter(LocalDate.now())){
            JOptionPane.showMessageDialog(null,"No puedes poner una fecha del futuro");
            return false;
        }
        
        return true;
    }
    
    public boolean comprobarContrasena(){
        char[] primera = objVista.jPasswordField1.getPassword();
        char[] segunda = objVista.jPasswordField2.getPassword();
        
        if (primera.length == 0) {
                JOptionPane.showMessageDialog(null, "Escribe una contraseña");
                return false;
            }
        
        if (primera.length < 8) {
            JOptionPane.showMessageDialog(null, "La contraseña debe tener al menos 8 caracteres");
            return false;
        }

        if (!java.util.Arrays.equals(primera,segunda)) {
            JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden");
            return false;
        }

        return true;
    }
    
    public boolean comprobarUsuario(){
        String usuario = objVista.jTextField7.getText();
        
        if(new MedicoDAO().buscarUsuario(usuario) != null){//aplicamos clase anonima
            JOptionPane.showMessageDialog(null, "El nombre de usuario ya existe");
            return false;
        }
        
        return true;
    }
    
    public boolean ingresarDatos(){
        fecha = (Date) objVista.jDateChooser1.getDate();
        String contrasena = new String(objVista.jPasswordField1.getPassword());
        
        objMedico = new Medico();
        objMedico.setNombre(objVista.jTextField1.getText());
        objMedico.setApPaterno(objVista.jTextField2.getText());
        objMedico.setApMaterno(objVista.jTextField3.getText());
        objMedico.setFechaNacimiento(fecha.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());
        objMedico.setCarrera(objVista.jTextField4.getText());
        objMedico.setEspecialidad(objVista.jTextField5.getText());
        objMedico.setCedula(objVista.jTextField6.getText());
        objMedico.setUsuario(objVista.jTextField7.getText());
        objMedico.setPassword(new SeguridadContrasena().hashear(contrasena));
        
        objDao = new MedicoDAO();
        return objDao.registrar(objMedico);
    }
    
    public void windowListener(){
        this.objVista.addWindowListener(new java.awt.event.WindowAdapter(){
            @Override
            public void windowClosing(java.awt.event.WindowEvent event){
                objMenu = new Cuentas();
                objMenu.setVisible(true);
                objMenu.setSize(425,530);
            }
        });
    }
}

//seguimos con el login
