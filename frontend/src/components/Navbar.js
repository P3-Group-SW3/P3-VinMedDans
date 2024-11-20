import React from 'react';
import { useNavigate } from "react-router-dom";
import '../styles/header.css';

function NavBar({ links }) {
    const navigate = useNavigate();

    return (
        <nav className="nav justify-content-between border-bottom vh-100">
            {links.map((link, index) => (
                <NavItem
                    key={index}
                    text={link.text}
                    color={link.color}
                    onClick={() => navigate(link.path)}
                />
            ))}
        </nav>
    );
}

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

export default NavBar;