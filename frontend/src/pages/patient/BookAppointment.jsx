import { useEffect, useState } from "react";
import api from "../../api/axios";
import Loader from "../../components/Loader";
import toast from "react-hot-toast";
import { useNavigate } from "react-router-dom";

function BookAppointment() {

  const [doctors, setDoctors] = useState([]);
  const [doctorId, setDoctorId] = useState("");
  const [appointmentDate, setAppointmentDate] = useState("");
  const [loading, setLoading] = useState(true);
  const [submitting, setSubmitting] = useState(false);

  const navigate = useNavigate();

  useEffect(() => {
    fetchDoctors();
  }, []);

  const fetchDoctors = async () => {
    try {

      const res = await api.get("/doctors");

      const data = res?.data?.content || res?.data || [];

      setDoctors(data);

    } catch (err) {

      console.error("Doctor fetch error:", err);

      toast.error("Failed to load doctors");

    } finally {

      setLoading(false);

    }
  };

  const handleSubmit = async (e) => {

    e.preventDefault();

    if (!doctorId) {
      toast.error("Please select a doctor");
      return;
    }

    if (!appointmentDate) {
      toast.error("Please select appointment date");
      return;
    }

    try {

      setSubmitting(true);

      const payload = {
        doctorId: Number(doctorId),
        appointmentDate: appointmentDate + ":00"
      };

      await api.post("/appointments", payload);

      toast.success("Appointment booked successfully 🎉");

      setDoctorId("");
      setAppointmentDate("");

      navigate("/appointments/my");

    } catch (err) {

      console.error("Booking error:", err);

      if (err?.response?.data?.message) {
        toast.error(err.response.data.message);
      } else {
        toast.error("Booking failed");
      }

    } finally {

      setSubmitting(false);

    }
  };

  if (loading) return <Loader />;

  return (
    <div className="bg-white dark:bg-slate-800 p-6 rounded-xl shadow max-w-lg">

      <h2 className="text-xl font-bold mb-6 dark:text-white">
        Book Appointment
      </h2>

      <form onSubmit={handleSubmit} className="space-y-4">

        {/* Doctor Select */}
        <select
          value={doctorId}
          onChange={(e) => setDoctorId(e.target.value)}
          disabled={loading}
          className="w-full border p-3 rounded dark:bg-slate-700 dark:text-white"
        >

          <option value="">Select Doctor</option>

          {doctors.map((doc) => (
            <option key={doc.id} value={doc.id}>
              {doc.name} - {doc.specialization}
            </option>
          ))}

        </select>

        {/* Appointment Date */}
        <input
          type="datetime-local"
          min={new Date().toISOString().slice(0, 16)}
          value={appointmentDate}
          onChange={(e) => setAppointmentDate(e.target.value)}
          className="w-full border p-3 rounded dark:bg-slate-700 dark:text-white"
        />

        {/* Submit Button */}
        <button
          type="submit"
          disabled={submitting}
          className="w-full bg-blue-600 text-white p-3 rounded hover:bg-blue-700 disabled:bg-gray-400"
        >

          {submitting ? "Booking..." : "Book Appointment"}

        </button>

      </form>

    </div>
  );
}

export default BookAppointment;