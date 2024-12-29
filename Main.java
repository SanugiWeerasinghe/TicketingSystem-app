import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Main menu loop
        while (true) {
            System.out.println("Welcome to the Ticketing System!");
            System.out.println("Please select an option:");
            System.out.println("1. Start the system");
            System.out.println("2. Exit the system");
            System.out.print("Enter your choice (1 for Start, 2 for Exit): ");

            String userResponse = scanner.nextLine().trim();

            // If the user enters '2', exit the program
            if ("2".equals(userResponse)) {
                System.out.println("Exiting the system...");
                break; // Exit the program
            }

            // If the user chooses to start the system
            if ("1".equals(userResponse)) {
                // Configuration setup
                int totalTickets = 0;
                int ticketReleaseRate = 0;
                int customerRetrievalRate = 0;
                int maxTicketCapacity = 0;

                System.out.println("Starting new configuration setup...");

                // Input total tickets
                while (true) {
                    System.out.print("Enter total tickets (must be greater than 0): ");
                    try {
                        totalTickets = Integer.parseInt(scanner.nextLine());
                        if (totalTickets > 0) break;
                        else System.out.println("Total tickets must be greater than 0.");
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a positive integer.");
                    }
                }

                // Input ticket release rate
                while (true) {
                    System.out.print("Enter ticket release rate (must be greater than 0): ");
                    try {
                        ticketReleaseRate = Integer.parseInt(scanner.nextLine());
                        if (ticketReleaseRate > 0)
                            break;
                        else System.out.println("Ticket release rate must be greater than 0.");
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a positive integer.");
                    }
                }

                // Input customer retrieval rate
                while (true) {
                    System.out.print("Enter customer retrieval rate (must be greater than 0): ");
                    try {
                        customerRetrievalRate = Integer.parseInt(scanner.nextLine());
                        if (customerRetrievalRate > 0)
                            break;
                        else System.out.println("Customer retrieval rate must be greater than 0.");
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a positive integer.");
                    }
                }

                // Input max ticket capacity
                while (true) {
                    System.out.print("Enter max ticket capacity (must be greater than total tickets and greater than 0): ");
                    try {
                        maxTicketCapacity = Integer.parseInt(scanner.nextLine());
                        if (maxTicketCapacity > totalTickets && maxTicketCapacity > 0)
                            break;
                        else System.out.println("Max ticket capacity must be greater than total tickets and greater than 0.");
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a positive integer.");
                    }
                }

                // Create Configuration object and save to JSON
                Configuration config = new Configuration(totalTickets, ticketReleaseRate, customerRetrievalRate, maxTicketCapacity);
                config.saveToJson("config.json");

                // Initialize ticket pool
                TicketPool ticketPool = new TicketPool(maxTicketCapacity);

                // Start system
                System.out.println("Ticket System Started successfully.");
                System.out.println("\nStarting vendor and customer threads...");

                // Vendor and customer threads
                Vendor vendor = new Vendor(ticketPool, totalTickets, ticketReleaseRate);
                Customer customer = new Customer(ticketPool, customerRetrievalRate, totalTickets);

                Thread vendorThread = new Thread(vendor, "TicketVendor");
                Thread customerThread = new Thread(customer, "TicketCustomer");

                vendorThread.start();
                customerThread.start();

                // Stop the system based on user input
                System.out.println("System is running. Enter 'S' to terminate the system.");
                while (true) {
                    String input = scanner.nextLine();
                    if (input.equalsIgnoreCase("S")) {
                        System.out.println("\nStopping the system...");
                        vendorThread.interrupt();
                        customerThread.interrupt();
                        try {
                            vendorThread.join();
                            customerThread.join();
                        } catch (InterruptedException e) {
                            System.out.println("Error during thread termination: " + e.getMessage());
                        }
                        break; // Exit the inner loop and return to the main menu
                    }
                }
            } else {
                System.out.println("Invalid input. Please type '1' to start the system or '2' to terminate.");
            }
        }
        scanner.close();  // Close the scanner when the loop ends
    }
}
