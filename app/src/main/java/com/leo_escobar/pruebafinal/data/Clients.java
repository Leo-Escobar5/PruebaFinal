package com.leo_escobar.pruebafinal.data;

import java.util.List;

public class Clients {

    private List<Client> clients;

    public Clients(List<Client> clients) {
        this.clients = clients;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }
}
