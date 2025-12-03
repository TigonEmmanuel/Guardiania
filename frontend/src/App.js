import React, { useEffect, useState, useContext } from "react";
import axios from "axios";
import { AuthContext } from "./context/AuthContext"; // Assuming you have AuthContext

export default function DashboardLogs() {
  const [logs, setLogs] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const { user, token } = useContext(AuthContext); // Get token from AuthContext

  useEffect(() => {
    if (!token) {
      setError("You must be logged in to view logs");
      setLoading(false);
      return;
    }

    const fetchLogs = async () => {
      try {
        const res = await axios.get("http://localhost:8080/api/v1/logs", {
          headers: {
            Authorization: `Bearer ${token}`, // send JWT to backend
          },
        });
        setLogs(res.data); // assuming backend returns an array of logs
      } catch (err) {
        console.error(err);
        setError("Failed to fetch logs");
      } finally {
        setLoading(false);
      }
    };

    fetchLogs();
  }, [token]);

  return (
    <div style={{ padding: 24, fontFamily: "Arial, sans-serif" }}>
      <h1>Guardian Admin Dashboard</h1>
      <p>Audit logs will appear here.</p>

      {loading && <p>Loading logs...</p>}
      {error && <p style={{ color: "red" }}>{error}</p>}

      {!loading && !error && (
        <table
          border="1"
          cellPadding="8"
          style={{ borderCollapse: "collapse", width: "100%" }}
        >
          <thead>
            <tr>
              <th>ID</th>
              <th>Original</th>
              <th>Sanitized</th>
              <th>PII</th>
              <th>NSFW</th>
              <th>Timestamp</th>
            </tr>
          </thead>
          <tbody>
            {logs.length === 0 ? (
              <tr>
                <td colSpan="6">No logs available</td>
              </tr>
            ) : (
              logs.map((l) => (
                <tr key={l.id}>
                  <td>{l.id}</td>
                  <td>{l.original}</td>
                  <td>{l.sanitized}</td>
                  <td>{l.piiDetected ? "Y" : "N"}</td>
                  <td>{l.nsfwDetected ? "Y" : "N"}</td>
                  <td>{l.timestamp}</td>
                </tr>
              ))
            )}
          </tbody>
        </table>
      )}
    </div>
  );
}
