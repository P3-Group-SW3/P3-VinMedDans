import React from "react";
import Header from "../components/Header";
import Navbar from "../components/Navbar";
import Footer from "../components/Footer";
import Item from "../components/Item";



const items = [
    {
        id: 1,
        name: "Cabernet Sauvignon",
        description: "A full-bodied red wine with dark fruit flavors and savory tastes from black pepper to bell pepper.",
        price: 29.99,
        stock: 50,
        imageUrl: "https://via.placeholder.com/150",
    },
    {
        id: 2,
        name: "Chardonnay",
        description: "A popular white wine with flavors ranging from apple and lemon to papaya and pineapple.",
        price: 19.99,
        stock: 75,
        imageUrl: "https://via.placeholder.com/150",
    },
    {
        id: 3,
        name: "Pinot Noir",
        description: "A light-bodied red wine with flavors of red fruit, flowers, and spices.",
        price: 34.99,
        stock: 40,
        imageUrl: "https://via.placeholder.com/150",
    },
    {
        id: 4,
        name: "Sauvignon Blanc",
        description: "A crisp, dry, and aromatic white wine with flavors of lime, green apple, passion fruit, and white peach.",
        price: 24.99,
        stock: 60,
        imageUrl: "https://via.placeholder.com/150",
    },
    {
        id: 5,
        name: "Merlot",
        description: "A smooth red wine with flavors of black cherry, raspberry, and plum.",
        price: 22.99,
        stock: 55,
        imageUrl: "https://via.placeholder.com/150",
    }
];

function ProductPage() {
    return (
        <div className="productPage">
            <Header />
            <Navbar />
            
            {items.map((item, index) => (
                <Item
                    key={index}
                    title={item.name}
                    description={item.description}
                    price={item.price}
                    image={item.imageBase64}
                    stock={item.stock}
                    imagePos="left"
                ></Item>
            ))}
            <Footer />
        </div>
    );
}

export default ProductPage;