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

function UserInfo({ goHome, chosenFlight, role, email, name }) {
  const [users, setUsers] = useState([]);

  const fetchUsers = () => {
    axios
      .get("http://localhost:8080/systemAdmin/printUsers", {
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
          setUsers(response.data);
        }
      })
      .catch((error) => {
        console.error("Error fetching users:", error);
      });
  };

  useEffect(() => {
    fetchUsers();
  }, []);

  return (
    <>
      <div className="d-flex justify-content-between align-items-center">
        <h1>Users</h1>
      </div>
      <Table hover>
        <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Email</th>
            <th>Password</th>
            <th>User Role</th>
          </tr>
        </thead>
        <tbody>
          {users.map((user, index) => (
            <tr key={index}>
              <td>{user.id}</td>
              <td>{user.name}</td>
              <td>{user.email}</td>
              <td>{user.passwordKey}</td>
              <td>{user.userRole}</td>
            </tr>
          ))}
        </tbody>
      </Table>
    </>
  );
}

export default UserInfo;