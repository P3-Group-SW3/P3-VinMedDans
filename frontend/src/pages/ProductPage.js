import { useState, useEffect } from "react";
import AgeVerification from "../components/AgeVerification";
import Header from "../components/Header";
import Footer from "../components/Footer";
import AlternatingItems from "../components/AlternatingItems";
import {CustomerLinks} from "./CustomerLinkContext";

/*
 * This page displays all products currently stored in the database.
 */
function ProductPage() {

    // The 'items' state will contain all items
    const [items, setItems] = useState([]);
    
    // On mount: Get all items and store in state variable 'items'
    useEffect(() => {
        fetch('/api/wine/getList')
            .then(response => response.json())
            .then(data => setItems(data))
            .catch(error => console.error('Error fetching data:', error));
    }, []);

    return (
        <div>
            <AgeVerification/>
            <Header
                links={ CustomerLinks }
                showCart={true}
            />
            {/* Items are passed into an AlternatingItems component */}
            <AlternatingItems items={items}/>
            <Footer />
        </div>
    );
}

export default ProductPage;
