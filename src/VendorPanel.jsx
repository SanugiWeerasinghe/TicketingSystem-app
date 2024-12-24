import React, { useState } from 'react';
import axios from 'axios';  // Import axios
import './VendorPanel.css';

const VendorPanel = () => {
  const [tickets, setTickets] = useState([]);
  const [eventName, setEventName] = useState('');
  const [ticketPrice, setTicketPrice] = useState('');
  const [totalTickets, setTotalTickets] = useState(0);
  const [releaseRate, setReleaseRate] = useState(0);
  const [retriavelRate, setRetriavelRate] = useState(0);
  const [maxCapacity, setMaxCapacity] = useState(0);
  const [isRunning, setIsRunning] = useState(false);
  const [intervalId, setIntervalId] = useState(null);
  const [releasedCount, setReleasedCount] = useState(0);

  // Handle adding tickets to the queue and sending to backend
  const handleAddTicketsToQueue = async () => {
    if (releasedCount >= totalTickets) {
      // Stop adding tickets if the released count reaches the total tickets
      clearInterval(intervalId);
      setIsRunning(false);
      return;
    }

    const newTickets = [];
    for (let i = 0; i < releaseRate; i++) {
      if (releasedCount + newTickets.length < totalTickets) {
        newTickets.push({
          price: ticketPrice,
          event: eventName,
          status: 'Available',
          totalTickets: totalTickets,
          releaseRate: releaseRate,
          customerRetrievalRate: retriavelRate,
          maxCapacity: maxCapacity,
        });

        // Send the new ticket data to the backend without ticket name
        try {
          await axios.post('http://localhost:8080/api/ticket/create', newTickets[newTickets.length - 1]);
        } catch (error) {
          console.error('Error adding ticket to the backend:', error);
        }
      }
    }

    // Update local state with the new tickets
    setTickets((prevTickets) => [...prevTickets, ...newTickets]);
    setReleasedCount((prevCount) => prevCount + newTickets.length);
  };

  // Start the ticket release system
  const handleStart = () => {
    if (!eventName || !ticketPrice || totalTickets <= 0 || releaseRate <= 0) {
      alert('Please fill in all fields with valid values before starting.');
      return;
    }

    setIsRunning(true);
    const newIntervalId = setInterval(() => {
      handleAddTicketsToQueue();
    }, 1000); // Release tickets every second
    setIntervalId(newIntervalId);
  };

  // Stop the ticket release system
  const handleStop = () => {
    setIsRunning(false);
    clearInterval(intervalId);
  };

  // Reset the system stats
  const handleReset = () => {
    setTickets([]);
    setEventName('');
    setTicketPrice('');
    setTotalTickets(0);
    setReleaseRate(0);
    setRetriavelRate(0);
    setMaxCapacity(0);
    setReleasedCount(0);
    setIsRunning(false);
    clearInterval(intervalId); // Stop the interval if it's running
  };

  // Remove a ticket from the queue
  const handleRemoveTicket = (ticketId) => {
    setTickets((prevTickets) => prevTickets.filter((ticket) => ticket.id !== ticketId));
  };

  return (
    <div className="panel-container">
      <h2>Vendor Dashboard</h2>

      {/* Configuration Form */}
      <div className="configuration-form">
        <h3>Configuration</h3>
        <form>
          <div>
            <label>Event Name:</label>
            <input
              type="text"
              value={eventName}
              onChange={(e) => setEventName(e.target.value)}
              required
            />
          </div>
          <div>
            <label>Ticket Price:</label>
            <input
              type="number"
              value={ticketPrice}
              onChange={(e) => setTicketPrice(e.target.value)}
              required
            />
          </div>
          <div>
            <label>Total Tickets:</label>
            <input
              type="number"
              value={totalTickets}
              onChange={(e) => setTotalTickets(Number(e.target.value))}
              min="1"
              required
            />
          </div>
          <div>
            <label>Release Rate (tickets per second):</label>
            <input
              type="number"
              value={releaseRate}
              onChange={(e) => setReleaseRate(Number(e.target.value))}
              min="1"
              required
            />
          </div>
          <div>
            <label>Customer Retriavel Rate (tickets per second):</label>
            <input
              type="number"
              value={retriavelRate}
              onChange={(e) => setRetriavelRate(Number(e.target.value))}
              min="0"
              required
            />
          </div>
          <div>
            <label>Maximum Capacity:</label>
            <input
              type="number"
              value={maxCapacity}
              onChange={(e) => setMaxCapacity(Number(e.target.value))}
              min="0"
            />
          </div>
        </form>
      </div>

      {/* Ticket Queue */}
      <div className="ticket-queue">
        <h3>Ticket Queue</h3>
        {tickets.length === 0 ? (
          <p>No tickets added yet.</p>
        ) : (
          <table>
            <thead>
              <tr>
                <th>Price</th>
                <th>Event</th>
                <th>Status</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {tickets.map((ticket) => (
                <tr key={ticket.id}>
                  <td>{ticket.price}</td>
                  <td>{ticket.event}</td>
                  <td>{ticket.status}</td>
                  <td>
                    <button onClick={() => handleRemoveTicket(ticket.id)}>Remove</button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>

      {/* Control Panel */}
      <div className="control-panel">
        <h3>Control Panel</h3>
        <div>
          <button onClick={handleStart} disabled={isRunning || releasedCount >= totalTickets}>
            Start
          </button>
          <button onClick={handleStop} disabled={!isRunning}>
            Stop
          </button>
          <button onClick={handleReset}>Reset</button>
        </div>
      </div>

      {/* System Status */}
      <div className="system-status">
        <h3>System Status</h3>
        <p className={isRunning ? 'running' : 'paused'}>
          System is {isRunning ? 'Running' : 'Paused'}
        </p>
        <p>Tickets Released: {releasedCount}/{maxCapacity > 0 ? maxCapacity : totalTickets}</p>
      </div>
    </div>
  );
};

export default VendorPanel;
