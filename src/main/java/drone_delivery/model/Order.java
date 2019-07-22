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

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public LocalTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalTime orderTime) {
        this.orderTime = orderTime;
    }

    public long getPriorityNumber() {
        return priorityNumber;
    }

    public void setPriorityNumber(long priorityNumber) {
        this.priorityNumber = priorityNumber;
    }

    public long getDeliveryTimeMinutes() {
        return deliveryTimeMinutes;
    }

    public void setDeliveryTimeMinutes(long deliveryTimeMinutes) {
        this.deliveryTimeMinutes = deliveryTimeMinutes;
    }
}
