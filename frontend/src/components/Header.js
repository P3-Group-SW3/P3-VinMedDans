// Header.js
import React, { useEffect, useRef, useState } from 'react';
import logo from '../images/logo.png';
import { useNavigate } from "react-router-dom";
import Cart from './Cart';
import Navbar from './Navbar';
import AgeVerification from "./AgeVerification";
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

    const [cookieAge, setCookieAge] = useState('new');
    const [show, setShow] = useState(false);

    useEffect(() => {
        fetch('api/createcookie')
            .then(response => console.log(response))
            .catch(error => console.error('Error fetching data: ', error))

        fetch('api/cookieAge')
            .then(data => setCookieAge(data))
            .catch(error => console.error('Error fetching data: ', error))

        console.log("Cookie age:",cookieAge);
        console.log("Cookie age type:",typeof cookieAge);
        if (cookieAge ==='new') {
            setShow(true);
        } else if (cookieAge === 'old') {
            setShow(false);
        } else {
            console.log("invalid value!! >:(")
        }

    }, []);

    return (
        <div ref={headerRef} className="d-flex fixed-top flex-column bg-white w-100">
            <div className="d-flex flex-row align-items-center justify-content-center w-100">
                < AgeVerification show={show}/>
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