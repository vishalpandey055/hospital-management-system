import { useEffect, useState } from "react";
import api from "../../api/axios";
import Loader from "../../components/Loader";
import toast from "react-hot-toast";

function MedicalHistory() {

  const [records, setRecords] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchHistory();
  }, []);

  const fetchHistory = async () => {

    try {

      const res = await api.get("/medical-records/my");

      const data = Array.isArray(res?.data) ? res.data : [];

      setRecords(data);

    } catch (err) {

      console.error(err);
      toast.error("Failed to load medical history");

    } finally {

      setLoading(false);

    }
  };

  if (loading) return <Loader />;

  return (
    <div className="bg-white dark:bg-slate-800 p-6 rounded-xl shadow">

      <h2 className="text-xl font-bold mb-6 dark:text-white">
        My Medical History
      </h2>

      <div className="overflow-x-auto">

        <table className="w-full text-left">

          <thead>
            <tr className="border-b bg-gray-100 dark:bg-slate-700">
              <th className="p-2">Diagnosis</th>
              <th className="p-2">Treatment</th>
            </tr>
          </thead>

          <tbody>

            {records.length === 0 ? (

              <tr>
                <td colSpan="2" className="text-center py-6 text-gray-500">
                  No medical records found
                </td>
              </tr>

            ) : (

              records.map((r) => (

                <tr key={r.id} className="border-b hover:bg-gray-100 dark:hover:bg-slate-700">

                  <td className="p-2">{r.diagnosis}</td>
                  <td className="p-2">{r.treatment}</td>

                </tr>

              ))

            )}

          </tbody>

        </table>

      </div>

    </div>
  );
}

export default MedicalHistory;