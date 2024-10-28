import PropTypes from "prop-types";
import React from "react";

export const Button = ({ buttonClassName, text = "TEXT HERE" }) => {
  return (
    <button className={`btn d-inline-flex align-items-center justify-content-center position-relative ${buttonClassName}`} style={{ height: "60px", padding: "10px 25px" }}>
      <div className="position-absolute bg-magenta rounded" style={{ height: "60px", width: "197px", top: "0", left: "0" }}></div>
      <div className="text-white" style={{ fontFamily: "var(--button-font-family)", fontSize: "var(--button-font-size)", fontStyle: "var(--button-font-style)", fontWeight: "var(--button-font-weight)", letterSpacing: "var(--button-letter-spacing)", lineHeight: "var(--button-line-height)" }}>
        {text}
      </div>
    </button>
  );
};

Button.propTypes = {
  text: PropTypes.string,
};