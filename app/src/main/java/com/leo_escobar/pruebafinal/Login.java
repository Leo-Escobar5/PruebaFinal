package com.leo_escobar.pruebafinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.leo_escobar.pruebafinal.data.BodyLogin;
import com.leo_escobar.pruebafinal.data.Client;
import com.leo_escobar.pruebafinal.data.Clients;
import com.leo_escobar.pruebafinal.data.LoginRespuesta;
import com.leo_escobar.pruebafinal.data.User;
import com.leo_escobar.pruebafinal.data.Usesrs;
import com.leo_escobar.pruebafinal.databinding.ActivityLoginBinding;
import com.leo_escobar.pruebafinal.databinding.ActivityMainBinding;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    String token;
    String correo_usuario;
    String password;
    private ServiceRetrofit api;

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        api = RetrofitPrueba.getInstance().getService();


        //boton para iniciar sesion
        binding.btnLogin.setOnClickListener(v -> {
            //asignarle el valor de correo_usuario y password a las variables
            correo_usuario = binding.correoUsuario.getText().toString();
            password = binding.password.getText().toString();

            //validar que los campos de correo y contraseña no esten vacios
            if(correo_usuario.isEmpty() || password.isEmpty()) {
                Toast.makeText(Login.this, "Ingrese todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            //llamar al metodo para obtener el token
            obtenerToken(correo_usuario, password);
        });

    }

    private void obtenerToken(String correo_usuario, String password) {



        Call<LoginRespuesta> call = api.login(new BodyLogin(correo_usuario, password));
        call.enqueue(new Callback<LoginRespuesta>() {
            @Override
            public void onResponse(Call<LoginRespuesta> call, Response<LoginRespuesta> response) {
                if(response.isSuccessful()) {
                    LoginRespuesta loginRespuesta = response.body();
                    if(loginRespuesta != null) {
                        // Obtener el token de la respuesta
                        String token = loginRespuesta.getToken();
                        // Validar que el token no sea nulo o vacío
                        if(token != null && !token.isEmpty()) {
                            // Guardar el token en SharedPreferences
                            SharedPreferences sharedPreferences = getApplication().getSharedPreferences("MiPreferencia", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("token", token);
                            editor.apply();
                            // Iniciar la actividad MainActivity y pasar el token como extra
                            Intent intent = new Intent(Login.this, MainActivity.class);
                            intent.putExtra("token", token);
                            startActivity(intent);
                        } else {
                            // Mostrar un mensaje de error si el token es nulo o vacío
                            Toast.makeText(Login.this, "Credenciales inválidas", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Mostrar un mensaje de error si la respuesta es nula
                        Toast.makeText(Login.this, "Error: Respuesta nula", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //verificar si el codigo de estado es 401(no autorizado)
                    if(response.code() == 401) {
                        Toast.makeText(Login.this, "Credenciales inválidas", Toast.LENGTH_SHORT).show();
                    }else {

                        // Mostrar un mensaje de error si la llamada no fue exitosa
                        Toast.makeText(Login.this, "Error en el servidor", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginRespuesta> call, Throwable t) {
                // Mostrar un mensaje de error si ocurre un error en la llamada
                if (t instanceof IOException) {
                    // Mostrar un mensaje de error de conexión si ocurre un IOException
                    Toast.makeText(Login.this, "Error: No se puede conectar al servidor", Toast.LENGTH_SHORT).show();
                } else {
                    // Mostrar un mensaje de error genérico si ocurre cualquier otro tipo de excepción
                    Toast.makeText(Login.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

        });

        Call<Clients> call3 = api.getClientes(token);

        Call<Usesrs> call2 = api.getUsuarios(token);
        call3.enqueue(new Callback<Clients>() {
            @Override
            public void onResponse(Call<Clients> call, Response<Clients> response) {
                if (response.isSuccessful()) {
                Log.d("Respuesta", "onResponse desde clientes: " + response.body());
                //log de prueba
                Log.i("Respuesta", "Estoy en el Login desde getClientes");
                Clients clients = response.body();
                List<Client> clientList = clients.getClients();
                for (Client client : clientList) {
                    Log.d("Respuesta", "onResponse desde clientes: " + client.getRazon_social());
                    Log.d("Respuesta", "onResponse desde clientes: " + client.getCiudad());
                }
                } else {
                    Log.d("Respuesta", "onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Clients> call, Throwable t) {

            }
        });
        call2.enqueue(new Callback<Usesrs>() {
            @Override
            public void onResponse(Call<Usesrs> call, Response<Usesrs> response) {
                if (response.isSuccessful()) {
                    Log.d("Respuesta", "onResponse desde users: " + response.body());
                    Log.i("Respuesta", "Estoy en el Login desde getUsers");

                    Usesrs usesrs = response.body();
                    List<User> users = usesrs.getUsers();
                    for (User user : users) {
                        Log.d("Respuesta", "onResponse desde users: " + user.getCorreo());
                    }
                } else {
                    Log.d("Respuesta", "onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Usesrs> call, Throwable t) {
                Log.d("Respuesta", "onFailure: " + t.getMessage());
            }
        });

    }
}