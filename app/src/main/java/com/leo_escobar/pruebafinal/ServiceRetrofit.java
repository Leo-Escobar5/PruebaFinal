package com.leo_escobar.pruebafinal;

import com.leo_escobar.pruebafinal.data.BodyLogin;
import com.leo_escobar.pruebafinal.data.Client;
import com.leo_escobar.pruebafinal.data.ClientInsert;
import com.leo_escobar.pruebafinal.data.Clients;
import com.leo_escobar.pruebafinal.data.LoginRespuesta;
import com.leo_escobar.pruebafinal.data.User;
import com.leo_escobar.pruebafinal.data.Usesrs;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ServiceRetrofit {
    @POST("auth/login")
    Call<LoginRespuesta> login(@Body BodyLogin bodyLogin);

    @GET("usuarios")
    Call<Usesrs> getUsuarios(@Header("token") String token);

    // GET clientes
    @GET("clientes")
    Call<Clients> getClientes(@Header("token") String token);

    // GET de un solo cliente
    @GET("clientes/{cliente_id}")
    Call<Client> getCliente(@Header("token") String token, @Path("cliente_id") int cliente_id);

    // POST de insertar un cliente
    @POST("clientes")
    Call<ClientInsert> postInsertarCliente(@Header("token") String token, @Body ClientInsert clientInsert);


    //Delete de eliminar un cliente
    @DELETE("clientes/{cliente_id}")
    Call<Client> deleteCliente(@Header("token") String token, @Path("cliente_id") int cliente_id);

    //PUT de actualizar un cliente
    @PUT("clientes/{cliente_id}")
    Call<Client> updateCliente(@Header("token") String token, @Path("cliente_id") int cliente_id, @Body Client client);

//    @GET("usuario")
//    Call<BodyLogin> login(@Header("token") String token, User User);



}
