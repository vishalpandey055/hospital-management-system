import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App";

import { BrowserRouter } from "react-router-dom";

import { AuthProvider } from "./context/AuthContext";
import { ThemeProvider } from "./context/ThemeContext";

import ErrorBoundary from "./components/ErrorBoundary";
import { Toaster } from "react-hot-toast";

import "./index.css";

ReactDOM.createRoot(document.getElementById("root")).render(
  <React.StrictMode>

    <ErrorBoundary>

      <BrowserRouter>

        <AuthProvider>
          <ThemeProvider>

            <App />

            <Toaster position="top-right" />

          </ThemeProvider>
        </AuthProvider>

      </BrowserRouter>

    </ErrorBoundary>

  </React.StrictMode>
);