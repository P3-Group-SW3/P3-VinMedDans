import React from "react";
import Button from "./Button";
import CartModify from "./CartModify"
import '../styles/fonts.css'
import { useNavigate } from "react-router-dom";

const AlternatingItems = ({ items }) => {

    const navigate = useNavigate();
    console.log('Items:',items);

    return (
        <div className="container my-5">
            {items.map((item, index) => (
                <div className="row align-items-center justify-content-center mb-4 mx-auto fit-content" key={index} style={{backgroundColor: item.title ? "#FFFFFF" : "#FFFAED"}}>
                    <div className={`col-lg-6 ${index % 2 === 0 ? "order-lg-1" : "order-lg-2"} py-0`}>
                        <img
                            src={item.imageURL}
                            alt={item.title? item.title : item.name}
                            className="img-fluid d-block"
                        />
                    </div>
                    <div className={`col-lg-6 ${index % 2 === 0 ? "order-lg-2" : "order-lg-1"} text-center text-lg-start`}>
                        <h3 className="header-large pt-3">{item.title ? item.title : item.name}</h3>
                        {item.price && <h2 className="price-text" > {item.price} DKK </h2>}
                        <p className="body-text">{item.description}</p>
                        {item.buttonText && <div className="d-flex justify-content-center pt-3">
                            <Button text={item.buttonText} isWide={true} onClick={() => navigate(item.buttonPath)}/>
                        </div>
                        }
                        {item.stock && <div className="d-flex pt-3">
                            < CartModify item={item} />
                        </div>
                        }
                    </div>
                </div>
                ))}
            </div>
    );
};

export default AlternatingItems;