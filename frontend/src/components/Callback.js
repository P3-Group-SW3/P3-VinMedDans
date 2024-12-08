// frontend/src/components/Callback.js
import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useCriiptoVerify } from '@criipto/verify-react';

const Callback = () => {
  const navigate = useNavigate();
  const { handleRedirectCallback, user } = useCriiptoVerify();

  useEffect(() => {
    handleRedirectCallback().then(() => {
      if (user && user.age >= 18) {
        navigate('/checkout'); // Should redirect to payment page
      } else {
        alert('You must be at least 18 years old to proceed.');
        navigate('/');
      }
    }).catch((error) => {
      console.error('Callback error:', error);
      navigate('/');
    });
  }, [handleRedirectCallback, navigate, user]);

  return <div>Loading...</div>;
};

export default Callback;