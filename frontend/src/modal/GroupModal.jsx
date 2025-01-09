import React from "react";
import "./GroupModal.css";
import { createGroup } from "../data/groups/postData";

const GroupModal = ({ isOpen, onClose }) => {
    const [groupName, setGroupName] = React.useState("");
    const [goal, setGoal] = React.useState("");
    const [currency, setCurrency] = React.useState("");
    const [joinCode, setJoinCode] = React.useState("");
    const [activeTab, setActiveTab] = React.useState("createGroup");

    if (!isOpen) {
        return null;
    }

    const handleTabChange = (tab) => {
        setActiveTab(tab);
    };

    const handleSave = async () => {
        if (activeTab === "createGroup") {
            if (!groupName || !goal || !currency) {
                alert("Please fill in all fields");
                return;
            }

            const groupData = {
                groupName: groupName,
                goal: goal,
                currency: currency,
            };

            const response = await createGroup(groupData);
            if (response) {
                alert(`Group created successfully! Join code: ${response.joinCode}`);
                onClose();
            }
        } else if (activeTab === "joinGroup") {
            if (!joinCode) {
                alert("Please enter a join code");
                return;
            }

            // TODO: Handle join group functionality
            alert(`Joining group with code: ${joinCode}`);
        }
    };

    return (
        <div className="modal-overlay" onClick={() => onClose()}>
            <div className="modal-content" onClick={(event) => event.stopPropagation()}>

                <div className="tabs">
                    <button
                        className={activeTab === "createGroup" ? "active" : ""}
                        onClick={() => handleTabChange("createGroup")}
                    >
                        Stwórz grupę
                    </button>

                    <button
                        className={activeTab === "joinGroup" ? "active" : ""}
                        onClick={() => handleTabChange("joinGroup")}
                    >
                        Dołącz do grupy
                    </button>
                </div>

                {activeTab === "createGroup" ? (
                    <div className="form-container">
                        <input
                            type="text"
                            placeholder="Nazwa grupy"
                            value={groupName}
                            onChange={(e) => setGroupName(e.target.value)}
                        />
                        <input
                            type="text"
                            placeholder="Cel grupy"
                            value={goal}
                            onChange={(e) => setGoal(e.target.value)}
                        />
                        <select
                            value={currency}
                            onChange={(e) => setCurrency(e.target.value)}
                        >
                            <option value="" disabled>-- Wybierz walutę --</option>
                            <option value="PLN">PLN</option>
                            <option value="EUR">EUR</option>
                            <option value="USD">USD</option>
                            <option value="GBP">GBP</option>
                            <option value="JPY">JPY</option>
                        </select>
                    </div>
                ) : (
                    <div className="form-container">
                        <input
                            type="text"
                            placeholder="Kod grupy"
                            value={joinCode}
                            onChange={(e) => setJoinCode(e.target.value)}
                        />
                    </div>
                )}

                <div className="modal-buttons">
                    <button onClick={handleSave}>Save</button>
                    <button onClick={() => onClose()}>Close</button>
                </div>
            </div>
        </div>
    );
};

export default GroupModal;
