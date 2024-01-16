import React, { useState, useEffect } from "react";
import {
  Container,
  Row,
  Col,
  Card,
  Form,
  Button,
  Modal,
} from "react-bootstrap";
import axios from "axios";

function Payment({ goHome, goBack, chosenFlight, selectedSeats, role, email, name }) {
  const seatNumbers = selectedSeats.map((seat) => seat.seatNumber).join(", ");

  const [showConfirmation, setShowConfirmation] = useState(false);

  const [totalPrice, setTotalPrice] = useState(
    selectedSeats.reduce((total, seat) => (total + seat.price) * 1.05, 0)
  );

  const [hasTicketInsurance, setHasTicketInsurance] = useState(false);
  const [hasFreeVoucher, setHasFreeVoucher] = useState(false);
  const [voucherApplied, setVoucherApplied] = useState(false);

  const [creditCard, setCreditCard] = useState({
    cardNumber: "1111111111111111",
    expiryDate: "11/1111",
    cvv: "111",
  });
  console.log(creditCard)

  const calculatePrice = () => {
    const subTotal = selectedSeats.reduce(
      (total, seat) => total + seat.price,
      0
    );
    const tax = subTotal * 0.05;
    let updatedPrice = subTotal + tax;
  
    if (hasTicketInsurance) {
      updatedPrice += 100;
    }
  
    if (voucherApplied) {
      const cheapestTicketPrice = findCheapestTicketPrice();
      updatedPrice -= cheapestTicketPrice;
    }
  
    setTotalPrice(updatedPrice > 0 ? updatedPrice : 0);
  };

  const findCheapestTicketPrice = () => {
    if (selectedSeats.length === 0) return 0;

    let cheapestPrice = selectedSeats[0].price;
    for (let i = 1; i < selectedSeats.length; i++) {
      if (selectedSeats[i].price < cheapestPrice) {
        cheapestPrice = selectedSeats[i].price;
      }
    }
    return cheapestPrice;
  };

  const handleUseVoucher = () => {
    setVoucherApplied(!voucherApplied);
  };

  // when we load the page we want to see if the user is a registered
  // if they are, we need to see if they have a free voucher
  useEffect(() => {
    if (role === "registered-user") {
      axios
        .get("http://localhost:8080/premiumUser/getVoucher", {
          params: {
            email: email
          }
        })
        .then((response) => {
          if (response.status === 200) {
            setHasFreeVoucher(response.data);
          }
        })
        .catch((error) => {
          console.error("Error fetching tickets:", error);
        });
    }
    
  }, []);
  console.log(selectedSeats.price)

  // when we purchase we should be changing the seat availability
  // in the database, validating the credit card info, and updating ticket
  // insurance if they chose it.
  // (Long flightID, List<Seat> seats, boolean hasTicketInsurance, CreditCard creditCard, String email) {
  const handleConfirmPurchase = () => {
    console.log(selectedSeats)
    for(let i = 0; i < selectedSeats.length; i++){
      selectedSeats[i]["flightNumber"] = hasTicketInsurance;
      selectedSeats[i]["flightNumber"] = chosenFlight.flightNumber;
    }
    console.log(selectedSeats);
    axios.post("http://localhost:8080/payment/process", {
        flightNumber: chosenFlight.flightNumber,
        flightID: chosenFlight.id,
        seats: selectedSeats,
        hasTicketInsurance: hasTicketInsurance,
        creditCard: creditCard,
        email: email,
        name: name
      })
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
      console.log(totalPrice);
      console.log(email);
      axios.put(`http://localhost:8080/user/takeMoney/${totalPrice}/${email}`)
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
    console.log(selectedSeats)
    setShowConfirmation(false);
    goHome()
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    setShowConfirmation(true);
  };

  const handleTicketInsurance = () => {
    setHasTicketInsurance(!hasTicketInsurance);
  };

  useEffect(() => {
    calculatePrice();
  }, [selectedSeats, hasTicketInsurance, voucherApplied]);

  return (
    <>
      <div className="d-flex justify-content-between align-items-center">
        <h1>
          Flight {chosenFlight.flightNumber} from {chosenFlight.departure} to{" "}
          {chosenFlight.destination}
        </h1>
      </div>
      <Row className="justify-content-center">
        <Col md="9" lg="7" xl="5">
          <Card>
            <Card.Body>
              <Card.Title className="d-flex justify-content-between mb-0">
                <p className="text-muted mb-0">Selected Seats: {seatNumbers}</p>
                <p className="mb-0">${totalPrice} (tax included)</p>
              </Card.Title>
            </Card.Body>
            <div className="rounded-bottom" style={{ backgroundColor: "#eee" }}>
              <Card.Body>
                <Row className="justify-content-center">
                  <Col xs="10">
                    {selectedSeats.map((seat, index) => (
                      <div key={index}>
                        <p>
                          Seat Number: {seat.seatNumber}, Seat Type:{" "}
                          {seat.seatType}, Seat Price: ${seat.price}.
                        </p>
                      </div>
                    ))}
                  </Col>
                  <Col xs="10">
                    <Form>
                      <Form.Group controlId="formCardNumber">
                        <Form.Label>Credit Card Number</Form.Label>
                        <Form.Control
                          type="text"
                          placeholder="1234 5678 1234 5678"
                          onChange={(e) =>
                            setCreditCard({
                              ...creditCard,
                              cardNumber: e.target.value,
                            })
                          }
                        />
                      </Form.Group>
                      <Row className="mb-3">
                        <Col>
                          <Form.Group controlId="formExpire">
                            <Form.Label>Credit Card Expiry Date</Form.Label>
                            <Form.Control
                              type="text"
                              placeholder="MM/YYYY"
                              onChange={(e) =>
                                setCreditCard({
                                  ...creditCard,
                                  expiryDate: e.target.value,
                                })
                              }
                            />
                          </Form.Group>
                        </Col>
                        <Col>
                          <Form.Group controlId="formCVV">
                            <Form.Label>Credit Card CVV</Form.Label>
                            <Form.Control
                              type="text"
                              placeholder="CVV"
                              onChange={(e) =>
                                setCreditCard({
                                  ...creditCard,
                                  cvv: e.target.value,
                                })
                              }
                            />
                          </Form.Group>
                        </Col>
                      </Row>
                      <Button
                        variant="primary"
                        style={{ marginLeft: "20px", marginRight: "5px" }}
                        type="submit"
                        onClick={handleSubmit}
                      >
                        Confirm Payment
                      </Button>
                      <Button
                        onClick={() => goBack(selectedSeats)}
                        variant="primary"
                        style={{ marginLeft: "5px", marginRight: "5px" }}
                      >
                        Cancel Payment
                      </Button>
                      <Button
                        variant="primary"
                        onClick={handleTicketInsurance}
                        style={{ marginLeft: "5px", marginRight: "5px" }}
                      >
                        {hasTicketInsurance
                          ? "Cancel Insurance"
                          : "Add Ticket Insurance"}
                      </Button>
                      {role === "registered-user" && hasFreeVoucher && ( 
                        <Button
                          variant="primary"
                          style={{ marginTop: "5px", marginRight: "10px" }}
                          onClick={handleUseVoucher}
                        >
                          {voucherApplied
                            ? "Cancel Free Ticket Voucher"
                            : "Use Free Ticket Voucher"}
                        </Button>
                      )}
                    </Form>
                  </Col>
                </Row>
              </Card.Body>
            </div>
          </Card>
        </Col>
      </Row>
      <Modal show={showConfirmation} onHide={() => setShowConfirmation(false)}>
        <Modal.Header closeButton>
          <Modal.Title>Confirm Purchase</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          Confirm purchase of Flight {chosenFlight.flightNumber} from{" "}
          {chosenFlight.departure} to {chosenFlight.destination}?
        </Modal.Body>
        <Modal.Footer>
          <Button
            variant="secondary"
            onClick={() => setShowConfirmation(false)}
          >
            Close
          </Button>
          <Button variant="primary" onClick={handleConfirmPurchase}>
            Confirm Purchase
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
}

export default Payment;
