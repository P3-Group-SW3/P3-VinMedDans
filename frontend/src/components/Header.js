import React, { useEffect, Component } from 'react';
import logoImg from '../images/Logo Img.png';
import logoText from '../images/Logo Text.png';
import {useNavigate} from "react-router-dom";
import navbar from './Navbar'

const Header = () => {

  const navigate = useNavigate();

  useEffect(() => {
    const link = document.createElement('link');
    link.href = 'https://fonts.googleapis.com/css2?family=Shadows+Into+Light+Two&family=Special+Elite&family=Rubik&family=Cherry+Bomb+One&family=Rubik+Dirt&display=swap';
    link.rel = 'stylesheet';
    document.head.appendChild(link);
  }, []);

  return (
    <div className="d-flex justify-content-center align-items-center vh-100">
      <img src={logoImg} alt="Logo" className="me-2"/>
      <img src={logoText} alt="Secondary Logo"/>
    </div>
  );
};

export default Header;