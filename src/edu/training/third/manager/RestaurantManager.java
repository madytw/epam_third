package edu.training.third.manager;

import edu.training.third.entity.Cashbox;
import edu.training.third.entity.Client;
import edu.training.third.entity.Restaurant;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Roman on 11.10.2016.
 */
public class RestaurantManager {
    private static Lock lock = new ReentrantLock();

    public static void findOptimalCashboxForClient(Restaurant r, Client c) {
        lock.lock();
        try {
            Cashbox optimal = r.getCashboxes().stream().min((o1, o2) -> Long.compare(o1.timeInQueue(), o2.timeInQueue())).get();
            if (c.getCashbox() == null) {
                optimal.addToQueue(c);
                c.setCashbox(optimal);
                System.out.println(c + " was added to the " + optimal + " queue!");
            } else if (!c.getCashbox().equals(optimal)) {
                int oldId = c.getCashbox().getCashboxId();
                c.getCashbox().removeFromQueue(c);
                optimal.addToQueue(c);
                c.setCashbox(optimal);
                System.out.println(c + " was waiting in Cashbox #" + oldId +
                                   " for " + c.getWaitingTime() +
                                   " mills and was moved to "+ c.getCashbox());
            }
        } finally {
            lock.unlock();
        }
    }
}
