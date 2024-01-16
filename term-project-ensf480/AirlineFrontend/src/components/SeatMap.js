import React, { useState, useEffect } from "react";
import { Row, Col, Button, Form } from "react-bootstrap";
import Payment from "./Payment";
import axios from "axios";
import Modal from "react-bootstrap/Modal";

function SeatMap({ goHome, chosenFlight, role, email, name }) {
  const [flight, setFlight] = useState(null);
  const [selectedSeats, setSelectedSeats] = useState([]);
  const [seats, setSeats] = useState([]);
  const [showPayment, setShowPayment] = useState(false);
  const [showLegendModal, setShowLegendModal] = useState(false);
  const [showSeatInformation, setShowSeatInformation] = useState(false);

  const handleCloseLegendModal = () => setShowLegendModal(false);
  const handleShowLegendModal = () => setShowLegendModal(true);

  const handleCloseSeatInformation = () => setShowSeatInformation(false);
  const handleShowSeatInformation = () => setShowSeatInformation(true);

  const displaySeatInformation = () => {
    handleShowSeatInformation();
  };

  useEffect(() => {
    setFlight(chosenFlight);
    console.log(chosenFlight)
    const generateSeats = () => {
      const rows = 15;
      const cols = 6;
      const seats = [];

      const seatTypes = ["Business", "Comfort", "Regular"];

      for (let row = 1; row <= rows; row++) {
        const seatType =
          row <= chosenFlight.aircraft.lastBusinessRow ? "Business" : row <= chosenFlight.aircraft.lastComfortRow ? "Comfort" : "Regular";

        for (let col = 1; col <= cols; col++) {
          const seatNumber = `${row}${String.fromCharCode(64 + col)}`;
          const seatAvailable = chosenFlight.seatMap.seats[(row - 1) * 6 + col - 1].seatAvailable === true;
          seats.push({
            seatNumber,
            seatType,
            seatAvailable,
            price:
              seatType === "Business"
                ? 200
                : seatType === "Comfort"
                ? 150
                : 100,
          });
        }
      }
      return seats;
    };

    const allSeats = generateSeats();
    setSeats(allSeats);
    console.log(allSeats);
  }, []);

  const getSeatBackgroundColor = (seat) => {
    if (!seat || !seat.seatAvailable) {
      return "black";
    } else {
      switch (seat.seatType) {
        case "Business":
          return selectedSeats.includes(seat) ? "#009771" : "#01114E";
        case "Comfort":
          return selectedSeats.includes(seat) ? "#009771" : "#0D6EFD";
        case "Regular":
          return selectedSeats.includes(seat) ? "#009771" : "#55D2FE";
        default:
          return "light";
      }
    }
  };

  const handleSeatClick = (seat) => {
    console.log(seat);
    setSelectedSeats((prevSelectedSeats) => {
      if (prevSelectedSeats.includes(seat)) {
        // removes seat if it was previously selected
        return prevSelectedSeats.filter(
          (selectedSeat) => selectedSeat !== seat
        );
      } else {
        // add seat to selected seats
        return [...prevSelectedSeats, seat];
      }
    });
  };

  const handleConfirmSelection = () => {
    if (selectedSeats.length > 0) {
      setShowPayment(true);
    }
  };

  const goBack = (chosenSeats) => {
    setSelectedSeats(chosenSeats);
    setShowPayment(false);
  };

  return (
    <>
      {showPayment ? (
        <Payment
          goHome={goHome}
          goBack={goBack}
          chosenFlight={chosenFlight}
          selectedSeats={selectedSeats}
          name={name}
          email={email}
          role={role}
        />
      ) : (
        <>
          <div className="d-flex justify-content-between">
            <h1>Seat Map</h1>
            <Button
              variant="primary"
              onClick={handleShowLegendModal}
              style={{ height: "40px" }}
            >
              Legend
            </Button>
          </div>

          <div>
            {Array.from({ length: 15 }, (_, rowIndex) => (
              <div key={rowIndex}>
                {seats
                  .filter((seat, index) => Math.floor(index / 6) === rowIndex)
                  .map((seat, index) => (
                    <Button
                      key={index}
                      style={{
                        backgroundColor: getSeatBackgroundColor(seat),
                        color: "white",
                        fontSize: "12px",
                        width: "60px",
                        height: "35px",
                        margin:
                          index === 1 || index === 3
                            ? "0 30px 10px 0"
                            : "0 5px 10px 0",
                      }}
                      size="sm"
                      disabled={!seat.seatAvailable}
                      onClick={() => handleSeatClick(seat)}
                    >
                      {seat.seatNumber}
                    </Button>
                  ))}
              </div>
            ))}
            <Button
              variant="primary"
              onClick={handleConfirmSelection}
              style={{ marginTop: "20px" }}
            >
              Confirm Seat Selection
            </Button>
            <Button
              variant="primary"
              onClick={displaySeatInformation}
              style={{ marginTop: "20px", marginLeft: "20px" }}
            >
              View Selected Seat Information
            </Button>
          </div>
          <Modal show={showLegendModal} onHide={handleCloseLegendModal}>
            <Modal.Header closeButton>
              <Modal.Title>Seat Legend</Modal.Title>
            </Modal.Header>
            <Modal.Body>
              <Button
                style={{
                  backgroundColor: "#585858",
                  pointerEvents: "none",
                }}
              >
                Unavailable
              </Button>{" "}
              <Button
                style={{
                  backgroundColor: "#009771",
                  pointerEvents: "none",
                }}
              >
                Selected
              </Button>{" "}
              <Button
                style={{
                  backgroundColor: "#01114E",
                  pointerEvents: "none",
                }}
              >
                Business
              </Button>{" "}
              <Button
                style={{
                  backgroundColor: "#0D6EFD",
                  pointerEvents: "none",
                }}
              >
                Comfort
              </Button>{" "}
              <Button
                style={{
                  backgroundColor: "#55D2FE",
                  pointerEvents: "none",
                }}
              >
                Regular
              </Button>{" "}
            </Modal.Body>
            <Modal.Footer>
              <Button variant="secondary" onClick={handleCloseLegendModal}>
                Close
              </Button>
            </Modal.Footer>
          </Modal>
          <Modal show={showSeatInformation} onHide={handleCloseSeatInformation}>
            <Modal.Header closeButton>
              <Modal.Title>Selected Seat Information</Modal.Title>
            </Modal.Header>
            <Modal.Body>
              {selectedSeats.map((seat, index) => (
                <div key={index}>
                  <p>Seat Number: {seat.seatNumber}</p>
                  <p>Seat Type: {seat.seatType}</p>
                  <p>Seat Price: {seat.price}</p>
                  <hr />
                </div>
              ))}
            </Modal.Body>
            <Modal.Footer>
              <Button variant="secondary" onClick={handleCloseSeatInformation}>
                Close
              </Button>
            </Modal.Footer>
          </Modal>
        </>
      )}
    </>
  );
}

export default SeatMap;
