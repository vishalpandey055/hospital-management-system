import { useEffect, useState } from "react";
import api from "../../api/axios";
import Loader from "../../components/Loader";
import SearchBar from "../../components/SearchBar";
import toast from "react-hot-toast";

function MyAppointments() {
  const [appointments, setAppointments] = useState([]);
  const [loading, setLoading] = useState(true);
  const [searchDoctor, setSearchDoctor] = useState("");

  useEffect(() => {
    fetchAppointments();
  }, []);

  const fetchAppointments = async () => {
    try {
      const res = await api.get("/appointments/my");

      const data = res?.data?.content || res?.data || [];
      setAppointments(data);
    } catch (err) {
      console.error(err);
      toast.error("Failed to load appointments");
    } finally {
      setLoading(false);
    }
  };

  const filteredAppointments = appointments.filter((a) =>
    (a?.doctorName || "").toLowerCase().includes(searchDoctor.toLowerCase()),
  );

  if (loading) return <Loader />;

  const getStatusBadge = (status) => {
    if (status === "BOOKED") return "bg-yellow-100 text-yellow-700";
    if (status === "COMPLETED") return "bg-green-100 text-green-700";
    if (status === "CANCELLED") return "bg-red-100 text-red-700";

    return "bg-gray-100 text-gray-700";
  };

  return (
    <div className="bg-white dark:bg-slate-800 p-6 rounded-xl shadow">
      <div className="flex flex-col md:flex-row md:justify-between md:items-center gap-4 mb-6">
        <h2 className="text-xl font-bold dark:text-white">My Appointments</h2>
        <SearchBar
          value={searchDoctor}
          onChange={setSearchDoctor}
          placeholder="Search by doctor name..."
        />
      </div>

      <div className="overflow-x-auto">
        <table className="w-full text-left">
          <thead>
            <tr className="border-b bg-gray-100 dark:bg-slate-700">
              <th className="p-2">Date</th>
              <th className="p-2">Doctor</th>
              <th className="p-2">Status</th>
            </tr>
          </thead>

          <tbody>
            {filteredAppointments.length === 0 ? (
              <tr>
                <td colSpan="3" className="text-center py-6 text-gray-500">
                  No appointments found
                </td>
              </tr>
            ) : (
              filteredAppointments.map((a) => (
                <tr
                  key={a.id}
                  className="border-b hover:bg-gray-100 dark:hover:bg-slate-700"
                >
                  <td className="p-2">
                    {new Date(a.appointmentDate).toLocaleString()}
                  </td>

                  <td className="p-2">{a.doctorName}</td>

                  <td className="p-2">
                    <span
                      className={`px-2 py-1 rounded text-sm ${getStatusBadge(a.status)}`}
                    >
                      {a.status}
                    </span>
                  </td>
                </tr>
              ))
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
}

export default MyAppointments;
