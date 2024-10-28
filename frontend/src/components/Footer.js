import React from 'react';

function Footer() {
  return (
    <footer className="container-fluid bg-light py-4">
      <div className="row">
        <FooterColumn title="Instagram" items={["Hvad skal der stå her?", "Hvad skal der stå her?", "Hvad skal der stå her?"]} />
        <FooterColumn items={["Hvad skal der stå her?", "Hvad skal der stå her?", "Hvad skal der stå her?"]} />
      </div>
    </footer>
  );
}

function FooterColumn({ title, items }) {
  return (
    <div className="col-md-6">
      {title && <h5>{title}</h5>}
      <ul className="list-unstyled">
        {items.map((item, index) => (
          <li key={index} style={{ fontFamily: 'Rubik' }}>{item}</li>
        ))}
      </ul>
    </div>
  );
}

export default Footer;
