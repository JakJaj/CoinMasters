import React, { useEffect } from 'react';
import { Link } from 'react-router-dom';
import './LandingPage.css';
import { getData } from '../data/users/getData';

export const LandingPage = () => {

    useEffect(() => {
        const temporary = async () => {
            const data = await getData();

        }
        temporary();
    })

    return (
        <div className='landing-wrapper'>
            <div>
                <h1 className='main-title'>
                    <span className='typing-animation'>COINMASTERS</span>
                </h1>
                <p className='subtitle'>Manage your finances in one place</p>
                <div className='button-container'>
                    <Link to="/register">
                        <button className='button'>JOIN US</button>
                    </Link>
                    <Link to="/login">
                        <button className='button'>Login</button>
                    </Link>
                </div>
            </div>
        </div>
    );
}

export default LandingPage;
