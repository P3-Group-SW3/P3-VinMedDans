import React, { useEffect, Component } from 'react';
import logoImg from '../images/Logo Img.png';
import logoText from '../images/Logo Text.png';
import CartOverlay from './CartOverlay'

const Header = () => {
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
        <div>
            <CartOverlay show={this.state.show} handleClose={this.hideModal}>
                <p>Modal</p>
            </CartOverlay>
            <button type="button" onClick={this.showModal}>
                Open
            </button>
        </div>
      </div>
  )
      ;
};

export default Header;