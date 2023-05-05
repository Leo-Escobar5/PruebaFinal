package com.leo_escobar.pruebafinal.data;

public class LoginRespuesta {
    private String token;
    private usuarioEncontrado usuarioEncontrado;

    public LoginRespuesta(String token, usuarioEncontrado usuarioEncontrado) {
        this.token = token;
        this.usuarioEncontrado = usuarioEncontrado;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public com.leo_escobar.pruebafinal.data.usuarioEncontrado getUsuarioEncontrado() {
        return usuarioEncontrado;
    }

    public void setUsuarioEncontrado(com.leo_escobar.pruebafinal.data.usuarioEncontrado usuarioEncontrado) {
        this.usuarioEncontrado = usuarioEncontrado;
    }

    @Override
    public String toString() {
        return "LoginRespuesta{" +
                "token='" + token + '\'' +
                ", usuarioEncontrado=" + usuarioEncontrado +
                '}';
    }
}
