import React, { useEffect } from 'react';
import ReactDOM from 'react-dom/client';
import {BrowserRouter, Routes, Route, useLocation, Navigate} from 'react-router-dom';
//import { CriiptoVerifyProvider } from '@criipto/verify-react';
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
import Checkout from "./pages/Checkout";
import AdminDashboard from "./pages/AdminDashboard";
import LoadFonts from "./components/LoadFonts";
import AdminEdit from "./pages/AdminEdit";
//import CriiptoAuthWrapper from './components/CriiptoAuthWrapper';
//import Callback from './components/Callback';
//import Payment from './pages/Payment';


const SetTitle = () => {
  useEffect(() => {
    document.title = 'Vinmeddans';
  }, []);

  return null;
};

/*
<Route path="payment" element={
                <CriiptoAuthWrapper>
                  <Checkout />
</CriiptoAuthWrapper>
} />
 */

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <BrowserRouter>
      <SetTitle />
      <LoadFonts />
      <CartProvider>
          <Routes>
            <Route path="/">
              <Route index element={<LandingPage/>} />
              <Route path="shop" element={<ProductPage />} />
              <Route path="about" element={<AboutUsPage />} />
              <Route path="locations" element={<LocationPage />} />
              <Route path="events" element={<EventPage />} />
              <Route path="contact" element={<ContactPage />} />
              <Route path="checkout" element={<Checkout />} />
              <Route path="administrator">
                <Route index element={<AdminDashboard />}/>
                <Route path=":category/:id" element={<AdminEdit/>} />
                <Route path=":category/create" element={<AdminEdit/>} />
              </Route>
              <Route path="*" element={<h1>404 - Page Not Found</h1>} />
            </Route>
          </Routes>
      </CartProvider>
    </BrowserRouter>
  </React.StrictMode>
);

reportWebVitals();
