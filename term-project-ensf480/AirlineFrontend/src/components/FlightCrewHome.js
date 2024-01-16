import React, { useState, useEffect } from "react";
import Button from "react-bootstrap/Button";
import Table from "react-bootstrap/Table";
import Navbar from "react-bootstrap/Navbar";
import Nav from "react-bootstrap/Nav";
import "./DefaultHome.css";
import axios from "axios";
import Form from "react-bootstrap/Form";
import FormControl from "react-bootstrap/FormControl";
import Passengers from "./Passengers";

function FlightCrewHome({ role, email, name, signOut }) {
  const [flights, setFlights] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [selectedFlight, setSelectedFlight] = useState(null);

  // get the flights that person is working
  const fetchFlights = () => {
    axios.get('http://localhost:8080/flightCrew/browseFlights', { 
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

  const viewFlights = () => {
    fetchFlights();
    setSelectedFlight(null);
  };

  // filtered flights based on destination
  const filteredFlights = flights.filter((flight) =>
    flight.destination.toLowerCase().includes(searchTerm.toLowerCase())
  );

  const handleFlightSelection = (flight) => {
    setSelectedFlight(flight);
  };

  return (
    <div>
      <Navbar variant="dark">
        <div className="container-fluid justify-content-between">
          <Navbar.Brand>Fly-ght Club Airlines - Flight Crew</Navbar.Brand>
          <Nav>
            <Nav.Link href="#flights" onClick={viewFlights}>
              Flights
            </Nav.Link>
          </Nav>
          <Button variant="outline-light" onClick={signOut}>
            Sign Out
          </Button>
        </div>
      </Navbar>
      <div className="home-page text-center mt-4 px-3">
        {selectedFlight ? (
          <Passengers flight={selectedFlight} goBack={viewFlights} />
        ) : (
          <>
            <div className="d-flex justify-content-between align-items-center">
              <h1>Your Flights</h1>
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
          </>
        )}
      </div>
    </div>
  );
}

export default FlightCrewHome;
