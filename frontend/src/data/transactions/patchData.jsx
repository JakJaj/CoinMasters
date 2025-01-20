import Cookies from "js-cookie";
import URL_API from "../const";

const url = URL_API;

export const updateTransactionDetails = async (transactionId, transactionDetails) => {
    try {
        const response = await fetch(`${url}/transactions/${transactionId}`, {
            method: "PATCH",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${Cookies.get("token")}`,
            },
            body: JSON.stringify(transactionDetails),
        });

        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(`HTTP error ${response.status}: ${errorText}`);
        }

        const result = await response.json();
        return result;
    } catch (error) {
        console.error("Błąd przy aktualizacji szczegółów transakcji:", error);
        throw error;
    }
};
