/*
 * Author: Samuel Wanck
 */

package drone_delivery.enums;

public enum ErrorMessages {

    NO_ARGUMENTS("No arguments were found.  The complete file path to the orders input must be provided."),
    INPUT_FILE_NOT_FOUND("The provided input file could not be found."),
    INVALID_INPUT_LINE("The provided input file contains a line of invalid data.  The format is:\n{orderId} {customerCoordinates} {orderTimestamp}"),
    INVALID_CUSTOMER_COORDINATES("The provided input file contains invalid customer coordinates.  The format is:\n{N|S}{grid number}{E|W}{grid number}");

    private String message;

    private ErrorMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
