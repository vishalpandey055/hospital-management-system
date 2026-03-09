import { useEffect, useState } from "react";
import api from "../../api/axios";
import Loader from "../../components/Loader";
import SearchBar from "../../components/SearchBar";
import toast from "react-hot-toast";

function Doctors() {

  const [doctors, setDoctors] = useState([]);
  const [departments, setDepartments] = useState([]);
  const [loading, setLoading] = useState(true);
  const [submitting, setSubmitting] = useState(false);
  const [search, setSearch] = useState("");

  const [form, setForm] = useState({
    name: "",
    specialization: "",
    availableDays: "",
    departmentId: "",
    username: "",
    password: ""
  });

  const [editingId, setEditingId] = useState(null);

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {

    setLoading(true);

    try {

      const [docRes, depRes] = await Promise.all([
        api.get("/doctors"),
        api.get("/departments?page=0&size=100")
      ]);

      setDoctors(docRes?.data || []);
      setDepartments(depRes?.data?.content || []);

    } catch (err) {

      console.error(err);
      toast.error("Failed to load doctors");

    } finally {
      setLoading(false);
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;

    setForm((prev) => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = async (e) => {

    e.preventDefault();

    if (!form.name || !form.specialization || !form.departmentId) {
      toast.error("Please fill required fields");
      return;
    }

    try {

      setSubmitting(true);

      if (editingId) {

        await api.put(`/doctors/${editingId}`, form);
        toast.success("Doctor Updated");

      } else {

        await api.post("/doctors", form);
        toast.success("Doctor Added");

      }

      setForm({
        name: "",
        specialization: "",
        availableDays: "",
        departmentId: "",
        username: "",
        password: ""
      });

      setEditingId(null);

      fetchData();

    } catch (err) {

      console.error(err);
      toast.error("Operation failed");

    } finally {
      setSubmitting(false);
    }
  };

  const handleEdit = (doctor) => {

    setForm({
      name: doctor.name || "",
      specialization: doctor.specialization || "",
      availableDays: doctor.availableDays || "",
      departmentId: doctor.departmentId || "",
      username: "",
      password: ""
    });

    setEditingId(doctor.id);
  };

  const handleDelete = async (id) => {

    if (!window.confirm("Delete this doctor?")) return;

    try {

      await api.delete(`/doctors/${id}`);
      toast.success("Doctor Deleted");

      fetchData();

    } catch (err) {

      console.error(err);
      toast.error("Delete failed");

    }
  };

  const filteredDoctors = doctors.filter((doc) =>
    doc.name?.toLowerCase().includes(search.toLowerCase())
  );

  if (loading) return <Loader />;

  return (
    <div className="space-y-8">

      {/* FORM */}
      <div className="bg-white dark:bg-slate-800 p-6 rounded-xl shadow">

        <h2 className="text-xl font-bold mb-4 dark:text-white">
          {editingId ? "Edit Doctor" : "Add Doctor"}
        </h2>

        <form onSubmit={handleSubmit} className="grid grid-cols-1 md:grid-cols-2 gap-4">

          <input
            name="name"
            placeholder="Doctor Name"
            value={form.name}
            onChange={handleChange}
            className="border p-2 rounded dark:bg-slate-700 dark:text-white"
          />

          <input
            name="specialization"
            placeholder="Specialization"
            value={form.specialization}
            onChange={handleChange}
            className="border p-2 rounded dark:bg-slate-700 dark:text-white"
          />

          <input
            name="availableDays"
            placeholder="Available Days"
            value={form.availableDays}
            onChange={handleChange}
            className="border p-2 rounded dark:bg-slate-700 dark:text-white"
          />

          <select
            name="departmentId"
            value={form.departmentId}
            onChange={handleChange}
            className="border p-2 rounded dark:bg-slate-700 dark:text-white"
          >

            <option value="">Select Department</option>

            {departments.map((dep) => (
              <option key={dep.id} value={dep.id}>
                {dep.name}
              </option>
            ))}

          </select>

          <input
            name="username"
            placeholder="Doctor Login Username"
            value={form.username}
            onChange={handleChange}
            className="border p-2 rounded dark:bg-slate-700 dark:text-white"
          />

          <input
            name="password"
            type="password"
            placeholder="Doctor Login Password"
            value={form.password}
            onChange={handleChange}
            className="border p-2 rounded dark:bg-slate-700 dark:text-white"
          />

          <button
            type="submit"
            disabled={submitting}
            className="col-span-1 md:col-span-2 bg-blue-600 text-white p-2 rounded hover:bg-blue-700 disabled:bg-gray-400"
          >
            {submitting ? "Saving..." : editingId ? "Update Doctor" : "Add Doctor"}
          </button>

        </form>

      </div>

      {/* TABLE */}
      <div className="bg-white dark:bg-slate-800 p-6 rounded-xl shadow">

        <div className="flex flex-col md:flex-row md:justify-between md:items-center gap-3 mb-4">

          <h2 className="text-xl font-bold dark:text-white">
            Doctors List
          </h2>

          <SearchBar
            value={search}
            onChange={setSearch}
            placeholder="Search by name..."
          />

        </div>

        <div className="overflow-x-auto">

          <table className="w-full text-left border">

            <thead>
              <tr className="border-b bg-gray-100 dark:bg-slate-700">
                <th className="p-2">Name</th>
                <th className="p-2">Specialization</th>
                <th className="p-2">Available Days</th>
                <th className="p-2">Department</th>
                <th className="p-2">Actions</th>
              </tr>
            </thead>

            <tbody>

              {filteredDoctors.length === 0 ? (
                <tr>
                  <td colSpan="5" className="text-center py-6 text-gray-500">
                    No doctors available
                  </td>
                </tr>
              ) : (
                filteredDoctors.map((doc) => (

                  <tr
                    key={doc.id}
                    className="border-b hover:bg-gray-100 dark:hover:bg-slate-700"
                  >

                    <td className="p-2">{doc.name}</td>
                    <td className="p-2">{doc.specialization}</td>
                    <td className="p-2">{doc.availableDays}</td>
                    <td className="p-2">{doc.departmentName}</td>

                    <td className="space-x-3 p-2">

                      <button
                        onClick={() => handleEdit(doc)}
                        className="text-blue-600 hover:underline"
                      >
                        Edit
                      </button>

                      <button
                        onClick={() => handleDelete(doc.id)}
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

export default Doctors;