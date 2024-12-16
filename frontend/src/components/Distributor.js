import React from 'react';
import PropTypes from 'prop-types';

const Distributor = ({ name, location, websiteURL }) => {

    return (
        <div className="container my-3" style={{ backgroundColor: '#f8f6e8'}}>
            <div className="row no-gutters align-items-stretch">
                <div className="col-md-6">
                    <h2 style={{ fontFamily: 'Rubik Dirt', fontWeight: 'bold', color: '#405071' }}>{name}</h2>
                    <p style={{ fontFamily: 'Rubik', color: 'black' }}> {location} </p>
                    <p style={{ fontFamily: 'Rubik', color: 'black' }}> {websiteURL} </p>
                </div>
            </div>
        </div>
    );
};

Distributor.propTypes = {
    title: PropTypes.string.isRequired,
    name: PropTypes.string.isRequired,
    location: PropTypes.string.isRequired,
    websiteURL: PropTypes.string.isRequired,
};

export default Distributor;