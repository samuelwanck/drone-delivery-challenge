/*
 * Author: Samuel Wanck
 */

package drone_delivery.write;

import drone_delivery.model.Order;

import java.io.BufferedWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalTime;
import java.util.ArrayDeque;
import java.util.List;

import static drone_delivery.enums.DateTimeFormatterEnum.HOURS_MINUTES_SECONDS;
import static java.time.temporal.ChronoUnit.HOURS;
import static java.time.temporal.ChronoUnit.MINUTES;

public class DroneScheduleWriter {

    private DroneScheduleWriter() {
        // Constructor should be inaccessible for this utility class
    }

    /**
     * Writes the drone's delivery schedule and calculates NPS
     * If an order is next in priority but has not been ordered yet, it moves into a queue.
     * The queue is checked after each delivery to see if the next delivery in it can be made.
     * (This is a bit of weirdness resulting from knowing when orders will be made before they
     * are placed.)
     * @param orders
     * @param bufferedWriter
     * @throws IOException
     */
    public static void writeSchedule(List<Order> orders, BufferedWriter bufferedWriter) throws IOException {
        LocalTime currentTime = LocalTime.of(6, 0, 0);
        double promoters = 0;
        double detractors = 0;
        ArrayDeque<Order> futureOrderQueue = new ArrayDeque<>();
        int orderIndex = 0;
        while(orderIndex < orders.size() || !futureOrderQueue.isEmpty()) {
            Order currentOrder = null;
            // Check if there are any new orders which can be delivered fast
            if (!futureOrderQueue.isEmpty() && !currentTime.isBefore(futureOrderQueue.peek().getOrderTime())) {
                currentOrder = futureOrderQueue.remove();
            }
            // Else take the next order in the list
            else if(orderIndex < orders.size()){
                currentOrder = orders.get(orderIndex);
                orderIndex++;
            }
            if(currentOrder != null) {
                if (currentTime.isBefore(currentOrder.getOrderTime())) {
                    futureOrderQueue.add(currentOrder);
                } else {
                    bufferedWriter.write(currentOrder.getOrderId() + " " + HOURS_MINUTES_SECONDS.getDateTimeFormatter().format(currentTime) + "\n");
                    // Add time for drone to travel to order destination
                    currentTime = currentTime.plus(currentOrder.getDeliveryTimeMinutes(), MINUTES);
                    if (currentOrder.getOrderTime().until(currentTime, HOURS) < 2) {
                        promoters++;
                    } else if (currentOrder.getOrderTime().until(currentTime, HOURS) > 3) {
                        detractors++;
                    }
                    // Add time to return to launch facility
                    currentTime = currentTime.plus(currentOrder.getDeliveryTimeMinutes(), MINUTES);
                }
            } else {
                // If only future orders can be delivered, wait for the next one to be placed
                currentTime = futureOrderQueue.peek().getOrderTime();
            }
        }
        bufferedWriter.write("\nNPS " + new DecimalFormat("#.###").format((promoters / orders.size() - detractors / orders.size()) * 100));
        bufferedWriter.close();
    }
}
