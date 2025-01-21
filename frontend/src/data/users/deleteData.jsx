import Cookies from 'js-cookie';
import URL_API from "../const";

const url = URL_API;

export const deleteSelfFromGroup = async (groupId) => {
    try {
        const response = await fetch(`${url}/groups/${groupId}/users`, {
            method: "DELETE",
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
        console.error("Error leaving the group:", error);
        throw error;
    }
};
