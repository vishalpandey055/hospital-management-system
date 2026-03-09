import { useEffect, useState } from "react";
import api from "../../api/axios";
import Loader from "../../components/Loader";
import toast from "react-hot-toast";

function Departments() {

  const [departments, setDepartments] = useState([]);
  const [loading, setLoading] = useState(true);
  const [submitting, setSubmitting] = useState(false);

  const [name, setName] = useState("");
  const [editingId, setEditingId] = useState(null);

  useEffect(() => {
    fetchDepartments();
  }, []);

  const fetchDepartments = async () => {

    try {

      const res = await api.get("/departments?page=0&size=100");

      setDepartments(res?.data?.content || []);

    } catch {

      toast.error("Failed to load departments");

    } finally {

      setLoading(false);
    }
  };

  const handleSubmit = async (e) => {

    e.preventDefault();

    if (!name.trim()) {
      toast.error("Department name is required");
      return;
    }

    try {

      setSubmitting(true);

      if (editingId) {

        await api.put(`/departments/${editingId}`, { name });
        toast.success("Department Updated");

      } else {

        await api.post("/departments", { name });
        toast.success("Department Added");

      }

      setName("");
      setEditingId(null);

      fetchDepartments();

    } catch {

      toast.error("Operation failed");

    } finally {

      setSubmitting(false);
    }
  };

  const handleEdit = (dep) => {

    setName(dep.name || "");
    setEditingId(dep.id);
  };

  const handleDelete = async (id) => {

    if (!window.confirm("Delete this department?")) return;

    try {

      await api.delete(`/departments/${id}`);
      toast.success("Department Deleted");

      fetchDepartments();

    } catch {

      toast.error("Delete failed");

    }
  };

  if (loading) return <Loader />;

  return (
    <div className="space-y-8">

      {/* FORM */}
      <div className="bg-white dark:bg-slate-800 p-6 rounded-xl shadow">

        <h2 className="text-xl font-bold mb-4 dark:text-white">
          {editingId ? "Edit Department" : "Add Department"}
        </h2>

        <form
          onSubmit={handleSubmit}
          className="flex flex-col sm:flex-row gap-4"
        >

          <input
            type="text"
            placeholder="Department Name"
            value={name}
            onChange={(e) => setName(e.target.value)}
            className="border p-2 rounded flex-1 dark:bg-slate-700 dark:text-white"
          />

          <button
            type="submit"
            disabled={submitting}
            className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700 disabled:bg-gray-400"
          >
            {submitting
              ? "Saving..."
              : editingId
              ? "Update"
              : "Add"}
          </button>

        </form>

      </div>

      {/* TABLE */}
      <div className="bg-white dark:bg-slate-800 p-6 rounded-xl shadow">

        <h2 className="text-xl font-bold mb-4 dark:text-white">
          Departments
        </h2>

        <div className="overflow-x-auto">

          <table className="w-full text-left">

            <thead>
              <tr className="border-b bg-gray-100 dark:bg-slate-700">
                <th className="p-2">Name</th>
                <th className="p-2">Actions</th>
              </tr>
            </thead>

            <tbody>

              {departments.length === 0 ? (
                <tr>
                  <td colSpan="2" className="text-center py-6 text-gray-500">
                    No departments available
                  </td>
                </tr>
              ) : (
                departments.map((dep) => (

                  <tr
                    key={dep.id}
                    className="border-b hover:bg-gray-100 dark:hover:bg-slate-700"
                  >

                    <td className="p-2">{dep.name}</td>

                    <td className="space-x-3 p-2">

                      <button
                        onClick={() => handleEdit(dep)}
                        className="text-blue-600 hover:underline"
                      >
                        Edit
                      </button>

                      <button
                        onClick={() => handleDelete(dep.id)}
                        className="text-red-600 hover:underline"
                      >
                        Delete
                      </button>

                    </td>

                  </tr>

                ))
              )}

            </tbody>

          </table>

        </div>

      </div>

    </div>
  );
}

export default Departments;