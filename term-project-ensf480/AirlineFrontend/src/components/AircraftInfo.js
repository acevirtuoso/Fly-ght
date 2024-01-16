import React, { useState, useEffect } from "react";
import Table from "react-bootstrap/Table";
import axios from "axios";
import Button from "react-bootstrap/Button";
import Navbar from "react-bootstrap/Navbar";
import Nav from "react-bootstrap/Nav";
import "./DefaultHome.css";
import Bookings from "./Bookings";
import Form from "react-bootstrap/Form";
import FormControl from "react-bootstrap/FormControl";
import SeatMap from "./SeatMap";
import Benefits from "./Benefits";
import CrewInfo from "./CrewInfo";
import UserInfo from "./UserInfo";
import { Modal } from "react-bootstrap";

function AircraftInfo(goHome, chosenFlight, role, email, name) {
  const [aircrafts, setAircrafts] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [newAircraft, setNewAircraft] = useState({
    aircraftID: "",
    flightNumber: 0,
    lastBusinessRow: 0,
    lastComfortRow: 0,
    totalBusinessColumns: 0,
    totalRegularColumns: 0,
    rowsNumber: 0,
    columnsNumber: 0,
  });

  const handleCloseModal = () => {
    setShowModal(false);
  };

  const handleShowModal = () => {
    setShowModal(true);
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setNewAircraft({
      ...newAircraft,
      [name]: value,
    });
  };

  const fetchAircrafts = () => {
    axios
      .get("http://localhost:8080/systemAdmin/browseAircafts", {
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
          setAircrafts(response.data);
        }
      })
      .catch((error) => {
        console.error("Error fetching aircrafts:", error);
      });
  };

  useEffect(() => {
    fetchAircrafts();
  }, []);

  const handleAddAircraft = () => {
    axios
      .post("http://localhost:8080/systemAdmin/addAircraft", newAircraft)
      .then((response) => {
        if (response.status === 200) {
          console.log("Aircraft added successfully"); 
          axios
            .put(`http://localhost:8080/systemAdmin/assignAircraft/${newAircraft.aircraftID}`, {
              chosenFlight,
              
            })
            .then((assignResponse) => {
              if (assignResponse.status === 200) {
                fetchAircrafts();
                handleCloseModal();
              }
            })
            .catch((assignError) => {
              console.error("Error assigning aircraft to flight:", assignError);
            });
        }
      })
      .catch((error) => {
        console.error("Error adding aircraft:", error);
      });
  };

  const handleDeleteAircraft = (aircraft) => {
    axios
      .delete("http://localhost:8080/systemAdmin/removeAircraft", {
        data: aircraft,
        headers: {
          "Content-Type": "application/json",
        },
      })
      .then((response) => {
        if (response.status === 200) {
          console.log("Aircraft deleted successfully");
          fetchAircrafts();
        }
      })
      .catch((error) => {
        console.error("Error deleting aircraft:", error);
      });
  };

  return (
    <>
      <div className="d-flex justify-content-between align-items-center">
        <h1>Aircrafts</h1>
        <div>
          <Button onClick={handleShowModal}>Add Aircraft</Button>
        </div>
      </div>
      <Table hover>
        <thead>
          <tr>
            <th>ID</th>
            <th>Aircraft ID</th>
            <th>Columns Number</th>
            <th>Last Business Row</th>
            <th>Last Comfort Row</th>
            <th>Rows Number</th>
            <th>Total Business Columns</th>
            <th>Total Regular Columns</th>
            <th>Flight Number</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {aircrafts.map((aircraft, index) => (
            <tr key={index}>
              <td>{aircraft.id}</td>
              <td>{aircraft.aircraftID}</td>
              <td>{aircraft.columnsNumber}</td>
              <td>{aircraft.lastBusinessRow}</td>
              <td>{aircraft.lastComfortRow}</td>
              <td>{aircraft.rowsNumber}</td>
              <td>{aircraft.totalBusinessColumns}</td>
              <td>{aircraft.totalRegularColumns}</td>
              <td>{aircraft.flight ? aircraft.flight.flightNumber : "N/A"}</td>
              <td>
                <Button
                  variant="danger"
                  size="sm"
                  onClick={() => handleDeleteAircraft(aircraft)}
                >
                  Delete
                </Button>
              </td>
            </tr>
          ))}
        </tbody>
      </Table>
      <Modal show={showModal} onHide={handleCloseModal}>
        <Modal.Header closeButton>
          <Modal.Title>Add Aircraft</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form>
            <Form.Group controlId="aircraftID">
              <Form.Label>Aircraft ID</Form.Label>
              <Form.Control
                style={{ maxWidth: "465px" }}
                type="text"
                name="aircraftID"
                value={newAircraft.aircraftID}
                onChange={handleChange}
              />
            </Form.Group>
            <Form.Group controlId="lastBusinessRow">
              <Form.Label>Last Business Row</Form.Label>
              <Form.Control
                style={{ maxWidth: "465px" }}
                type="number"
                name="lastBusinessRow"
                value={newAircraft.lastBusinessRow}
                onChange={handleChange}
              />
            </Form.Group>
            <Form.Group controlId="lastComfortRow">
              <Form.Label>Last Comfort Row</Form.Label>
              <Form.Control
                style={{ maxWidth: "465px" }}
                type="number"
                name="lastComfortRow"
                value={newAircraft.lastComfortRow}
                onChange={handleChange}
              />
            </Form.Group>
            <Form.Group controlId="totalBusinessColumns">
              <Form.Label>Total Business Columns</Form.Label>
              <Form.Control
                style={{ maxWidth: "465px" }}
                type="number"
                name="totalBusinessColumns"
                value={newAircraft.totalBusinessColumns}
                onChange={handleChange}
              />
            </Form.Group>
            <Form.Group controlId="totalRegularColumns">
              <Form.Label>Total Regular Columns</Form.Label>
              <Form.Control
                style={{ maxWidth: "465px" }}
                type="number"
                name="totalRegularColumns"
                value={newAircraft.totalRegularColumns}
                onChange={handleChange}
              />
            </Form.Group>
            <Form.Group controlId="rowsNumber">
              <Form.Label>Number of Rows</Form.Label>
              <Form.Control
                style={{ maxWidth: "465px" }}
                type="number"
                name="rowsNumber"
                value={newAircraft.rowsNumber}
                onChange={handleChange}
              />
            </Form.Group>
            <Form.Group controlId="columnsNumber">
              <Form.Label>Number of Columns</Form.Label>
              <Form.Control
                style={{ maxWidth: "465px" }}
                type="number"
                name="columnsNumber"
                value={newAircraft.columnsNumber}
                onChange={handleChange}
              />
            </Form.Group>
            <Form.Group controlId="columnsNumber">
              <Form.Label>Flight Number Assigned</Form.Label>
              <Form.Control
                style={{ maxWidth: "465px" }}
                type="number"
                name="flightAssigned"
                value={chosenFlight ? chosenFlight.flightNumber : ""}
                onChange={handleChange}
              />
            </Form.Group>
          </Form>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleCloseModal}>
            Close
          </Button>
          <Button variant="primary" onClick={handleAddAircraft}>
            Add Aircraft
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
}

export default AircraftInfo;
