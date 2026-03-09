import { useEffect, useState } from "react";
import api from "../../api/axios";
import Loader from "../../components/Loader";
import toast from "react-hot-toast";

function DoctorAppointments() {
  const [appointments, setAppointments] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchAppointments();
  }, []);

  const fetchAppointments = async () => {
    try {
      const res = await api.get("/appointments/doctor/my");

      setAppointments(res.data || []);
    } catch (error) {
      console.error(error);
      toast.error("Failed to load appointments");
    } finally {
      setLoading(false);
    }
  };

  if (loading) return <Loader />;

  return (
    <div className="bg-white p-6 rounded-xl shadow">
      <h2 className="text-xl font-bold mb-6">My Appointments</h2>

      <table className="w-full text-left">
        <thead>
          <tr className="border-b">
            <th>Date</th>
            <th>Status</th>
          </tr>
        </thead>

        <tbody>
          {appointments.length === 0 ? (
            <tr>
              <td colSpan="2" className="text-center py-6 text-gray-500">
                No appointments found
              </td>
            </tr>
          ) : (
            appointments.map((a) => (
              <tr key={a.id} className="border-b">
                <td>{new Date(a.appointmentDate).toLocaleString()}</td>
                <td>{a.status}</td>
              </tr>
            ))
          )}
        </tbody>
      </table>
    </div>
  );
}

export default DoctorAppointments;
