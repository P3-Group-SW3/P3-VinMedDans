import React from "react";
import { useState, useEffect } from "react";
import Header from "../components/Header";
import Footer from "../components/Footer";
import Item from "../components/Item";

function ProductPage() {
    const [items, setItems] = useState([]);

    useEffect(() => {
        fetch('/api/getAllWines')
            .then(response => response.json())
            .then(data => setItems(data))
            .catch(error => console.error('Error fetching data:', error));
    }, []);

    console.log(items);

    return (
        <div className="productPage">
            <Header />
            
            {items.map((item, index) => (
                <Item
                    key={index}
                    title={item?.name}
                    description={item?.description}
                    price={item?.price?.toString()} 
                    image={item.imageURL}
                    stock={item?.stock?.toString()}
                    imagePos="left"
                    item={item}  
                ></Item>
            ))}
            <Footer />
        </div>
    );
}

export default ProductPage;