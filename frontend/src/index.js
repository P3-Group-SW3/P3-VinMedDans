import React, { useEffect } from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter, Routes, Route } from "react-router-dom";
import './index.css';
import reportWebVitals from './reportWebVitals';
import Checkout from "./pages/Checkout";
import Landingpage from "./pages/LandingPage";
import ProductPage from "./pages/ProductPage";
import AboutUsPage from "./pages/AboutUsPage";
import LocationPage from "./pages/LocationPage";
import ContactPage from "./pages/ContactPage";
import EventPage from "./pages/EventPage";
import LoadFonts from "./components/LoadFonts";
import AgeVerification from "./components/AgeVerification";

const SetTitle = () => {
  useEffect(() => {
    document.title = 'Vinmeddans';
  }, []);

  return null;
};

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <BrowserRouter>
      <SetTitle />
      <LoadFonts />
      <AgeVerification />
      <Routes>
        <Route path="/">
          <Route index element={<Landingpage />} />
          <Route path="checkout" element={<Checkout />} />
          <Route path="shop" element={<ProductPage />} />
          <Route path="about" element={<AboutUsPage />} />
          <Route path="locations" element={<LocationPage />} />
          <Route path="contact" element={<ContactPage />} />
          <Route path="events" element={<EventPage />} />
          <Route path="*" element={<h1>Page not found</h1>} />
        </Route>
      </Routes>
    </BrowserRouter>
  </React.StrictMode>
);

reportWebVitals();