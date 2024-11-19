import React from 'react';
import PropTypes from 'prop-types';
import '../styles/button.css';

function Button({ text, onClick }) {
  return (
    <button
      className="button"
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