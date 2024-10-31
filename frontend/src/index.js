import React, { useEffect } from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter, Routes, Route } from "react-router-dom";
import './index.css';
import reportWebVitals from './reportWebVitals';
import Checkout from "./pages/Checkout";
import Landingpage from "./pages/LandingPage";
import ProductPage from "./pages/ProductPage";

const SetTitle = () => {
  useEffect(() => {
    document.title = 'Vin Med Dans 💃';
  }, []);

  return null;
};

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <BrowserRouter>
      <SetTitle />
      <Routes>
        <Route path="/">
          <Route index element={<Landingpage />} />
          <Route path="Checkout" element={<Checkout />} />
          <Route path="webshop" element={<ProductPage />} />
          <Route path="*" element={<h1>404 - Not Found</h1>} />
        </Route>
      </Routes>
    </BrowserRouter>
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
