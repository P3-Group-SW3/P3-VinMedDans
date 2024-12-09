import React from 'react';
import '../styles/order.css';

const StateCircles = ({ state, customStateNames }) => {
    const states = ["REGISTERED", "CONFIRMED", "PACKED", "SHIPPED"];
    const currentStateIndex = states.indexOf(state);

    return (
        <div className="order-status">
            {states.map((s, index) => (
                <React.Fragment key={s}>
                    <div className={`step ${index <= currentStateIndex ? 'completed' : ''} ${index === currentStateIndex ? 'active' : ''}`}>
                        <div className="circle">{index + 1}</div>
                        <p>{customStateNames[s] || s}</p>
                    </div>
                    {index < states.length - 1 && <div className="line"></div>}
                </React.Fragment>
            ))}
        </div>
    );
};

export default StateCircles;