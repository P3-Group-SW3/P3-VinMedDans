import React from 'react';
import PropTypes from 'prop-types';
import { CartModify } from './cartModify';
import '../styles/fonts.css';
import '../styles/item.css';

const Item = ({ title, description, price, image, stock, imagePos, item}) => {

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
        <div className="container my-5 rounded-box" style={{ width: '100%', height: '100%' }}>
            <div className={`row align-items-center ${imagePos === 'left' ? '' : 'flex-row-reverse'}`}>
                <div className="col-md-6 d-flex justify-content-center">
                    <div className="product-image">
                        {image && (
                            <img
                                src={image}
                                alt={title}
                                className="img-fluid"
                            />
                        )}
                    </div>
                </div>
                <div className="col-md-6">
                    <h2 className="header-large">{title}</h2>
                    <p className="price-text">{price} DKK</p>
                    <p className="body-text">{description}</p>
                    <CartModify item={item} />
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