import { useEffect, useState } from "react";
import api from "../../api/axios";
import Loader from "../../components/Loader";
import toast from "react-hot-toast";

function MedicalRecords() {
  const [records, setRecords] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchRecords();
  }, []);

  const fetchRecords = async () => {
    try {
      // 🔹 doctor endpoint
      const res = await api.get("/medical-records/doctor");

      setRecords(Array.isArray(res.data) ? res.data : []);
    } catch (err) {
      console.error("Medical records error:", err);

      if (err.response?.status === 403) {
        toast.error("You are not allowed to view these records");
      } else if (err.response?.status === 404) {
        toast.error("Medical records endpoint not found");
      } else {
        toast.error("Failed to load medical records");
      }
    } finally {
      setLoading(false);
    }
  };

  if (loading) return <Loader />;

  return (
    <div className="bg-white dark:bg-slate-800 p-6 rounded-xl shadow">
      <h2 className="text-xl font-bold mb-6 dark:text-white">
        Medical Records
      </h2>

      <table className="w-full text-left">
        <thead>
          <tr className="border-b">
            <th className="p-2">Doctor</th>
            <th className="p-2">Diagnosis</th>
            <th className="p-2">Treatment</th>
          </tr>
        </thead>

        <tbody>
          {records.length === 0 ? (
            <tr>
              <td colSpan="3" className="text-center py-6 text-gray-500">
                No medical records found
              </td>
            </tr>
          ) : (
            records.map((record) => (
              <tr key={record.id} className="border-b">
                <td className="p-2">{record.doctorName || "Doctor"}</td>

                <td className="p-2">{record.diagnosis}</td>

                <td className="p-2">{record.treatment}</td>
              </tr>
            ))
          )}
        </tbody>
      </table>
    </div>
  );
}

export default MedicalRecords;
