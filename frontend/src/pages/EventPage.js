import Footer from "../components/Footer";
import Navbar from "../components/Navbar";
import Header from "../components/Header";
import Event from "../components/Event";
import React from "react";
import images from "../images/images";

const events = [
    {
        id: 1,
        title: "Æbleplukning",
        date: "12/12-2024",
        time: "20:00",
        location: "Farmeren og Skytten, Vemmetoftevej 15, 4640 Faxe",
        description: "Kom med ud når vi plukker æbler til vores frugtvin.",
        imageUrl: images.plukning,
    },
    {
        id: 2,
        title: "Pop up på Pio",
        date: "05/01-2025",
        time: "17:00",
        location: "Pio vin bar",
        description: "Kom på Pio og smag den nye havtorben, hvor vi snakker om vores vin og hvordan vi producerer den",
        imageUrl: images.venner,
    },
    {
        id: 3,
        title: "Omstikning på lageret",
        date: "12/02-2025",
        time: "12:00",
        location: "Rødovre",
        description: "Kom med på lageret når vi omstikker vores vin, og smager på sagerne!",
        imageUrl: images.lager,
    },
    {
        id: 4,
        title: "Vinsmagning på gaarden",
        date: "12/05-2025",
        time: "13:00",
        location: "Falster",
        description: "Tag med på gården til Vin Med Dans påskefrokost=D",
        imageUrl: images.gaarden,
    }
];

function EventPage() {
    return (
        <div className="Landingpage">
            <Header />
            <Navbar />
            {events.map((event) => (
                <Event
                    key={event.id}
                    title={event.title}
                    date={event.date}
                    time={event.time}
                    location={event.location}
                    description={event.description}
                    imageUrl={event.imageUrl}
                />
            ))}
            <Footer />
        </div>
    );
}

export default EventPage;