import Cookies from 'js-cookie';
import URL_API from "../const";

const url = URL_API;

export const fetchGroups = async () => {
    console.log(`${url}/users/groups`)
    console.log(Cookies.get("token"))
    try {
        const response = await fetch(`${url}/users/groups`, {
            method: "GET",
            headers: {
                //"Content-Type": "application/json",
                "Authorization": `Bearer ${Cookies.get("token")}`,
            },
        });
        if (response.ok) {
            const result = await response.json();
            return result.userGroups;
            console.log(result);
        }
        return;
    } catch (error) {
        console.log(error);
    }
}

export const fetchGroupUsers = async (groupId) => {
    const token = Cookies.get("token");
    const url = `${URL_API}/groups/${groupId}/users`;

    try {
        const response = await fetch(url, {
            method: "GET",
            headers: {
                "Authorization": `Bearer ${token}`,
            },
        });
        if (response.ok) {
            const result = await response.json();
            return result.groupMembers;
        } else {
            throw new Error("Błąd przy pobieraniu danych.");
        }
    } catch (error) {
        console.error("Błąd:", error);
        return [];
    }
};