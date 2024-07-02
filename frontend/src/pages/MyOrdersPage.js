// src/pages/MyOrdersPage.js
import React, { useEffect, useState } from 'react';
import { Helmet } from 'react-helmet';
import { Link } from 'react-router-dom';
import { auth } from '../firebase';
import './MyOrdersPage.css';

function MyOrdersPage() {
    const [orders, setOrders] = useState([]);
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState(null);
    const [currentUserEmail, setCurrentUserEmail] = useState('');

    useEffect(() => {
        const fetchUserEmail = async () => {
            try {
                const user = auth.currentUser;
                if (user) {
                    setCurrentUserEmail(user.email);
                }
            } catch (error) {
                console.error('Error fetching user email:', error);
            }
        };

        fetchUserEmail();
    }, []);

    useEffect(() => {
        const fetchUserOrders = async () => {
            if (!currentUserEmail) return;

            setIsLoading(true);
            setError(null);

            try {
                const idTokenResult = await auth.currentUser.getIdTokenResult();
                const response = await fetch(`/getUserOrders?email=${currentUserEmail}`, {
                    headers: { Authorization: `Bearer ${idTokenResult.token}` },
                });

                if (!response.ok) {
                    throw new Error('Failed to fetch orders');
                }

                const data = await response.json();
                setOrders(data);
            } catch (err) {
                setError(err.message);
            } finally {
                setIsLoading(false);
            }
        };

        fetchUserOrders();
    }, [currentUserEmail]);

    if (isLoading) return <div className="loader">Loading orders...</div>;
    if (error) return <div className="error-message">Error: {error}</div>;

    return (
        <main className="my-orders-page">
            <Helmet>
                <title>My Orders - BTrip</title>
                <link rel="icon" type="image/x-icon" href="%PUBLIC_URL%/assets/logos/favicon.ico" />
            </Helmet>

            <h1>My Orders</h1>

            {orders.length === 0 ? (
                <p>You have no orders yet.</p>
            ) : (
                <ul className="order-list">
                    {orders.map((order, index) => (
                        <li key={index} className="order-item">
                            <h3>Order #{order.id}</h3>
                            <p>Date: {new Date(order.time).toLocaleDateString()}</p>
                            <p>Flight: {order.flight.departure} to {order.flight.destination}</p>
                            <p>Departure Time: {new Date(order.flight.departureTime).toLocaleString()}</p>
                            <p>Seat: {order.seat.seatCode}</p>
                            <p>Price: ${order.seat.price}</p>
                            <p>Status: <span className={`status ${order.seat.booked ? 'booked' : 'reserved'}`}>
                                {order.seat.booked ? 'Booked' : 'Reserved'}
                            </span></p>
                            <Link to={`/order-confirmation/${order.id}`} className="view-details-btn">View Details</Link>
                        </li>
                    ))}
                </ul>
            )}
        </main>
    );
}

export default MyOrdersPage;