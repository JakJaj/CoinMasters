import Cookies from 'js-cookie';
import URL_API from "../const";

const url = URL_API;

export const deleteTransactionData = async (transactionId) => {
    try {
        const response = await fetch(`${url}/transactions/${transactionId}`, {
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
        return result.transactions;
    } catch (error) {
        console.error("Błąd przy usuwaniu transakcji:", error);
        throw error;
    }
}