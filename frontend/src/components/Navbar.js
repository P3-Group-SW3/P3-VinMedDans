import React from 'react';
import {useNavigate} from "react-router-dom";

function NavBar() {

    const navigate = useNavigate();

  return (
    <nav className="nav justify-content-center py-3 border-bottom">
        <NavItem text="Webshop" color="#E93271" onClick={() => navigate(`/shop`)}/>
      <NavItem text="Hvem er vi" color="#C44097" onClick={() => navigate(`/about`)}/>
      <NavItem text="Hvor er vi" color="#F4AC46" onClick={() => navigate(`/locations`)}/>
      <NavItem text="Events" color="#F190A2" onClick={() => navigate(`/events`)}/>
      <NavItem text="Kontakt os" color="#882D69" onClick={() => navigate(`/contact`)}/>
    </nav>
  );
}

function NavItem({ text, color, onClick}) {
  return (
    <a className="nav-link"
       href="#"
       style={{ color, fontSize: '1.5rem', fontFamily: 'Cherry Bomb One' }}
       onClick={onClick}
    >
      {text.toUpperCase()}
    </a>
  );
}

export default NavBar;
