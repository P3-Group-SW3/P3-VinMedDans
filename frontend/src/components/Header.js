// Header.js
import React, { useEffect, useRef } from 'react';
import logo from '../images/logo.png';
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
                <a onClick={() => navigate('/')}>
                    <img src={logo} alt="Logo" className="me-2" style={{cursor: 'pointer'}}/>
                </a>
                {showCart && <Cart/>}
            </div>
            <div>
                <Navbar links={links} />
            </div>
        </div>
    );
};

export default Header;