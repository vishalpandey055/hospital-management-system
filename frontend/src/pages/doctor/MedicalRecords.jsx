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

      const res = await api.get("/medical-records/doctor");

      const data = Array.isArray(res.data) ? res.data : [];

      setRecords(data);

    } catch (error) {

      console.error("Medical records error:", error);

      if (error.response?.status === 403) {
        toast.error("Access denied");
      } else if (error.response?.status === 404) {
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

      <div className="overflow-x-auto">

        <table className="w-full text-left">

          <thead>
            <tr className="border-b dark:border-slate-600">
              <th className="p-2">Record ID</th>
              <th className="p-2">Appointment</th>
              <th className="p-2">Diagnosis</th>
              <th className="p-2">Treatment</th>
            </tr>
          </thead>

          <tbody>

            {records.length === 0 ? (

              <tr>
                <td
                  colSpan="4"
                  className="text-center py-6 text-gray-500 dark:text-gray-400"
                >
                  No medical records found
                </td>
              </tr>

            ) : (

              records.map((record) => (

                <tr
                  key={record.id}
                  className="border-b dark:border-slate-700"
                >

                  <td className="p-2">
                    {record.id}
                  </td>

                  <td className="p-2">
                    {record.appointmentId || "N/A"}
                  </td>

                  <td className="p-2">
                    {record.diagnosis || "Not provided"}
                  </td>

                  <td className="p-2">
                    {record.treatment || "Not provided"}
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

export default MedicalRecords;