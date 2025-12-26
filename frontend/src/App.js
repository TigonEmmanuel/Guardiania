import React from "react";
import {
  BrowserRouter as Router,
  Routes,
  Route,
  Navigate,
} from "react-router-dom";
import { AuthProvider, AuthContext } from "./context/AuthContext";
import Login from "./pages/login";
import Dashboard from "./pages/dashboard";
import Workflow from "./pages/workflow";

function PrivateRoute({ children }) {
  const { token } = React.useContext(AuthContext);
  return token ? children : <Navigate to="/login" />;
}

const App = () => {
  return (
    <AuthProvider>
      <Router>
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route
            path="/dashboard"
            element={
              <PrivateRoute>
                <Dashboard />
              </PrivateRoute>
            }
          />
          <Route
            path="/workflow"
            element={
              <PrivateRoute>
                <Workflow />
              </PrivateRoute>
            }
          />
          <Route path="*" element={<Navigate to="/dashboard" />} />
        </Routes>
      </Router>
    </AuthProvider>
  );
};

export default App;
