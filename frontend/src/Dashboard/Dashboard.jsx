import React, { useEffect, useState } from "react";
import "./Dashboard.css";
import { Link, useLocation, Navigate } from "react-router-dom";
import { fetchGroupUsers } from "../data/groups/getData";
import { getGroupTransactions, getGroupTransactionsDetails } from "../data/transactions/getData";
import TransactionModal from "../modal/TransactionModal";
import { deleteTransactionData } from "../data/transactions/deleteData";
import EditTransactionModal from "../modal/EditTransactionModal";
import { deleteGroupData } from "../data/groups/deleteData";
import { deleteSelfFromGroup } from "../data/users/deleteData";
import { getUserDetails } from "../data/users/getData";
import { changePassword } from "../data/users/putData";
import PasswordChangeModal from "../modal/ChangePasswordModal";
import GroupDetailsEditModal from "../modal/GroudDetailsEditModal";

const Dashboard = () => {
    const location = useLocation();
    const { group } = location.state || {};
    const [groupUsers, setGroupUsers] = useState([]);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [transactions, setTransactions] = useState([]);
    const [depositSum, setDepositSum] = useState(0);
    const [withdrawalSum, setWithdrawalSum] = useState(0);
    const [balance, setBalance] = useState(0);
    const [isEditModalOpen, setIsEditModalOpen] = useState(false);
    const [transactionToEdit, setTransactionToEdit] = useState(null);
    const [selectedAction, setSelectedAction] = useState("");
    const [userName, setUserName] = useState("");
    const [isPasswordModalOpen, setIsPasswordModalOpen] = useState(false);
    const [isGroupDetailsModalOpen, setIsGroupDetailsModalOpen] = useState(false);
    const [updatedGroupDetails, setUpdatedGroupDetails] = useState({});


    if (!group) {
        return <Navigate to="/grouppage" replace />;
    }

    const { groupName, currency, goal, groupId, joinCode } = group;

    useEffect(() => {
        if (group) {
            const getUsers = async () => {
                try {
                    const users = await fetchGroupUsers(group.groupId);
                    console.log(group.groupId)
                    setGroupUsers(users);
                } catch (error) {
                    console.error("Błąd przy pobieraniu członków grupy:", error);
                }
            };
            getUsers();
        }
    }, [group]);

    useEffect(() => {
        console.log(userName)
        const fetchUser = async () => {
            try {
                const userDetails = await getUserDetails();
                setUserName(userDetails.name);
            } catch (error) {
                console.error("Błąd przy pobieraniu danych użytkownika:", error);
            }
        };

        fetchUser();
    }, []);

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

    const handleEdit = (transaction) => {
        setTransactionToEdit(transaction);
        setIsEditModalOpen(true);
    };

    const closeEditModal = () => {
        setTransactionToEdit(null);
        setIsEditModalOpen(false);
    };

    const handleGroupDelete = async () => {
        try {
            const result = await deleteGroupData(groupId);
            if (result && result.status === 'success') {
                alert(result.message || "Grupa została usunięta.");
                window.location.href = "/grouppage";
            } else {
                alert(result.message || "Nie udało się usunąć grupy.");
            }
        } catch (error) {
            console.error("Błąd przy usuwaniu grupy:", error);
            alert("Wystąpił błąd podczas usuwania grupy.");
        }
    };

    const handleActionChange = (e) => {
        const action = e.target.value;
        setSelectedAction(action);

        if (action === "leave") {
            if (window.confirm("Czy na pewno chcesz opuścić grupę?")) {
                handleLeaveGroup();
            }
        } else if (action === "delete") {
            if (window.confirm("Czy na pewno chcesz usunąć grupę?")) {
                handleGroupDelete();
            }
        } else if (action === "edit") {
            setIsGroupDetailsModalOpen(true);
        } else if (action === "password") {
            setIsPasswordModalOpen(true);
        }
    };


    const handleLeaveGroup = async () => {
        try {
            const result = await deleteSelfFromGroup(groupId);
            if (result && result.status === 'success') {
                alert(result.message || "Pomyślnie opuściłeś grupę.");
                window.location.href = "/grouppage";
            } else {
                alert(result.message || "Nie udało się opuścić grupy.");
            }
        } catch (error) {
            console.error("Błąd przy opuszczaniu grupy:", error);
            alert("Wystąpił błąd podczas opuszczania grupy.");
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
                <div className="username">USERNAME: {userName}</div>

                <select
                    className="group-actions-dropdown"
                    value={selectedAction}
                    onChange={handleActionChange}
                >
                    <option value="" disabled hidden>Opcje grupy</option>
                    <option value="leave">Opuść grupę</option>
                    <option value="delete">Usuń grupę</option>
                    <option value="edit">Zmień dane</option>
                    <option value="password">Zmień hasło</option>
                </select>

                {isPasswordModalOpen && (
                    <PasswordChangeModal
                        isOpen={isPasswordModalOpen}
                        onClose={() => setIsPasswordModalOpen(false)}
                        onPasswordChanged={() => {
                            setIsPasswordModalOpen(false);
                            alert("Password successfully updated.");
                        }}
                    />
                )}

                {isGroupDetailsModalOpen && (
                    <GroupDetailsEditModal
                        isOpen={isGroupDetailsModalOpen}
                        onClose={() => setIsGroupDetailsModalOpen(false)}
                        groupId={groupId}
                        initialGroupDetails={updatedGroupDetails}
                        onGroupDetailsUpdated={(newDetails) => {
                            setUpdatedGroupDetails(newDetails);
                            alert("Group details have been updated.");
                        }}
                    />
                )}


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
                                <div className="transaction-actions">
                                    <button className="edit-button" onClick={() => handleEdit(transaction)}>
                                        ✎
                                    </button>
                                    <button className="delete-button" onClick={() => handleDelete(transaction.transactionId)}>
                                        X
                                    </button>
                                </div>

                            </div>
                        ))}
                    </div>
                </div>
            </div>

            <div className="sidebar">
                <div className="savings-goal">
                    <div className="goal-title">Cel oszczędzania: <br /> {goal} </div>
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
                    <span>Kod dostępu: {joinCode} </span>
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

            {isEditModalOpen && (
                <EditTransactionModal
                    isOpen={isEditModalOpen}
                    onClose={closeEditModal}
                    transactionToEdit={transactionToEdit}
                    onTransactionUpdated={fetchTransactions}
                />
            )}

        </div>
    );
};

export default Dashboard;