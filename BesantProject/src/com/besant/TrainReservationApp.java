package com.besant;

import java.util.*;

class Train {
    private String destination;
    private List<String> availability;
    private int totalSeats;
    private Set<Integer> bookedSeats;

    public Train(String destination, List<String> availability, int totalSeats) {
        this.destination = destination;
        this.availability = availability;
        this.totalSeats = totalSeats;
        this.bookedSeats = new HashSet<>();
    }

    public String getDestination() {
        return destination;
    }

    public List<String> getAvailability() {
        return availability;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public Set<Integer> getBookedSeats() {
        return bookedSeats;
    }

    public boolean bookSeats(int numSeats, List<Integer> selectedSeats) {
        if (numSeats <= totalSeats - bookedSeats.size()) {
            for (int i = 0; i < numSeats; i++) {
                int seatNumber = selectedSeats.get(i);
                if (seatNumber > 0 && seatNumber <= totalSeats && !bookedSeats.contains(seatNumber)) {
                    bookedSeats.add(seatNumber);
                } else {
                    // Rollback if invalid seat number is chosen
                    for (int j = 0; j < i; j++) {
                        bookedSeats.remove(selectedSeats.get(j));
                    }
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public double calculateCost(int numSeats) {
        // For simplicity, let's assume the cost is $10 per seat
        return numSeats * 100.0;
    }
}

class ReservationSystem {
    Map<String, Train> trains;

    public ReservationSystem() {
        initializeTrains();
    }

    private void initializeTrains() {
        trains = new HashMap<>();
        trains.put("Chennai", new Train("Chennai", List.of("14-01-2024 15:00", "14-01-2024 18:30", "15-01-2024 8:45"), 100));
        trains.put("Bangalore", new Train("Bangalore", List.of("14-01-2024 16:25", "14-01-2024 19:00"), 100));
        trains.put("Mumbai", new Train("Mumbai", List.of("14-01-2024 14:00", "14-01-2024 17:30"), 100));
    }

    public void displayDestinations() {
        System.out.println("Available Destinations:");
        for (String destination : trains.keySet()) {
            System.out.println(destination);
        }
    }

    public void displayAvailability(String destination) {
        if (trains.containsKey(destination)) {
            Train train = trains.get(destination);
            System.out.println("Availability for " + destination + " destination:");
            for (String time : train.getAvailability()) {
                System.out.println(time);
            }
            System.out.println("Total number of seats: " + train.getTotalSeats());
        } else {
            System.out.println("Invalid destination.");
        }
    }

    public void bookTicket(String destination, String datetime, int numSeats, List<Integer> selectedSeats) {
        if (trains.containsKey(destination)) {
            Train train = trains.get(destination);

            // Book seats and calculate cost
            if (train.bookSeats(numSeats, selectedSeats)) {
                double cost = train.calculateCost(numSeats);

                // Display confirmation message
                System.out.println("Booking successful!");
                System.out.println("Cost of the ticket: $" + cost);
                System.out.println("Seats booked: " + train.getBookedSeats());
            } else {
                System.out.println("Invalid seat selection or not enough seats available.");
            }
        } else {
            System.out.println("Invalid destination.");
        }
    }
}

public class TrainReservationApp {
    public static void main(String[] args) {
        ReservationSystem reservationSystem = new ReservationSystem();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Train Reservation System!");

        // Display available destinations
        reservationSystem.displayDestinations();

        // Get destination choice from the user
        System.out.print("Enter your destination: ");
        String destination = scanner.nextLine();
        
      //  System.out.println("Enter the date and time ");
      //  String datetime = scanner.nextLine();

        // Display availability for the chosen destination
        reservationSystem.displayAvailability(destination);
        System.out.println("Enter the date and time ");
        String datetime = scanner.nextLine();


        // Get number of seats from the user
        System.out.print("Enter the number of seats you want to book: ");
        int numSeats = scanner.nextInt();

        // Display total number of seats and prompt to choose seat numbers
        Train train = reservationSystem.trains.get(destination);
        System.out.println("Total number of seats for the train: " + train.getTotalSeats());
        System.out.print("Select seat number(s) separated by spaces (e.g., 1 2): ");
        List<Integer> selectedSeats = new ArrayList<>();
        for (int i = 0; i < numSeats; i++) {
            selectedSeats.add(scanner.nextInt());
        }

        // Book the ticket and display confirmation
        reservationSystem.bookTicket(destination,datetime, numSeats, selectedSeats);

        scanner.close();
    }
}
/*import java.util.*;

class Train {
    private String destination;
    private Map<String, List<String>> availability;
    private int totalSeats;
    private Set<Integer> bookedSeats;

    public Train(String destination, Map<String, List<String>> availability, int totalSeats) {
        this.destination = destination;
        this.availability = availability;
        this.totalSeats = totalSeats;
        this.bookedSeats = new HashSet<>();
    }

    public String getDestination() {
        return destination;
    }

    public Map<String, List<String>> getAvailability() {
        return availability;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public Set<Integer> getBookedSeats() {
        return bookedSeats;
    }

    public boolean bookSeats(int numSeats, List<Integer> selectedSeats) {
        if (numSeats <= totalSeats - bookedSeats.size()) {
            for (int i = 0; i < numSeats; i++) {
                int seatNumber = selectedSeats.get(i);
                if (seatNumber > 0 && seatNumber <= totalSeats && !bookedSeats.contains(seatNumber)) {
                    bookedSeats.add(seatNumber);
                } else {
                    // Rollback if invalid seat number is chosen
                    for (int j = 0; j < i; j++) {
                        bookedSeats.remove(selectedSeats.get(j));
                    }
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public double calculateCost(int numSeats) {
        // For simplicity, let's assume the cost is $10 per seat
        return numSeats * 10.0;
    }

    public boolean isDateTimeAvailable(String date, String time) {
        List<String> availableTimes = availability.get(date);
        return availableTimes != null && availableTimes.contains(time);
    }
}

class ReservationSystem {
    Map<String, Train> trains;

    public ReservationSystem() {
        initializeTrains();
    }

    private void initializeTrains() {
        trains = new HashMap<>();

        Map<String, List<String>> chennaiAvailability = new HashMap<>();
        chennaiAvailability.put("14-01-2024", List.of("15:00", "18:30", "08:45"));
        trains.put("Chennai", new Train("Chennai", chennaiAvailability, 100));

        Map<String, List<String>> bangaloreAvailability = new HashMap<>();
        bangaloreAvailability.put("14-01-2024", List.of("16:25", "19:00"));
        trains.put("Bangalore", new Train("Bangalore", bangaloreAvailability, 100));

        Map<String, List<String>> mumbaiAvailability = new HashMap<>();
        mumbaiAvailability.put("14-01-2024", List.of("14:00", "17:30"));
        trains.put("Mumbai", new Train("Mumbai", mumbaiAvailability, 100));
    }

    public void displayDestinations() {
        System.out.println("Available Destinations:");
        for (String destination : trains.keySet()) {
            System.out.println(destination);
        }
    }

    public void displayAvailability(String destination) {
        if (trains.containsKey(destination)) {
            Train train = trains.get(destination);
            System.out.println("Availability for " + destination + " destination:");
            for (Map.Entry<String, List<String>> entry : train.getAvailability().entrySet()) {
                System.out.println("Date: " + entry.getKey() + ", Times: " + entry.getValue());
            }
            System.out.println("Total number of seats: " + train.getTotalSeats());
        } else {
            System.out.println("Invalid destination.");
        }
    }

    public void bookTicket(String destination, String date, String time, int numSeats, List<Integer> selectedSeats) {
        if (trains.containsKey(destination)) {
            Train train = trains.get(destination);

            // Check if the chosen date and time are valid
            if (train.getAvailability().containsKey(date) && train.getAvailability().get(date).contains(time)) {

                // Book seats and calculate cost
                if (train.bookSeats(numSeats, selectedSeats)) {
                    double cost = train.calculateCost(numSeats);

                    // Display confirmation message
                    System.out.println("Booking successful!");
                    System.out.println("Cost of the ticket: $" + cost);
                    System.out.println("Seats booked: " + train.getBookedSeats());
                } else {
                    System.out.println("Invalid seat selection or not enough seats available.");
                }

            } else {
                System.out.println("Invalid date or time selection. Please check train availability and choose again.");
            }
        } else {
            System.out.println("Invalid destination.");
        }
    }
}

public class TrainReservationApp {
    public static void main(String[] args) {
        ReservationSystem reservationSystem = new ReservationSystem();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Train Reservation System!");

        // Display available destinations
        reservationSystem.displayDestinations();

        // Get destination choice from the user
        System.out.print("Enter your destination: ");
        String destination = scanner.nextLine();

        // Display availability for the chosen destination
        reservationSystem.displayAvailability(destination);

        // Get date and time choice from the user
        System.out.print("Enter date (e.g., 14-01-2024): ");
        String date = scanner.nextLine();
        System.out.print("Enter time (e.g., 15:00): ");
        String time = scanner.nextLine();

        // Get number of seats from the user
        System.out.print("Enter the number of seats you want to book: ");
        int numSeats = scanner.nextInt();

        // Display total number of seats and prompt to choose seat numbers
        Train train = reservationSystem.trains.get(destination);
        System.out.println("Total number of seats for the train: " + train.getTotalSeats());
        System.out.print("Select seat number(s) separated by spaces (e.g., 1 2): ");
        List<Integer> selectedSeats = new ArrayList<>();
        for (int i = 0; i < numSeats; i++) {
            selectedSeats.add(scanner.nextInt());
        }

        // Book the ticket and display confirmation
        reservationSystem.bookTicket(destination, date, time, numSeats, selectedSeats);

        scanner.close();
    }
}*/









