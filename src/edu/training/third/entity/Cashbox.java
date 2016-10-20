package edu.training.third.entity;

import edu.training.third.manager.RestaurantManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


/**
 * Created by Roman on 10.10.2016.
 */
public class Cashbox {
    private int cashboxId;
    private ArrayDeque<Client> queue;
    private ReadWriteLock wrLock = new ReentrantReadWriteLock();
    private static final Logger LOG = LogManager.getLogger();

    public Cashbox(int id) {
        cashboxId = id;
        queue = new ArrayDeque<Client>();
    }

    public void serveClient(Client client) {
        try {
            if (wrLock.writeLock().tryLock(client.getWaitingTime(), TimeUnit.MILLISECONDS)) { // waiting in the queue for the waiting time
                try {
                    if (!client.equals(queue.peek())) { // checking is client's turn to be served
                        return;
                    }
                    queue.poll(); // serving client
                    System.out.println(this + " serving " + client + " for " + client.getOrder().getCookingTime() + " mills!");
                    TimeUnit.MILLISECONDS.sleep(client.getOrder().getCookingTime());
                    client.setServed(true);
                } catch (InterruptedException e) {
                    LOG.error(e);
                } finally {
                    wrLock.writeLock().unlock();
                }
            } else { // waiting time is over, switching client to another cashbox
                RestaurantManager.findOptimalCashboxForClient(Restaurant.getInstance(), client);
            }
        } catch (InterruptedException e) {
            LOG.error(e);
        }
    }

    public void addToQueue(Client client) {
        wrLock.writeLock().lock();
        try {
            queue.add(client);
        } finally {
            wrLock.writeLock().unlock();
        }
    }

    public void removeFromQueue(Client client) {
        wrLock.writeLock().lock();
        try {
            if (!queue.isEmpty()) {
                queue.remove(client);
            }
        } finally {
            wrLock.writeLock().unlock();
        }
    }

    public long timeInQueue() {
        wrLock.readLock().lock();
        try {
            return queue.stream().mapToLong((c) -> c.getOrder().getCookingTime()).sum();
        } finally {
            wrLock.readLock().unlock();
        }
    }

    public int getCashboxId() {
        wrLock.readLock().lock();
        try {
            return cashboxId;
        } finally {
            wrLock.readLock().unlock();
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cashbox cashbox = (Cashbox) o;

        return cashboxId == cashbox.cashboxId;

    }

    @Override
    public int hashCode() {
        return cashboxId;
    }

    @Override
    public String toString() {
        return "Cashbox #" + cashboxId;
    }
}
