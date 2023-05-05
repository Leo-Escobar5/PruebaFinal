package com.leo_escobar.pruebafinal.DAO;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.leo_escobar.pruebafinal.data.Cliente;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Cliente.class}, version = 1)
public abstract class ClienteDatabase extends RoomDatabase {

    public abstract ClienteDao clienteDao();
    private static final int NUMBER_OF_THREADS = 4;

    private static volatile ClienteDatabase INSTANCE;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    public static ClienteDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ClienteDatabase.class) {
                if (INSTANCE == null) {
                    if (context == null) {
                        throw new IllegalStateException("Contexto nulo en getDatabase()");
                    }
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    ClienteDatabase.class, "cliente_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}

