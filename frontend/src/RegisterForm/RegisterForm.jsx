import React, { useState } from 'react';
import './RegisterForm.css';
import { FaUserCircle, FaEye, FaEyeSlash } from "react-icons/fa";
import { Link } from 'react-router-dom';
import { registerUser } from '../data/auth/postData';
import { useNavigate } from 'react-router-dom';

export const RegisterForm = () => {
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [showPassword, setShowPassword] = useState(false);
    const [errors, setErrors] = useState([]);
    const [userName, setUsername] = useState('');
    const [email, setEmail] = useState('');
    const navigate = useNavigate();

    const handlePasswordChange = (e) => {
        setPassword(e.target.value);
    };

    const handleConfirmPasswordChange = (e) => {
        setConfirmPassword(e.target.value);
    };

    const togglePasswordVisibility = () => {
        setShowPassword(!showPassword);
    };

    const validatePassword = () => {
        const errorMessages = [];
        const passwordRegex = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;

        if (password !== confirmPassword) {
            errorMessages.push("Passwords do not match.");
        }

        if (!passwordRegex.test(password)) {
            errorMessages.push("Password must be at least 8 characters long, contain at least one letter, one digit, and one special character.");
        }

        if (!/^[a-zA-Z\d@$!%*?&]+$/.test(password)) {
            errorMessages.push("Password contains invalid characters.");
        }

        setErrors(errorMessages);

        return errorMessages.length === 0;
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        const userData = {
            name: userName,
            password: password,
            email: email
        }
        try {
            const response = await registerUser(userData)

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
            <form onSubmit={handleSubmit}>
                <h1>Register</h1>

                <div className='input-box'>
                    <input type="text" placeholder='Username' onChange={(e) => setUsername(e.target.value)} value={userName} /*required*/ />
                    <FaUserCircle className='icon' />
                </div>

                <div className='input-box'>
                    <input type="text" placeholder='Email' onChange={(e) => setEmail(e.target.value)} value={email} /*required*/ />
                    <FaUserCircle className='icon' />
                </div>

                <div className='input-box'>
                    <input
                        type={showPassword ? "text" : "password"}
                        placeholder='Password'
                        value={password}
                        onChange={handlePasswordChange}
                    /*required*/
                    />
                    {showPassword ? (
                        <FaEyeSlash className='icon' onClick={togglePasswordVisibility} />
                    ) : (
                        <FaEye className='icon' onClick={togglePasswordVisibility} />
                    )}
                </div>

                <div className='input-box'>
                    <input
                        type={showPassword ? "text" : "password"}
                        placeholder='Confirm Password'
                        value={confirmPassword}
                        onChange={handleConfirmPasswordChange}
                    /*required*/
                    />
                    {showPassword ? (
                        <FaEyeSlash className='icon' onClick={togglePasswordVisibility} />
                    ) : (
                        <FaEye className='icon' onClick={togglePasswordVisibility} />
                    )}
                </div>
                <Link to="/grouppage">
                    <button className='button' onClick={handleSubmit} type='submit'>Register</button>
                </Link>

                {errors.length > 0 && (
                    <div className='error-messages'>
                        {errors.map((error, index) => (
                            <p key={index} className="error">{error}</p>
                        ))}
                    </div>
                )}

                <div className="login-link">
                    <p>Already have an account? <Link to="/login">Login here!</Link></p>
                </div>
            </form>
        </div>
    );
}

export default RegisterForm;
