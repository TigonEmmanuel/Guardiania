import React, { useContext } from "react";
import { AuthContext } from "../context/AuthContext";
import { Link, useNavigate } from "react-router-dom";
import DashboardLogs from "./dashboard";

const Dashboard = () => {
  const { user, logout } = useContext(AuthContext);
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate("/login");
  };

  return (
    <div className="dashboard" style={{ padding: 24 }}>
      <h1>Welcome, {user?.username || "User"}!</h1>
      <button onClick={handleLogout}>Logout</button>
      <div style={{ margin: "16px 0" }}>
        <Link to="/workflow">View AI Workflow</Link>
      </div>
      <DashboardLogs />
    </div>
  );
};

export default Dashboard;
