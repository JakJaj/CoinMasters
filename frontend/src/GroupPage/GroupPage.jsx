import React, { useState, useEffect } from "react";
import "./GroupPage.css";
import { Link } from "react-router-dom";
import { fetchGroups } from "../data/groups/getData";
import GroupModal from "../modal/GroupModal";

const GroupPage = () => {
    const [groups, setGroups] = useState([]);
    const [isModalOpen, setIsModalOpen] = useState(false);

    useEffect(() => {
        const getGroups = async () => {
            try {
                const response = await fetchGroups(); //TODO: Wrtie fetchGroups function
            }
            catch (error) {
                console.log(error);
            }
        };

        getGroups();
    }, []);

    return (
        <div className="group-wrapper">
            <div className="add-card" onClick={() => setIsModalOpen(true)}>
                +
            </div>
            <GroupModal isOpen={isModalOpen} onClose={() => setIsModalOpen(false)} />
        </div>
    );
};

export default GroupPage;