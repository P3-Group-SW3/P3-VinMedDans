import React from 'react';
import {useNavigate} from "react-router-dom";
import '../styles/header.css'
import Cart from './Cart'

function NavBar() {

    const navigate = useNavigate();

  return (
    <nav className="nav justify-content-between border-bottom vh-100">
      <NavItem text="Webshop" color="#E93271" onClick={() => navigate(`/shop`)}/>
      <NavItem text="Hvem er vi" color="#C44097" onClick={() => navigate(`/about`)}/>
      <NavItem text="Hvor er vi" color="#F4AC46" onClick={() => navigate(`/locations`)}/>
      <NavItem text="Events" color="#F190A2" onClick={() => navigate(`/events`)}/>
      <NavItem text="Kontakt os" color="#882D69" onClick={() => navigate(`/contact`)}/>
      <Cart />
    </nav>
  );
}

function NavItem({ text, color, onClick}) {
    return (
    <a className='nav-link header-large nav-button'
       href="#"
       style={{ '--main-color': color }}
       onClick={onClick}
    >
      {text.toLowerCase()}
    </a>
  );
}

export default NavBar;
