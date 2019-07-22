Drone Delivery Scheduler

Author: Samuel Wanck

### Assumptions:
- Time to delivery begins at 6:00 if order time is before 6:00
- The drone cannot move diagonally across a grid block.  It can only north, south, east, or west
- Deliveries that are not able to be made (and have the drone returned) by 22:00 will be 
pushed to the head of the queue for the following day.  I accommodated this by outputing a datetime
instead of just a time.
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
- The hour value for the provided order timestamps are the clock value (1-24)
