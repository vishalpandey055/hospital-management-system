import { Link } from "react-router-dom";
import { useContext } from "react";
import { AuthContext } from "../context/AuthContext";

function NotFound() {

  const { token } = useContext(AuthContext);

  const redirectPath = token ? "/dashboard" : "/login";
  const buttonText = token ? "Go to Dashboard" : "Go to Login";

  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-gray-100 dark:bg-slate-900">

      <h1 className="text-7xl font-bold text-blue-600 mb-4">
        404
      </h1>

      <p className="text-lg mb-6 text-gray-600 dark:text-gray-300">
        Oops! The page you are looking for does not exist.
      </p>

      <Link
        to={redirectPath}
        className="bg-blue-600 text-white px-6 py-2 rounded hover:bg-blue-700 transition"
      >
        {buttonText}
      </Link>

    </div>
  );
}

export default NotFound;