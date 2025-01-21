import AgeVerification from "../components/AgeVerification";
import Footer from "../components/Footer";
import Header from "../components/Header";
import Event from "../components/Event";
import {useState, useEffect} from "react";
import {CustomerLinks} from "./CustomerLinkContext";

/*
 * This page displays all events currently stored in the database.
 */
const EventPage = () => {
    // The 'events' state will contain all events
    const [events, setEvents] = useState([]);

    // On mount: Get all events and store in state variable 'events'
    useEffect(() => {

        fetch("/api/event/getList")
            .then((response) => response.json())
            .then((data) => setEvents(data))
            .catch(error => console.error('Error fetching data:', error));
    }, []);

    return (
        <div>
            <AgeVerification/>
            <Header
                links={ CustomerLinks }
                showCart={true}
            />
            {events.map((event) => (
                <Event
                    key={event.id}
                    title={event.title}
                    date={event.date}
                    time={event.time}
                    location={event.location}
                    description={event.description}
                    imageUrl={event.imageURL}
                />
            ))}
            <Footer />
        </div>
    );
}

export default EventPage;