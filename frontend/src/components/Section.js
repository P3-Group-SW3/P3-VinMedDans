import React from 'react';
import PropTypes from 'prop-types';
import images from '../images/images'; // Adjust the path as needed

const Section = ({ title, description, imagePosition, buttonText, imagePath }) => {
  const isImageLeft = imagePosition === 'left';

  // Dynamically select the image from the images object
  const selectedImage = images[imagePath];

  return (
    <div className="container my-5">
      <div className={`row align-items-center ${isImageLeft ? '' : 'flex-row-reverse'}`}>
        <div className={`col-md-6 d-flex ${isImageLeft ? 'justify-content-start' : 'justify-content-end'}`}>
          <div style={{ width: '450px', height: '450px' }}>
            {selectedImage && (
              <img
                src={selectedImage}
                alt={title}
                className="img-fluid"
                style={{ width: '100%', height: '100%', objectFit: 'cover' }}
              />
            )}
          </div>
        </div>
        <div className="col-md-6">
          <h2 className="display-5" style={{ fontSize: '3.5rem', fontFamily: 'Shadows' }}>{title}</h2> {/* Adjusted font size */}
          <p className="lead" style={{ fontSize: '1.1rem', fontFamily: 'RubikRegular' }}>{description}</p> {/* Adjusted font size */}
          {buttonText && <button style={{background: '#E93271', border: 'none', borderRadius: '25px', fontSize: '1.2rem',fontFamily: 'CherryBomb'}} className="btn btn-primary mt-3">{buttonText}</button>} {/* Added margin-top */}
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