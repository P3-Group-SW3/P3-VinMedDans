import React from 'react';
import PropTypes from 'prop-types';
import { CartModify } from './cartModify';

const Item = ({ title, description, price, image, stock, imagePos }) => {
    const isImageLeft = imagePos === 'left';


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
                    <h2 className="display-5" style={{ fontSize: '2rem' }}>{title}</h2>
                    <p className="lead" style={{ fontSize: '1.1rem' }}>{description}</p>
                    <p className="lead" style={{ fontSize: '1.1rem' }}>Pris: {price}</p>
                    <CartModify />
                    <p className="lead" style={{ fontSize: '1.1rem' }}>Lager: {stock}</p>
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