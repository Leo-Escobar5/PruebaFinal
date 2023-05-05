package com.leo_escobar.pruebafinal.data;

public class BodyLogin {

    private String correo_usuario;
    private String password;

    public BodyLogin(String correo_usuario, String password) {
        this.correo_usuario = correo_usuario;
        this.password = password;
    }

    public String getCorreo_usuario() {
        return correo_usuario;
    }

    public void setCorreo_usuario(String correo_usuario) {
        this.correo_usuario = correo_usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "BodyLogin{" +
                "correo_usuario='" + correo_usuario + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
