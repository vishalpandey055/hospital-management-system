import { useEffect, useState } from "react";
import api from "../../api/axios";
import Loader from "../../components/Loader";
import toast from "react-hot-toast";

function Patients() {

  const [patients, setPatients] = useState([]);
  const [loading, setLoading] = useState(true);
  const [submitting, setSubmitting] = useState(false);

  const [editingId, setEditingId] = useState(null);

  const [form, setForm] = useState({
    name: "",
    age: "",
    gender: "",
    phone: "",
    email: "",
    username: "",
    password: ""
  });

  useEffect(() => {
    fetchPatients();
  }, []);

  const fetchPatients = async () => {
    try {

      const res = await api.get("/patients");

      setPatients(Array.isArray(res.data) ? res.data : []);

    } catch (err) {

      console.error(err);
      toast.error("Failed to load patients");

    } finally {
      setLoading(false);
    }
  };

  const handleChange = (e) => {

    const { name, value } = e.target;

    setForm(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = async (e) => {

    e.preventDefault();

    if (!form.name || !form.age || !form.phone) {
      toast.error("Name, Age and Phone are required");
      return;
    }

    try {

      setSubmitting(true);

      const payload = {
        ...form,
        age: Number(form.age)
      };

      if (editingId) {

        await api.put(`/patients/${editingId}`, payload);
        toast.success("Patient updated successfully");

      } else {

        await api.post("/patients", payload);
        toast.success("Patient added successfully");

      }

      resetForm();
      fetchPatients();

    } catch (err) {

      console.error(err);
      toast.error("Operation failed");

    } finally {

      setSubmitting(false);
    }
  };

  const resetForm = () => {

    setForm({
      name: "",
      age: "",
      gender: "",
      phone: "",
      email: "",
      username: "",
      password: ""
    });

    setEditingId(null);
  };

  const handleEdit = (patient) => {

    setForm({
      name: patient.name || "",
      age: patient.age || "",
      gender: patient.gender || "",
      phone: patient.phone || "",
      email: patient.email || "",
      username: "",
      password: ""
    });

    setEditingId(patient.id);
  };

  const handleDelete = async (id) => {

    if (!window.confirm("Delete this patient?")) return;

    try {

      await api.delete(`/patients/${id}`);

      toast.success("Patient deleted successfully");

      fetchPatients();

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
          {editingId ? "Edit Patient" : "Add Patient"}
        </h2>

        <form
          onSubmit={handleSubmit}
          className="grid grid-cols-1 md:grid-cols-2 gap-4"
        >

          <input
            name="name"
            placeholder="Name"
            value={form.name}
            onChange={handleChange}
            className="border p-2 rounded dark:bg-slate-700 dark:text-white"
          />

          <input
            name="age"
            type="number"
            placeholder="Age"
            value={form.age}
            onChange={handleChange}
            className="border p-2 rounded dark:bg-slate-700 dark:text-white"
          />

          <input
            name="gender"
            placeholder="Gender"
            value={form.gender}
            onChange={handleChange}
            className="border p-2 rounded dark:bg-slate-700 dark:text-white"
          />

          <input
            name="phone"
            placeholder="Phone"
            value={form.phone}
            onChange={handleChange}
            className="border p-2 rounded dark:bg-slate-700 dark:text-white"
          />

          <input
            name="email"
            placeholder="Email"
            value={form.email}
            onChange={handleChange}
            className="border p-2 rounded dark:bg-slate-700 dark:text-white"
          />

          <input
            name="username"
            placeholder="Username"
            value={form.username}
            onChange={handleChange}
            className="border p-2 rounded dark:bg-slate-700 dark:text-white"
          />

          <input
            name="password"
            type="password"
            placeholder="Password"
            value={form.password}
            onChange={handleChange}
            className="border p-2 rounded dark:bg-slate-700 dark:text-white"
          />

          <button
            type="submit"
            disabled={submitting}
            className="col-span-1 md:col-span-2 bg-green-600 text-white p-2 rounded hover:bg-green-700 disabled:bg-gray-400"
          >
            {submitting
              ? "Saving..."
              : editingId
              ? "Update Patient"
              : "Add Patient"}
          </button>

        </form>

      </div>

      {/* TABLE */}
      <div className="bg-white dark:bg-slate-800 p-6 rounded-xl shadow">

        <h2 className="text-xl font-bold mb-4 dark:text-white">
          Patients List
        </h2>

        <div className="overflow-x-auto">

          <table className="w-full text-left">

            <thead>
              <tr className="border-b bg-gray-100 dark:bg-slate-700">
                <th className="p-2">Name</th>
                <th className="p-2">Age</th>
                <th className="p-2">Gender</th>
                <th className="p-2">Phone</th>
                <th className="p-2">Email</th>
                <th className="p-2">Actions</th>
              </tr>
            </thead>

            <tbody>

              {patients.length === 0 ? (
                <tr>
                  <td colSpan="6" className="text-center py-6 text-gray-500">
                    No patients available
                  </td>
                </tr>
              ) : (
                patients.map((p) => (

                  <tr
                    key={p.id}
                    className="border-b hover:bg-gray-100 dark:hover:bg-slate-700"
                  >

                    <td className="p-2">{p.name}</td>
                    <td className="p-2">{p.age}</td>
                    <td className="p-2">{p.gender}</td>
                    <td className="p-2">{p.phone}</td>
                    <td className="p-2">{p.email}</td>

                    <td className="space-x-3 p-2">

                      <button
                        onClick={() => handleEdit(p)}
                        className="text-blue-600 hover:underline"
                      >
                        Edit
                      </button>

                      <button
                        onClick={() => handleDelete(p.id)}
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

export default Patients;