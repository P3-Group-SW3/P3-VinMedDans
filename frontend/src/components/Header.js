// Header.js
import React, { useEffect, useRef } from 'react';
import logoImg from '../images/Logo Img.png';
import logoText from '../images/Logo Text.png';
import { useNavigate } from "react-router-dom";
import Cart from './Cart';
import Navbar from './Navbar';
import '../styles/header.css';

const Header = ({ links, showCart }) => {
    const navigate = useNavigate();
    const headerRef = useRef(null);

    useEffect(() => {
        const headerHeight = headerRef.current.offsetHeight;
        document.body.style.paddingTop = `${headerHeight}px`;

        return () => {
            document.body.style.paddingTop = '0';
        };
    }, []);

    return (
        <div ref={headerRef} className="d-flex fixed-top flex-column bg-white w-100">
            <div className="d-flex flex-row align-items-center justify-content-center w-100">
                <img src={logoImg} alt="Logo" className="me-2" />
                <img src={logoText} alt="Secondary Logo" />
                {showCart && <Cart />}
            </div>
            <div>
                <Navbar links={links} />
            </div>
        </div>
    );
};

export default Header;