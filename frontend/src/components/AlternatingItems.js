import React from "react";
import Button from "./Button";
import { useNavigate } from "react-router-dom";

const AlternatingItems = ({ items }) => {

    const navigate = useNavigate();

    return (
        <div className="container my-5">
            {items.map((item, index) => (
                <div className="row align-items-center mb-4" key={index}>
                    <div className={`col-lg-6 ${index % 2 === 0 ? "order-lg-2" : "order-lg-1"}`}>
                        <img
                            src={item.imagePath}
                            alt={item.title}
                            className="img-fluid mx-auto d-block"
                        />
                    </div>
                    <div className={`col-lg-6 ${index % 2 === 0 ? "order-lg-1" : "order-lg-2"} text-center text-lg-start`}>
                        <h3 className="header-large pt-3">{item.title}</h3>
                        <p className="body-text">{item.description}</p>
                        <div className="d-flex justify-content-center pt-3">
                            {item.buttonText && <Button text={item.buttonText} isWide={true} onClick={() => navigate(item.buttonPath)}/> }
                        </div>
                    </div>
                </div>
            ))}
        </div>
    );
};

export default AlternatingItems;
