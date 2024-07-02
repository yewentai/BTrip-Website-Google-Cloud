// scr/pages/LoginPage.js

import React, { useEffect } from 'react';
import {
    onAuthStateChanged,
    createUserWithEmailAndPassword,
    signInWithEmailAndPassword,
} from 'firebase/auth';
import { auth } from "../firebase";
import './LoginPage.css';

const wireGuiUpEvents = () => {
    const email = document.getElementById('email');
    const password = document.getElementById('password');
    const signInButton = document.getElementById('btnSignIn');
    const signUpButton = document.getElementById('btnSignUp');

    signInButton.addEventListener('click', () => {
        signInWithEmailAndPassword(auth, email.value, password.value)
            .then(() => {
                console.log('signed in');
            })
            .catch((error) => {
                console.log('error signInWithEmailAndPassword:', error.message);
                alert(error.message);
            });
    });

    signUpButton.addEventListener('click', () => {
        createUserWithEmailAndPassword(auth, email.value, password.value)
            .then(() => {
                console.log('created');
            })
            .catch((error) => {
                console.log('error createUserWithEmailAndPassword:', error.message);
                alert(error.message);
            });
    });

};

const fetchData = (token) => {
    getHello(token);
    whoami(token);
};

const showAuthenticated = (username) => {
    document.getElementById('logindiv').style.display = 'none';
    document.getElementById('contentdiv').style.display = 'block';
};

const showUnAuthenticated = () => {
    document.getElementById('email').value = '';
    document.getElementById('password').value = '';
    document.getElementById('logindiv').style.display = 'block';
    document.getElementById('contentdiv').style.display = 'none';
};

const addContent = (text) => {
    document.getElementById('contentdiv').innerHTML += text + '<br/>';
};

const getHello = (token) => {
    fetch('/api/hello', {
        headers: { Authorization: `Bearer ${token}` },
    })
        .then((response) => response.text())
        .then((data) => {
            console.log(data);
            addContent(data);
        })
        .catch((error) => {
            console.log(error);
        });
};

const whoami = (token) => {
    fetch('/api/whoami', {
        headers: { Authorization: `Bearer ${token}` },
    })
        .then((response) => response.json())
        .then((data) => {
            console.log(data.email + data.role);
            addContent('Whoami at rest service: ' + data.email + ' - ' + data.role);
        })
        .catch((error) => {
            console.log(error);
        });
};

const LoginPage = () => {
    useEffect(() => {
        wireGuiUpEvents();
        // wireUpAuthChange();
    }, []);

    return (
        <main>
            <div id="logindiv" className="login">
                <div className="textfield">
                    <input type="text" id="email" required placeholder=" " />
                    <label htmlFor="email">Email</label>
                </div>
                <div className="textfield">
                    <input type="password" id="password" required placeholder=" " />
                    <label htmlFor="password">Password</label>
                </div>
                <div className="login-button" id="btnSignUp">
                    Sign up
                </div>
                <div className="login-button" id="btnSignIn">
                    Sign In
                </div>
            </div>
            <div className="page" id="contentdiv"></div>
        </main>
    );
};

export default LoginPage;
