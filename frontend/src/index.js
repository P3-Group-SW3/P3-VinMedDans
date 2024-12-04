// frontend/src/index.js
import React, { useEffect } from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
//import { CriiptoVerifyProvider } from '@criipto/verify-react';
import './index.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';
import reportWebVitals from './reportWebVitals';
import Checkout from './pages/Checkout';
import LandingPage from './pages/LandingPage';
import ProductPage from './pages/ProductPage';
import AboutUsPage from './pages/AboutUsPage';
import LocationPage from './pages/LocationPage';
import ContactPage from './pages/ContactPage';
import EventPage from './pages/EventPage';
import AdminPageFunc from './pages/AdminPageFunc';
import DetailOrderAdmin from './pages/DetailOrderAdmin';
import DetailProductAdmin from './pages/DetailProductAdmin';
import CreateNewWinePage from './pages/CreateNewWinePage';
import LoadFonts from './components/LoadFonts';
import AgeVerification from './components/AgeVerification';
import CriiptoAuthWrapper from './components/CriiptoAuthWrapper';
import Callback from './components/Callback';
import Payment from './pages/Payment';


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
      <AgeVerification />
      <Routes>
        <Route path="/">
          <Route index element={<LandingPage />} />
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
          <Route path="*" element={<h1>Page not found</h1>} />
        </Route>
      </Routes>
    </BrowserRouter>
  </React.StrictMode>
);

reportWebVitals();
