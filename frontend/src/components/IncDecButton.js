import React from 'react';
import PropTypes from 'prop-types';
import '../styles/productpage.css';

function Button({ text, onClick }) {
    console.log('Button rendered with text:', text); // Debugging log
    return (
        <button className="button" aria-label="Action button" onClick={onClick}>
            {text}
        </button>
    );
}

Button.propTypes = {
    text: PropTypes.string.isRequired,
    onClick: PropTypes.func,
};

export default Button;