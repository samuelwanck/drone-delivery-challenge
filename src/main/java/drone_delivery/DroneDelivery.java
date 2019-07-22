/*
 * Author: Samuel Wanck
 */

package drone_delivery;

import drone_delivery.enums.ErrorMessages;
import drone_delivery.model.Order;
import drone_delivery.sort.OrderSorter;
import drone_delivery.write.DroneScheduleWriter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.logging.Logger;

import static drone_delivery.error.ErrorHandler.handleArgumentError;

public class DroneDelivery {

    private static final Logger LOGGER = Logger.getLogger(DroneDelivery.class.getName());

    /**
     * main method to execute drone delivery launches estimator
     * @param args - should include 1 argument: complete file path for input file
     */
    public static void main(String[] args) {
        if(args == null || args.length == 0) {
            handleArgumentError(ErrorMessages.NO_ARGUMENTS.getMessage());
        }
        else {
            BufferedReader bufferedReader = createBufferedReaderForFile(args[0]);

            List<Order> orders = OrderSorter.sortOrders(bufferedReader);

            // write the output
            DroneScheduleWriter.writeSchedule(orders);
        }
        LOGGER.info("Drone delivery scheduling is complete");
    }

    /**
     * Helper method for creating a BufferedReader using
     * the provided filePath.
     * @param filePath
     * @return BufferedReader to read the input file with
     */
    private static BufferedReader createBufferedReaderForFile(String filePath) {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(new File(filePath)));
        } catch (FileNotFoundException e) {
            handleArgumentError(ErrorMessages.INPUT_FILE_NOT_FOUND.getMessage());
        }
        return bufferedReader;
    }
}
