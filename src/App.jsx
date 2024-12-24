// src/App.js
import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import CustomerLogin from './CustomerLogin';  // Customer Login
import CustomerSignup from './CustomerSignup';  // Customer Signup
import VendorLogin from './VendorLogin';  // Vendor Login
import VendorSignup from './VendorSignup';  // Vendor Signup
import CustomerPanel from './CustomerPanel';  // Customer Panel
import VendorPanel from './VendorPanel';  // Vendor Panel
import Home from './Home';  // Home Page
import './index.css';

function App() {
  return (
    <Router>
      <div style={{ fontFamily: 'Arial, sans-serif', backgroundColor: '#f4f4f4' }}>
        {/* Define the routes for login, signup, customer, vendor, and home pages */}
        <Routes>
          {/* Home Page */}
          <Route path="/" element={<Home />} />
          
          {/* Customer Routes */}
          <Route path="/customer/login" element={<CustomerLogin />} />
          <Route path="/customer/signup" element={<CustomerSignup />} />
          <Route path="/customer" element={<CustomerPanel />} />
          
          {/* Vendor Routes */}
          <Route path="/vendor/login" element={<VendorLogin />} />
          <Route path="/vendor/signup" element={<VendorSignup />} />
          <Route path="/vendor" element={<VendorPanel />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
