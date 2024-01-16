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
import Benefits from "./Benefits";
import AircraftInfo from "./AircraftInfo";
import CrewInfo from "./CrewInfo";
import UserInfo from "./UserInfo";
import { Modal } from "react-bootstrap";

function AdminHome({ role, email, name, signOut }) {
  const [flights, setFlights] = useState([]);
  const [showBookings, setShowBookings] = useState(false);
  const [showBenefits, setShowBenefits] = useState(false);
  const [searchTerm, setSearchTerm] = useState("");
  const [selectedFlight, setSelectedFlight] = useState(null);
  const [showModal, setShowModal] = useState(false);
  const [showSeatMap, setShowSeatMap] = useState(false);

  const [newFlight, setNewFlight] = useState({
    aircraftID: "",
    flightNumber: 0,
    departure: "",
    destination: "",
    departureTime: "",
    arrivalTime: "",
    duration: 0,
  });

  const handleCloseModal = () => {
    setShowModal(false);
  };

  const handleShowModal = () => {
    setShowModal(true);
  };

  const handleFlightSelection = (flight) => {
    setSelectedFlight(flight);
    handleShowModal();
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setNewFlight({
      ...newFlight,
      [name]: value,
    });
  };

  const fetchFlights = () => {
    axios
      .get("http://localhost:8080/systemAdmin/browseFlights", {
        method: "get",
        headers: {
          Accept: "application/json, text/plain, */*",
          "Content-Type": "application/json",
        },
        credentials: "same-origin",
      })
      .then((response) => {
        if (response.status === 200) {
          console.log(response.data);
          setFlights(response.data);
        }
      })
      .catch((error) => {
        console.error("Error fetching flights:", error);
      });
  };

  useEffect(() => {
    fetchFlights();
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

  const [showFlights, setShowFlights] = useState(true);
  const [showUsers, setShowUsers] = useState(false);
  const [showAircrafts, setShowAircrafts] = useState(false);
  const [showCrew, setShowCrew] = useState(false);

  const viewFlights = () => {
    setSelectedFlight(null);
    fetchFlights();
    setShowFlights(true);
    setShowUsers(false);
    setShowAircrafts(false);
    setShowCrew(false);
    setShowSeatMap(false);
  };

  const viewUsers = () => {
    setShowFlights(false);
    setShowUsers(true);
    setShowAircrafts(false);
    setShowCrew(false);
    setShowSeatMap(false);
  };

  const viewAircrafts = () => {
    setShowFlights(false);
    setShowUsers(false);
    setShowAircrafts(true);
    setShowCrew(false);
    setShowSeatMap(false);
  };

  const viewCrew = () => {
    setShowFlights(false);
    setShowUsers(false);
    setShowAircrafts(false);
    setShowCrew(true);
    setShowSeatMap(false);
  };
  console.log(flights)

  const handleAddFlight = () => {
    const formattedNewFlight = {
      ...newFlight,
      departureDateTime: new Date(newFlight.departureTime).toISOString(), 
      arrivalDateTime: new Date(newFlight.arrivalTime).toISOString(), 
    };
    console.log(formattedNewFlight);
    axios
      .post("http://localhost:8080/systemAdmin/addFlight", formattedNewFlight)
      .then((response) => {
        if (response.status === 200) {
          console.log("Flight added successfully");
          fetchFlights();
          setShowModal(false);
        }
      })
      .catch((error) => {
        console.error("Error adding flight:", error);
      });
  };

  const handleDeleteFlight = (flight) => {
    console.log(flight);
    axios
      .delete("http://localhost:8080/systemAdmin/removeFlight", {
        data: flight,
        headers: {
          "Content-Type": "application/json",
        },
      })
      .then((response) => {
        if (response.status === 200) {
          console.log("Flight deleted successfully");
          fetchFlights();
        }
      })
      .catch((error) => {
        console.error("Error deleting flight:", error);
      });
  };

  const filteredFlights = flights.filter((flight) =>
    flight.destination.toLowerCase().includes(searchTerm.toLowerCase())
  );

  const handleOptionSelection = (option) => {
    switch (option) {
      case "crew":
        setShowFlights(false);
        setShowUsers(false);
        setShowAircrafts(false);
        setShowCrew(true);
        break;
      case "aircraft":
        setShowFlights(false);
        setShowUsers(false);
        setShowAircrafts(true);
        setShowCrew(false);
        break;
      case "user":
        setShowFlights(false);
        setShowUsers(true);
        setShowAircrafts(false);
        setShowCrew(false);
        break;
      case "seatmap":
        setShowSeatMap(true);
        break;
      default:
        break;
    }
    handleCloseModal();
  };

  return (
    <div>
      <Navbar variant="dark">
        <div className="container-fluid justify-content-between">
          <Navbar.Brand>Fly-ght Club Airlines - Admin</Navbar.Brand>

          <Nav>
            <Nav.Link href="#flights" onClick={viewFlights}>
              Flights
            </Nav.Link>
            <Nav.Link
              href="#users"
              onClick={() => handleOptionSelection("user")}
            >
              Users
            </Nav.Link>
            <Nav.Link
              href="#aircrafts"
              onClick={() => handleOptionSelection("aircraft")}
            >
              Aircrafts
            </Nav.Link>
            <Nav.Link
              href="#crews"
              onClick={() => handleOptionSelection("crew")}
            >
              Crews
            </Nav.Link>
          </Nav>
          <Button variant="outline-light" onClick={signOut}>
            Sign Out
          </Button>
        </div>
      </Navbar>
      <div className="home-page text-center mt-4 px-3">
        {showSeatMap && (
          <SeatMap
            chosenFlight={selectedFlight}
            name={name}
            email={email}
            role={role}
            goHome={viewFlights}
          />
        )}

        {showAircrafts && (
          <AircraftInfo
            chosenFlight={selectedFlight}
            name={name}
            email={email}
            role={role}
            goHome={viewFlights}
          />
        )}
        {showCrew && (
          <CrewInfo
            chosenFlight={selectedFlight}
            name={name}
            email={email}
            role={role}
            goHome={viewFlights}
          />
        )}
        {showUsers && (
          <UserInfo
            chosenFlight={selectedFlight}
            name={name}
            email={email}
            role={role}
            goHome={viewFlights}
          />
        )}
        {!showSeatMap && !showAircrafts && !showCrew && !showUsers && (
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
              <div>
                <Button onClick={handleShowModal}>Add Flight</Button>
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
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                {filteredFlights.map((flight, index) => (
                  <tr
                    key={index}
                    
                    style={{ cursor: "pointer" }}
                  >
                    <td>{flight.flightNumber}</td>
                    <td>{flight.departure}</td>
                    <td>{flight.destination}</td>
                    <td>{formattedTime(flight.departureTime)}</td>
                    <td>{formattedTime(flight.arrivalTime)}</td>
                    <td>{flight.duration} minutes</td>
                    <td>
                      <Button
                        variant="danger"
                        size="sm"
                        onClick={() => handleDeleteFlight(flight)}
                      >
                        Delete
                      </Button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          </>
        )}
      </div>
      <Modal show={showModal} onHide={handleCloseModal}>
        <Modal.Header closeButton>
          <Modal.Title>Add Flight</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form>
            <Form.Group controlId="aircraftID">
              <Form.Label>Aircraft ID</Form.Label>
              <Form.Control
                style={{ maxWidth: "465px" }}
                type="text"
                name="aircraftID"
                value={newFlight.aircraftID}
                onChange={handleChange}
              />
            </Form.Group>
            <Form.Group controlId="flightNumber">
              <Form.Label>Flight Number</Form.Label>
              <Form.Control
                style={{ maxWidth: "465px" }}
                type="number"
                name="flightNumber"
                value={newFlight.flightNumber}
                onChange={handleChange}
              />
            </Form.Group>
            <Form.Group controlId="departure">
              <Form.Label>Departure</Form.Label>
              <Form.Control
                style={{ maxWidth: "465px" }}
                name="departure"
                value={newFlight.departure}
                onChange={handleChange}
              />
            </Form.Group>
            <Form.Group controlId="destination">
              <Form.Label>Destination</Form.Label>
              <Form.Control
                style={{ maxWidth: "465px" }}
                name="destination"
                value={newFlight.destination}
                onChange={handleChange}
              />
            </Form.Group>
            <Form.Group controlId="departureTime">
              <Form.Label>Departure Time</Form.Label>
              <Form.Control
               type="datetime-local"
                style={{ maxWidth: "465px" }}
                name="departureTime"
                value={newFlight.departureTime}
                onChange={handleChange}
              />
            </Form.Group>
            <Form.Group controlId="arrivalTime">
              <Form.Label>Arrival Time</Form.Label>
              <Form.Control
               type="datetime-local"
                style={{ maxWidth: "465px" }}
                name="arrivalTime"
                value={newFlight.arrivalTime}
                onChange={handleChange}
              />
            </Form.Group>
            <Form.Group controlId="duration">
              <Form.Label>Duration (minutes)</Form.Label>
              <Form.Control
                style={{ maxWidth: "465px" }}
                type="number"
                name="duration"
                value={newFlight.duration}
                onChange={handleChange}
              />
            </Form.Group>
          </Form>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleCloseModal}>
            Close
          </Button>
          <Button variant="primary" onClick={handleAddFlight}>
            Add Flight
          </Button>
        </Modal.Footer>
      </Modal>
    </div>
  );
}

export default AdminHome;
