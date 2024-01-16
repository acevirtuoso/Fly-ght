import React, { useState } from "react";
import Form from "react-bootstrap/Form";
import Navbar from "react-bootstrap/Navbar";
import Nav from "react-bootstrap/Nav";
import Modal from "react-bootstrap/Modal";
import Button from "react-bootstrap/Button";
import "./LoginSignUp.css";
import axios from "axios";

function LoginSignUp({ onLoginSignUp }) {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [name, setName] = useState("");
  const [isSignUp, setIsSignUp] = useState(false);
  const [role, setRole] = useState("");
  const [showInvalidLogin, setShowInvalidLogin] = useState(false);
  const [showInvalidSignUp, setShowInvalidSignUp] = useState(false);

  const handleEmailChange = (e) => {
    setEmail(e.target.value);
  };

  const handlePasswordChange = (e) => {
    setPassword(e.target.value);
  };

  const handleNameChange = (e) => {
    setName(e.target.value);
  };

  const handleCloseLoginModal = () => {
    setShowInvalidLogin(false);
  };

  const handleCloseSignUpModal = () => {
    setShowInvalidSignUp(false);
  };

  const handleSignUp = (e) => {
     axios.post('http://localhost:8080/user/addUser', {
       email: email,
       password: password,
       name: name,
       userRole: "default-user"
     })
       .then(response => {
         if (response.status === 200) {  // valid response from database
            setRole("default-user"); // will always be a default user when signing up
            onLoginSignUp("default-user", name, email);
            console.log(name + ' sign up');
         } else {
           setShowInvalidLogin(true);
         }
       })
       .catch(error => {
         console.error('Error during login:', error);
       });
    console.log("Signed up:", { name, email, password });

    setName("");
    setEmail("");
    setPassword("");
  };

  const handleLogin = (e) => {
    console.log(email)
    axios.post('http://localhost:8080/user/getUser', {
      email: email,
    })
      .then(response => {
        if (response.status === 200) {  // valid response from database
          if (email === response.data.email && password === response.data.passwordKey) {
            console.log(response)
            setEmail(response.data.email);
            setRole(response.data.userRole);
            setName(response.data.name);
            console.log(name + ' Hello');
            onLoginSignUp(response.data.userRole, name, email);
          } else {
            setShowInvalidLogin(true);
          }
        } else {
          setShowInvalidLogin(true);
        }
      })
      .catch(error => {
        console.error('Error during login:', error);
      });

    console.log("Logged in:", { email, password, name });

    setEmail("");
    setPassword("");
  };

  return (
    <>
      <Navbar variant="dark">
        <div className="container-fluid justify-content-between">
          <Navbar.Brand href="#home">Fly-ght Club Airlines</Navbar.Brand>
          <Nav>
            <Nav.Link onClick={() => setIsSignUp(true)}>Sign Up</Nav.Link>
            <Nav.Link onClick={() => setIsSignUp(false)}>Login</Nav.Link>
          </Nav>
        </div>
      </Navbar>

      <div className="login-signup-container">
        <h1 className="mb-4">{isSignUp ? "Sign Up" : "Login"}</h1>
        <Form onSubmit={isSignUp ? handleSignUp : handleLogin}>
          {isSignUp && (
            <div>
              <Form.Group className="mb-3" controlId="formBasicName">
                <Form.Label>Name</Form.Label>
                <Form.Control
                  type="text"
                  placeholder="Enter your name"
                  value={name}
                  onChange={handleNameChange}
                />
              </Form.Group>
            </div>
          )}

          <Form.Group className="mb-3" controlId="formBasicEmail">
            <Form.Label>Email address</Form.Label>
            <Form.Control
              type="email"
              placeholder="Enter email"
              value={email}
              onChange={handleEmailChange}
            />
          </Form.Group>

          <Form.Group className="mb-3" controlId="formBasicPassword">
            <Form.Label>Password</Form.Label>
            <Form.Control
              type="password"
              placeholder="Password"
              value={password}
              onChange={handlePasswordChange}
            />
          </Form.Group>

          <Button
            className="btn btn-primary"
            onClick={isSignUp ? handleSignUp : handleLogin}
            type="button"
          >
            {isSignUp ? "Sign Up" : "Login"}
          </Button>
        </Form>

        <Modal show={showInvalidLogin} onHide={handleCloseLoginModal} centered>
          <Modal.Header closeButton>
            <Modal.Title>Invalid Login</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <p>Invalid email or password. Please try again.</p>
          </Modal.Body>
          <Modal.Footer>
            <Button variant="secondary" onClick={handleCloseLoginModal}>
              Close
            </Button>
          </Modal.Footer>
        </Modal>
        <Modal
          show={showInvalidSignUp}
          onHide={handleCloseSignUpModal}
          centered
        >
          <Modal.Header closeButton>
            <Modal.Title>Invalid Sign Up</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <p>Invalid email or password. Please try again.</p>
          </Modal.Body>
          <Modal.Footer>
            <Button variant="secondary" onClick={handleCloseSignUpModal}>
              Close
            </Button>
          </Modal.Footer>
        </Modal>
      </div>
    </>
  );
}

export default LoginSignUp;
