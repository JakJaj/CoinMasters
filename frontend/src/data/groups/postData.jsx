import Cookies from 'js-cookie';
import URL_API from "../const";

const url = URL_API;

export const createGroup = async (groupData) => {
    try {
        const response = await fetch(`${url}/groups`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${Cookies.get("token")}`,
            },
            body: JSON.stringify(groupData),
        });

        if (response.ok) {
            const result = await response.json();
            console.log(result);
            return result;
        } else {
            const error = await response.json();
            console.error("Error creating group:", error);
            alert(error.message || "Failed to create group");
        }
    } catch (error) {
        console.error(error);
    }
};

export const joinGroup = async (joinCode) => {
    try {
        const response = await fetch(`${url}/groups/users`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${Cookies.get("token")}`,
            },
            body: JSON.stringify(joinCode),
        });

        if (response.ok) {
            const result = await response.json();
            console.log(result);
            return result;
        } else {
            const error = await response.json();
            console.error("Error joining group:", error);
            alert(error.message || "Failed to join group");
        }
    } catch (error) {
        console.error(error);
    }
};