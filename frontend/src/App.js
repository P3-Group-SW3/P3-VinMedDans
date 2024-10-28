import React from 'react';
import Header from './components/Header';
import Navbar from './components/Navbar';
import Section from './components/Section';
import Footer from './components/Footer';
import Item from './components/Item';
import './styles/styles.css';
import './bootstrap/dist/css/bootstrap.min.css';


function App() {
  return (
    <div className="App">
      <Header />
      <Navbar />
      <Section 
        title="Hvad er frugtvin"
        description="Lorem ipsum consectetur adipiscing elit. Maecenas tincidunt ac dolor eget gravida..."
        imagePosition="left"
        imagePath='image4'
      />
      <Section 
        title="Jord til bord"
        description="Lorem ipsum consectetur adipiscing elit. Maecenas tincidunt ac dolor eget gravida..."
        imagePosition="right"
        imagePath='image5'
      />
      <Section 
        title="Køb vores frugtvin"
        description="Lorem ipsum consectetur adipiscing elit. Maecenas tincidunt ac dolor eget gravida..."
        buttonText="Gå til webshop"
        imagePosition="left"
        imagePath='image6'
      />
      <Item 
        title="Frugtvin"
        description="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas tincidunt ac dolor eget gravida. Sed nec..."
        price="100 kr."
        stock="På lager"
        image="https://images.unsplash.com/photo-1572448815633-4e3f2a7c3e2f"
        imagePos="left"
      />
      <Footer />
    </div>
  );
}

export default App;
