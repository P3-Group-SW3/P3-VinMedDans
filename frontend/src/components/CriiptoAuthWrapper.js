import {useEffect} from 'react';
import {useNavigate} from 'react-router-dom';

const CriiptoAuthWrapper = ({ children, redirectUrl }) => {
  const navigate = useNavigate();

  useEffect(() => {
    if (redirectUrl) {
      const params = {
        scope: 'openid',
        client_id: 'urn:my:application:identifier:416999',
        redirect_uri: redirectUrl,
        response_type: 'code',
        response_mode: 'fragment',
        nonce: 'ecnon-1d297cb1-fafc-4fdd-81ef-f3aede0df2c7',
        acr_values: 'urn:grn:authn:dk:mitid:low urn:age-verification'
      };

      const authUrl = `https://no-org-test.criipto.id/oauth2/authorize?${new URLSearchParams(params).toString()}`;
      window.location.href = authUrl;
    }
  }, [navigate, redirectUrl]);

  return children;
};

export default CriiptoAuthWrapper;