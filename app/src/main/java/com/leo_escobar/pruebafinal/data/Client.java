package com.leo_escobar.pruebafinal.data;

public class Client {
    private int cliente_id;
    private String razon_social;
    private String telefono;
    private String correo;
    private String fecha_creacion;
    private String referencia;
    private int estado_id;
    private String estado;
    private int ciudad_id;
    private String ciudad;
    private String colonia;
    private String calle;
    private String cp;
    private double latitud;
    private double longitud;

    public Client(int cliente_id, String razon_social, String telefono, String correo, String fecha_creacion, String referencia, int estado_id, String estado, int ciudad_id, String ciudad, String colonia, String calle, String cp, double latitud, double longitud) {
        this.cliente_id = cliente_id;
        this.razon_social = razon_social;
        this.telefono = telefono;
        this.correo = correo;
        this.fecha_creacion = fecha_creacion;
        this.referencia = referencia;
        this.estado_id = estado_id;
        this.estado = estado;
        this.ciudad_id = ciudad_id;
        this.ciudad = ciudad;
        this.colonia = colonia;
        this.calle = calle;
        this.cp = cp;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    //constructor sin el id
    public Client(String razon_social, String telefono, String correo, String fecha_creacion, String referencia, int estado_id, String estado, int ciudad_id, String ciudad, String colonia, String calle, String cp, double latitud, double longitud) {
        this.razon_social = razon_social;
        this.telefono = telefono;
        this.correo = correo;
        this.fecha_creacion = fecha_creacion;
        this.referencia = referencia;
        this.estado_id = estado_id;
        this.estado = estado;
        this.ciudad_id = ciudad_id;
        this.ciudad = ciudad;
        this.colonia = colonia;
        this.calle = calle;
        this.cp = cp;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    //constructor sin el id y sin el estado y ciudad pero si con sus id
    public Client(String razon_social, String telefono, String correo, String fecha_creacion, String referencia, int estado_id, int ciudad_id, String colonia, String calle, String cp, double latitud, double longitud) {
        this.razon_social = razon_social;
        this.telefono = telefono;
        this.correo = correo;
        this.fecha_creacion = fecha_creacion;
        this.referencia = referencia;
        this.estado_id = estado_id;
        this.ciudad_id = ciudad_id;
        this.colonia = colonia;
        this.calle = calle;
        this.cp = cp;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public int getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(int cliente_id) {
        this.cliente_id = cliente_id;
    }

    public String getRazon_social() {
        return razon_social;
    }

    public void setRazon_social(String razon_social) {
        this.razon_social = razon_social;
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

    public String getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(String fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public int getEstado_id() {
        return estado_id;
    }

    public void setEstado_id(int estado_id) {
        this.estado_id = estado_id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getCiudad_id() {
        return ciudad_id;
    }

    public void setCiudad_id(int ciudad_id) {
        this.ciudad_id = ciudad_id;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    @Override
    public String toString() {
        return "Client{" +
                "cliente_id=" + cliente_id +
                ", razon_social='" + razon_social + '\'' +
                ", telefono='" + telefono + '\'' +
                ", correo='" + correo + '\'' +
                ", fecha_creacion='" + fecha_creacion + '\'' +
                ", referencia='" + referencia + '\'' +
                ", estado_id=" + estado_id +
                ", estado='" + estado + '\'' +
                ", ciudad_id=" + ciudad_id +
                ", ciudad='" + ciudad + '\'' +
                ", colonia='" + colonia + '\'' +
                ", calle='" + calle + '\'' +
                ", cp='" + cp + '\'' +
                ", latitud=" + latitud +
                ", longitud=" + longitud +
                '}';
    }
}
