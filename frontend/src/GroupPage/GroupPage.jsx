import React from "react";
import "./GroupPage.css";
import { Link } from "react-router-dom";


const GroupPage = () => {
    return (
        <div className="group-wrapper">
            <div className="group">
                <div className="group-id">178951</div>
                <div className="group-name">Rachuneczki</div>
                <Link to="/dashboard">
                    <button className="group-btn">SELECT</button>
                </Link>
            </div>
        </div>
    );
}

export default GroupPage;