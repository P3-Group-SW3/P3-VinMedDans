import React from 'react';
import PropTypes from 'prop-types';
import '../styles/button.css';

/*
 * The Button component is styled to match the website's design.
 * They can be easily modified in ways that are useful in our system.
 */
const Button = ({ text, isWide, onClick, flatRight, flatLeft, scale }) => {
    //The padding, font size and rounded corners are multiplied with the scale variable
    const scaled = {
        //If button isWise, the padding on the sides are scaled to be wider
        padding: isWide ? `0 ${40 * scale}px` : `0 ${10 * scale}px`,
        fontSize: `${20 * scale}px`,
        //If button is flatLeft, the corners on the left side are not rounded.
        borderRadius: flatLeft
            ? `0 ${6 * scale}px ${6 * scale}px 0`
            //If button is flatRight, the corners on the right side are not rounded.
            : flatRight
                ? `${6 * scale}px 0 0 ${6 * scale}px`
                //If it is neither, all corners are rounded with 6*scale px.
                : `${6 * scale}px`,
    };

    return (
        <button
            className="button"
            onClick={onClick}
            style={scaled}
        >
            {text}
        </button>
    );
}

Button.propTypes = {
    text: PropTypes.string.isRequired,
    onClick: PropTypes.func,
    scale: PropTypes.number,
};

//Default scale for button is 1
Button.defaultProps = {
    scale: 1,
};

export default Button;
