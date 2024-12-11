import React, { useEffect, useRef } from 'react';
import logo from '../images/logo.png';
import Cart from './Cart';
import burger from '../images/burger.svg';
import '../styles/header.css';

const Header = ({ links, showCart }) => {
    const headerRef = useRef(null);

    useEffect(() => {
        const headerHeight = headerRef.current.offsetHeight;
        document.body.style.paddingTop = `${headerHeight}px`;

        return () => {
            document.body.style.paddingTop = '0';
        };
    }, []);

    return (
        <nav ref={headerRef} className="navbar navbar-expand-lg fixed-top bg-white justify-content-between" style={{boxShadow: '0 2px 5px rgba(0, 0, 0, 0.1)'}}>
                <button
                    className="unstyled d-lg-none"
                    type="button"
                    data-bs-toggle="collapse"
                    data-bs-target="#navbarSupportedContent"
                >
                    <img src={burger} alt="Burger menu"/>
                </button>
                <a href='/' style={{display: 'flex', justifyContent: 'center', flex: 1}}>
                    <img src={logo} alt="Logo" className="img-fluid" />
                </a>
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

function NavItem({text, color, href}) {
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