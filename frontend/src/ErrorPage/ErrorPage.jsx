import React from 'react';
import { Link } from 'react-router-dom';
import './ErrorPage.css';

export const ErrorPage = () => {
    return (
        <div className='error-wrapper'>
            <div>
                <h1 className='main-title'>
                    <span>OOPS</span>
                </h1>
                <p className='subtitle'>You should not be here</p>
                <div className='button-container'>
                    <Link to="/">
                        <button className='button'>Back to Home</button>
                    </Link>
                </div>
            </div>
        </div>
    );
}

export default ErrorPage;
