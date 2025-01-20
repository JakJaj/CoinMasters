import URL_API from "../const";
import Cookies from "js-cookie";

const url = URL_API;

export const getData = async () => {
    try {
        const response = await fetch(`${url}/actuator`, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
            }
        });
        const result = await response.json();
        const data = {
            status: result.status,
        }
        console.log(result);
        return null;
    }
    catch (error) {
        console.log(error);
        return;
    }
}

export const getUserDetails = async () => {
    try {
        const response = await fetch(`${url}/users`, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${Cookies.get("token")}`,
            },
        });

        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(`HTTP error ${response.status}: ${errorText}`);
        }

        const result = await response.json();
        return result;
    } catch (error) {
        console.error("Błąd przy pobieraniu danych użytkownika:", error);
        throw error;
    }
};
