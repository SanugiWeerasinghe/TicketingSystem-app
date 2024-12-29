import java.util.LinkedList;
import java.util.Queue;

public class TicketPool {
    private final Queue<TicketingSystem> ticketQueue;  // Explicit type
    private final int maximumCapacity;

    public TicketPool(int maximumCapacity) {
        this.maximumCapacity = maximumCapacity;
        // Replace diamond operator with explicit type
        this.ticketQueue = new LinkedList<TicketingSystem>();  // Explicit type parameter
    }

    public synchronized void addTickets(TicketingSystem ticket) {
        while (ticketQueue.size() >= maximumCapacity) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " interrupted while waiting to add tickets.");
                return;
            }
        }
        ticketQueue.add(ticket);
        notifyAll();
        System.out.println(Thread.currentThread().getName() + " has added ticket to the Pool. Current size is " + ticketQueue.size());
    }

    public synchronized TicketingSystem buyTicket() {
        while (ticketQueue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " interrupted while waiting to buy tickets.");
                return null;
            }
        }
        TicketingSystem ticket = ticketQueue.poll();
        notifyAll();
        System.out.println(Thread.currentThread().getName() + " has bought a ticket from the pool. Current size is " + ticketQueue.size());
        return ticket;
    }
}
