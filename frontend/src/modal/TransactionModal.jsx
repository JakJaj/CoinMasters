import React, { useState, useEffect } from "react";
import { createTransaction } from "../data/transactions/postData";

const TransactionModal = ({ isOpen, onClose, onTransactionCreated, groupId }) => {
    const [name, setName] = useState("");
    const [category, setCategory] = useState("");
    const [date, setDate] = useState("");
    const [amount, setAmount] = useState("");

    useEffect(() => {
        const resetForm = () => {
            setName("");
            setCategory("");
            setDate("");
            setAmount("");
        };

        if (!isOpen) resetForm();

        return resetForm;
    }, [isOpen]);

    if (!isOpen) {
        return null;
    }

    const handleSave = async () => {
        if (!name || !category || !date || !amount) {
            alert("Please fill in all fields");
            return;
        }

        const transactionData = {
            name,
            category,
            date,
            amount: parseFloat(amount),
            groupId,
        };

        console.log("Transaction to be sent:", transactionData);

        try {
            const response = await createTransaction(transactionData);
            if (response) {
                console.log("Transaction successfully created:", response);
                alert("Transaction added successfully!");
                onClose();
                onTransactionCreated();
            }
        } catch (error) {
            console.error("Error creating transaction:", error);
        }
    };

    return (
        <div className="modal-overlay" onClick={() => onClose()}>
            <div className="modal-content" onClick={(event) => event.stopPropagation()}>
                <div className="form-container">
                    <input
                        type="text"
                        placeholder="Nazwa transakcji"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                    />
                    <input
                        type="text"
                        placeholder="Kategoria"
                        value={category}
                        onChange={(e) => setCategory(e.target.value)}
                    />
                    <input type="date" value={date} onChange={(e) => setDate(e.target.value)} />
                    <input
                        type="number"
                        placeholder="Kwota"
                        value={amount}
                        onChange={(e) => setAmount(e.target.value)}
                    />
                </div>
                <div className="modal-buttons">
                    <button onClick={handleSave}>Zapisz</button>
                    <button onClick={() => onClose()}>Zamknij</button>
                </div>
            </div>
        </div>
    );
};

export default TransactionModal;
