import React, { useState, useEffect } from "react";
import { updateTransactionDetails } from "../data/transactions/patchData";

const EditTransactionModal = ({
    isOpen,
    onClose,
    transactionToEdit,
    onTransactionUpdated
}) => {
    const [name, setName] = useState("");
    const [category, setCategory] = useState("");
    const [date, setDate] = useState("");
    const [amount, setAmount] = useState("");

    useEffect(() => {
        if (transactionToEdit) {
            setName(transactionToEdit.name || "");
            setCategory(transactionToEdit.category || "");
            setDate(transactionToEdit.date || "");
            setAmount(transactionToEdit.amount || "");
        }
    }, [transactionToEdit]);

    const handleSave = async () => {
        if (!name || !category || !date || !amount) {
            alert("Please fill in all fields");
            return;
        }

        const updatedTransaction = {
            newName: name,
            newCategory: category,
            newDate: date,
            newAmount: parseFloat(amount),
        };

        try {
            const response = await updateTransactionDetails(transactionToEdit.transactionId, updatedTransaction);
            if (response) {
                alert("Transaction updated successfully!");
                onTransactionUpdated();
                onClose();
            }
        } catch (error) {
            console.error("Error updating transaction:", error);
        }
    };

    if (!isOpen) {
        return null;
    }

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
                    <input
                        type="date"
                        value={date}
                        onChange={(e) => setDate(e.target.value)}
                    />
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

export default EditTransactionModal;