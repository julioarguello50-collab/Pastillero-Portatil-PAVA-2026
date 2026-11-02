/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;

/**
 *
 * @author argue
 */
public class Asignacion {
    private int idAsignacion;
    private double dosisMiligramos;
    private int intervaloHoras;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private int idMedico;
    private int idMedicamento;
    private int idCubiculo;
    
    public Asignacion() {
    }

    public Asignacion(int idAsignacion, double dosisMiligramos, int intervaloHoras, LocalDate fechaInicio, LocalDate fechaFin, int idMedico, int idMedicamento, int idCubiculo) {
        this.idAsignacion = idAsignacion;
        this.dosisMiligramos = dosisMiligramos;
        this.intervaloHoras = intervaloHoras;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.idMedico = idMedico;
        this.idMedicamento = idMedicamento;
        this.idCubiculo = idCubiculo;
    }

    public int getIdAsignacion() {
        return idAsignacion;
    }

    public void setIdAsignacion(int idAsignacion) {
        this.idAsignacion = idAsignacion;
    }

    public double getDosisMiligramos() {
        return dosisMiligramos;
    }

    public void setDosisMiligramos(double dosisMiligramos) {
        this.dosisMiligramos = dosisMiligramos;
    }

    public int getIntervaloHoras() {
        return intervaloHoras;
    }

    public void setIntervaloHoras(int intervaloHoras) {
        this.intervaloHoras = intervaloHoras;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(int idMedico) {
        this.idMedico = idMedico;
    }

    public int getIdMedicamento() {
        return idMedicamento;
    }

    public void setIdMedicamento(int idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

    public int getIdCubiculo() {
        return idCubiculo;
    }

    public void setIdCubiculo(int idCubiculo) {
        this.idCubiculo = idCubiculo;
    }

}    