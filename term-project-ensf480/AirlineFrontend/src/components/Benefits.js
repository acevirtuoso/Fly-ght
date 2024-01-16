import React, { useState, useEffect } from "react";
import Modal from "react-bootstrap/Modal";
import Button from "react-bootstrap/Button";
import axios from "axios";

function Benefits({ goBack, name, email, role }) {
  const [isSubscribedMonthlyNews, setIsSubscribedMonthlyNews] = useState(false);
  const [isSubscribedMember, setIsSubscribedMember] = useState(false);
  const [isSubscribedCreditCard, setIsSubscribedCreditCard] = useState(false);

  const generateRandomCreditCard = () => {
    const generateRandomNumber = (min, max) => {
      return Math.floor(Math.random() * (max - min) + min);
    };

    const generateRandomExpDate = () => {
      const month = generateRandomNumber(1, 13);
      const year = generateRandomNumber(2023, 2030);
      return `${month}/${year}`;
    };

    const generateRandomCVV = () => {
      return generateRandomNumber(100, 1000);
    };

    const generateRandomCardNumber = () => {
      const segments = [];
      for (let i = 0; i < 4; i++) {
        const segment = generateRandomNumber(1000, 10000).toString();
        segments.push(segment);
      }
      return segments.join(" ");
    };

    const creditCardInfo = {
      cardNumber: generateRandomCardNumber(),
      expirationDate: generateRandomExpDate(),
      cvv: generateRandomCVV(),
    };

    return creditCardInfo;
  };

  const registerForMonthly = () => {
    axios
      .get("http://localhost:8080/premiumUser/registerForMonthly", {
        params: {
          email: email,
        },
      })
      .then((response) => {
        if (response.status === 200) {
          setIsSubscribedMonthlyNews(response.data);
        }
      })
      .catch((error) => {
        console.error("Error fetching registered user information:", error);
      });
  };

  const registerForMembership = () => {
    axios
      .get("http://localhost:8080/premiumUser/registerForMembership", {
        params: {
          email: email,
        },
      })
      .then((response) => {
        if (response.status === 200) {
          setIsSubscribedMember(response.data);
        }
      })
      .catch((error) => {
        console.error("Error fetching registered user information:", error);
      });
  };

  const registerForCreditCard = () => {
    axios
      .get("http://localhost:8080/premiumUser/registerForCreditCard", {
        params: {
          email: email,
        },
      })
      .then((response) => {
        if (response.status === 200) {
          setIsSubscribedCreditCard(response.data);
        }
      })
      .catch((error) => {
        console.error("Error fetching registered user information:", error);
      });
  };

  // when the user loads the page we need to see what they have registered for
  useEffect(() => {
    registerForCreditCard();
    registerForMembership();
    registerForMonthly();
  }, []);

  const handleMembershipClick = () => {
    registerForMembership();
    if (isSubscribedMember) {
      setIsSubscribedMember(false);
    } else {
      setIsSubscribedMember(true);
    }
  };

  const handlePromoNewsClick = () => {
    registerForMonthly();
    if (isSubscribedMonthlyNews) {
      setIsSubscribedMonthlyNews(false);
    } else {
      setIsSubscribedMonthlyNews(true);
    }
  };

  const handleCreditCardClick = () => {
    registerForCreditCard();
    if (isSubscribedCreditCard) {
      setIsSubscribedCreditCard(false);
    } else {
      setIsSubscribedCreditCard(true);
    }
  };

  return (
    <>
      <div className="d-flex justify-content-between align-items-center">
        <h1>Benefits</h1>
      </div>
      <button
        style={{ marginRight: "10px", marginLeft: "10px" }}
        className="btn btn-primary btn-lg mr-3"
        onClick={handleMembershipClick}
      >
        {isSubscribedMember ? "Cancel Membership" : "Register for Membership"}
      </button>
      <button
        style={{ marginRight: "10px", marginLeft: "10px" }}
        className="btn btn-primary btn-lg mr-3"
        onClick={handleCreditCardClick}
      >
        {isSubscribedCreditCard
          ? "Cancel Company Credit Card"
          : "Register for Company Credit Card"}
      </button>
      <button
        style={{ marginRight: "10px", marginLeft: "10px" }}
        className="btn btn-primary btn-lg mr-3"
        onClick={handlePromoNewsClick}
      >
        {isSubscribedMonthlyNews
          ? "Cancel Monthly News"
          : "Register for Monthly Promotion News"}
      </button>
    </>
  );
}

export default Benefits;
