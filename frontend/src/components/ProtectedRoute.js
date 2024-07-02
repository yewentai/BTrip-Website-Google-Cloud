// src/components/ProtectedRoute.js
import React from 'react';
import { Navigate, useLocation } from 'react-router-dom';
import { getAuth } from 'firebase/auth'; // You'll need Firebase Auth for this

function ProtectedRoute({ children, isAdmin }) {
    const location = useLocation();
    const auth = getAuth();

    if (!auth.currentUser) { // User is not logged in
        return <Navigate to="/login" state={{ from: location }} replace />;
    }

    if (isAdmin && !auth.currentUser.getIdTokenResult().then((idTokenResult) => {
        return idTokenResult.claims.admin;
    })) { // User is not an admin
        return <Navigate to="/" state={{ from: location }} replace />; // Or redirect to a forbidden page
    }

    return children; // Render the protected component
}

export default ProtectedRoute;
