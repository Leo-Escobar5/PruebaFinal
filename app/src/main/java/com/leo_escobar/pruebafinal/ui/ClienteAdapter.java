package com.leo_escobar.pruebafinal.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.leo_escobar.pruebafinal.R;
import com.leo_escobar.pruebafinal.data.Client;
import com.leo_escobar.pruebafinal.data.Cliente;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.ViewHolderDatos> implements Filterable {

    private final int tamañoOriginal;
    ArrayList<Client> clients;
    Context context;
    private AlertDialog alertDialog;
    private ArrayList<Client> clientesOriginales;
    private ArrayList<Client> clientesFiltrados;

    public ClienteAdapter(ArrayList<Client> clients, Context context) {
        Log.i("ClienteAdapter", "estoy en el constructor");
        this.clients = clients;
        this.context = context;
        this.clientesOriginales = new ArrayList<>(clients);
        this.clientesFiltrados = new ArrayList<>(clients);
        this.tamañoOriginal = clients.size();

    }


    private OnItemClickListener onItemClickListener;


    public interface OnItemClickListener{
        void onItemClick(Client client);
    }
    public interface OnDeleteClickListener{
        void onDeleteClick(Client client, int position);
    }
    private OnDeleteClickListener onDeleteClickListener;
    public void setOnDeleteClickListener(OnDeleteClickListener listener) {
        onDeleteClickListener = listener;
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public void onItemClick(int position){
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(clients.get(position));
        }
    }


    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //log indicando que funciona el onCreateViewHolder
        Log.i("ClienteAdapter", "estoy en el onCreateViewHolder");
        //usar el DataBinding para inflar el layout
        View view = LayoutInflater.from(context).inflate(R.layout.pf_list_item, parent, false);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {
        //log indicando que funciona el onBindViewHolder
        Log.i("ClienteAdapter", "estoy en el onBindViewHolder");
        Client client = clientesFiltrados.get(position);

        holder.textViewNombres.setText(client.getRazon_social());
        holder.textViewTelefono.setText(client.getTelefono());
        holder.textViewCorreo.setText(client.getCorreo());
        holder.textViewDireccion.setText(client.getEstado_id() + ", " + client.getCiudad_id() + ", " + client.getCalle() + ", " + client.getColonia());


        holder.textViewNombres.setText(client.getRazon_social());
        holder.textViewTelefono.setText(client.getTelefono());
        holder.textViewCorreo.setText(client.getCorreo());
        holder.textViewDireccion.setText(client.getEstado_id() + ", " + client.getCiudad_id() + ", " + client.getCalle() + ", " + client.getColonia());

    }

    @Override
    public int getItemCount() {

        //log de tamaño de la lista
        Log.i("ClienteAdapter", "Tamaño de la lista desde getItemCount : " + clients.size());

        //log de tamaño de clientesFiltrados
        Log.i("ClienteAdapter", "Tamaño de clientesFiltrados desde getItemCount : " + clientesFiltrados.size());
        //log de tamaño de clientesOriginales
        Log.i("ClienteAdapter", "Tamaño de clientesOriginales desde getItemCount : " + clientesOriginales.size());


        //toast de prueba
        //Toast.makeText(context, "Tamaño de la lista desde getItemCount : " + clients.size(), Toast.LENGTH_SHORT).show();
        return clientesFiltrados.isEmpty() ? tamañoOriginal : clientesFiltrados.size();


    }







    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String query = constraint.toString().toLowerCase().trim();
                ArrayList<Client> filteredList = new ArrayList<>();

                if (query.isEmpty()) {

                    //log indicando que el query está vacío
                    Log.i("ClienteAdapter", "Query vacío");
                    //indicar el tamaño de clientesOriginales
                    Log.i("ClienteAdapter", "Tamaño de clientesOriginales: " + clientesOriginales.size());
                    //indicar el tamaño de clientes
                    Log.i("ClienteAdapter", "Tamaño de clientes: " + clients.size());
                    //llenar la lista con todos los clientes
                    filteredList.addAll(clients);



                } else {
                    for (Client client : clients) {
                        if (client.getRazon_social().toLowerCase().contains(query) || client.getTelefono().toLowerCase().contains(query) || client.getCorreo().toLowerCase().contains(query)) {
                            filteredList.add(client);
                        }
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                filterResults.count = filteredList.size();
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                ArrayList<Client> filteredList = (ArrayList<Client>) results.values;
                clientesFiltrados.clear();
                clientesFiltrados.addAll(filteredList);
                notifyDataSetChanged();

                if (filteredList.size() == 0) {
                    Toast.makeText(context, "No se encontraron resultados", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(context, "Se encontraron " + filteredList.size() + " resultados", Toast.LENGTH_SHORT).show();
                }



            }

        };
    }





    public class ViewHolderDatos extends RecyclerView.ViewHolder{

        public TextView textViewNombres;
        public TextView textViewTelefono;
        public TextView textViewCorreo;
        public TextView textViewDireccion;

        public Button eliminarButton;
        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            textViewNombres = itemView.findViewById(R.id.nombres);
            textViewTelefono = itemView.findViewById(R.id.telefono);
            textViewCorreo = itemView.findViewById(R.id.correo);
            textViewDireccion = itemView.findViewById(R.id.direccion);
            eliminarButton = itemView.findViewById(R.id.eliminarButton);
            eliminarButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Eliminar");
                    builder.setMessage("¿Estás seguro de que deseas eliminar a este usuario?");

                    builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(onDeleteClickListener != null)
                                onDeleteClickListener.onDeleteClick(clients.get(position), position);
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Acción a realizar si el usuario hace clic en No
                        }
                    });

                    AlertDialog alertDialog = builder.create();

                    // Personalizar los colores de los botones
                    alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                        @Override
                        public void onShow(DialogInterface dialog) {
                            Button positiveButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                            positiveButton.setTextColor(context.getColor(android.R.color.white));
                            //poner el fondo tipo boton redondo color azul
                            positiveButton.setBackgroundColor(context.getColor(R.color.blue));


                            Button negativeButton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                            negativeButton.setTextColor(context.getColor(android.R.color.white));
                            negativeButton.setBackgroundColor(context.getColor(R.color.red));
                        }
                    });


                    alertDialog.show();

                }
            });



            //agregar el listener al item
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    onItemClick(position);
                }
            });
        }


    }

    public void setItems(List<Client> items){
        //agregar los items a la lista
        clients.clear();
        clients.addAll(items);
        //log de nombres de los clientes
        for (Client cliente : clients){
            Log.d("Cliente", cliente.getRazon_social());
        }
        notifyDataSetChanged();
    }






}