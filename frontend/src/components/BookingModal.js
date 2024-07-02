// src/components/BookingModal.js
import React, { useState, useEffect } from 'react';
import './BookingModal.css';
import { useNavigate } from 'react-router-dom';
import { auth } from '../firebase';

const BookingModal = ({ onClose }) => {
    const [dates, setDates] = useState({
        startDate: '2024-07-01',
    });
    const [adults, setAdults] = useState(2);
    const [children, setChildren] = useState(1);
    const [pricePerPerson, setPricePerPerson] = useState(5279);
    const [currentUserEmail, setCurrentUserEmail] = useState('');
    const [reserveSuccess, setReserveSuccess] = useState(false);
    const [confirmSuccess, setConfirmSuccess] = useState(false);
    const [bookingMessage, setBookingMessage] = useState('');

    useEffect(() => {
        let isMounted = true; // Track whether component is mounted
        const fetchUserEmail = async () => {
            try {
                const user = auth.currentUser;
                if (user && isMounted) {
                    setCurrentUserEmail(user.email);
                }
            } catch (error) {
                console.error('Error fetching user email:', error);
            }
        };

        fetchUserEmail();
        return () => { isMounted = false; }; // Cleanup function to avoid memory leaks
    }, []);

    const handleConfirm = () => {
        // Prepare the data to be sent to the backend
        const data = {
            email: currentUserEmail,
            startDate: dates.startDate,
            adults: adults,
            children: children,
            totalPrice: (pricePerPerson * (adults + children))
        };

        fetch('/booking', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
            .then(response => {
                if (!response.ok) {
                    return response.text().then(msg => {
                        throw new Error(msg || 'Network response was not ok');
                    });
                }
                return response.text(); // Handle text response here
            })
            .then(text => {
                console.log('Booking successful');
                setBookingMessage(text);
                setReserveSuccess(true);
            })
            .catch(error => {
                console.error('There was a problem with the booking:', error.message);
                setBookingMessage('Failed to book. Please try again.');
                setReserveSuccess(false);
            });
    };

    const handleFinalConfirm = () => {
        // Implement the final confirmation logic
        // Prepare the data to be sent to the backend

        fetch('/confirm', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: bookingMessage
        })
            .then(response => {
                if (!response.ok) {
                    return response.text().then(msg => {
                        throw new Error(msg || 'Network response was not ok');
                    });
                }
                return response.text(); // Handle text response here
            })
            .then(text => {
                console.log('Booking confirmed!!!');
                setBookingMessage(text);
                setConfirmSuccess(true);
            })
            .catch(error => {
                console.error('There was a problem with the confirm:', error.message);
                setBookingMessage('Failed to confirm. Please try again.');
                setConfirmSuccess(false);
            });
        //onClose();  // Optionally close the modal or navigate away
    };

    // const handleCancel = () => {
    //     fetch('/cancel', {
    //         method: 'POST',
    //         headers: {
    //             'Content-Type': 'application/json'
    //         },
    //         body: bookingMessage
    //     })
    //         .then(response => {
    //             if (!response.ok) {
    //                 return response.text().then(msg => {
    //                     throw new Error(msg || 'Network response was not ok');
    //                 });
    //             }
    //             return response.text(); // Handle text response here
    //         })
    //         .then(text => {
    //             console.log('Booking cancelled !');
    //             setBookingMessage(text);
    //             setReserveSuccess(false);
    //         })
    //         .catch(error => {
    //             console.error('There was a problem with the cancel:', error.message);
    //             setBookingMessage('Failed to cancel. Please try again.');
    //             setReserveSuccess(true);
    //         });
    //     console.log('Booking finally cancelled !');
    // };

    const handleChange = (event) => {
        const { name, value } = event.target;
        if (name === 'startDate') {
            setDates({
                ...dates,
                [name]: value
            });
        } else if (name === 'adults') {
            setAdults(value);
        } else if (name === 'children') {
            setChildren(value);
        } else if (name === 'pricePerPerson') {
            setPricePerPerson(value);
        }
    };

    const totalPrice = (pricePerPerson * (parseInt(adults) + parseInt(children))).toLocaleString();
    if (!reserveSuccess){
    return (
        <div className="modal">
            <div className="modal-content">
                <span className="close" onClick={onClose}>&times;</span>
                <div className="booking-details">

                    <div className="details-section">
                        <h4>Dates</h4>
                        <p>
                            Start Date:
                            <input
                                type="date"
                                name="startDate"
                                value={dates.startDate}
                                onChange={handleChange}
                            />
                        </p>
                    </div>

                    <div className="details-section">
                        <h4>Guests</h4>
                        <p>
                            Adults:
                            <input
                                type="number"
                                name="adults"
                                value={adults}
                                min="1"
                                onChange={handleChange}
                            />
                        </p>
                        <p>
                            Children:
                            <input
                                type="number"
                                name="children"
                                value={children}
                                min="0"
                                onChange={handleChange}
                            />
                        </p>
                    </div>

                    <div className="details-section">
                        <h4>Price</h4>
                        <p>€{pricePerPerson} Per person</p>
                        <p>€{totalPrice} Total price</p>
                    </div>

                </div>
                <button onClick={handleConfirm}>Book this Holiday</button>
            </div>
        </div>
    );}else{
        return (
            <div className="modal">
                <div className="modal-content">
                    <span className="close" onClick={onClose}>&times;</span>
                    <div className="booking-details">

                        <div className="details-section">
                            <h4>Order Details</h4>
                            <p>
                                {bookingMessage}
                            </p>
                        </div>

                    </div>
                    {!confirmSuccess && <button onClick={handleFinalConfirm}>Confirm</button>}

                </div>
            </div>
        );
    }

};

export default BookingModal;
