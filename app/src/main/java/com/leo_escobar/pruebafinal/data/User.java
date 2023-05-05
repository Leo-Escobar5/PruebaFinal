package com.leo_escobar.pruebafinal.data;

public class User {
    private int usuario_id;
    private String correo;
    private String fecha_creacion;
    private String nombre;
    private String usuario;
    private int rol_id;
    private String rol;

    public User(int usuario_id, String correo, String fecha_creacion, String nombre, String usuario, int rol_id, String rol) {
        this.usuario_id = usuario_id;
        this.correo = correo;
        this.fecha_creacion = fecha_creacion;
        this.nombre = nombre;
        this.usuario = usuario;
        this.rol_id = rol_id;
        this.rol = rol;
    }

    public int getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public int getRol_id() {
        return rol_id;
    }

    public void setRol_id(int rol_id) {
        this.rol_id = rol_id;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "User{" +
                "usuario_id=" + usuario_id +
                ", correo='" + correo + '\'' +
                ", fecha_creacion='" + fecha_creacion + '\'' +
                ", nombre='" + nombre + '\'' +
                ", usuario='" + usuario + '\'' +
                ", rol_id=" + rol_id +
                ", rol='" + rol + '\'' +
                '}';
    }
}
