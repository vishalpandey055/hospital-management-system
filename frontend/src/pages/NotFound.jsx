import { Link } from "react-router-dom";

function NotFound() {
  const token = localStorage.getItem("token");

  const redirect = token ? "/dashboard" : "/login";

  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-gray-100 dark:bg-slate-900">
      <h1 className="text-7xl font-bold text-blue-600 mb-4">404</h1>

      <p className="text-lg mb-6 text-gray-600 dark:text-gray-300">
        Oops! The page you are looking for does not exist.
      </p>

      <Link
        to={redirect}
        className="bg-blue-600 text-white px-6 py-2 rounded hover:bg-blue-700 transition"
      >
        {token ? "Go to Dashboard" : "Go to Login"}
      </Link>
    </div>
  );
}

export default NotFound;
