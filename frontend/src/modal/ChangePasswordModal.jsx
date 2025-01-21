import React, { useState, useEffect } from "react";
import { changePassword } from "../data/users/putData";

const PasswordChangeModal = ({ isOpen, onClose, onPasswordChanged }) => {
    const [oldPassword, setOldPassword] = useState("");
    const [newPassword, setNewPassword] = useState("");

    useEffect(() => {
        if (!isOpen) {
            setOldPassword("");
            setNewPassword("");
        }
    }, [isOpen]);

    if (!isOpen) {
        return null;
    }

    const handlePasswordChange = async () => {
        if (!oldPassword || !newPassword) {
            alert("Please fill in all fields");
            return;
        }

        if (oldPassword === newPassword) {
            alert("New password cannot be the same as the old password");
            return;
        }

        try {
            const response = await changePassword(oldPassword, newPassword);
            if (response.success) {
                alert("Password changed successfully!");
                onClose();
                onPasswordChanged();
            } else {
                alert(response.error || "An error occurred while changing the password");
            }
        } catch (error) {
            console.error("Error changing password:", error);
            alert("An error occurred. Please try again later.");
        }
    };

    return (
        <div className="modal-overlay" onClick={() => onClose()}>
            <div className="modal-content" onClick={(e) => e.stopPropagation()}>
                <div className="form-container">
                    <input
                        type="password"
                        placeholder="Old Password"
                        value={oldPassword}
                        onChange={(e) => setOldPassword(e.target.value)}
                    />
                    <input
                        type="password"
                        placeholder="New Password"
                        value={newPassword}
                        onChange={(e) => setNewPassword(e.target.value)}
                    />
                </div>
                <div className="modal-buttons">
                    <button onClick={handlePasswordChange}>Change Password</button>
                    <button onClick={() => onClose()}>Close</button>
                </div>
            </div>
        </div>
    );
};

export default PasswordChangeModal;