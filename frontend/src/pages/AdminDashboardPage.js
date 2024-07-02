// src/pages/AdminDashboardPage.js
import React, { useState, useEffect } from 'react';
import { Helmet } from 'react-helmet';
import { auth } from '../firebase';
import { API_BASE_URL } from '../config';
import './AdminDashboardPage.css';

function AdminDashboardPage() {
    const [orders, setOrders] = useState([]);
    const [customers, setCustomers] = useState([]);
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState(null);
    const [currentUserEmail, setCurrentUserEmail] = useState('');

    useEffect(() => {
        const fetchData = async () => {
            setIsLoading(true);
            setError(null);

            try {
                const user = auth.currentUser;
                if (user) {
                    setCurrentUserEmail(user.email);
                }

                const idTokenResult = await auth.currentUser.getIdTokenResult();
                const headers = { Authorization: `Bearer ${idTokenResult.token}` };

                // Fetch all orders
                const ordersResponse = await fetch(`${API_BASE_URL}/getAllOrders`, { headers });
                if (!ordersResponse.ok) throw new Error('Failed to fetch orders');
                const ordersData = await ordersResponse.json();

                // Fetch all customers
                const customersResponse = await fetch(`${API_BASE_URL}/getAllCustomers`, { headers });
                if (!customersResponse.ok) throw new Error('Failed to fetch customers');
                const customersData = await customersResponse.json();

                setOrders(ordersData);
                setCustomers(customersData);
            } catch (err) {
                setError(err.message);
            } finally {
                setIsLoading(false);
            }
        };

        fetchData();
    }, []);

    if (isLoading) return <div className="admin-loader">Loading dashboard data...</div>;
    if (error) return <div className="admin-error">Error: {error}</div>;

    return (
        <div className="admin-dashboard">
            <Helmet>
                <title>Admin Dashboard - BTrip</title>
            </Helmet>

            <h1>Admin Dashboard</h1>
            <p>Welcome, {currentUserEmail}!</p>

            <section className="admin-section">
                <h2>Orders Overview</h2>
                <p>Total Orders: {orders.length}</p>
                <table className="admin-table">
                    <thead>
                        <tr>
                            <th>Order ID</th>
                            <th>Customer Email</th>
                            <th>Flight</th>
                            <th>Seat</th>
                            <th>Date</th>
                        </tr>
                    </thead>
                    <tbody>
                        {orders.map(order => (
                            <tr key={order.id}>
                                <td>{order.id}</td>
                                <td>{order.email}</td>
                                <td>{`${order.flight.departure} to ${order.flight.destination}`}</td>
                                <td>{`${order.seat.seatCode} (${order.seat.price})`}</td>
                                <td>{new Date(order.time).toLocaleString()}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </section>

            <section className="admin-section">
                <h2>Customers Overview</h2>
                <p>Total Customers: {customers.length}</p>
                <table className="admin-table">
                    <thead>
                        <tr>
                            <th>Email</th>
                            <th>Role</th>
                        </tr>
                    </thead>
                    <tbody>
                        {customers.map((customer, index) => (
                            <tr key={index}>
                                <td>{customer.email}</td>
                                <td>{customer.role || 'No role'}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </section>
        </div>
    );
}

export default AdminDashboardPage;