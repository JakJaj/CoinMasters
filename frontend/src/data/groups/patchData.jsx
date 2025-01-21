import Cookies from "js-cookie";
import URL_API from "../const";

const url = URL_API;

export const updateGroupDetails = async (groupID, groupDetails) => {
    try {
        const response = await fetch(`${url}/groups/${groupID}`, {
            method: "PATCH",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${Cookies.get("token")}`,
            },
            body: JSON.stringify(groupDetails),
        });

        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(`HTTP error ${response.status}: ${errorText}`);
        }

        const result = await response.json();
        return result;
    } catch (error) {
        console.error("Error updating group details:", error);
        throw error;
    }
};
