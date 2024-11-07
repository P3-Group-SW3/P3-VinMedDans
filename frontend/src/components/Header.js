import React, { useEffect, Component, useRef } from 'react';
import logoImg from '../images/Logo Img.png';
import logoText from '../images/Logo Text.png';
import {useNavigate} from "react-router-dom";
import Navbar from './Navbar'
import '../styles/header.css'

const Header = () => {

  const navigate = useNavigate();

  useEffect(() => {
    const link = document.createElement('link');
    link.href = 'https://fonts.googleapis.com/css2?family=Shadows+Into+Light+Two&family=Special+Elite&family=Rubik&family=Cherry+Bomb+One&family=Rubik+Dirt&display=swap';
    link.rel = 'stylesheet';
    document.head.appendChild(link);
  }, []);

    const headerRef = useRef(null);

    useEffect(() => {
        // Get the height of the header after it mounts
        const headerHeight = headerRef.current.offsetHeight;
        // Set the body's padding-top to the header height
        document.body.style.paddingTop = `${headerHeight}px`;

        // Cleanup function to reset padding when the component unmounts
        return () => {
            document.body.style.paddingTop = '0';
        };
    }, []);

  return (
    <div ref={headerRef} className="d-flex fixed-top flex-column bg-white w-100">
        <div className="d-flex flex-row align-items-center justify-content-center w-100">
            <img src={logoImg} alt="Logo" className="me-2"/>
            <img src={logoText} alt="Secondary Logo"/>
        </div>
        <div>
            <Navbar />
        </div>
    </div>
  );
};

export default Header;