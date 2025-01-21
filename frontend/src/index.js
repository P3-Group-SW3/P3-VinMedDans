// frontend/src/index.js
import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import './index.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';
import reportWebVitals from './reportWebVitals';
import { CartProvider } from "./components/CartContext";
import LandingPage from "./pages/LandingPage";
import ProductPage from "./pages/ProductPage";
import AboutUsPage from "./pages/AboutUsPage";
import EventPage from "./pages/EventPage";
import LocationPage from "./pages/LocationPage";
import ContactPage from "./pages/ContactPage";
import OrderPage from "./pages/OrderPage";
import Checkout from "./pages/Checkout";
import AdminDashboard from "./pages/AdminDashboard";
import AdminEdit from "./pages/AdminEdit";
import LoadFonts from "./components/LoadFonts";
import AgeVerification from "./components/AgeVerification";
import CriiptoCallback from "./components/CriiptoCallback";
import AdminOrders from "./pages/AdminOrders";
import AdminOrderEdit from "./pages/AdminOrderEdit";


const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <BrowserRouter>
      <LoadFonts />
      <AgeVerification />
      <CartProvider>
        <Routes>
          <Route path="/" element={<LandingPage />} />
          <Route path="/shop" element={<ProductPage />} />
          <Route path="/about" element={<AboutUsPage />} />
          <Route path="/locations" element={<LocationPage />} />
          <Route path="/events" element={<EventPage />} />
          <Route path="/contact" element={<ContactPage />} />
          <Route path="/checkout" element={<Checkout />} />
          <Route path="/order" element={<OrderPage />} />
          <Route path="/callback" element={<CriiptoCallback />} />
          <Route path="/stripe-page" element={<div>Stripe Page</div>} />
          <Route path="administrator">
            <Route index element={<AdminDashboard />}/>
            <Route path="orders" element={<AdminOrders/>} />
            <Route path="orders/:id" element={<AdminOrderEdit/>} />
            <Route path=":category/:id" element={<AdminEdit/>} />
            <Route path=":category/create" element={<AdminEdit/>} />
          </Route>
          <Route path="/*" element={<h1>404 - Page Not Found</h1>} />
        </Routes>
      </CartProvider>
    </BrowserRouter>
  </React.StrictMode>
);

reportWebVitals();
