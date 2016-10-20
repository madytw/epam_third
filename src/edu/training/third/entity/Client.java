package edu.training.third.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by Roman on 11.10.2016.
 */
public class Client extends Thread {
    private int clientId;
    private long waitingTime;
    private Cashbox cashbox;
    private Order order;
    private boolean isServed;
    private static final Logger LOG = LogManager.getLogger();

    public Client(int clientId, long waitingTime, Order order) {
        this.clientId = clientId;
        this.waitingTime = waitingTime;
        this.order = order;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public Cashbox getCashbox() {
        return cashbox;
    }

    public void setCashbox(Cashbox cashbox) {
        this.cashbox = cashbox;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public boolean isServed() {
        return isServed;
    }

    public long getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(long waitingTime) {
        this.waitingTime = waitingTime;
    }

    public void setServed(boolean served) {
        isServed = served;
    }

    @Override
    public void run() {
        try {
            while (!isServed) {
                cashbox.serveClient(this);
            }
            System.out.println(this + " is eating!");
            TimeUnit.MILLISECONDS.sleep(new Random().nextInt(2000));
            System.out.println(this + " left the restaurant.");

        } catch (InterruptedException e) {
            LOG.error(e);
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        return clientId == client.clientId;

    }

    @Override
    public int hashCode() {
        return clientId;
    }

    @Override
    public String toString() {
        return "Client #" + clientId;
    }
}
