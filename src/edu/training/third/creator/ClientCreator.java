package edu.training.third.creator;

import edu.training.third.entity.Client;
import edu.training.third.entity.Order;
import edu.training.third.entity.Restaurant;
import edu.training.third.manager.RestaurantManager;


import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Roman on 18.10.2016.
 */
public class ClientCreator {
    private static int clientId = 0;
    private static final long INF = 10_000_000;
    private static Random rand = new Random();
    private int amountOfClients;
    private ArrayList<Client> clients;

    public ClientCreator(int amountOfClients) {
        this.amountOfClients = amountOfClients;
        clients = new ArrayList<>();
    }

    public ArrayList<Client> getClients(){
        for (int i = 0; i < amountOfClients; i++) {
            Client c = createClient();
            RestaurantManager.findOptimalCashboxForClient(Restaurant.getInstance(), c);
            clients.add(c);
        }
        return clients;
    }

    private Client createClient() {
        Client c = new Client(++clientId, rand.nextInt(3000) + 1000, new Order(rand.nextInt(8000) + 1000));
        if(c.getClientId() % 2 == 0) {
            c.setWaitingTime(INF);
        }
        return c;
    }
}
