package com.leo_escobar.pruebafinal.data;

public class usuarioEncontrado {
    private int usuario_id;
    private String correo;
    private String usuario;
    private String contrasenia;
    private String nombre;
    private int rol_id;
    private String rol;

public usuarioEncontrado(int usuario_id, String correo, String usuario, String contrasenia, String nombre, int rol_id, String rol) {
        this.usuario_id = usuario_id;
        this.correo = correo;
        this.usuario = usuario;
        this.contrasenia = contrasenia;
        this.nombre = nombre;
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

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
        return "usuarioEncontrado{" +
                "usuario_id=" + usuario_id +
                ", correo='" + correo + '\'' +
                ", usuario='" + usuario + '\'' +
                ", contrasenia='" + contrasenia + '\'' +
                ", nombre='" + nombre + '\'' +
                ", rol_id=" + rol_id +
                ", rol='" + rol + '\'' +
                '}';
    }
}
