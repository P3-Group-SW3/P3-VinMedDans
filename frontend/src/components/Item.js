import React from 'react';
import PropTypes from 'prop-types';
import { CartModify } from './cartModify';
import '../styles/fonts.css'; // Ensure the correct path to your CSS file

const Item = ({ title, description, price, image, stock, imagePos }) => {
    const isImageLeft = imagePos === 'left';

    const renderStockMessage = () => {
        if (stock > 10) {
            return <p className="lead stock-green" style={{ fontSize: '1.1rem' }}>10 eller flere på lager</p>;
        } else if (stock > 0 && stock <= 10) {
            return <p className="lead stock-yellow" style={{ fontSize: '1.1rem' }}>{stock} på lager</p>;
        } else {
            return <p className="lead stock-red" style={{ fontSize: '1.1rem' }}>Ingen vare på lager</p>;
        }
    };

    return (
        <div className="container my-5">
            <div className={`row align-items-center ${isImageLeft ? '' : 'flex-row-reverse'}`}>
                <div className="col-md-6 d-flex justify-content-center">
                    <div className="border p-3" style={{ width: '450px', height: '450px' }}>
                        {image && (
                            <img
                                src={image}
                                alt={title}
                                className="img-fluid"
                                style={{ width: '100%', height: '100%', objectFit: 'cover' }}
                            />
                        )}
                    </div>
                </div>
                <div className="col-md-6">
                    <h2 className="header-large">{title}</h2>
                    <p className="body-text">{description}</p>
                    <p className="price-text">Pris: {price}</p>
                    <CartModify />
                    {renderStockMessage()}
                </div>
            </div>
        </div>
    );
}

Item.propTypes = {
    title: PropTypes.string.isRequired,
    description: PropTypes.string.isRequired,
    price: PropTypes.string.isRequired,
    image: PropTypes.string.isRequired,
    stock: PropTypes.string.isRequired,
    imagePos: PropTypes.oneOf(['left', 'right']),
};

export default Item;