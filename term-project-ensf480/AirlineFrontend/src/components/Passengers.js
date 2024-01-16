import React, { useState, useEffect } from "react";
import Table from "react-bootstrap/Table";
import Button from "react-bootstrap/Button";
import axios from "axios";

function Passengers({ goBack, flight }) {
  const [passengers, setPassengers] = useState([]);

  // should be getting an array seats with passenger names
  useEffect(() => {
    axios.get('http://localhost:8080/flightCrew/browsePassengers/' + flight.flightNumber,
    { 
      method: 'get',
      headers: {
        'Accept': 'application/json, text/plain, */*',
        'Content-Type': 'application/json',
      },
      'credentials': 'same-origin'
    })
      .then(response => {
        if (response.status === 200) {  // valid response from database
          setPassengers(response.data);
        }
      })
      .catch(error => {
        console.error('Error fetching passengers:', error);
      });
    //setPassengers([
      //{ name: "Passenger 1", seat: "A1", seatType: "Regular" },
      //{ name: "Passenger 2", seat: "B2", seatType: "Business" },
    //]);
  }, []); 

  return (
    <div className="passengers-table">
      <div className="d-flex justify-content-between align-items-center">
        <h1>Passengers for Flight {flight.flightNumber}</h1>
      </div>
      <Table hover>
        <thead>
          <tr>
            <th>Email</th>
            <th>Seat</th>
            <th>Seat Class</th>
          </tr>
        </thead>
        <tbody>
          {passengers.map((passenger, index) => (
            <tr key={index}>
              <td>{passenger.seatBookedBy}</td>
              <td>{passenger.seatNumber}</td>
              <td>{passenger.seatType}</td>
            </tr>
          ))}
        </tbody>
      </Table>
    </div>
  );
}

export default Passengers;
