// src/index.js
import React from 'react';
import ReactDOM from 'react-dom/client'; // or 'react-dom' if using an older React version
import App from './App'; // Import your App component

// If you're using React 18 or later, use createRoot for the entry point
const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);
