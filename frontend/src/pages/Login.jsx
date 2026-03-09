import { useState, useContext } from "react";
import api from "../api/axios";
import { AuthContext } from "../context/AuthContext";
import { useNavigate, Link } from "react-router-dom";
import toast from "react-hot-toast";

function Login() {

  const [form, setForm] = useState({
    username: "",
    password: ""
  });

  const [loading, setLoading] = useState(false);

  const { login } = useContext(AuthContext);
  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;

    setForm((prev) => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!form.username.trim() || !form.password.trim()) {
      toast.error("Please fill all fields");
      return;
    }

    try {

      setLoading(true);

      const res = await api.post("/auth/login", form);

      const token = res?.data?.token;

      if (!token) {
        toast.error("Login failed. Token not received.");
        return;
      }

      login(token);

      toast.success("Login Successful 🎉");

      navigate("/dashboard", { replace: true });

    } catch (err) {

      console.error("Login error:", err);

      const status = err?.response?.status;

      if (status === 400 || status === 401) {
        toast.error("Invalid username or password");
      } else if (status === 403) {
        toast.error("Access denied");
      } else {
        toast.error("Server error. Please try again later.");
      }

    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="flex items-center justify-center min-h-screen bg-gray-100 dark:bg-slate-900">

      <form
        onSubmit={handleSubmit}
        className="bg-white dark:bg-slate-800 p-8 rounded-xl shadow-lg w-full max-w-sm"
      >

        <h2 className="text-2xl font-bold mb-6 text-center dark:text-white">
          Hospital Login
        </h2>

        {/* Username */}
        <input
          name="username"
          placeholder="Username"
          autoComplete="username"
          value={form.username}
          onChange={handleChange}
          className="w-full border p-3 mb-4 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 dark:bg-slate-700 dark:text-white"
        />

        {/* Password */}
        <input
          name="password"
          type="password"
          placeholder="Password"
          autoComplete="current-password"
          value={form.password}
          onChange={handleChange}
          className="w-full border p-3 mb-6 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 dark:bg-slate-700 dark:text-white"
        />

        {/* Login Button */}
        <button
          type="submit"
          disabled={loading}
          className="w-full bg-blue-600 text-white p-3 rounded-lg hover:bg-blue-700 transition disabled:bg-gray-400"
        >
          {loading ? "Logging in..." : "Login"}
        </button>

        {/* Register */}
        <p className="text-center mt-4 text-sm text-gray-600 dark:text-gray-300">
          Don't have an account?{" "}
          <Link
            to="/register"
            className="text-blue-600 hover:underline"
          >
            Register here
          </Link>
        </p>

      </form>

    </div>
  );
}

export default Login;