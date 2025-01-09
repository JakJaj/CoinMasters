import React, { useEffect, useState } from "react";
import "./Dashboard.css";
import { Link, useLocation, Navigate } from "react-router-dom";
import { fetchGroupUsers } from "../data/groups/getData";
import { getGroupTransactions, getGroupTransactionsDetails } from "../data/transactions/getData";
import TransactionModal from "../modal/TransactionModal";
import { deleteTransactionData } from "../data/transactions/deleteData";

const Dashboard = () => {
    const location = useLocation();
    const { group } = location.state || {};
    const [groupUsers, setGroupUsers] = useState([]);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [transactions, setTransactions] = useState([]);
    const [depositSum, setDepositSum] = useState(0);
    const [withdrawalSum, setWithdrawalSum] = useState(0);
    const [balance, setBalance] = useState(0);

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
                } catch (error) {
                    console.error("Błąd przy pobieraniu członków grupy:", error);
                }
            };
            getUsers();
        }
    }, [group]);

    const fetchTransactions = async () => {
        try {
            if (!groupId) return;

            const transactionList = await getGroupTransactions(groupId);
            if (!transactionList) return;

            const transactionDetails = await Promise.all(
                transactionList.map(async (transaction) => {
                    try {
                        const details = await getGroupTransactionsDetails(transaction.transactionId);
                        return { ...transaction, ...details };
                    } catch (detailError) {
                        console.error(`Błąd pobierania szczegółów transakcji ${transaction.transactionId}:`, detailError);
                        return transaction;
                    }
                })
            );

            setTransactions(transactionDetails);
        } catch (error) {
            console.error("Błąd przy pobieraniu transakcji:", error);
        }
    };

    const calculateSums = (transactions) => {
        let currentDepositSum = 0;
        let currentWithdrawalSum = 0;

        transactions.forEach((transaction) => {
            if (transaction && transaction.amount) {
                if (transaction.amount >= 0) {
                    currentDepositSum += transaction.amount;
                } else {
                    currentWithdrawalSum += transaction.amount;
                }
            }
        });

        setDepositSum(currentDepositSum);
        setWithdrawalSum(currentWithdrawalSum);
        setBalance(currentDepositSum + currentWithdrawalSum);
    };

    const handleDelete = async (transactionId) => {
        try {
            const result = await deleteTransactionData(transactionId);
            if (result && result.status === 'success') {
                alert(result.message);
                fetchTransactions();
            } else {
                alert("Nie udało się usunąć transakcji.");
            }
        } catch (error) {
            console.error('Błąd przy usuwaniu transakcji:', error);
        }
    };

    useEffect(() => {
        if (groupId) {
            fetchTransactions();
        }
    }, [groupId]);

    useEffect(() => {
        calculateSums(transactions);
    }, [transactions]);

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
                    <div className="card-value">{depositSum} {currency}</div>
                </div>

                <div className="card">
                    <div className="card-title">Zaoszczędzone / stracone</div>
                    <div className={`card-value ${balance >= 0 ? '' : 'negative'}`}>
                        {balance} {currency}
                    </div>
                </div>

                <div className="card">
                    <div className="card-title">Wypłaty SUM</div>
                    <div className="card-value negative">{withdrawalSum} {currency}</div>
                </div>

                <div className="transactions">
                    <button className="add-transaction-btn" onClick={() => setIsModalOpen(true)}>
                        Dodaj nową transakcję
                    </button>

                    <div className="transaction-list">
                        {transactions.map((transaction) => (
                            <div key={transaction.transactionId} className="transaction-item">
                                <div className="transaction-details">
                                    <span>{transaction.name}</span>
                                    <span>{transaction.category}</span>
                                    <span>{transaction.date}</span>
                                    <span>{transaction.amount} {currency}</span>
                                    <span>Utworzył: {transaction.creatorName}</span>
                                </div>
                                <button className="delete-button" onClick={() => handleDelete(transaction.transactionId)}>
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
                    <div className="goal-value">11 918,06 {currency}</div>
                </div>

                <div className="members-list">
                    <span>Członkowie grupy:</span>
                    <ul>
                        {groupUsers.map((user) => (
                            <li key={user.userId}>{user.name}</li>
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