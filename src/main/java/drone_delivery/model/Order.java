/*
 * Author: Samuel Wanck
 */

package drone_delivery.model;

import java.time.LocalTime;

public class Order {

    private String orderId;
    private LocalTime orderTime;
    private long priorityNumber;
    private long deliveryTimeMinutes;

    public Order(String orderId, LocalTime orderTime, long priorityNumber, long deliveryTimeMinutes) {
        this.orderId = orderId;
        this.orderTime = orderTime;
        this.priorityNumber = priorityNumber;
        this.deliveryTimeMinutes = deliveryTimeMinutes;
    }

    public String getOrderId() {
        return orderId;
    }

    public LocalTime getOrderTime() {
        return orderTime;
    }

    public long getPriorityNumber() {
        return priorityNumber;
    }

    public long getDeliveryTimeMinutes() {
        return deliveryTimeMinutes;
    }
}
