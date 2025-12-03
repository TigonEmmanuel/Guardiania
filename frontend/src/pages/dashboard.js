import React, { useContext } from "react";
import { AuthContext } from "../context/AuthContext";
import { Link } from "react-router-dom";

const Dashboard = () => {
  const { user, logout } = useContext(AuthContext);

  return (
    <div className="dashboard">
      <h2>Welcome, {user?.username || "User"}!</h2>
      <button onClick={logout}>Logout</button>
      <div>
        <Link to="/workflow">View AI Workflow</Link>
      </div>
      <div>
        {/* Add student/admin specific widgets here */}
        <p>Dashboard content placeholder</p>
      </div>
    </div>
  );
};

export default Dashboard;
