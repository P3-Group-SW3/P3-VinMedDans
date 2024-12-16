import AgeVerification from "../components/AgeVerification";
import Footer from "../components/Footer";
import Header from "../components/Header";
import {CustomerLinks} from "./CustomerLinkContext";
import Event from "../components/Event";
import React from "react";
import Distributor from "../components/Distributor";


function LocationPage() {
    const [distributors, setDistributors] = React.useState([]);

    React.useEffect(() => {
        fetch("/api/distributor/getList")
            .then((response) => response.json())
            .then((data) => setDistributors(data))
            .catch(error => console.error('Error fetching data:', error));
    }, []);

    function LocationPage() {
        return (
            <div>
                <AgeVerification/>
                <Header
                    links={CustomerLinks}
                    showCart={true}
                />
                {distributors.map((event) => (
                    <Distributor
                        key={event.id}
                        title={distributors.title}
                        name={distributors.name}
                        location={distributors.locations}
                        websiteURL={distributors.websiteURL}
                    />
                ))}
                <Footer/>
            </div>
        );
    }
}
    export default LocationPage;
