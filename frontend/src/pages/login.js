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
    <div className="login-page">
      <div className="login-box">
        <header className="login-header">
          <div className="logo" aria-hidden>
            G
          </div>
          <h2>Sign in</h2>
        </header>

        <form onSubmit={handleSubmit} aria-label="Login form">
          <label htmlFor="username" className="sr-only">
            Username
          </label>
          <input
            id="username"
            type="text"
            name="username"
            placeholder="Username"
            onChange={handleChange}
            required
            autoComplete="username"
            aria-required="true"
          />

          <label htmlFor="password" className="sr-only">
            Password
          </label>
          <input
            id="password"
            type="password"
            name="password"
            placeholder="Password"
            onChange={handleChange}
            required
            autoComplete="current-password"
            aria-required="true"
          />

          <button type="submit">Login</button>
        </form>

        {error && (
          <p className="error" role="alert">
            {error}
          </p>
        )}

        <div className="login-footer">
          <a href="#" onClick={(e) => e.preventDefault()}>
            Forgot password?
          </a>
        </div>
      </div>
    </div>
  );
};

export default Login;
