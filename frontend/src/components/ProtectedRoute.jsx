import { useContext } from "react";
import { Navigate, Outlet } from "react-router-dom";
import { AuthContext } from "../context/AuthContext";

function ProtectedRoute({ allowedRoles }) {

  const { token, role } = useContext(AuthContext);

  // Auth still loading
  if (token && role === null) {
    return (
      <div className="flex items-center justify-center h-screen">
        <p className="text-gray-500">Loading...</p>
      </div>
    );
  }

  // Not logged in
  if (!token) {
    return <Navigate to="/login" replace />;
  }

  // Role not allowed
  if (allowedRoles && !allowedRoles.includes(role)) {
    return <Navigate to="/dashboard" replace />;
  }

  return <Outlet />;
}

export default ProtectedRoute;