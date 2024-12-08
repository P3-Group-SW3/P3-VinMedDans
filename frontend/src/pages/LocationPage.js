import Footer from "../components/Footer";
import Header from "../components/Header";
import {CustomerLinks} from "./CustomerLinkContext";


function LocationPage() {
    return (
        <div className="Landingpage">
            <Header
                links={ CustomerLinks }
                showCart={true}
            />
            <Footer/>
        </div>
    );
}

export default LocationPage;