import React from 'react';
import PropTypes from 'prop-types';
import '../styles/button.css';

function Button({ text, isWide, onClick, flatRight, flatLeft }) {
  return (
    <button
      className={isWide? 'button wide' : 'button'}
      aria-label="Action button"
      onClick={onClick}
      style={
        flatLeft ? { borderRadius: '0 6px 6px 0' } :
            flatRight ? { borderRadius: '6px 0 0 6px' } :
                { borderRadius: '6px' }
      }
    >
      {text}
    </button>
  );
}

Button.propTypes = {
  text: PropTypes.string.isRequired,
  onClick: PropTypes.func,
};

export default Button;