import React, { useState, useEffect } from 'react';
import DefaultHome from './DefaultHome';
import LoginSignUp from './LoginSignUp';
import AdminHome from './AdminHome';
import AirlineAgentHome from './AirlineAgentHome';
import FlightCrewHome from './FlightCrewHome';
import RegisteredUserHome from './RegisteredUserHome';
import axios from "axios";

function App() {
  const [loggedIn, setLoggedIn] = useState(false);
  const [userRole, setUserRole] = useState("");
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");

  const handleLogin = (role, name, email) => {
    setUserRole(role);
    setName(name);
    setEmail(email);
    setLoggedIn(true);
  };

  const handleSignOut = () => {

    setLoggedIn(false);
    setName("");
    setEmail("");
    setUserRole("");
  };

  useEffect(() => {
    console.log("Updated userRole: ", userRole);
  }, [userRole]);

  return (
    <div>
      {loggedIn ? (
        <div>
          {userRole === "default-user" && (
            <DefaultHome role={userRole} email={email} name={name} signOut={handleSignOut} />
          )}
          {userRole === "admin" && (
            <AdminHome role={userRole} email={email} name={name} signOut={handleSignOut} />
          )}
          {userRole === "flight-crew" && (
            <FlightCrewHome role={userRole} email={email} name={name} signOut={handleSignOut} />
          )}
          {userRole === "registered-user" && (
            <RegisteredUserHome role={userRole} email={email} name={name} signOut={handleSignOut} />
          )}
          {userRole === "airline-agent" && (
            <AirlineAgentHome role={userRole} email={email} name={name} signOut={handleSignOut} />
          )}
          {userRole == "system-admin" && (
            <AdminHome role = {userRole} email = {email} name = {name} signOut={handleLogin} />
          )}
        </div>
      ) : (
        <LoginSignUp onLoginSignUp={handleLogin} />
      )}
    </div>
  );
}

export default App;
