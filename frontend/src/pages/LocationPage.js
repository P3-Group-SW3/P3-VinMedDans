import {useState, useEffect} from "react";
import AgeVerification from "../components/AgeVerification";
import Footer from "../components/Footer";
import Header from "../components/Header";
import Distributor from "../components/Distributor";
import {CustomerLinks} from "./CustomerLinkContext";

/*
 * This page displays all distributors currently stored in the database.
 */
const LocationPage = () => {

    // The 'distributors' state will contain all distributors
    const [distributors, setDistributors] = useState([]);

    // On mount: Get all distributors and store in state variable 'distributors'
    useEffect(() => {

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
