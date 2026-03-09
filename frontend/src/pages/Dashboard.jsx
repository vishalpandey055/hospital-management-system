import { useEffect, useState } from "react";
import api from "../api/axios";
import toast from "react-hot-toast";
import {
  FiUsers,
  FiUser,
  FiCalendar,
  FiCheckCircle
} from "react-icons/fi";

function Dashboard() {

  const [stats, setStats] = useState({
    totalDoctors: 0,
    totalPatients: 0,
    totalAppointments: 0,
    completedAppointments: 0,
  });

  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchStats();
  }, []);

  const fetchStats = async () => {

    try {

      const res = await api.get("/dashboard/stats");

      const data = res?.data;

      setStats({
        totalDoctors: data?.totalDoctors ?? 0,
        totalPatients: data?.totalPatients ?? 0,
        totalAppointments: data?.totalAppointments ?? 0,
        completedAppointments: data?.completedAppointments ?? 0
      });

    } catch (err) {

      console.error("Dashboard error:", err);

      toast.error("Failed to load dashboard");

    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return (
      <div className="flex justify-center items-center h-[60vh]">
        <p className="text-lg font-semibold dark:text-white">
          Loading Dashboard...
        </p>
      </div>
    );
  }

  return (
    <div className="p-6 md:p-10">

      <h1 className="text-2xl md:text-3xl font-bold mb-8 dark:text-white">
        Hospital Dashboard
      </h1>

      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">

        <StatCard
          title="Doctors"
          value={stats.totalDoctors}
          icon={<FiUsers size={26} />}
          color="bg-blue-500"
        />

        <StatCard
          title="Patients"
          value={stats.totalPatients}
          icon={<FiUser size={26} />}
          color="bg-green-500"
        />

        <StatCard
          title="Appointments"
          value={stats.totalAppointments}
          icon={<FiCalendar size={26} />}
          color="bg-purple-500"
        />

        <StatCard
          title="Completed"
          value={stats.completedAppointments}
          icon={<FiCheckCircle size={26} />}
          color="bg-orange-500"
        />

      </div>

    </div>
  );
}

/* Reusable Stat Card */
function StatCard({ title, value, icon, color }) {

  return (
    <div className={`${color} text-white p-6 rounded-xl shadow hover:scale-[1.02] transition`}>

      <div className="flex justify-between items-center">
        <h2 className="text-lg">{title}</h2>
        {icon}
      </div>

      <p className="text-3xl font-bold mt-4">
        {value}
      </p>

    </div>
  );
}

export default Dashboard;