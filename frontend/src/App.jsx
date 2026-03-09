import { Routes, Route, Navigate } from "react-router-dom";
import { useContext } from "react";
import { AuthContext } from "./context/AuthContext";

// pages
import Login from "./pages/Login";
import Register from "./pages/Register";
import Dashboard from "./pages/Dashboard";
import Profile from "./pages/Profile";
import ChangePassword from "./pages/ChangePassword";
import NotFound from "./pages/NotFound";

// admin
import Departments from "./pages/admin/Departments";
import Doctors from "./pages/admin/Doctors";
import Patients from "./pages/admin/Patients";

// doctor
import DoctorAppointments from "./pages/doctor/DoctorAppointments";
import MedicalRecords from "./pages/doctor/MedicalRecords";

// patient
import BookAppointment from "./pages/patient/BookAppointment";
import MedicalHistory from "./pages/patient/MedicalHistory";
import MyAppointments from "./pages/patient/MyAppointments";

// layout
import Layout from "./components/Layout";
import ProtectedRoute from "./components/ProtectedRoute";

function App() {
  const { role } = useContext(AuthContext);

  return (
    <Routes>
      {/* ROOT */}
      <Route
        path="/"
        element={
          role ? (
            <Navigate to="/dashboard" replace />
          ) : (
            <Navigate to="/login" replace />
          )
        }
      />

      {/* PUBLIC ROUTES */}
      <Route path="/login" element={<Login />} />
      <Route path="/register" element={<Register />} />

      {/* PROTECTED APP */}
      <Route
        element={
          <ProtectedRoute allowedRoles={["ADMIN", "DOCTOR", "PATIENT"]} />
        }
      >
        <Route element={<Layout role={role} />}>
          {/* COMMON */}
          <Route path="/dashboard" element={<Dashboard />} />
          <Route path="/profile" element={<Profile />} />
          <Route path="/change-password" element={<ChangePassword />} />

          {/* ADMIN */}
          <Route element={<ProtectedRoute allowedRoles={["ADMIN"]} />}>
            <Route path="/departments" element={<Departments />} />
            <Route path="/doctors" element={<Doctors />} />
            <Route path="/patients" element={<Patients />} />
          </Route>

          {/* DOCTOR */}
          <Route element={<ProtectedRoute allowedRoles={["DOCTOR"]} />}>
            <Route
              path="/doctor/appointments"
              element={<DoctorAppointments />}
            />

            <Route path="/doctor/records" element={<MedicalRecords />} />
          </Route>

          {/* PATIENT */}
          <Route element={<ProtectedRoute allowedRoles={["PATIENT"]} />}>
            <Route path="/appointments/book" element={<BookAppointment />} />

            <Route path="/appointments/history" element={<MedicalHistory />} />

            <Route path="/appointments/my" element={<MyAppointments />} />
          </Route>
        </Route>
      </Route>

      {/* 404 */}
      <Route path="*" element={<NotFound />} />
    </Routes>
  );
}

export default App;
