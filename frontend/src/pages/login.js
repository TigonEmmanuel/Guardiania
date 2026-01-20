import React, { useState, useContext } from "react";
import { useNavigate } from "react-router-dom";
import API from "../services/api";
import { AuthContext } from "../context/AuthContext";

const Login = () => {
  const { login } = useContext(AuthContext);
  const navigate = useNavigate();
  const [credentials, setCredentials] = useState({
    username: "",
    password: "",
  });
  const [error, setError] = useState("");
  const [showPassword, setShowPassword] = useState(false);

  const handleChange = (e) =>
    setCredentials({ ...credentials, [e.target.name]: e.target.value });

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const res = await API.post("/auth/login", credentials);
      login(res.data.user, res.data.token);
      navigate("/dashboard");
    } catch (err) {
      setError(err.response?.data?.message || "Login failed");
    }
  };

  return (
    <div className="auth-container">
      <div className="split">
        <aside className="left-panel">
          <div className="art-card">
            <div className="art-badge">THE TEVX AI NETWORK</div>
            <h1 className="art-title">ADMINS DASHBOARD</h1>
            <h2 className="art-hero">HERE IS THE AUTOMATED NETWORK</h2>
            <p className="art-sub">
              10 Million+ mails been manage and control automatic.
              <br />
              We invite you to join the workflow.
            </p>
            <div className="art-signin">
              <span>Already have an account?</span>
              <a href="#" onClick={(e) => e.preventDefault()}>
                Sign in
              </a>
            </div>
          </div>
          <div className="floating-circles" aria-hidden />
        </aside>

        <main className="right-panel">
          <div className="form-card">
            <h2>Login</h2>

            <form onSubmit={handleSubmit} aria-label="Login form">
              <label htmlFor="username">Admins panel</label>
              <input
                id="username"
                type="text"
                name="username"
                placeholder="admins name"
                onChange={handleChange}
                required
                autoComplete="username"
                aria-required="true"
              />

              <label htmlFor="password">Enter password</label>
              <input
                id="password"
                type={showPassword ? "text" : "password"}
                name="password"
                placeholder="Enter password"
                onChange={handleChange}
                required
                autoComplete="current-password"
                aria-required="true"
              />

              <label className="show-password">
                <input
                  type="checkbox"
                  onChange={(e) => setShowPassword(e.target.checked)}
                />{" "}
                Show password
              </label>

              <button type="submit" className="primary-btn">
                Sign up →
              </button>

              <div className="or-divider">
                <span>or</span>
              </div>

              <button type="button" className="google-btn">
                Continue with Google
              </button>
            </form>

            {error && (
              <p className="error" role="alert">
                {error}
              </p>
            )}
          </div>
        </main>
      </div>
    </div>
  );
};

export default Login;
