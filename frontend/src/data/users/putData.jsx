import URL_API from "../const";
import Cookies from "js-cookie";

const url = URL_API;

export const changePassword = async (oldPassword, newPassword) => {
    try {
        const token = Cookies.get("token");
        if (!token) {
            throw new Error("Authorization token is missing");
        }

        const response = await fetch(`${url}/users/password`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${token}`,
            },
            body: JSON.stringify({
                oldPassword: oldPassword,
                newPassword: newPassword,
            }),
        });

        if (!response.ok) {
            const errorResult = await response.json();
            console.error("Error response:", errorResult);
            return {
                success: false,
                error: errorResult.message || "Unknown error occurred",
            };
        }

        const result = await response.json();
        console.log("Password change result:", result);
        return {
            success: true,
            data: result,
        };
    } catch (error) {
        console.error("An error occurred:", error);
        return {
            success: false,
            error: error.message,
        };
    }
};