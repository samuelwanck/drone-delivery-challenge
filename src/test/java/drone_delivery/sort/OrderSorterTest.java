/*
 * Author: Samuel Wanck
 */

package drone_delivery.sort;

import drone_delivery.DroneDelivery;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OrderSorterTest {

    @Test
    void testInvalidOrderLine() {
        // Given
        String[] args = new String[]{"C:\\Users\\Sam\\Projects\\drone-delivery-challenge\\src\\test\\resources\\invalid_line.txt"};

        // When
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> DroneDelivery.main(args));

        // Then
        assertThat(illegalArgumentException.getMessage(), is("The provided input file contains a line of invalid data.  The format is:\n{orderId} {customerCoordinates} {orderTimestamp}"));
    }

    @Test
    void testInvalidCustomerCoordinates() {
        // Given
        String[] args = new String[]{"C:\\Users\\Sam\\Projects\\drone-delivery-challenge\\src\\test\\resources\\invalid_coordinates.txt"};

        // When
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> DroneDelivery.main(args));

        // Then
        assertThat(illegalArgumentException.getMessage(), is("The provided input file contains invalid customer coordinates.  The format is:\n{N|S}{grid number}{E|W}{grid number}"));
    }

    @Test
    void test() {
        List<Integer> list = new ArrayList<>();
        list.add(0);
        list.add(1, 1);
    }
}
