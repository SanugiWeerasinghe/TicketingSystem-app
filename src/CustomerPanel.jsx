import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './CustomerPanel.css';

const CustomerPanel = () => {
  const [events, setEvents] = useState([]);
  const [bookedTickets, setBookedTickets] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchEvents = async () => {
      try {
        const response = await axios.get('http://localhost:8080/api/ticket/available');
        setEvents(response.data);
      } catch (error) {
        console.error('Error fetching events:', error);
      } finally {
        setLoading(false);
      }
    };
    fetchEvents();
  }, []);

  const handleBookTicket = async (eventId) => {
    try {
      await axios.put(`http://localhost:8080/api/ticket/purchase/${eventId}`);
      setEvents(events.map(event =>
        event.id === eventId
          ? { ...event, availableTickets: event.availableTickets - 1 }
          : event
      ));
      setBookedTickets([...bookedTickets, { eventId }]);
    } catch (error) {
      console.error('Error booking ticket:', error);
      alert('Failed to book ticket. Please try again.');
    }
  };

  const handleCancelTicket = async (eventId) => {
    try {
      await axios.put(`http://localhost:8080/api/ticket/cancel/${eventId}`);
      setBookedTickets(bookedTickets.filter(ticket => ticket.eventId !== eventId));
      setEvents(events.map(event =>
        event.id === eventId
          ? { ...event, availableTickets: event.availableTickets + 1 }
          : event
      ));
    } catch (error) {
      console.error('Error canceling ticket:', error);
      alert('Failed to cancel ticket. Please try again.');
    }
  };

  return (
    <div className="panel-container">
      <h2>Customer Dashboard</h2>

      {/* Available Events */}
      <div className="events-section">
        <h3>Available Events</h3>
        {loading ? <p>Loading events...</p> : (
          <table>
            <thead>
              <tr>
                <th>Event Name</th>
                <th>Price</th>
                <th>Available Tickets</th>
                <th>Action</th>
              </tr>
            </thead>
            <tbody>
              {events.map(event => (
                <tr key={event.id}>
                  <td>{event.event}</td>
                  <td>{event.price}</td>
                  <td>{event.availableTickets}</td>
                  <td>
                    {event.availableTickets > 0 ? (
                      <button onClick={() => handleBookTicket(event.id)}>
                        Book
                      </button>
                    ) : (
                      <button disabled>Sold Out</button>
                    )}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>

      {/* Booked Events */}
      <div className="booked-events-section">
        <h3>Your Booked Events</h3>
        <table>
          <thead>
            <tr>
              <th>Event Name</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            {bookedTickets.map((ticket, index) => {
              const event = events.find(e => e.id === ticket.eventId);
              return (
                <tr key={index}>
                  <td>{event ? event.event : 'Event not found'}</td>
                  <td>
                    <button onClick={() => handleCancelTicket(ticket.eventId)}>
                      Cancel
                    </button>
                  </td>
                </tr>
              );
            })}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default CustomerPanel;
