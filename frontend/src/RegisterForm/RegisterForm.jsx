import React from 'react';
import './RegisterForm.css';
import { FaUserCircle, FaLock } from "react-icons/fa";
import { Link } from 'react-router-dom';

export const RegisterForm = () => {
    return (
        <div className='wrapper'>
            <form action=''>
                <h1>Register</h1>
                <div className='input-box'>
                    <input type="text" placeholder='Username' required />
                    <FaUserCircle className='icon' />
                </div>
                <div className='input-box'>
                    <input type="password" placeholder='Password' required />
                    <FaLock className='icon' />
                </div>
                <div className='input-box'>
                    <input type="password" placeholder='Confirm Password' required />
                    <FaLock className='icon' />
                </div>

                <button className='button' type='submit'>Register</button>

                <div className="login-link">
                    <p>Already have an account? <Link to="/">Login here!</Link></p>
                </div>
            </form>
        </div>
    )
}

export default RegisterForm;