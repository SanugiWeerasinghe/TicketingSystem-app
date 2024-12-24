// src/components/Home.js
import React from 'react';

const Home = () => {
  return (
    <div style={{ textAlign: 'center', marginTop: '50px', padding: '40px'}}>
        <h1 style={{ fontSize: '2.5em', color: '#333' }}>Welcome to the Ticketing System</h1>
        <p style={{ fontSize: '1.2em', color: '#666' }}>Choose your role and log in to start:</p>
    <div>
        <button style={{ margin: '10px', padding: '10px', backgroundColor: '#1a82e3', color: 'white', borderRadius: '4px' }}>
          <a href="/customer/login" style={{ textDecoration: 'none', color: 'white' }}>Customer Login</a>
        </button>
        <button style={{ margin: '10px', padding: '10px', backgroundColor: '#1a82e3', color: 'white', borderRadius: '4px' }}>
          <a href="/vendor/login" style={{ textDecoration: 'none', color: 'white' }}>Vendor Login</a>
        </button>
      </div>
    </div>
  );
};

export default Home;
