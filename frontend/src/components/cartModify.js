import React from "react";
import Button from './Button';


export const CartModify = () => {
  return (
    <div className="d-inline-flex align-items-center gap-3 position-relative">
      <div className="modify-amount">
        <div className="text-wrapper">1</div>
        <div className="d-flex justify-content-center align-items-center modify-amount-button-instance" style={{ height: "60px", width: "50px" }}>
          <div className="bg-magenta rounded-circle d-flex justify-content-center align-items-center" style={{ height: "50px", width: "50px" }}>
            <div className="text-white" style={{ fontFamily: "var(--button-font-family)", fontSize: "44px", fontWeight: "400", lineHeight: "30px" }}>
              -
            </div>
          </div>
        </div>
        <div className="d-flex justify-content-center align-items-center design-component-instance-node" style={{ height: "60px", width: "50px" }}>
          <div className="bg-magenta rounded-circle d-flex justify-content-center align-items-center" style={{ height: "50px", width: "50px" }}>
            <div className="text-white" style={{ fontFamily: "var(--button-font-family)", fontSize: "44px", fontWeight: "400", lineHeight: "30px" }}>
              +
            </div>
          </div>
        </div>
      </div>
      <Button
        text="FØJ TIL KURV"
        onClick={() => console.log("Button clicked")}
      />
    </div>
  );
};