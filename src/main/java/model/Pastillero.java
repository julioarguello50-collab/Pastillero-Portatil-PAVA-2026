/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author argue
 */
public class Pastillero {
    private int idPastillero;
    private int cantidadCubiculo;
    private int idMedico;

    public Pastillero() {
    }

    public Pastillero(int idPastillero, int cantidadCubiculo, int idMedico) {
        this.idPastillero = idPastillero;
        this.cantidadCubiculo = cantidadCubiculo;
        this.idMedico = idMedico;
    }

    public int getIdPastillero() {
        return idPastillero;
    }

    public void setIdPastillero(int idPastillero) {
        this.idPastillero = idPastillero;
    }

    public int getCantidadCubiculo() {
        return cantidadCubiculo;
    }

    public void setCantidadCubiculo(int cantidadCubiculo) {
        this.cantidadCubiculo = cantidadCubiculo;
    }

    public int getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(int idMedico) {
        this.idMedico = idMedico;
    }
   
    @Override
    public String toString(){
        return "Pastillero "+idPastillero;
    }
}
