import React, { useState, useEffect } from "react";
import { updateGroupDetails } from "../data/groups/patchData";

const GroupDetailsEditModal = ({ isOpen, onClose, groupId, initialGroupDetails, onGroupDetailsUpdated }) => {
    const [newGroupName, setNewGroupName] = useState("");
    const [newGoal, setNewGoal] = useState("");
    const [newCurrency, setNewCurrency] = useState("");

    useEffect(() => {
        if (isOpen && initialGroupDetails) {
            setNewGroupName(initialGroupDetails.groupName || "");
            setNewGoal(initialGroupDetails.goal || "");
            setNewCurrency(initialGroupDetails.currency || "");
        }
    }, [isOpen, initialGroupDetails]);

    if (!isOpen) {
        return null;
    }

    const handleGroupDetailsChange = async () => {
        try {
            const updatedDetails = {
                newGroupName,
                newGoal,
                newCurrency,
            };

            const response = await updateGroupDetails(groupId, updatedDetails);
            alert("Group details updated successfully!");
            onGroupDetailsUpdated(response);
            onClose();
        } catch (error) {
            console.error("Error updating group details:", error);
            alert("Failed to update group details. Please try again.");
        }
    };

    return (
        <div className="modal-overlay" onClick={onClose}>
            <div className="modal-content" onClick={(e) => e.stopPropagation()}>
                <h3>Edit Group Details</h3>
                <div className="form-container">
                    <input
                        type="text"
                        placeholder="Group Name"
                        value={newGroupName}
                        onChange={(e) => setNewGroupName(e.target.value)}
                    />
                    <input
                        type="text"
                        placeholder="Goal"
                        value={newGoal}
                        onChange={(e) => setNewGoal(e.target.value)}
                    />
                    <input
                        type="text"
                        placeholder="Currency"
                        value={newCurrency}
                        onChange={(e) => setNewCurrency(e.target.value)}
                    />
                </div>
                <div className="modal-buttons">
                    <button onClick={handleGroupDetailsChange}>Save Changes</button>
                    <button onClick={onClose}>Cancel</button>
                </div>
            </div>
        </div>
    );
};

export default GroupDetailsEditModal;
