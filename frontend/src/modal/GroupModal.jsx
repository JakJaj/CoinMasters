import React from "react";
import "./GroupModal.css";

const GroupModal = ({ isOpen, onClose }) => {
    const [input1, setInput1] = React.useState("");
    const [input2, setInput2] = React.useState("");
    const [activeTab, setActiveTab] = React.useState("createGroup");

    if (!isOpen) {
        return null;
    }

    const handleTabChange = (tab) => {
        setActiveTab(tab);
    };

    return (
        <div className="modal-overlay" onClick={() => {
            setInput1("");
            setInput2("");
            setActiveTab("createGroup");
            onClose();
        }
        }>
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
                            value={input1}
                            onChange={(e) => setInput1(e.target.value)}
                        />
                        <input
                            type="text"
                            placeholder="Cel grupy"
                            value={input2}
                            onChange={(e) => setInput2(e.target.value)}
                        />
                        <select id="wybierz walute">
                            <option value="" disabled selected>-- Wybierz walutę --</option>
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
                            value={input1}
                            onChange={(e) => setInput1(e.target.value)}
                        />
                    </div>
                )}

                <div className="modal-buttons">
                    <button onClick={() => alert("Kliknięto Save!")}>Save</button>
                    <button onClick={() => {
                        setInput1("");
                        setInput2("");
                        setActiveTab("createGroup");
                        onClose();
                    }}>Close</button>
                </div>
            </div>
        </div>
    );
};

export default GroupModal;
