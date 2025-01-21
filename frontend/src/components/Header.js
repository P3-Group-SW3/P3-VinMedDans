import React, { useEffect, useRef } from 'react';
import logo from '../images/logo.png';
import Cart from './Cart';
import burger from '../images/burger.svg';
import '../styles/header.css';

/*
 * Header component for navigation purposes.
 * Displayed at the top of each customer-facing page
 */
const Header = ({ links, showCart }) => {
    //Header reference used to ensure fixed header does not block content
    const headerRef = useRef(null);

    useEffect(() => {
        //Use padding to offset content based on the header's height
        const headerHeight = headerRef.current.offsetHeight;
        document.body.style.paddingTop = `${headerHeight}px`;

        //Calibrate the new content position to be at 0
        return () => {
            document.body.style.paddingTop = '0';
        };
    }, []);

    return (
        <nav ref={headerRef} className="navbar navbar-expand-lg fixed-top bg-white justify-content-between" style={{boxShadow: '0 2px 5px rgba(0, 0, 0, 0.1)'}}>
            {/* Burger button is hidden on larger screens. When clicked, navigation links are displayed */}
            <button
                className="unstyled d-lg-none"
                type="button"
                data-bs-toggle="collapse"
                data-bs-target="#navbarSupportedContent"
            >
                <img src={burger} alt="Burger menu"/>
            </button>
            {/* Header logo, leads to front page */}
            <a href='/' style={{display: 'flex', justifyContent: 'center', flex: 1}}>
                <img src={logo} alt="Logo" className="img-fluid" />
            </a>
            {/* Cart has two placements based on screen size */}
            {showCart &&
                <div className="d-lg-none">
                    <Cart/>
                </div>}
            <div
                className="collapse navbar-collapse justify-content-between align-items-center"
                id="navbarSupportedContent"
            >
                {links.map((link, index) => (
                    <NavItem
                        key={index}
                        text={link.text}
                        color={link.color}
                        href={link.path}
                    />
                ))}
                {showCart && <div className="d-none d-lg-block"><Cart/></div>}
            </div>
        </nav>
    );
};

//Each navigation link is styled according to the design using custom css
const NavItem = ({text, color, href}) => {
    return (
        <a
            className="nav-link"
            style={{'--main-color': color}}
            href={href}
        >
            {text.toLowerCase()}
        </a>
    );
}

export default Header;