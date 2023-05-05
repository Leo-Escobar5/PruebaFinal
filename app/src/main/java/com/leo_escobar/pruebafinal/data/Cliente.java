package com.leo_escobar.pruebafinal.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cliente")
public class Cliente {
    //atributos
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int idCliente;
    private String nombres;
    private String apellidos;
    private String telefono;
    private String correo;
    private String fechaCreacion;
    private String estado;
    private String municipio;
    private String codigoPostal;
    private String calle;
    private String colonia;
    private String referencia;
    private double coordenadaX;
    private double coordenadaY;

    public Cliente(int idCliente, String nombres, String apellidos, String telefono, String correo, String fechaCreacion, String estado, String municipio, String codigoPostal, String calle, String colonia, String referencia, double coordenadaX, double coordenadaY) {
        this.idCliente = idCliente;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.correo = correo;
        this.fechaCreacion = fechaCreacion;
        this.estado = estado;
        this.municipio = municipio;
        this.codigoPostal = codigoPostal;
        this.calle = calle;
        this.colonia = colonia;
        this.referencia = referencia;
        this.coordenadaX = coordenadaX;
        this.coordenadaY = coordenadaY;
    }



    //getters y setters
    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public double getCoordenadaX() {
        return coordenadaX;
    }

    public void setCoordenadaX(double coordenadaX) {
        this.coordenadaX = coordenadaX;
    }

    public double getCoordenadaY() {
        return coordenadaY;
    }

    public void setCoordenadaY(double coordenadaY) {
        this.coordenadaY = coordenadaY;
    }


}
