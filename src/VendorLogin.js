// src/components/VendorLogin.js
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

const VendorLogin = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    
    try {
      const response = await fetch('http://localhost:8080/api/vendor/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ email, password }),
      });

      if (response.ok) {
        const data = await response.json();
        console.log('Vendor logged in:', data);
        navigate('/vendor'); // Redirect to vendor dashboard after successful login
      } else {
        const error = await response.text();
        alert(error);
      }
    } catch (error) {
      console.error('Login error:', error);
      alert('Error logging in');
    }
  };

  return (
    <div style={{ padding: '50px', maxWidth: '500px', margin: '0 auto', backgroundColor: '#f4f4f4', borderRadius: '8px' }}>
      <h2>Vendor Login</h2>
      <form onSubmit={handleLogin}>
        <div style={{ marginBottom: '10px' }}>
          <input
            type="email"
            placeholder="Email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
            style={{ width: '100%', padding: '10px', borderRadius: '4px', border: '1px solid #ccc' }}
          />
        </div>
        <div style={{ marginBottom: '10px' }}>
          <input
            type="password"
            placeholder="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
            style={{ width: '100%', padding: '10px', borderRadius: '4px', border: '1px solid #ccc' }}
          />
        </div>
        <button type="submit" style={{ width: '100%', padding: '10px', backgroundColor: '#1a82e3', color: 'white', borderRadius: '4px' }}>
          Login
        </button>
      </form>
      <p style={{ textAlign: 'center', marginTop: '10px' }}>
        Don't have an account? <a href="/vendor/signup" style={{ textDecoration: 'none', color: '#0044cc' }}>Sign up</a>
      </p>
    </div>
  );
};

export default VendorLogin;
