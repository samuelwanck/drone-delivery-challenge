/*
 * Author: Samuel Wanck
 */

package drone_delivery.sort;

import drone_delivery.enums.DateTimeFormatterEnum;
import drone_delivery.enums.ErrorMessages;
import drone_delivery.model.Order;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static drone_delivery.error.ErrorHandler.handleArgumentError;
import static java.time.temporal.ChronoUnit.MINUTES;

public class OrderSorter {

    private static final Pattern COORDINATE_PATTERN = Pattern.compile("\\D(\\d+)\\D(\\d+)");

    private OrderSorter() {
        // Constructor should be inaccessible for this utility class
    }

    /**
     * Reads the input file and parses each line as a separate
     * Order.  Each order is inserted into a list in ascending order
     * based on a sum of the order time and the distance the drone must
     * travel from the launch facility to the customer destination
     * @param bufferedReader
     * @return
     */
    public static List<Order> sortOrders(BufferedReader bufferedReader) {
        List<Order> orders = new ArrayList<>();

        String line;
        boolean hasMoreLines = true;
        while(hasMoreLines) {
            line = readLine(bufferedReader);
            if(line != null) {
                String[] tokens = line.split(" ");
                if(tokens.length < 3) {
                    handleArgumentError(ErrorMessages.INVALID_INPUT_LINE.getMessage());
                } else {
                    Order order = parseOrder(tokens);
                    int indexToInsertAt = orders.isEmpty() ? 0 : findIndexToInsertAt(order, orders);
                    orders.add(indexToInsertAt, order);
                }
            }
            else {
                hasMoreLines = false;
            }
        }
        return orders;
    }

    /**
     * Reads a line from the input file
     * @param bufferedReader
     * @return
     */
    private static String readLine(BufferedReader bufferedReader) {
        String line = null;
        try {
            line = bufferedReader.readLine();
        } catch (IOException e) {
            handleArgumentError(e.getMessage());
        }
        return line;
    }

    /**
     * Parses the input file line and creates an
     * Order object representing the order
     * @param tokens
     * @return
     */
    private static Order parseOrder(String[] tokens) {
        Order order = null;
        Matcher coordinateDistances = COORDINATE_PATTERN.matcher(tokens[1]);
        LocalTime droneStartTime = LocalTime.of(6, 0, 0);
        if(coordinateDistances.find()) {
            long totalDistance = Long.parseLong(coordinateDistances.group(1)) + Long.parseLong(coordinateDistances.group(2));

            LocalTime orderTime = LocalTime.parse(tokens[2], DateTimeFormatterEnum.HOURS_MINUTES_SECONDS.getDateTimeFormatter());
            long timeOffset = droneStartTime.until(orderTime, MINUTES);

            // if order was made before 6:00 AM, effective order time will be 6:00 AM
            LocalTime adjustedOrderTime = timeOffset >=0 ? orderTime : droneStartTime;
            // if order was made before 6:00 AM, priority will be set to 6:00 AM
            long adjustedTimeOffset = timeOffset >= 0 ? timeOffset : 0;

            order = new Order(tokens[0], adjustedOrderTime, totalDistance + adjustedTimeOffset, totalDistance);
        } else {
            handleArgumentError(ErrorMessages.INVALID_CUSTOMER_COORDINATES.getMessage());
        }
        return order;
    }

    /**
     * Does a binary search on the list of orders based on priority number
     * Returns the lowest index for which all orders to the right
     * have an equal or greater priority number and all orders to the left
     * have a lesser priority number.
     * @param order
     * @param orders
     * @return
     */
    private static int findIndexToInsertAt(Order order, List<Order> orders) {
        int low = 0;
        int high = orders.size() - 1;
        int mid = 0;
        boolean indexNotFound = true;
        while(indexNotFound) {
            mid = low + ((high - low) / 2);
            Order midOrder = orders.get(mid);
            if(low == mid || high == mid) {
                if(orders.get(high).getPriorityNumber() < order.getPriorityNumber()) {
                    mid = high;
                }
                if(midOrder.getPriorityNumber() < order.getPriorityNumber()) {
                    mid++;
                }
                indexNotFound = false;
            } else {
                if(midOrder.getPriorityNumber() > order.getPriorityNumber()) {
                    high = mid;
                }
                else {
                    low = mid;
                }
            }
        }
        return mid;
    }
}
