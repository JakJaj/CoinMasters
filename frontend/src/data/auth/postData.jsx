import URL_API from "../const";
import Cookies from 'js-cookie';

const url = URL_API;

export const registerUser = async (userData) => {
    try {
        const response = await fetch(`${url}/auth/register`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(userData),
        });
        if (response.ok) {
            const result = await response.json();
            Cookies.set("token", result.token, { expires: 1 });
            return result;
        }
        return;
    } catch (error) {
        console.log(error);
    }
}

export const loginUser = async (userData) => {
    try {
        const response = await fetch(`${url}/auth/login`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(userData),
        });
        if (response.ok) {
            const result = await response.json();
            Cookies.set("token", result.token, { expires: 1 });
            return result;
        }
        return;
    } catch (error) {
        console.log(error);
    }
}