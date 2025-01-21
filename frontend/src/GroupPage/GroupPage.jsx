import React, { useState, useEffect } from "react";
import "./GroupPage.css";
import { fetchGroups } from "../data/groups/getData";
import GroupModal from "../modal/GroupModal";
import { useNavigate } from 'react-router-dom';

const GroupPage = () => {
    const [groups, setGroups] = useState([]);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const navigate = useNavigate();

    const refreshGroups = async () => {
        try {
            const response = await fetchGroups();
            setGroups(response);
        } catch (error) {
            console.error("Błąd podczas odświeżania grup:", error);
        }
    };

    useEffect(() => {
        refreshGroups();
    }, []);

    return (
        <div className="group-wrapper">
            <div className="add-card" onClick={() => setIsModalOpen(true)}>
                +
            </div>

            {groups.map((group) => (
                <div key={group.groupId} className="group">
                    <h3>{group.groupName}</h3>
                    <p>{group.goal}</p>
                    <p><strong>Waluta:</strong> {group.currency}</p>
                    <button
                        onClick={() => navigate('/dashboard', {
                            state: { group }
                        })}
                        className="group-btn">Otwórz</button>
                </div>
            ))}

            <GroupModal
                isOpen={isModalOpen}
                onClose={() => setIsModalOpen(false)}
                onGroupCreated={refreshGroups}
            />
        </div>
    );
};

export default GroupPage;