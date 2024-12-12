import { useEffect } from 'react';
import Cookies from 'js-cookie';

const CriiptoCallback = () => {
  useEffect(() => {
    const handleCallback = () => {
      const params = new URLSearchParams(window.location.hash.substring(1));
      const code = params.get('code');
      const state = params.get('state');
      const idToken = params.get('id_token');

      if (code || state || idToken) {
        const data = {
          code,
          state,
          idToken
        };
        console.log('Criipto callback response:', data);

        // Read the Stripe URL from the cookie
        const stripeUrl = Cookies.get('stripeUrl');
        console.log('Stripe URL retrieved from cookie:', stripeUrl);
        if (stripeUrl) {
          window.location.href = stripeUrl;
        } else {
          console.error('Stripe URL is missing');
        }
      }
    };

    handleCallback();
  }, []);

  return <div>Loading...</div>;
};

export default CriiptoCallback;