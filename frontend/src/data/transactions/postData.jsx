import Cookies from 'js-cookie';
import URL_API from "../const";

const url = URL_API;

export const createTransaction = async (transactionData) => {
    try {
        const response = await fetch(`${url}/transactions`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${Cookies.get("token")}`,
            },
            body: JSON.stringify(transactionData),
        });

        if (response.ok) {
            const result = await response.json();
            console.log(result);
            return result;
        } else {
            const error = await response.json();
            console.error("Error creating transaction:", error);
            alert(error.message || "Failed to create transaction");
        }
    } catch (error) {
        console.error(error);
    }
};
