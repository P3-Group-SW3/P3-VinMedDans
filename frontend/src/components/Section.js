import React from 'react';
import PropTypes from 'prop-types';

const Section = ({ title, description, imagePosition, buttonText, imagePath }) => {
  const isImageLeft = imagePosition === 'left';


  return (
    <div className="container my-5">
      <div className={`row align-items-center ${isImageLeft ? '' : 'flex-row-reverse'}`}>
        <div className="col-md-6 d-flex justify-content-center">
          <div className="border p-3" style={{ width: '450px', height: '450px' }}>
            {imagePath && (
              <img
                src={imagePath}
                alt={title}
                className="img-fluid"
                style={{ width: '100%', height: '100%', objectFit: 'cover' }}
              />
            )}
          </div>
        </div>
        <div className="col-md-6">
          <h2 className="display-5" style={{ fontSize: '2rem' }}>{title}</h2> {/* Adjusted font size */}
          <p className="lead" style={{ fontSize: '1.1rem' }}>{description}</p> {/* Adjusted font size */}
          {buttonText && <button className="btn btn-primary mt-3">{buttonText}</button>} {/* Added margin-top */}
        </div>
      </div>
    </div>
  );
};

Section.propTypes = {
  title: PropTypes.string.isRequired,
  description: PropTypes.string.isRequired,
  imagePosition: PropTypes.oneOf(['left', 'right']),
  buttonText: PropTypes.string,
  imagePath: PropTypes.string.isRequired, // Ensure this prop is required for the image source
};

export default Section;