import React from 'react';
import logoImg from '../images/Logo Img.png';
import logoText from '../images/Logo Text.png';

const Header = () => {
  return (
    <div className="d-flex justify-content-center align-items-center vh-100">
      <img src={logoImg} alt="Logo" className="me-2" />
      <img src={logoText} alt="Secondary Logo" />
    </div>
  );
};

export default Header;