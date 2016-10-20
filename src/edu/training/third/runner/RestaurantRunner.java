package edu.training.third.runner;

import edu.training.third.creator.ClientCreator;

/**
 * Created by Roman on 11.10.2016.
 */
public class RestaurantRunner {
    public static void main(String... args) {
        ClientCreator creator = new ClientCreator(12);
        creator.getClients().stream().forEach(c -> c.start());
    }
}
