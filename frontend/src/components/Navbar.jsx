import { useContext } from "react";
import { ThemeContext } from "../context/ThemeContext";
import { AuthContext } from "../context/AuthContext";

function Navbar() {

  const { theme, toggleTheme } = useContext(ThemeContext);
  const { logout, role } = useContext(AuthContext);

  return (
    <header className="flex items-center justify-between px-4 md:px-6 py-3 bg-white dark:bg-slate-800 dark:text-white shadow">

      {/* Page Title */}
      <h1 className="text-lg md:text-xl font-semibold">
        Dashboard
      </h1>

      {/* Right Controls */}
      <div className="flex items-center gap-3 md:gap-4">

        {/* Role Badge */}
        <span className="text-xs md:text-sm font-semibold px-2 py-1 rounded bg-blue-100 text-blue-700 dark:bg-blue-900 dark:text-blue-300">
          {role || "USER"}
        </span>

        {/* Theme Toggle */}
        <button
          onClick={toggleTheme}
          className="px-3 py-1 rounded bg-gray-200 dark:bg-gray-700 hover:bg-gray-300 dark:hover:bg-gray-600 transition"
        >
          {theme === "dark" ? "☀️ Light" : "🌙 Dark"}
        </button>

        {/* Logout */}
        <button
          onClick={logout}
          className="px-3 py-1 rounded bg-red-500 text-white hover:bg-red-600 transition"
        >
          Logout
        </button>

      </div>

    </header>
  );
}

export default Navbar;