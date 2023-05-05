package com.leo_escobar.pruebafinal;

import static com.leo_escobar.pruebafinal.BR.viewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.leo_escobar.pruebafinal.data.Client;
import com.leo_escobar.pruebafinal.data.Cliente;
import com.leo_escobar.pruebafinal.databinding.ActivityMainBinding;
import com.leo_escobar.pruebafinal.ui.ClienteAdapter;
import com.leo_escobar.pruebafinal.ui.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ClienteAdapter adapter;
    private ActivityMainBinding viewDataBinding;
    private ViewModel myViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewDataBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(viewDataBinding.getRoot());

        myViewModel = new ViewModelProvider(this).get(ViewModel.class);

        //instanciar el adapter
        adapter = new ClienteAdapter(new ArrayList<>(), this);

        //asignar el adapter al recycler view
        viewDataBinding.eqRecycler.setAdapter(adapter);
        viewDataBinding.eqRecycler.setLayoutManager(new LinearLayoutManager(this));

        //validación de que el token no sea nulo o vacío
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("MiPreferencia", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        //log del token de las preferencias
        Log.d("MainActivity", "Token_preferences: " + token);


        //Toast.makeText(this,"Token_preferences"+ token, Toast.LENGTH_SHORT).show();


        //Toast.makeText(this, token, Toast.LENGTH_SHORT).show();


        //manejar  el click en un item del recycler view
        adapter.setOnItemClickListener(new ClienteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Client client) {
                String mensaje = "ID del cliente seleccionado: " + client.getCliente_id();
                //Toast.makeText(MainActivity.this, mensaje, Toast.LENGTH_SHORT).show();

                //Guardar el Id del cliente seleccionado en una variable en MainActivity
                int idClienteSeleccionado = client.getCliente_id();

                //iniciar la actividad EditarCliente y pasar el id del cliente seleccionado
                Intent intent = new Intent(MainActivity.this, editarCliente.class);

                SharedPreferences sharedPreferences2 = getSharedPreferences("MiPreferencia", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences2.edit();
                editor.putInt("idClienteObtenido", idClienteSeleccionado);
                editor.apply();

                intent.putExtra("edicion","main");


                //intent.putExtra("idCliente", idClienteSeleccionado);
                startActivity(intent);
            }
        });




                //obtener los datos del ViewModel y pasarlos al adapter
                myViewModel.obtenerClientes().observe(this, clients -> {
                    Log.d("MainActivity", "Lista actualizada: " + clients.size());
                    adapter.setItems(clients);
                });


        //llamar el boton para ir al activity de crear cliente
        viewDataBinding.fabAgregarCliente.setOnClickListener(v -> {

            //crear un nuevo cliente
            //Cliente clienteNuevo = new Cliente(4, "Juan", "Pérez", "1234567890", "juanperez@gmail.com", "2021-05-01", "Jalisco", "Guadalajara", "45678", "Calle 1", "Colonia 1", "Referencia 1", 20.0, 20.0);

//obtener instancia del ViewModel
            //ViewModel myViewModel = new ViewModelProvider(this).get(ViewModel.class);



//llamar al método insertarClientePrueba del ViewModel
            //myViewModel.insertarClientePrueba();

            //myViewModel.crearClienteDePrueba(this,  "Juan", "Pérez", "1234567890", "pepe@hotmail.com", "2023-04-28", "Jalisco", "Guadalajara", "45678", "Calle 1", "Colonia 1", "Referencia 1", 20.0, 20.0);

            //pasar el token a la actividad CrearCliente
            //intent.putExtra("token", tokenRecibido);
            //log del token
            Log.d("MainActivity", "Token Recibido: " + token);

            startActivity(new Intent(MainActivity.this, CrearCliente.class));


    });

        adapter.setOnDeleteClickListener(new ClienteAdapter.OnDeleteClickListener(){
            @Override
            public void onDeleteClick(Client client, int position) {
                myViewModel.eliminarClienteCall6(client.getCliente_id());
            }
        });

        viewDataBinding.searchView.clearFocus();

        viewDataBinding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    adapter.getFilter().filter("");
                    //log de que no hay texto
                    Log.d("MainActivity", "No hay texto");


                } else {
                    adapter.getFilter().filter(newText);
                }

                return false;
            }
        });


    }

    private void filterList(String text) {
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            //llamar al método obtenerClientes del ViewModel para actualizar la lista de clientes
            myViewModel.obtenerClientes().observe(this, clients -> {
                adapter.setItems(clients);
            });
        }
    }
}



