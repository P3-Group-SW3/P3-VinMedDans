import React from 'react';
import PropTypes from 'prop-types';
import '../styles/button.css';

function Button({ text, onClick, makeCircle, makeSquare }) {
  return (
    <button
      className={makeCircle ? 'buttonCircle' : makeSquare ? 'buttonSquare' : 'button'}
      aria-label="Action button"
      onClick={onClick}
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