import React, { useEffect, useState } from "react";
import "./Dashboard.css";
import { Link, useLocation, Navigate } from "react-router-dom";
import { fetchGroupUsers } from "../data/groups/getData";
import { getGroupTransactions, getGroupTransactionsDetails } from "../data/transactions/getData";
import TransactionModal from "../modal/TransactionModal";

const Dashboard = () => {
    const location = useLocation();
    const { group } = location.state || {};
    const [groupUsers, setGroupUsers] = useState([]);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [transactions, setTransactions] = useState([]);

    if (!group) {
        return <Navigate to="/grouppage" replace />;
    }

    const { groupName, currency, goal, groupId } = group;

    useEffect(() => {
        if (group) {
            const getUsers = async () => {
                try {
                    const users = await fetchGroupUsers(group.groupId);
                    setGroupUsers(users);
                    console.log("Fetched group users:", users);
                } catch (error) {
                    console.error("Błąd przy pobieraniu członków grupy:", error);
                }
            };
            getUsers();
        }
    }, [group]);

    const fetchTransactions = async () => {
        try {
            console.log("Fetching transactions for groupId:", groupId);

            const transactionList = await getGroupTransactions(groupId);
            console.log("Fetched transaction list:", transactionList);

            const transactionDetails = await Promise.all(
                transactionList.map((transaction) =>
                    getGroupTransactionsDetails(transaction.transactionId)
                )
            );
            console.log("Fetched transaction details:", transactionDetails);

            setTransactions(transactionDetails);
        } catch (error) {
            console.error("Błąd przy pobieraniu transakcji:", error);
        }
    };

    const handleDelete = (transactionId) => {
        console.log(`Usuwanie transakcji o ID: ${transactionId}`);
        //TODO: Implement delete transaction
    };


    useEffect(() => {
        fetchTransactions();
    }, [groupId]);

    return (
        <div className="dashboard">
            <div className="header">
                <Link to="/grouppage">
                    <button className="back-btn">BACK</button>
                </Link>
                <div className="group-name">Nazwa grupy: {groupName}</div>
                <div className="currency">Waluta: {currency}</div>
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
                    <button
                        className="add-transaction-btn"
                        onClick={() => setIsModalOpen(true)}
                    >
                        Dodaj nową transakcję
                    </button>

                    <div className="transaction-list">
                        {transactions.map((transaction) => (
                            <div key={transaction.transactionId} className="transaction-item">
                                <div className="transaction-details">
                                    <span>{transaction.name}</span>
                                    <span>{transaction.category}</span>
                                    <span>{transaction.date}</span>
                                    <span>{transaction.amount} PLN</span>
                                    <span>Utworzył: {transaction.creatorName}</span>
                                </div>
                                <button
                                    className="delete-button"
                                    onClick={() => handleDelete(transaction.transactionId)}
                                >
                                    X
                                </button>
                            </div>
                        ))}
                    </div>


                </div>
            </div>

            <div className="sidebar">
                <div className="savings-goal">
                    <div className="goal-title">Cel oszczędzania: {goal} <br /> (pozostało)</div>
                    <div className="goal-value">11 918,06 PLN</div>
                </div>

                <div className="members-list">
                    <span>Członkowie grupy:</span>
                    <ul>
                        {groupUsers.map((user) => (
                            <li key={user.userId}>
                                {user.name}
                            </li>
                        ))}
                    </ul>
                </div>

                <div className="access-code">
                    <span>Kod dostępu</span>
                </div>

                <div className="calendar">
                    <span>kalendarzyk</span>
                </div>
            </div>

            {isModalOpen && (
                <TransactionModal
                    isOpen={isModalOpen}
                    onClose={() => setIsModalOpen(false)}
                    groupId={groupId}
                    onTransactionCreated={fetchTransactions}
                />
            )}
        </div>
    );
};

export default Dashboard;
