import React, { useState, useEffect } from "react";
import Button from "react-bootstrap/Button";
import Table from "react-bootstrap/Table";
import Navbar from "react-bootstrap/Navbar";
import Nav from "react-bootstrap/Nav";
import Modal from "react-bootstrap/Modal";
import axios from "axios";

function Bookings({ role, email, name, signOut }) {
  const [tickets, setTickets] = useState([]);
  const [showBookings, setShowBookings] = useState(false);
  const [selectedTicket, setSelectedTicket] = useState(null);
  const [showDeleteNotification, setShowDeleteNotification] = useState(false);
  const [showNonInsuredModal, setShowNonInsuredModal] = useState(false);

  const showConfirmModal = () => {
    if (selectedTicket) {
      setShowDeleteNotification(true);
    }
  };

  
  const getSeatPrice = () => {
    let price = 0;
  
    switch (selectedTicket.seatType) {
      case "Business":
        price += 200;
        break;
      case "Comfort":
        price += 150;
        break;
      case "Regular":
        price += 100;
        break;
      default:
        return "Invalid seat type";
    }
  
    if (selectedTicket.insured === true) {
      price += 100;
    }
  
    return price * 1.05;
  };

  // should delete the ticket from specific user in user database
  // and should make that seat for the flight available
  const cancelTicket = () => {
    if (selectedTicket.insured) {
      axios
        .post(`http://localhost:8080/payment/refund/${selectedTicket.id}`, {
          flightNumber: selectedTicket.flightNumber,
          flightID: selectedTicket.flightID,
          seats: null,
          hasTicketInsurance: selectedTicket.insured,
          creditCard: null,
          email: email,
          name: name,
        })
        .then((response) => {
          if (response.status === 200) {
          }
        })
        .catch((error) => {
          console.error("Error deleting ticket:", error);
        });
      axios
        .post("http://localhost:8080/user/deleteBookedSeat", {
          id: selectedTicket.id,
        })
        .then((response) => {
          if (response.status === 200) {
            setShowDeleteNotification(false);
            if (selectedTicket) {
              const updatedTickets = tickets.filter(
                // need to delete based on both flight number and seat number
                (ticket) =>
                  ticket.flightNumber !== selectedTicket.flightNumber ||
                  ticket.seat !== selectedTicket.seat
              );
              setTickets(updatedTickets);
              setSelectedTicket(null);
            }
          }
        })
        .catch((error) => {
          console.error("Error deleting ticket:", error);
        });
      console.log(selectedTicket)
      
        let price = getSeatPrice();
        console.log(price);
        console.log(email);
        axios.put(`http://localhost:8080/user/refundMoney/${price}/${email}`)
      .then((response) => {
        if (response.status === 200) {
          // valid response from database
          // load flights homepage
          console.log("works yay")
        }
      })
      .catch((error) => {
        console.error("Error fetching tickets:", error);
      });
      setShowDeleteNotification(false);
    } else {
      setShowDeleteNotification(false);
      setShowNonInsuredModal(true);
    }
  };

  const closeNonInsuredModal = () => {
    setShowNonInsuredModal(false);
  };

  const closeConfirmModal = () => {
    setShowDeleteNotification(false);
    setShowNonInsuredModal(false);
  };

  const fetchTickets = () => {
    axios
      .post("http://localhost:8080/user/getUsersTickets", {
        email: email,
      })
      .then((response) => {
        if (response.status === 200) {
          // valid response from database
          setTickets(response.data);
          console.log(response.data);
        }
      })
      .catch((error) => {
        console.error("Error fetching tickets:", error);
      });
  };

  useEffect(() => {
    fetchTickets();
  }, []);

  const formattedTime = (dateTimeString) => {
    const options = {
      year: "numeric",
      month: "numeric",
      day: "numeric",
      hour: "numeric",
      minute: "numeric",
      second: "numeric",
      timeZoneName: "short",
    };
    return new Date(dateTimeString).toLocaleString("en-US", options);
  };

  const handleTicketSelection = (ticket) => {
    setSelectedTicket(ticket);
    setShowDeleteNotification(true);
  };

  return (
    <>
      <div className="d-flex justify-content-between align-items-center">
        <h1>My Bookings</h1>
      </div>
      <Table hover>

        <thead>
          <tr>
            <th>Flight Number</th>
            <th>Origin</th>
            <th>Destination</th>
            <th>Departure Time</th>
            <th>Arrival Time</th>
            <th>Seat</th>
            <th>Insured</th>
            <th>Seat Class</th>
          </tr>
        </thead>
        <tbody>
          {tickets.map((ticket, index) => (
            <tr
              key={index}
              onClick={() => handleTicketSelection(ticket)}
              style={{
                cursor: "pointer",
              }}
            >
              <td>{ticket.flightNumber}</td>
              <td>{ticket.departure}</td>
              <td>{ticket.destination}</td>
              <td>{formattedTime(ticket.departureTime)}</td>
              <td>{formattedTime(ticket.arrivalTime)}</td>
              <td>{ticket.seat}</td>
              <td>{ticket.insured ? "Yes" : "No"}</td>
              <td>{ticket.seatType}</td>
            </tr>
          ))}
        </tbody>
      </Table>

      <Modal show={showNonInsuredModal} onHide={closeNonInsuredModal}>
        <Modal.Header closeButton>
          <Modal.Title>Non-Insured Ticket</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <p>You cannot cancel a non-insured ticket!</p>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={closeNonInsuredModal}>
            Close
          </Button>
        </Modal.Footer>
      </Modal>
      <Modal show={showDeleteNotification} onHide={closeConfirmModal}>
        <Modal.Header closeButton>
          <Modal.Title>Confirm Flight Cancellation?</Modal.Title>
        </Modal.Header>
        <Modal.Body>Are you sure you want to cancel this flight?</Modal.Body>
        {selectedTicket && (
          <Modal.Body>
            Flight {selectedTicket.flightNumber} from {selectedTicket.departure}{" "}
            to {selectedTicket.destination} <br />
            Seat: {selectedTicket.seat}, Price: {selectedTicket.price}, Seat
            Class: {selectedTicket.seatType}
          </Modal.Body>
        )}
        <Modal.Footer>
          <Button variant="secondary" onClick={closeConfirmModal}>
            Close
          </Button>
          <Button variant="danger" onClick={cancelTicket}>
            Confirm Cancellation
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
}

export default Bookings;
