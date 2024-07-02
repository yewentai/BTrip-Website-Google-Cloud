// scr/App.js

import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import LoginPage from './pages/LoginPage';
import Header from './components/Header';
import Footer from './components/Footer';
import HomePage from './pages/HomePage';
import DestinationPage from './pages/DestinationPage';
import * as portugalData from './pages/data/PortugalData';
import * as spainData from './pages/data/SpainData';
import * as italyData from './pages/data/ItalyData';
import * as icelandData from './pages/data/IcelandData';
import OrderConfirmationPage from './pages/OrderConfirmationPage';
import MyOrdersPage from './pages/MyOrdersPage';
import ProtectedRoute from './components/ProtectedRoute';
import AdminDashboardPage from './pages/AdminDashboardPage';



function App() {
  return (
    <Router>
      <div>
        <Header />
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/destinations/portugal" element={<DestinationPage data={portugalData} />} />
          <Route path="/destinations/spain" element={<DestinationPage data={spainData} />} />
          <Route path="/destinations/italy" element={<DestinationPage data={italyData} />} />
          <Route path="/destinations/iceland" element={<DestinationPage data={icelandData} />} />
          <Route
            path="/admin"
            element={
              <ProtectedRoute isAdmin={true}>
                <AdminDashboardPage />
              </ProtectedRoute>
            }
          />
          <Route path="/order-confirmation" element={<OrderConfirmationPage />} />
          <Route path="/my-orders" element={<MyOrdersPage />} /> */
        </Routes>
        <Footer />
      </div>
    </Router>
  );
}

export default App;
