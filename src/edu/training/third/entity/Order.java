package edu.training.third.entity;

/**
 * Created by Roman on 11.10.2016.
 */
public class Order {
    private long cookingTime;

    public Order(long cookingTime) {
        this.cookingTime = cookingTime;
    }

    public long getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(long cookingTime) {
        this.cookingTime = cookingTime;
    }
}
