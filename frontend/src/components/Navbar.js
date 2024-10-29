import React from 'react';

function NavBar() {
  return (
    <nav className="nav justify-content-center py-3 border-bottom">
      <NavItem text="Webshop" color="#E93271" />
      <NavItem text="Hvem er vi" color="#C44097" />
      <NavItem text="Hvor er vi" color="#F4AC46" />
      <NavItem text="Events" color="#F190A2" />
      <NavItem text="Kontakt os" color="#882D69" />
    </nav>
  );
}

function NavItem({ text, color }) {
  return (
    <a className="nav-link" href="#" style={{ color, fontSize: '1.5rem', fontFamily: 'CherryBomb' }}>
      {text.toUpperCase()}
    </a>
  );
}

export default NavBar;
