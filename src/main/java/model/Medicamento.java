/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author argue
 */
public class Medicamento {
    private String nombreMedicamento;
    private String tipoMedicamento;
    private double miligramos;
    private int cubiculoAsignado;
    private int intervaloToma;
    private int idMedicamento;
    
    
    public Medicamento(){  
    }
    
    public Medicamento(String nombreMedicamento,String tipoMedicamento,double miligramos,int cubiculoAsignado,int intervaloToma){
        this.nombreMedicamento = nombreMedicamento;
        this.tipoMedicamento = tipoMedicamento;
        this.miligramos = miligramos;
        this.cubiculoAsignado = cubiculoAsignado;
        this.intervaloToma = intervaloToma;
    }

    public String getNombreMedicamento() {
        return nombreMedicamento;
    }

    public void setNombreMedicamento(String NombreMedicamento) {
        this.nombreMedicamento = NombreMedicamento;
    }

    public String getTipoMedicamento() {
        return tipoMedicamento;
    }

    public void setTipoMedicamento(String TipoMedicamento) {
        this.tipoMedicamento = TipoMedicamento;
    }

    public double getMiligramos() {
        return miligramos;
    }

    public void setMiligramos(double miligramos) {
        this.miligramos = miligramos;
    }

    public int getCubiculoAsignado() {
        return cubiculoAsignado;
    }

    public void setCubiculoAsignado(int cubiculoAsignado) {
        this.cubiculoAsignado = cubiculoAsignado;
    }

    public int getIntervaloToma() {
        return intervaloToma;
    }

    public void setIntervaloToma(int intervaloToma) {
        this.intervaloToma = intervaloToma;
    }

    public int getIdMedicamento() {
        return idMedicamento;
    }

    public void setIdMedicamento(int idMedicamento) {
        this.idMedicamento = idMedicamento;
    }
    
    
}
