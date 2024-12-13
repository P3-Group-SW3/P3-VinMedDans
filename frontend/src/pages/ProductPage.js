import React from "react";
import { useState, useEffect } from "react";
import AgeVerification from "../components/AgeVerification";
import Header from "../components/Header";
import Footer from "../components/Footer";
import {CustomerLinks} from "./CustomerLinkContext";
import AlternatingItems from "../components/AlternatingItems";

function ProductPage() {
    const [items, setItems] = useState([]);

    /*
    call: /api/getAllWines
    method: GET
    response: JSON array of objects
    */
    useEffect(() => {
        fetch('/api/wine/getList')
            .then(response => response.json())
            .then(data => setItems(data))
            .catch(error => console.error('Error fetching data:', error));
    }, []);

    console.log(items);

    return (
        <div>
            <AgeVerification/>
            <Header
                links={ CustomerLinks }
                showCart={true}
            />

            <AlternatingItems items={items}/>
            <Footer />
        </div>
    );
}

export default ProductPage;
