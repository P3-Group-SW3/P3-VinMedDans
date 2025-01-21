import React from 'react';
import PropTypes from 'prop-types';

/*
 * The Event component handles displaying an event on the EventPage.
 */
const Event = ({ title, date, time, location, description, imageUrl }) => {

    //Checks if the date for the event has passed or not, display text for each case
    const renderEventStatus = () => {
        const eventDate = new Date(date);
        const today = new Date();
        if (eventDate < today) {
            return <p className="lead event-past" style={{ fontSize: '1.1rem' }}>Begivenheden er afholdt</p>;
        } else {
            return <p className="lead event-upcoming" style={{ fontSize: '1.1rem' }}>Kommende begivenhed</p>;
        }
    };

    return (
        <div className="container my-3" style={{ backgroundColor: '#f8f6e8'}}>
            <div className="row no-gutters align-items-stretch">
                <div className="col-md-6 body-text">
                    <h2 className="header-large" >{title}</h2>
                    {renderEventStatus()}
                    <p> {date} </p>
                    <p> {time} </p>
                    <p> {location} </p>
                    <p> {description} </p>
                </div>
                <div className="col-md-6">
                    {imageUrl && (
                        <img
                            src={imageUrl}
                            alt={title}
                            style={{width: '100%', height: '100%', objectFit: 'cover' }}
                        />
                    )}
                </div>
            </div>
        </div>
    );
};

Event.propTypes = {
    title: PropTypes.string.isRequired,
    date: PropTypes.string.isRequired,
    time: PropTypes.string.isRequired,
    location: PropTypes.string.isRequired,
    description: PropTypes.string.isRequired,
    imageURL: PropTypes.string,
};

export default Event;