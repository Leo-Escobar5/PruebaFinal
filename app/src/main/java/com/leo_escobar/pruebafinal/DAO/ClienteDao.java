package com.leo_escobar.pruebafinal.DAO;

import com.leo_escobar.pruebafinal.data.Cliente;

import java.util.List;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface ClienteDao {

    @Insert
    void insertarCliente(Cliente cliente);

    @Query("SELECT * FROM cliente")
    List<Cliente> obtenerClientes();

    @Query("SELECT * FROM cliente WHERE idCliente = :clienteId")
    Cliente obtenerClientePorId(int clienteId);

    //editar cliente
    @Query("UPDATE cliente SET nombres= :nombres, apellidos= :apellidos, telefono= :telefono, correo= :correo, fechaCreacion= :fechaCreacion, estado= :estado, municipio= :municipio, codigoPostal= :codigoPostal, calle= :calle, colonia= :colonia, referencia= :referencia, coordenadaX= :coordenadaX, coordenadaY= :coordenadaY WHERE idCliente = :clienteId")
    void editarCliente(int clienteId, String nombres, String apellidos, String telefono, String correo, String fechaCreacion, String estado, String municipio, String codigoPostal, String calle, String colonia, String referencia, double coordenadaX, double coordenadaY);

    @Query("DELETE FROM cliente WHERE idCliente = :clienteId")
    void eliminarCliente(int clienteId);


}

