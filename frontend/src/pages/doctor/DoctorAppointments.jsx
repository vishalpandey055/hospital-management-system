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

      const data = Array.isArray(res.data) ? res.data : [];

      setAppointments(data);

    } catch (error) {

      console.error("Appointments error:", error);

      if (error.response?.status === 403) {
        toast.error("Access denied");
      } else if (error.response?.status === 404) {
        toast.error("Appointments endpoint not found");
      } else {
        toast.error("Failed to load appointments");
      }

    } finally {
      setLoading(false);
    }

  };

  if (loading) return <Loader />;

  return (

    <div className="bg-white dark:bg-slate-800 p-6 rounded-xl shadow">

      <h2 className="text-xl font-bold mb-6 dark:text-white">
        My Appointments
      </h2>

      <div className="overflow-x-auto">

        <table className="w-full text-left">

          <thead>
            <tr className="border-b dark:border-slate-600">
              <th className="p-2">Date</th>
              <th className="p-2">Patient</th>
              <th className="p-2">Status</th>
            </tr>
          </thead>

          <tbody>

            {appointments.length === 0 ? (

              <tr>
                <td
                  colSpan="3"
                  className="text-center py-6 text-gray-500 dark:text-gray-400"
                >
                  No appointments found
                </td>
              </tr>

            ) : (

              appointments.map((appointment) => (

                <tr
                  key={appointment.id}
                  className="border-b dark:border-slate-700"
                >

                  <td className="p-2">
                    {new Date(appointment.appointmentDate).toLocaleString()}
                  </td>

                  <td className="p-2">
                    {appointment.patientName || "Unknown Patient"}
                  </td>

                  <td className="p-2">
                    {appointment.status}
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

export default DoctorAppointments;