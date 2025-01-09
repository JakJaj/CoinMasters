import Cookies from 'js-cookie';
import URL_API from "../const";

const url = URL_API;

export const getGroupTransactions = async (groupId) => {
    try {
        const response = await fetch(`${url}/transactions/groups/${groupId}`, {
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
        return result.transactions;
    } catch (error) {
        console.error("Błąd przy pobieraniu transakcji:", error);
        throw error;
    }
};

export const getGroupTransactionsDetails = async (transactionId) => {
    try {
        const response = await fetch(`${url}/transactions/${transactionId}`, {
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
        console.error("Błąd przy pobieraniu szczegółów transakcji:", error);
        throw error;
    }
};