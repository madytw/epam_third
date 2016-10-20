package edu.training.third.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Roman on 11.10.2016.
 */
public class Restaurant {
    private List<Cashbox> cashboxes = new ArrayList<>();
    private static AtomicBoolean isCreated = new AtomicBoolean(false);
    private static Restaurant instance = null;
    private static final int NUM_OF_BOXES = 3;
    private static ReentrantLock lock = new ReentrantLock();

    private Restaurant() {
        for(int i = 1; i<= NUM_OF_BOXES; i++) {
            cashboxes.add(new Cashbox(i));
        }
    }

    public static Restaurant getInstance() {
        if(!isCreated.getAndSet(true)) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = new Restaurant();
                }
                return instance;
            } finally {
                lock.unlock();
            }
        }else {
            return instance;
        }
    }

    public List<Cashbox> getCashboxes() {
        lock.lock();
        try {
            return cashboxes;
        }finally {
            lock.unlock();
        }
    }

}
