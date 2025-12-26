import React, { useState, useEffect } from "react";
import API from "../services/api";

const Workflow = () => {
  const [workflow, setWorkflow] = useState(null);

  useEffect(() => {
    const fetchWorkflow = async () => {
      try {
        const res = await API.get("/workflow"); // your backend endpoint
        setWorkflow(res.data);
      } catch (err) {
        console.error("Error fetching workflow:", err);
      }
    };
    fetchWorkflow();
  }, []);

  return (
    <div className="workflow-page">
      <h2>AI Guardrail Workflow</h2>
      {workflow ? (
        <pre>{JSON.stringify(workflow, null, 2)}</pre>
      ) : (
        <p>Loading...</p>
      )}
    </div>
  );
};

export default Workflow;
