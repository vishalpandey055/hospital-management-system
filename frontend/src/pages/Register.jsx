import { useState } from "react";
import api from "../api/axios";
import { useNavigate, Link } from "react-router-dom";
import toast from "react-hot-toast";

function Register() {

  const [form, setForm] = useState({
    username: "",
    password: "",
    role: "PATIENT"
  });

  const [loading, setLoading] = useState(false);

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

    const username = form.username.trim();
    const password = form.password.trim();

    if (!username || !password) {
      toast.error("Please fill all fields");
      return;
    }

    if (password.length < 6) {
      toast.error("Password must be at least 6 characters");
      return;
    }

    try {

      setLoading(true);

      await api.post("/auth/register", {
        ...form,
        username,
        password
      });

      toast.success("Registration successful 🎉");

      navigate("/login");

    } catch (error) {

      console.error("Register error:", error?.response?.data || error.message);

      toast.error(
        error?.response?.data?.message ||
        error?.response?.data ||
        "Registration failed"
      );

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
          Register
        </h2>

        <input
          name="username"
          placeholder="Username"
          autoComplete="username"
          value={form.username}
          onChange={handleChange}
          className="w-full border p-3 mb-4 rounded focus:outline-none focus:ring-2 focus:ring-blue-500 dark:bg-slate-700 dark:text-white"
        />

        <input
          name="password"
          type="password"
          placeholder="Password"
          autoComplete="new-password"
          value={form.password}
          onChange={handleChange}
          className="w-full border p-3 mb-4 rounded focus:outline-none focus:ring-2 focus:ring-blue-500 dark:bg-slate-700 dark:text-white"
        />

        <select
          name="role"
          value={form.role}
          onChange={handleChange}
          className="w-full border p-3 mb-4 rounded dark:bg-slate-700 dark:text-white"
        >
          <option value="PATIENT">Patient</option>
          <option value="DOCTOR">Doctor</option>
          <option value="ADMIN">Admin</option>
        </select>

        <button
          type="submit"
          disabled={loading}
          className="w-full bg-blue-600 text-white p-3 rounded hover:bg-blue-700 transition disabled:bg-gray-400"
        >
          {loading ? "Registering..." : "Register"}
        </button>

        <p className="text-center mt-4 text-sm text-gray-600 dark:text-gray-300">
          Already have an account?{" "}
          <Link
            to="/login"
            className="text-blue-600 hover:underline"
          >
            Login
          </Link>
        </p>

      </form>

    </div>
  );
}

export default Register;