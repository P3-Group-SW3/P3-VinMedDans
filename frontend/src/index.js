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
import AdminPageFunc from "./pages/AdminPageFunc";
import DetailOrderAdmin from "./pages/DetailOrderAdmin";
import DetailProductAdmin from "./pages/DetailProductAdmin";
import CreateNewWinePage from "./pages/CreateNewWinePage";

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
      <Routes>
        <Route path="/">
          <Route index element={<Landingpage />} />
          <Route path="checkout" element={<Checkout />} />
          <Route path="shop" element={<ProductPage />} />
          <Route path="about" element={<AboutUsPage />} />
          <Route path="locations" element={<LocationPage />} />
          <Route path="contact" element={<ContactPage />} />
          <Route path="events" element={<EventPage />} />
          <Route path="admin" element={<AdminPageFunc />} />
          <Route path="/orders/:id" element={<DetailOrderAdmin/>} />
          <Route path="/products/:id" element={<DetailProductAdmin/>} />
          <Route path="create-wine" element={<CreateNewWinePage/>} />
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
