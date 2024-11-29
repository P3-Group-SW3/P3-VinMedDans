// Header.js
import React, { useEffect, useRef } from 'react';
import logo from '../images/logo.png';
import { useNavigate } from "react-router-dom";
import Cart from './Cart';
import burger from '../images/burger.svg'
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
        <nav ref={headerRef} className="navbar navbar-expand-lg fixed-top bg-white" style={{height:'fit-content'}}>
            <div className="container flex-column justify-content-center">
                <div className="row">
                    <a
                        className="navbar-toggler"
                        type="button"
                        data-bs-toggle="collapse"
                        data-bs-target="#navbarSupportedContent"
                    >
                        <img src={burger} alt="Burger menu"/>
                    </a>
                    <a onClick={() => navigate('/')}>
                        <img src={logo} alt="Logo" className="img-fluid" style={{cursor: 'pointer'}}/>
                    </a>
                    {showCart && <Cart/>}
                </div>
                <div className="flex-row collapse navbar-collapse justify-content-between align-items-center"
                     id="navbarSupportedContent">
                    {links.map((link, index) => (
                        <NavItem
                            key={index}
                                text={link.text}
                                color={link.color}
                                onClick={() => navigate(link.path)}
                            />
                        ))}
                </div>
            </div>
        </nav>
    );
};

function NavItem({ text, color, onClick }) {
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

export default Header;