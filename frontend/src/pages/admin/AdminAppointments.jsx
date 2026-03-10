import { useEffect, useState } from "react";
import api from "../../api/axios";
import Loader from "../../components/Loader";
import toast from "react-hot-toast";

function AdminAppointments() {

  const [appointments, setAppointments] = useState([]);
  const [loading, setLoading] = useState(true);
  const [cancelLoading, setCancelLoading] = useState(null);

  useEffect(() => {
    fetchAppointments();
  }, []);

  const fetchAppointments = async () => {

    try {

      const res = await api.get("/appointments");

      setAppointments(res.data || []);

    } catch (error) {

      console.error(error);
      toast.error("Failed to load appointments");

    } finally {

      setLoading(false);

    }
  };

  // CANCEL APPOINTMENT
  const cancelAppointment = async (id) => {

    try {

      setCancelLoading(id);

      await api.put(`/appointments/${id}/cancel`);

      toast.success("Appointment cancelled");

      setAppointments((prev) =>
        prev.map((a) =>
          a.id === id ? { ...a, status: "CANCELLED" } : a
        )
      );

    } catch (error) {

      console.error(error);
      toast.error("Cancel failed");

    } finally {

      setCancelLoading(null);

    }
  };

  if (loading) {
    return (
      <div className="p-6">
        <Loader />
      </div>
    );
  }

  return (

    <div className="bg-white dark:bg-slate-800 p-6 rounded-xl shadow">

      <h2 className="text-xl font-bold mb-6 dark:text-white">
        All Appointments
      </h2>

      <div className="overflow-x-auto">

        <table className="w-full text-left">

          <thead>
            <tr className="border-b dark:border-slate-600">
              <th className="py-2">Doctor</th>
              <th className="py-2">Patient</th>
              <th className="py-2">Date</th>
              <th className="py-2">Status</th>
              <th className="py-2">Action</th>
            </tr>
          </thead>

          <tbody>

            {appointments.length === 0 ? (

              <tr>
                <td colSpan="5" className="text-center py-6 text-gray-500">
                  No appointments found
                </td>
              </tr>

            ) : (

              appointments.map((a) => (

                <tr key={a.id} className="border-b dark:border-slate-700">

                  <td className="py-2">{a.doctorName}</td>

                  <td className="py-2">{a.patientName}</td>

                  <td className="py-2">
                    {new Date(a.appointmentDate).toLocaleString()}
                  </td>

                  <td className="py-2">

                    <span
                      className={`px-2 py-1 rounded text-sm font-medium ${
                        a.status === "BOOKED"
                          ? "bg-blue-100 text-blue-600"
                          : a.status === "COMPLETED"
                          ? "bg-green-100 text-green-600"
                          : "bg-red-100 text-red-600"
                      }`}
                    >
                      {a.status}
                    </span>

                  </td>

                  <td className="py-2">

                    {a.status !== "CANCELLED" && (

                      <button
                        onClick={() => cancelAppointment(a.id)}
                        disabled={cancelLoading === a.id}
                        className="bg-red-500 text-white px-3 py-1 rounded hover:bg-red-600 transition disabled:opacity-50"
                      >
                        {cancelLoading === a.id ? "Cancelling..." : "Cancel"}
                      </button>

                    )}

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

export default AdminAppointments;