public class Customer implements Runnable {
    private final TicketPool ticketPool;
    private final int customerRetrievalRate;
    private final int quantity;

    public Customer(TicketPool ticketPool, int customerRetrievalRate, int quantity) {
        this.ticketPool = ticketPool;
        this.customerRetrievalRate = customerRetrievalRate;
        this.quantity = quantity;
    }


    public void run() {
        for (int i = 0; i < quantity; i++) {
            if (Thread.currentThread().isInterrupted()) {
                System.out.println(Thread.currentThread().getName() + " interrupted. Stopping...");
                break;
            }

            TicketingSystem ticket = ticketPool.buyTicket();
            if (ticket != null) {
                System.out.println(Thread.currentThread().getName() + " has bought a ticket. Ticket is " + ticket);
            }

            try {
                Thread.sleep(customerRetrievalRate * 1000);
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " interrupted during sleep. Stopping...");
                break;
            }
        }
    }
}

