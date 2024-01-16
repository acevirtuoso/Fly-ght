import React, { useState, useEffect } from "react";
import Table from "react-bootstrap/Table";
import axios from "axios";
import Button from "react-bootstrap/Button";
import { Modal, Form } from "react-bootstrap";

function CrewInfo({ goHome, chosenFlight, role, email, name }) {
  const [crewMembers, setCrewMembers] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [newCrewMember, setNewCrewMember] = useState({
    flightNumber: "",
    name: "",
  });

  const handleCloseModal = () => {
    setShowModal(false);
  };

  const handleShowModal = () => {
    setShowModal(true);
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setNewCrewMember({
      ...newCrewMember,
      [name]: value,
    });
  };

  const fetchCrewMembers = () => {
    axios
      .get("http://localhost:8080/flightCrew/getAllCrew", {
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
          setCrewMembers(response.data);
        }
      })
      .catch((error) => {
        console.error("Error fetching crew members:", error);
      });
  };

  useEffect(() => {
    fetchCrewMembers();
  }, []);

  const handleAddCrewMember = () => {
    axios
      .post("http://localhost:8080/systemAdmin/addCrew", newCrewMember)
      .then((response) => {
        if (response.status === 200) {
          console.log("Crew member added successfully");
          fetchCrewMembers(); // Fetch updated crew member list
          handleCloseModal(); // Close the modal after adding
        }
      })
      .catch((error) => {
        console.error("Error adding crew member:", error);
      });
  };

  const handleDeleteCrewMember = (crew) => {
    console.log(crew);
    axios
      .delete("http://localhost:8080/systemAdmin/removeCrew", {
        data: crew,
        headers: {
          "Content-Type": "application/json", // Specify content type if needed
        },
      })
      .then((response) => {
        if (response.status === 200) {
          console.log("Crew member deleted successfully");
          fetchCrewMembers();
        }
      })
      .catch((error) => {
        console.error("Error deleting crew member:", error);
      });
  };

  return (
    <>
      <div className="d-flex justify-content-between align-items-center">
        <h1>Crews</h1>
        <div>
          <Button onClick={handleShowModal}>Add Crew Member</Button>
        </div>
      </div>
      <Table hover>
        <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Registered Flight</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          {crewMembers.map((crew, index) => (
            <tr key={index}>
              <td>{crew.id}</td>
              <td>{crew.name}</td>
              <td>{crew.flightNumber}</td>
              <td>
                <Button
                  variant="danger"
                  size="sm"
                  onClick={() => handleDeleteCrewMember(crew)}
                >
                  Delete
                </Button>
              </td>
            </tr>
          ))}
        </tbody>
      </Table>
      {/* Modal for adding crew member */}
      <Modal show={showModal} onHide={handleCloseModal}>
        <Modal.Header closeButton>
          <Modal.Title>Add Crew Member</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form>
            <Form.Group controlId="flightNumber">
              <Form.Label>Flight Number</Form.Label>
              <Form.Control
                style={{ maxWidth: "465px" }}
                type="text"
                name="flightNumber"
                value={newCrewMember.flightNumber}
                onChange={handleChange}
              />
            </Form.Group>
            <Form.Group controlId="name">
              <Form.Label>Name</Form.Label>
              <Form.Control
                style={{ maxWidth: "465px" }}
                type="text"
                name="name"
                value={newCrewMember.name}
                onChange={handleChange}
              />
            </Form.Group>
          </Form>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleCloseModal}>
            Close
          </Button>
          <Button variant="primary" onClick={handleAddCrewMember}>
            Add Crew Member
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
}

export default CrewInfo;
