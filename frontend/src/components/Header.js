import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { FaUser } from 'react-icons/fa'; // Import FaUser icon
import './Header.css';
import {
    onAuthStateChanged,
} from 'firebase/auth';
import { auth } from "../firebase";
import { useNavigate } from "react-router-dom";

function Header() {
    const [isLoggedIn, setIsLoggedIn] = useState(false); // Initialize login state
    const [isManager, setIsManager] = useState(false);
    const navigate = useNavigate();
    useEffect(() => {
        handleAuthChange();
    }, []);


    // Function to handle login/logout
    const handleLogout = () => {
        try {
            setIsLoggedIn(false);
            auth.signOut();
            navigate("/");
        } catch (err) { }
    };

    const handleAuthChange = () => {
        onAuthStateChanged(auth, (user) => {
            console.log('onAuthStateChangedHeader');
            if (user == null || auth == null || auth.currentUser == null) {
                console.log("user is null or unauthenticated or undefined");
                return;
            }

            auth.currentUser.getIdTokenResult().then((idTokenResult) => {
                fetch('/api/whoami', {
                    headers: { Authorization: `Bearer ${idTokenResult.token}` },
                })
                    .then((response) => response.json())
                    .then((data) => {
                        console.log('Whoami at rest service: ' + data.email + ' - ' + data.role)
                    })
                    .catch((error) => {
                        console.log(error);
                    });
                console.log('Hello ' + auth.currentUser.email);
                console.log('Token: ' + idTokenResult.token);
                if (idTokenResult.claims.email === 'riot@lol.com') {
                    setIsManager(true);
                    navigate("/manager");
                } else {
                    setIsManager(false);
                    navigate("/");
                }
            });
            setIsLoggedIn(true);
        });
    };

    return (
        <header>
            <nav>
                <div className="logo">
                    <Link to="/">
                        <img src="/assets/logos/logo512.png" alt="BTrip Logo" />
                    </Link>
                    <span className="company-name">BTrip</span> {/* Add company name */}
                </div>
                <ul className="nav-links">
                    <li><Link to="/destinations/portugal">Portugal</Link></li>
                    <li><Link to="/destinations/spain">Spain</Link></li>
                    <li><Link to="/destinations/italy">Italy</Link></li>
                    <li><Link to="/destinations/iceland">Iceland</Link></li>
                </ul>
                <ul id="userUL" className="user-links">
                    {isLoggedIn ? (
                        <li className="user-dropdown">
                            <FaUser />
                            <ul className="dropdown-menu">
                                <li>
                                    <Link to="/orders">Orders</Link>
                                </li>
                                {isManager && (
                                    <li>
                                        <Link to="/manager">Manage</Link>
                                    </li>
                                )}
                                <li>
                                    <button id="btnLogout" onClick={handleLogout}>Logout</button>
                                </li>
                            </ul>
                        </li>
                    ) : (
                        <li>
                            <Link to="/login">
                                Login
                            </Link>
                        </li>
                    )}
                </ul>
            </nav>
        </header>
    );
}

export default Header;