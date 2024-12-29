import java.math.BigDecimal;

public class TicketingSystem {
    private final int ticketId;
    private final String eventName;
    private final BigDecimal ticketPrice;

    public TicketingSystem(int ticketId, String eventName, BigDecimal ticketPrice) {
        this.ticketId = ticketId;
        this.eventName = eventName;
        this.ticketPrice = ticketPrice;
    }

    @Override
    public String toString() {
        return "TicketingSystem{" +
                "ticketId=" + ticketId +
                ", eventName='" + eventName + '\'' +
                ", ticketPrice=" + ticketPrice +
                '}';
    }
}
