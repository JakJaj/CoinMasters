import React from "react";
import "./Dashboard.css";
import { Link } from "react-router-dom";

const Dashboard = () => {
    return (
        <div className="dashboard">
            <div className="header">
                <Link to="/grouppage">
                    <button className="back-btn">BACK</button>
                </Link>
                <div className="group-name">NAZWA GRUPY</div>
                <div className="currency">WALUTA</div>
                <div className="username">USERNAME</div>
                <Link to="/login">
                    <button className="logout-btn">LOGOUT</button>
                </Link>
            </div>

            <div className="main-content">
                <div className="card">
                    <div className="card-title">Wpłaty SUM</div>
                    <div className="card-value">123,58 PLN</div>
                    <div className="chart-placeholder">wykres</div>
                </div>

                <div className="card">
                    <div className="card-title">Zaoszczędzone / stracone</div>
                    <div className="card-value negative">-90,81 PLN</div>
                </div>

                <div className="card">
                    <div className="card-title">Wypłaty SUM</div>
                    <div className="card-value">123,58 PLN</div>
                    <div className="chart-placeholder">wykres</div>
                </div>

                <div className="transactions">
                    <button className="add-transaction-btn">Dodaj nowa</button>
                    <div className="transaction-list">
                        <div className="transaction-item">
                            <span>Transakcja 1</span>
                            <div className="transaction-actions">
                                <button className="edit-btn">edit</button>
                                <button className="delete-btn">usun</button>
                            </div>
                        </div>
                        <div className="transaction-item">
                            <span>Transakcja 2</span>
                            <div className="transaction-actions">
                                <button className="edit-btn">edit</button>
                                <button className="delete-btn">usun</button>
                            </div>
                        </div>
                        <div className="transaction-item">
                            <span>Transakcja 3</span>
                            <div className="transaction-actions">
                                <button className="edit-btn">edit</button>
                                <button className="delete-btn">usun</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div className="sidebar">
                <div className="savings-goal">
                    <div className="goal-title">Cel oszczędzania (pozostało)</div>
                    <div className="goal-value">11 918,06 PLN</div>
                </div>
                <div className="access-code">
                    <span>Kod dostępu</span>
                </div>
                <div className="calendar">
                    <span>kalendarzyk</span>
                </div>
                <div className="notes">
                    <span>a ________</span>
                    <span>b ________</span>
                    <span>c ________</span>
                    <span>d ________</span>
                </div>
            </div>
        </div>
    );
};

export default Dashboard;
