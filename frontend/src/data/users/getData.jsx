import URL_API from "../const";

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