import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

const CriiptoAuthWrapper = ({ children }) => {
  const navigate = useNavigate();

  useEffect(() => {
    const params = {
      scope: 'openid',
      client_id: 'urn:my:application:identifier:253367',
      redirect_uri: 'http://localhost:8080/shop',
      response_type: 'code',
      response_mode: 'fragment',
      nonce: 'ecnon-1d297cb1-fafc-4fdd-81ef-f3aede0df2c7',
      acr_values: 'urn:grn:authn:dk:mitid:low urn:age-verification'
    };

    const authUrl = `https://p3-test.criipto.id/oauth2/authorize?${new URLSearchParams(params).toString()}`;
    window.location.href = authUrl;
  }, [navigate]);

  return children;
};

export default CriiptoAuthWrapper;