import Footer from "../components/Footer";
import Header from "../components/Header";
import Event from "../components/Event";
import React from "react";


function EventPage() {

    /* 
    API handle: /api/event/getAll
    Method: GET
    Description: Get all events
    */

    const [events, setEvents] = React.useState([]);

    React.useEffect(() => {
        fetch("/api/event/getAll")
            .then((response) => response.json())
            .then((data) => setEvents(data));
    }, []);

    return (
        <div className="Landingpage">
            <Header />
            {events.map((event) => (
                <Event
                    key={event.id}
                    title={event.title}
                    date={event.date}
                    time={event.time}
                    location={event.location}
                    description={event.description}
                    imageUrl={event.imgURL}
                />
            ))}
            <Footer />
        </div>
    );
}

export default EventPage;