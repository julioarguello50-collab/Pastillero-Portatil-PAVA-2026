/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author argue
 */
public class Cubiculo {
    private int idCubiculo;
    private int numeroCubiculo;
    private int idPastillero;

    public Cubiculo() {
    }

    public Cubiculo(int idCubiculo, int numeroCubiculo, int idPastillero) {
        this.idCubiculo = idCubiculo;
        this.numeroCubiculo = numeroCubiculo;
        this.idPastillero = idPastillero;
    }

    public int getIdCubiculo() {
        return idCubiculo;
    }

    public void setIdCubiculo(int idCubiculo) {
        this.idCubiculo = idCubiculo;
    }

    public int getNumeroCubiculo() {
        return numeroCubiculo;
    }

    public void setNumeroCubiculo(int numeroCubiculo) {
        this.numeroCubiculo = numeroCubiculo;
    }

    public int getIdPastillero() {
        return idPastillero;
    }

    public void setIdPastillero(int idPastillero) {
        this.idPastillero = idPastillero;
    }
    
    @Override
    public String toString(){
        return "Cubiculo "+numeroCubiculo;
    }
}
