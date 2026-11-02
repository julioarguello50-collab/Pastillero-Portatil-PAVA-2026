/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import javax.swing.JOptionPane;

/**
 *
 * @author argue
 */
public class Validador {
    public Validador(){ 
    }
    
    public boolean comprobarCamposTexto(String[] array){
        for (String array1 : array) {
            if(!validacionDatosCadena(array1))
                return false;
        }
        return true;
    }
    
    public boolean comprobarCamposNumericos(String[] array){
        for(String array1: array){
            if(!validacionDatosNumericos(array1))
                return false;
        }
        return true;
    }
    
    
    public boolean validacionDatosCadena(String cadena){
        if(cadena == null || cadena.isBlank()){
            JOptionPane.showMessageDialog(null,"Error el campo esta vacio");
            return false;
        }
        
        return true;
    }
    
    public boolean validacionDatosNumericos(String array){
            if(array == null || array.isBlank()){
                JOptionPane.showMessageDialog(null,"Error no hay ningun dato");
                return false;
            }
            if(!esNumero(array)){
                JOptionPane.showMessageDialog(null,"Error el numero ingresado no es valido");
                return false;  
            }
            
            if(!validarEnteroRango(Double.parseDouble(array))){
                JOptionPane.showMessageDialog(null,"Error el numero ingresado no es valido");
                return false;
            }
        return true;
    }
    
    public boolean esNumero(String cadena){
        try{
            Double.parseDouble(cadena);
        }catch(NumberFormatException e){
            return false;
        }
        return true;
    }
    
    public boolean validarEnteroRango(double valor){
        if(valor < 0){
            JOptionPane.showMessageDialog(null,"Error el numero no es valido");
            return false;
        }
        return true;
    }

}
