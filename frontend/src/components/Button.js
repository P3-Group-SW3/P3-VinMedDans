import React from 'react';
import PropTypes from 'prop-types';
import '../styles/button.css';

function Button({ text, isWide, onClick, flatRight, flatLeft, scale }) {
    const scaled = {
        padding: isWide ? `0 ${40 * scale}px` : `0 ${10 * scale}px`,
        fontSize: `${20 * scale}px`,
        borderRadius: flatLeft
            ? `0 ${6 * scale}px ${6 * scale}px 0`
            : flatRight
                ? `${6 * scale}px 0 0 ${6 * scale}px`
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

Button.defaultProps = {
    scale: 1,
};

export default Button;
