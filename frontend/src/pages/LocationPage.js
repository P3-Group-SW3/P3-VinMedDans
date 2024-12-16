import AgeVerification from "../components/AgeVerification";
import Footer from "../components/Footer";
import Header from "../components/Header";
import {CustomerLinks} from "./CustomerLinkContext";
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

        return (
            <div>
                <AgeVerification/>
                <Header
                    links={CustomerLinks}
                    showCart={true}
                />
                {distributors.map((distributor) => (
                    <Distributor
                        key={distributor.id}
                        name={distributor.name}
                        location={distributor.location}
                        websiteURL={distributor.websiteURL}
                    />
                ))}
                <Footer/>
            </div>
        );
}
    export default LocationPage;
