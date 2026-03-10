import { useEffect, useState } from "react";
import api from "../api/axios";
import Loader from "../components/Loader";
import toast from "react-hot-toast";

function Profile() {

  const [profile, setProfile] = useState({
    username: "",
    email: "",
    phone: "",
    role: ""
  });

  const [loading, setLoading] = useState(true);
  const [updating, setUpdating] = useState(false);

  useEffect(() => {
    fetchProfile();
  }, []);

  const fetchProfile = async () => {
    try {

      const res = await api.get("/users/me");

      if (res?.data) {
        setProfile({
          username: res.data.username || "",
          email: res.data.email || "",
          phone: res.data.phone || "",
          role: res.data.role || ""
        });
      }

    } catch (err) {

      console.error("Profile fetch error:", err);
      toast.error("Failed to load profile");

    } finally {

      setLoading(false);

    }
  };

  const handleChange = (e) => {

    const { name, value } = e.target;

    setProfile((prev) => ({
      ...prev,
      [name]: value
    }));
  };

  const handleUpdate = async () => {

    if (!profile.username.trim()) {
      toast.error("Username cannot be empty");
      return;
    }

    try {

      setUpdating(true);

      await api.put("/users/me", {
        username: profile.username,
        email: profile.email,
        phone: profile.phone
      });

      toast.success("Profile updated successfully");

      // refresh profile
      await fetchProfile();

    } catch (err) {

      console.error("Update error:", err);

      if (err.response?.status === 400) {
        toast.error("Invalid data");
      } else {
        toast.error("Update failed");
      }

    } finally {

      setUpdating(false);

    }
  };

  if (loading) return <Loader />;

  return (
    <div className="bg-white dark:bg-slate-800 p-6 rounded-xl shadow max-w-lg">

      <h2 className="text-xl font-bold mb-6 dark:text-white">
        My Profile
      </h2>

      <div className="space-y-4">

        <input
          name="username"
          value={profile.username}
          onChange={handleChange}
          placeholder="Username"
          className="w-full border p-2 rounded dark:bg-slate-700 dark:text-white"
        />

        <input
          name="email"
          value={profile.email}
          onChange={handleChange}
          placeholder="Email"
          className="w-full border p-2 rounded dark:bg-slate-700 dark:text-white"
        />

        <input
          name="phone"
          value={profile.phone}
          onChange={handleChange}
          placeholder="Phone"
          className="w-full border p-2 rounded dark:bg-slate-700 dark:text-white"
        />

        <input
          value={profile.role}
          disabled
          className="w-full border p-2 rounded bg-gray-200 dark:bg-slate-600 dark:text-white"
        />

        <button
          onClick={handleUpdate}
          disabled={updating}
          className="w-full bg-blue-600 text-white p-3 rounded hover:bg-blue-700 disabled:bg-gray-400"
        >
          {updating ? "Updating..." : "Update Profile"}
        </button>

      </div>

    </div>
  );
}

export default Profile;