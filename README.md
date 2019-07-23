Drone Delivery Scheduler

Author: Samuel Wanck

### Solution Execution
To execute the the solution you can perform the following from a terminal:

1) execute "mvn clean install" in the base project folder.  This will compile the java code,
execute the units, and package the compiled code into an executable jar
2) execute "java -cp drone-delivery-challenge-1.0-SNAPSHOT.jar drone_delivery.DroneDelivery {path/to/input/file}"

If you wish to only run the tests:
 - mvn clean test


### Assumptions:
- Time to delivery begins at 6:00 if order time is before 6:00
- The drone cannot move diagonally across a grid block.  It can only north, south, east, or west
- All timestamps are for the same time zone
- The town does not span across different time zones
- Orders are independent, i.e. the drone does not carry multiple orders before returning 
to the launch facility (even for the same destination)
- Orders from the input file do not necessarily need to be sorted by order timestamp
- Drone has no downtime and pickup of next delivery at launch facility is immediate
- The number of orders for any given day will be less than or equal to ~50.  At this volume,
research suggests that the implemented binary insertion sort will outperform quicksort.  
If the volume regularly exceeds 50 (especially into the thousands), then a quicksort implementation
would be more performant.  Its difficult to know which sorting algorithm would be more appropriate
without knowing the volume of orders per day.  Performance testing different implementations would be
ideal.  For the sake of timeliness, I implemented only the binary insertion sort.  In a more comprehensive
solution, the sorting implementation could be decided during runtime based on the number of orders in
the input.  
- The drone doesn't know about an order until the order is made.  This means that even though we have a
list of all of the orders for the day, the drone cannot begin making a delivery for an order before the
order timestamp.  The drone must pick up the next order that has been made, even if an order with a 
shorter delivery time is soon to be made.
- Its okay to fail fast if the input file contains an invalid line (e.g. missing order timestamp) and
not continue.  If we should continue, then the invalid line should be noted for the client user and skipped.
This might have to change depending on how the scheduler would be used.
- The hour value for the provided order timestamps are the clock value (0-23)
- The input file will not contain deliveries that cannot be completed before 10 PM.  This case can be 
accommodated for by scheduling the remaining deliveries for the next day or simply stating that these deliveries
could not be made in the output.  For the sake of timeliness, I did not cover this case.


