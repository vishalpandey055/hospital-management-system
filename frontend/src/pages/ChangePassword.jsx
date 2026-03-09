import { useState } from "react";
import api from "../api/axios";
import toast from "react-hot-toast";

function ChangePassword() {

  const [form, setForm] = useState({
    currentPassword: "",
    newPassword: "",
    confirmPassword: ""
  });

  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {

    const { name, value } = e.target;

    setForm((prev) => ({
      ...prev,
      [name]: value
    }));

  };

  const handleSubmit = async (e) => {

    e.preventDefault();

    if (!form.currentPassword || !form.newPassword || !form.confirmPassword) {
      toast.error("Please fill all fields");
      return;
    }

    if (form.newPassword.length < 6) {
      toast.error("Password must be at least 6 characters");
      return;
    }

    if (form.newPassword !== form.confirmPassword) {
      toast.error("Passwords do not match");
      return;
    }

    try {

      setLoading(true);

      await api.put("/users/change-password", {
        currentPassword: form.currentPassword,
        newPassword: form.newPassword
      });

      toast.success("Password updated successfully");

      setForm({
        currentPassword: "",
        newPassword: "",
        confirmPassword: ""
      });

    } catch (err) {

      console.error(err);
      toast.error("Failed to update password");

    } finally {

      setLoading(false);

    }
  };

  return (
    <div className="bg-white dark:bg-slate-800 p-6 rounded-xl shadow max-w-md">

      <h2 className="text-xl font-bold mb-6 dark:text-white">
        Change Password
      </h2>

      <form onSubmit={handleSubmit} className="space-y-4">

        <input
          type="password"
          name="currentPassword"
          placeholder="Current Password"
          value={form.currentPassword}
          onChange={handleChange}
          className="w-full border p-3 rounded"
        />

        <input
          type="password"
          name="newPassword"
          placeholder="New Password"
          value={form.newPassword}
          onChange={handleChange}
          className="w-full border p-3 rounded"
        />

        <input
          type="password"
          name="confirmPassword"
          placeholder="Confirm New Password"
          value={form.confirmPassword}
          onChange={handleChange}
          className="w-full border p-3 rounded"
        />

        <button
          type="submit"
          disabled={loading}
          className="w-full bg-blue-600 text-white p-3 rounded hover:bg-blue-700 disabled:bg-gray-400"
        >
          {loading ? "Updating..." : "Update Password"}
        </button>

      </form>

    </div>
  );
}

export default ChangePassword;