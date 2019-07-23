/*
 * Author: Samuel Wanck
 */

package drone_delivery.error;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ErrorHandler {

    private ErrorHandler() {
        // The constructor for this utility class should be inaccessible
    }

    private static final Logger LOGGER = Logger.getLogger(ErrorHandler.class.getName());

    public static void handleArgumentError(String errorMessage) {
        LOGGER.log(Level.SEVERE, errorMessage);
        throw new IllegalArgumentException(errorMessage);
    }
}
