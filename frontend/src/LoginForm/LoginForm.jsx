import React, { useState } from 'react';
import './LoginForm.css';
import { FaUserCircle, FaLock } from "react-icons/fa";
import { Link, Navigate } from 'react-router-dom';
import { loginUser } from '../data/auth/postData';
import { useNavigate } from 'react-router-dom';

export const LoginForm = () => {
    const [password, setPassword] = useState('');
    const [email, setEmail] = useState('');
    const navigate = useNavigate();

    const handleLogin = async (e) => {
        e.preventDefault();

        const userData = {
            password: password,
            email: email
        }
        try {
            const response = await loginUser(userData)

            console.log(response);

            if (response.token) {
                navigate('/grouppage');
            }
            else {
                alert(response.message); //TODO: Display the error message
            }
        }
        catch (error) {
            console.log(error);
        }
    };

    return (
        <div className='wrapper'>
            <form action=''>
                <h1>Login</h1>

                <div className='input-box'>
                    <input type="text" placeholder='Email' onChange={(e) => setEmail(e.target.value)} value={email} /*required*/ />
                    <FaUserCircle className='icon' />
                </div>

                <div className='input-box'>
                    <input type="password" placeholder='Password' onChange={(e) => setPassword(e.target.value)} value={password} /*required*/ />
                    <FaLock className='icon' />
                </div>

                <div className="remember-forgot">
                    <label><input type="checkbox" /> Remember me</label>
                    <a href="#">Forgot Password?</a>
                </div>

                <Link to="/grouppage">
                    <button className='button' onClick={handleLogin} type='submit'>Login</button>
                </Link>

                <div className="register-link">
                    <p>Don't have an account? <Link to="/register">Register here!</Link></p>
                </div>
            </form>
        </div>
    )
}

export default LoginForm;