import React, { useState } from "react";
import "./GroupPage.css";
import { Link } from "react-router-dom";

const GroupPage = () => {
    const [groups, setGroups] = useState([]);

    const addGroup = () => {
        const newId = Date.now().toString();
        setGroups([...groups, { id: newId, name: "Nowa Grupa" }]);
    };

    return (
        <div className="group-wrapper">
            {groups.map((group, index) => (
                <div className="group" key={index}>
                    <div className="group-id">{group.id}</div>
                    <div className="group-name">{group.name}</div>
                    <Link to="/dashboard">
                        <button className="group-btn">SELECT</button>
                    </Link>
                </div>
            ))}
            <div className="add-card" onClick={addGroup}>
                +
            </div>
        </div>
    );
};

export default GroupPage;