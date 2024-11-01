import images from "../images/images";

const havtorben = images["havtorben"];
const stikkelsvin = images["stikkelsvin"];
const hoenefuld = images["hoenefuld"];



const Orders = [
    {
        id: 1,
        name: "Havtorben",
        quantity: 2,
        price: 100,
        image: havtorben
    },
    {
        id: 2,
        name: "Stikkelsvin",
        quantity: 2,
        price: 150,
        image: stikkelsvin
    },
    {
        id: 3,
        name: "Hønefuld",
        quantity: 2,
        price: 400,
        image: hoenefuld
    },
];

export default Orders;