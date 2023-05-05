package com.leo_escobar.pruebafinal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.core.view.WindowCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.leo_escobar.pruebafinal.DAO.ClienteDao;
import com.leo_escobar.pruebafinal.DAO.ClienteDatabase;
import com.leo_escobar.pruebafinal.data.Client;
import com.leo_escobar.pruebafinal.data.Cliente;
import com.leo_escobar.pruebafinal.data.Clients;
import com.leo_escobar.pruebafinal.data.DiccionarioCiudades;
import com.leo_escobar.pruebafinal.databinding.ActivityEditarClienteBinding;
import com.leo_escobar.pruebafinal.ui.ViewModel;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class editarCliente extends AppCompatActivity {

    private ActivityEditarClienteBinding binding;
    private ClienteDao clienteDao;
    private Cliente cliente;
    private Client client;
    private ServiceRetrofit api;
    private int municipioId;
    private int idCliente;
    SharedPreferences sharedPreferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEditarClienteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        api = RetrofitPrueba.getInstance().getService();
        sharedPreferences = getApplication().getSharedPreferences("MiPreferencia", Context.MODE_PRIVATE);



        // Crear una nueva instancia de ClienteDao
        ClienteDatabase clienteDatabase = ClienteDatabase.getDatabase(this);
        // Obtener una instancia del DAO a través de la base de datos
        clienteDao = clienteDatabase.clienteDao();

        // obtener el id del cliente que se quiere editar desde el intent
        //idCliente = getIntent().getIntExtra("idCliente", -1);


        // Guardar el idCliente en SharedPreferences
//        SharedPreferences sharedPreferences2 = getSharedPreferences("MiPreferencia", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences2.edit();
//        editor.putInt("idClienteObtenido", idCliente);
//        editor.apply();



         //si el id es igual o menor a 0, obtener el id del cliente
        int idClienteObtenido = sharedPreferences.getInt("idClienteObtenido",0);
        idCliente= idClienteObtenido;


        //toast con el id del cliente
        Toast.makeText(this, "idCliente del mapaActivity: " + idCliente, Toast.LENGTH_SHORT).show();

        //log con el id del cliente
        Log.d("idCliente", "idCliente: " + idCliente);

        // obtener el token desde el MainActivity
        //validación de que el token no sea nulo o vacío

        //obtener las coordenadas desde MapaActivity con putExtra
        double latitude2 = getIntent().getDoubleExtra("latitud", 0.0);
        double longitude2 = getIntent().getDoubleExtra("longitud", 0.0);
        // Obtener la calle y la colonia desde MapaActivity con putExtra
        String calleObtenida = getIntent().getStringExtra("calle");
        String coloniaObtenida = getIntent().getStringExtra("colonia");

        //obtener el codigo postal, ciudad y estado desde MapaActivity con putExtra
        String codigoPostalObtenido = getIntent().getStringExtra("codigoPostal");
        //convertir el codigo postal a String
        String codigoPostalObtenidoString = codigoPostalObtenido;
        String ciudadObtenida = getIntent().getStringExtra("ciudad");
        String estadoObtenido = getIntent().getStringExtra("estado");



        String token = sharedPreferences.getString("token", "");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
        Date fechaActual = new Date();


        HashMap<String, Integer> estados = new HashMap<>();
        estados.put("AGUASCALIENTES", 1);
        estados.put("BAJA CALIFORNIA", 2);
        estados.put("BAJA CALIFORNIA SUR", 3);
        estados.put("CAMPECHE", 4);
        estados.put("COAHUILA DE ZARAGOZA", 5);
        estados.put("COLIMA", 6);
        estados.put("CHIAPAS", 7);
        estados.put("CHIHUAHUA", 8);
        estados.put("CIUDAD DE MÉXICO", 9);
        estados.put("DURANGO", 10);
        estados.put("GUANAJUATO", 11);
        estados.put("GUERRERO", 12);
        estados.put("HIDALGO", 13);
        estados.put("JALISCO", 14);
        estados.put("ESTADO DE MÉXICO", 15);
        estados.put("MICHOACÁN DE OCAMPO", 16);
        estados.put("MORELOS", 17);
        estados.put("NAYARIT", 18);
        estados.put("NUEVO LEÓN", 19);
        estados.put("OAXACA DE JUÁREZ", 20);
        estados.put("PUEBLA", 21);
        estados.put("QUERÉTARO", 22);
        estados.put("QUINTANA ROO", 23);
        estados.put("SAN LUIS POTOSÍ", 24);
        estados.put("SINALOA", 25);
        estados.put("SONORA", 26);
        estados.put("TABASCO", 27);
        estados.put("TAMAULIPAS", 28);
        estados.put("TLAXCALA", 29);
        estados.put("VERACRUZ DE IGNACIO DE LA LLAVE", 30);
        estados.put("YUCATÁN", 31);
        estados.put("ZACATECAS", 32);

        // Crear un ArrayList con los nombres de los estados
        ArrayList<String> listaEstados = new ArrayList<>(Arrays.asList(
                "AGUASCALIENTES", "BAJA CALIFORNIA", "BAJA CALIFORNIA SUR", "CAMPECHE",
                "COAHUILA DE ZARAGOZA", "COLIMA", "CHIAPAS", "CHIHUAHUA", "CIUDAD DE MÉXICO",
                "DURANGO", "GUANAJUATO", "GUERRERO", "HIDALGO", "JALISCO", "ESTADO DE MÉXICO",
                "MICHOACÁN DE OCAMPO", "MORELOS", "NAYARIT", "NUEVO LEÓN", "OAXACA DE JUÁREZ",
                "PUEBLA", "QUERÉTARO", "QUINTANA ROO", "SAN LUIS POTOSÍ", "SINALOA", "SONORA",
                "TABASCO", "TAMAULIPAS", "TLAXCALA", "VERACRUZ DE IGNACIO DE LA LLAVE", "YUCATÁN",
                "ZACATECAS"));

// Crear un ArrayAdapter con la lista de estados
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaEstados);

// Establecer el layout a usar cuando se despliega la lista de opciones
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

// Establecer el adaptador para el Spinner
        binding.editTextEstado.setAdapter(adapter);

        //si el estadoObtenido es diferente de null se asigna el id del estado
        if (estadoObtenido != null) {
            int estadoId = estados.get(estadoObtenido.toUpperCase());
            //toast con el nombre del estado y su id
            Toast.makeText(this, "Estado: " + estadoObtenido + " id: " + estadoId, Toast.LENGTH_SHORT).show();
            binding.editTextEstado.setSelection(estadoId-1);
        }

        //instanciar ciudades de DiccionarioCiudades
        DiccionarioCiudades ciudades = new DiccionarioCiudades();


        //si la ciudadObtenida es diferente de null se asigna el id del municipio
        if (ciudadObtenida != null) {
            municipioId = ciudades.getCiudades().get(ciudadObtenida.toUpperCase());
            //toast con el nombre del municipio y su id
            Toast.makeText(this, "Municipio: " + ciudadObtenida + " id: " + municipioId, Toast.LENGTH_SHORT).show();
            binding.editTextMunicipio.setText(ciudadObtenida);
        }

        //Toast con el id del cliente seleccionado
        //Toast.makeText(this, "ID del cliente seleccionado: " + idCliente, Toast.LENGTH_SHORT).show();
        //Toast con el nombre del cliente seleccionado
        //Toast.makeText(this, "Nombre del cliente seleccionado: " + nombreCliente, Toast.LENGTH_SHORT).show();

        //Toast con el token
        //Toast.makeText(this, "Token: " + token, Toast.LENGTH_SHORT).show();

        // ejecutar una tarea asíncrona para obtener el cliente correspondiente a través del DAO

        String lugar = getIntent().getStringExtra("edicion");


        switch (lugar){
            case "mapa":

                binding.editTextCalle.setText(calleObtenida);
                binding.editTextColonia.setText(coloniaObtenida);


                // si el codigo postal, la ciudad y el estado son diferentes de null se asignan a los editText
                if (codigoPostalObtenido != null && ciudadObtenida != null && estadoObtenido != null) {
                    binding.editTextCodigoPostal.setText(codigoPostalObtenidoString);
                    //viewDataBinding.editTextCiudad.setText(ciudadObtenida);
                    //viewDataBinding.editTextEstado.setText(estadoObtenido);
                }else{
                    // toast de que no se asignaron las coordenadas
                    Toast.makeText(this, "No se asignaron el codigo postal, la ciudad y el estado", Toast.LENGTH_SHORT).show();
                }

                //si las coordenadas son diferente a 0.0 se asignan a los editText
                if (latitude2 != 0.0 && longitude2 != 0.0) {
                    binding.editTextCoordenadaX.setText(String.valueOf(latitude2));
                    binding.editTextCoordenadaY.setText(String.valueOf(longitude2));
                    // toast de que se asignaron las coordenadas
                    //Toast.makeText(this, "Se asignaron las coordenadas", Toast.LENGTH_SHORT).show();
                }else{
                    // toast de que no se asignaron las coordenadas
                    Toast.makeText(this, "No se asignaron las coordenadas", Toast.LENGTH_SHORT).show();
                }

                break;
            case "main":

                Call<Client> call4 = api.getCliente(token, idCliente);
                call4.enqueue(new Callback<Client>() {
                    @Override
                    public void onResponse(Call<Client> call, Response<Client> response) {
                        if (response.isSuccessful()) {
                            Client client = response.body();
                            if (client != null) {
                                //Obtener el primer objeto Client de la lista clients
                                // Mostrar los datos del cliente en el formulario
                                binding.editTextNombre.setText(client.getRazon_social());
                                binding.editTextTelefono.setText(client.getTelefono());
                                binding.editTextCorreo.setText(client.getCorreo());
                                //binding.editTextFechaCreacion.setText(client.getFecha_creacion());
                                //binding.editTextEstado.setText(String.valueOf(client.getEstado_id()));
//                        String estadoSeleccionado = (String) binding.editTextEstado.getSelectedItem();
//                        int estado = estados.get(estadoSeleccionado);
                                int estadoId = client.getEstado_id();
                                String estadoSeleccionado = ""; // variable para guardar el estado seleccionado en formato String
                                for (Map.Entry<String, Integer> entry : estados.entrySet()) {
                                    if (entry.getValue() == estadoId) {
                                        estadoSeleccionado = entry.getKey();
                                        break;
                                    }
                                }
                                int posicionEstado = listaEstados.indexOf(estadoSeleccionado);
                                binding.editTextEstado.setSelection(posicionEstado);


                                binding.editTextMunicipio.setText(String.valueOf(client.getCiudad_id()));
                                binding.editTextCodigoPostal.setText(client.getCp());
                                binding.editTextCalle.setText(client.getCalle());
                                binding.editTextColonia.setText(client.getColonia());
                                binding.editTextReferencia.setText(client.getReferencia());
                                binding.editTextCoordenadaX.setText(String.valueOf(client.getLatitud()));
                                binding.editTextCoordenadaY.setText(String.valueOf(client.getLongitud()));
                            } else {
                                Log.d("TAG1", "onResponse: clients is null");
                            }
                        } else {
                            Log.d("TAG1", "onResponse: " + response.errorBody());
                        }
                    }

                    @Override
                    public void onFailure(Call<Client> call, Throwable t) {
                        Log.d("TAG1", "onFailure: " + t.getMessage());
                    }
                });

                break;
        }







        // llamar el boton de btnEditar para editar el cliente
                binding.btnEditar.setOnClickListener(v -> {

                    //obtener los datos del formulario
                    String nombre = binding.editTextNombre.getText().toString();
                    //String apellido = binding.editTextApellidos.getText().toString();
                    String telefono = binding.editTextTelefono.getText().toString();
                    String email = binding.editTextCorreo.getText().toString();
                    String fechaNacimiento = sdf.format(fechaActual);
                    //int estado = Integer.parseInt(binding.editTextEstado.getText().toString());
                    String estadoSeleccionado = (String) binding.editTextEstado.getSelectedItem();
                    int estado = estados.get(estadoSeleccionado);
                    //int municipio = Integer.parseInt(binding.editTextMunicipio.getText().toString());
//                    String estado = binding.editTextEstado.getText().toString();
//                    String municipio = binding.editTextMunicipio.getText().toString();
                    String codigoPostal = binding.editTextCodigoPostal.getText().toString();
                    String calle = binding.editTextCalle.getText().toString();
                    String colonia = binding.editTextColonia.getText().toString();
                    String referencia = binding.editTextReferencia.getText().toString();
                    double latitud = Double.parseDouble(binding.editTextCoordenadaX.getText().toString());
                    double longitud = Double.parseDouble(binding.editTextCoordenadaY.getText().toString());

                    //obtener instancia del ViewModel
                    ViewModel myViewModel = new ViewModelProvider(this).get(ViewModel.class);

                    //llamar al método de editar cliente del ViewModel
                    //myViewModel.actualizarCliente(cliente.getIdCliente(), nombre, apellido, telefono, email, fechaNacimiento, estado, municipio, codigoPostal, calle, colonia, referencia, latitud, longitud);
                    myViewModel.actualizarClienteCall7(idCliente,nombre, telefono, email, referencia, calle, colonia, codigoPostal, municipioId, estado, latitud, longitud, fechaNacimiento);

                    //configurar el resultado que se devolverá al activity principal
                    Intent resultIntent = new Intent();
                    setResult(RESULT_OK, resultIntent);

                    // startActivity a MainActivity y finish a esta actividad
                    startActivity(new Intent(this, MainActivity.class));

                });

        // Llamar el boton de obtener coordenadas para irme al activity de mapaActivity
        binding.btnObtenerCoordenadas.setOnClickListener(v -> {
            //pasarle el idCliente a MapaActivity con putExtra
            Intent intent = new Intent(this, MapaActivity2.class);
            intent.putExtra("idCliente3", idCliente);
            //toast con el idCliente
            Toast.makeText(this, "id del cliente listo para pasarselo al mapaActivity " + idCliente, Toast.LENGTH_SHORT);

            startActivity(new Intent(this, MapaActivity2.class));
        });

    }


}
