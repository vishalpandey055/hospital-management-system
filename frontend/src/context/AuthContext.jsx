import { createContext, useState } from "react";
import { jwtDecode } from "jwt-decode";

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {

  const getInitialAuth = () => {

    const token = localStorage.getItem("token");

    if (!token) {
      return {
        token: null,
        role: null,
        isAuthenticated: false,
      };
    }

    try {
      const decoded = jwtDecode(token);

      return {
        token,
        role: decoded.role || null,
        isAuthenticated: true,
      };

    } catch (error) {

      console.error("JWT decode failed", error);

      localStorage.removeItem("token");

      return {
        token: null,
        role: null,
        isAuthenticated: false,
      };
    }
  };

  const [auth, setAuth] = useState(getInitialAuth());

  const login = (jwt) => {

    localStorage.setItem("token", jwt);

    try {
      const decoded = jwtDecode(jwt);

      setAuth({
        token: jwt,
        role: decoded.role || null,
        isAuthenticated: true,
      });

    } catch (error) {

      console.error("JWT decode failed", error);

      setAuth({
        token: jwt,
        role: null,
        isAuthenticated: true,
      });
    }
  };

  const logout = () => {

    localStorage.removeItem("token");

    setAuth({
      token: null,
      role: null,
      isAuthenticated: false,
    });
  };

  return (
    <AuthContext.Provider
      value={{
        ...auth,
        login,
        logout,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};