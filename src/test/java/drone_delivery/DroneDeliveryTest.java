/*
 * Author: Samuel Wanck
 */

package drone_delivery;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DroneDeliveryTest {

    @Test
    void testCommandLineArgumentsAreNull() {
        // Given
        String[] args = null;

        // When
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> DroneDelivery.main(args));

        // Then
        assertThat(illegalArgumentException.getMessage(), is("No arguments were found.  The complete file path to the orders input must be provided."));
    }

    @Test
    void testNoCommandLineArgumentsFound() {
        // Given
        String[] args = new String[0];

        // When
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> DroneDelivery.main(args));

        // Then
        assertThat(illegalArgumentException.getMessage(), is("No arguments were found.  The complete file path to the orders input must be provided."));
    }

    @Test
    void testInputFileNotFound() {
        // Given
        String[] args = new String[]{"bad_path"};

        // When
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> DroneDelivery.main(args));

        // Then
        assertThat(illegalArgumentException.getMessage(), is("The provided input file could not be found."));
    }

    @Test
    void testOutputFilePathNotFound() {
        // Given
        String[] args = new String[]{"bad_path"};

        // When
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> DroneDelivery.main(args));

        // Then
        assertThat(illegalArgumentException.getMessage(), is("The provided input file could not be found."));
    }

    @Test
    void testSchedule() throws IOException {
        // Given
        String inputFilePath = "src/test/resources/orders.txt";
        String expectedOutput = new String(Files.readAllBytes(Paths.get("src/test/resources/expected_output.txt")));

        // When
        DroneDelivery.main(new String[]{inputFilePath});

        // Then
        String actualOutput = new String(Files.readAllBytes(Paths.get("delivery_schedule.txt")));
        assertThat(actualOutput.equals(expectedOutput.replaceAll("\\r", "")), is(true));
    }
}
