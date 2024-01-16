import React, { useState, useEffect } from "react";
import Button from "react-bootstrap/Button";
import Table from "react-bootstrap/Table";
import Navbar from "react-bootstrap/Navbar";
import Nav from "react-bootstrap/Nav";
import "./DefaultHome.css";
import Bookings from "./Bookings";
import axios from "axios";
import Form from "react-bootstrap/Form";
import FormControl from "react-bootstrap/FormControl";
import SeatMap from "./SeatMap";
import Passengers from "./Passengers";
import Modal from "react-bootstrap/Modal";

function AirlineAgentHome({ role, email, name, signOut }) {
  const [flights, setFlights] = useState([]);
  const [showBookings, setShowBookings] = useState(false);
  const [searchTerm, setSearchTerm] = useState("");
  const [selectedFlight, setSelectedFlight] = useState(null);
  const [showModal, setShowModal] = useState(false);
  const [viewSeatMap, setViewSeatMap] = useState(false);
  const [viewPassengers, setViewPassengers] = useState(false);

  const fetchFlights = () => {
    axios.get('http://localhost:8080/airlineAgent/browseFlights', { 
      method: 'get',
      headers: {
        'Accept': 'application/json, text/plain, */*',
        'Content-Type': 'application/json',
      },
      'credentials': 'same-origin'
    })
    .then(response => {
      if (response.status === 200) {
        setFlights(response.data);
      }
    })
    .catch(error => {
      console.error('Error fetching flights:', error);
    });
  };

  useEffect(() => {
    fetchFlights(); // Load flights when the component mounts
  }, []); // Empty dependency array ensures it only runs once on mount

  const viewFlights = () => {
    fetchFlights(); // Fetch flights when the "Flights" button is clicked
    setSelectedFlight(null);
    setShowBookings(false);
    setShowModal(false);
    setViewSeatMap(false);
    setViewPassengers(false);
  };

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

  const viewBookings = () => {
    setShowBookings(true);
    setShowModal(false);
    setViewSeatMap(false);
    setViewPassengers(false);
  };

  // filtered flights based on destination
  const filteredFlights = flights.filter((flight) =>
    flight.destination.toLowerCase().includes(searchTerm.toLowerCase())
  );

  const handleOpenModal = () => {
    setShowModal(true);
  };

  const handleCloseModal = () => {
    setShowModal(false);
  };

  const handleFlightSelection = (flight) => {
    setSelectedFlight(flight);
    handleOpenModal();
  };

  const handleSeatMapSelection = () => {
    setViewSeatMap(true);
  };

  const handlePassengersSelection = () => {
    setViewPassengers(true);
  };

  return (
    <div>
      <Navbar variant="dark">
        <div className="container-fluid justify-content-between">
          <Navbar.Brand>Fly-ght Club Airlines - Airline Agent</Navbar.Brand>
          <Nav>
            <Nav.Link href="#flights" onClick={viewFlights}>
              Flights
            </Nav.Link>
            <Nav.Link href="#bookings" onClick={viewBookings}>
              My Bookings
            </Nav.Link>
          </Nav>
          <Button variant="outline-light" onClick={signOut}>
            Sign Out
          </Button>
        </div>
      </Navbar>
      <div className="home-page text-center mt-4 px-3">
        {viewPassengers && selectedFlight ? (
          <Passengers flight={selectedFlight} goBack={viewFlights}/>
        ) : (
          <>
            {showBookings ? (
              <Bookings name={name} email={email} role={role} />
            ) : selectedFlight && viewSeatMap ? (
              <SeatMap
                chosenFlight={selectedFlight}
                name={name}
                email={email}
                role={role}
                goHome={viewFlights}
              />
            ) : (
              <>
                <div className="d-flex justify-content-between align-items-center">
                  <h1>Flights</h1>
                  <div className="mx-auto">
                    <Form>
                      <FormControl
                        type="text"
                        placeholder="Search destination..."
                        value={searchTerm}
                        onChange={(e) => setSearchTerm(e.target.value)}
                      />
                    </Form>
                  </div>
                </div>
                <Table hover>
          
                  <thead>
                    <tr>
                      <th>Flight Number</th>
                      <th>Departure</th>
                      <th>Destination</th>
                      <th>Departure Time</th>
                      <th>Arrival Time</th>
                      <th>Duration</th>
                    </tr>
                  </thead>
                  <tbody>
                    {filteredFlights.map((flight, index) => (
                      <tr
                        key={index}
                        onClick={() => handleFlightSelection(flight)}
                        style={{ cursor: "pointer" }}
                      >
                        <td>{flight.flightNumber}</td>
                        <td>{flight.departure}</td>
                        <td>{flight.destination}</td>
                        <td>{formattedTime(flight.departureTime)}</td>
                        <td>{formattedTime(flight.arrivalTime)}</td>
                        <td>{flight.duration} minutes</td>
                      </tr>
                    ))}
                  </tbody>
                </Table>
                {selectedFlight && (
                  <Modal show={showModal} onHide={handleCloseModal} centered>
                    <Modal.Header closeButton>
                      <Modal.Title>
                        Flight {selectedFlight.flightNumber} options:
                      </Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                      <div>
                        <Button
                          style={{ margin: "5px" }}
                          variant="primary"
                          onClick={handlePassengersSelection}
                          className="mr-2"
                        >
                          View Passenger List
                        </Button>
                        <Button
                          style={{ margin: "5px" }}
                          variant="primary"
                          onClick={handleSeatMapSelection}
                        >
                          View Seat Map
                        </Button>
                      </div>
                    </Modal.Body>
                    <Modal.Footer>
                      <Button variant="secondary" onClick={handleCloseModal}>
                        Close
                      </Button>
                    </Modal.Footer>
                  </Modal>
                )}
              </>
            )}
          </>
        )}
      </div>
    </div>
  );
}

export default AirlineAgentHome;
