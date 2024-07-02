// src/pages/OrderConfirmationPage.js
import React, { useEffect, useState } from 'react';
import { Helmet } from 'react-helmet';
import { useParams, useNavigate } from 'react-router-dom';
import './OrderConfirmationPage.css';
import { API_BASE_URL } from '../config';

function OrderConfirmationPage() {
    const { orderId } = useParams();
    const navigate = useNavigate();
    const [orderDetails, setOrderDetails] = useState(null);
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState(null);
    const [confirmationStatus, setConfirmationStatus] = useState(null);

    useEffect(() => {
        const fetchOrderDetails = async () => {
            setIsLoading(true);
            setError(null);
            try {
                const token = localStorage.getItem('authToken');
                const response = await fetch(`${API_BASE_URL}/orders/${orderId}`, {
                    headers: { Authorization: `Bearer ${token}` }
                });

                if (!response.ok) {
                    throw new Error('Failed to fetch order details');
                }

                const data = await response.json();
                setOrderDetails(data);
            } catch (err) {
                setError(err.message);
            } finally {
                setIsLoading(false);
            }
        };

        fetchOrderDetails();
    }, [orderId]);

    const handleConfirmOrder = async () => {
        try {
            setIsLoading(true);
            const token = localStorage.getItem('authToken');
            const response = await fetch(`${API_BASE_URL}/orders/${orderId}/confirm`, {
                method: 'POST',
                headers: { Authorization: `Bearer ${token}` }
            });

            if (!response.ok) {
                throw new Error('Failed to confirm order');
            }

            setConfirmationStatus('success');
            setTimeout(() => navigate('/my-orders'), 3000);
        } catch (err) {
            setError(err.message);
            setConfirmationStatus('error');
        } finally {
            setIsLoading(false);
        }
    };

    if (isLoading) return <div className="loader">Loading order details...</div>;
    if (error) return <div className="error-message">Error: {error}</div>;

    return (
        <main className="order-confirmation-page">
            <Helmet>
                <title>Order Confirmation - BTrip</title>
                <link rel="icon" type="image/x-icon" href="%PUBLIC_URL%/assets/logos/favicon.ico" />
            </Helmet>

            <h1>Order Confirmation</h1>

            {orderDetails && (
                <div className="order-details">
                    {/* ... your existing order details rendering ... */}

                    {orderDetails.status === 'Pending' && (
                        <button onClick={handleConfirmOrder} disabled={isLoading}>
                            {isLoading ? 'Processing...' : 'Confirm Order'}
                        </button>
                    )}

                    {confirmationStatus === 'success' && (
                        <div className="success-message">
                            Order confirmed successfully! Redirecting to My Orders...
                        </div>
                    )}
                    {confirmationStatus === 'error' && (
                        <div className="error-message">
                            An error occurred during confirmation. Please try again.
                        </div>
                    )}
                </div>
            )}
        </main>
    );
}

export default OrderConfirmationPage;