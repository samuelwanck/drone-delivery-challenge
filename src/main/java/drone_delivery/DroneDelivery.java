/*
 * Author: Samuel Wanck
 */

package drone_delivery;

import drone_delivery.enums.ErrorMessages;
import drone_delivery.model.Order;
import drone_delivery.sort.OrderSorter;
import drone_delivery.write.DroneScheduleWriter;

import java.io.*;
import java.util.List;
import java.util.logging.Logger;

import static drone_delivery.error.ErrorHandler.handleArgumentError;

public class DroneDelivery {

    private static final Logger LOGGER = Logger.getLogger(DroneDelivery.class.getName());
    private static final String OUTPUT_FILE_PATH = "delivery_schedule.txt";

    /**
     * main method to execute drone delivery launches estimator
     * @param args - should include 1 argument: complete file path for input file
     * @throws IOException - if there's an internal error writing the output file
     */
    public static void main(String[] args) throws IOException {
        if(args == null || args.length == 0) {
            handleArgumentError(ErrorMessages.NO_ARGUMENTS.getMessage());
        }
        else {
            BufferedReader bufferedReader = createBufferedReaderForFile(args[0]);

            List<Order> orders = OrderSorter.sortOrders(bufferedReader);

            File outputFile = new File(OUTPUT_FILE_PATH);
            LOGGER.info("Output file path: " + outputFile.getAbsolutePath());
            DroneScheduleWriter.writeSchedule(orders, new BufferedWriter(new FileWriter(outputFile)));
        }
        LOGGER.info("Drone delivery scheduling is complete");
    }

    /**
     * Creates a BufferedReader using the provided filePath.
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
