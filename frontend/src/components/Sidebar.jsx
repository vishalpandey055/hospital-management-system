import { NavLink } from "react-router-dom";
import {
  FiHome,
  FiUsers,
  FiUser,
  FiCalendar,
  FiClipboard,
  FiUserCheck,
} from "react-icons/fi";

function Sidebar({ role }) {
  const linkClass =
    "flex items-center gap-3 mb-2 p-2 rounded hover:bg-blue-600 transition";

  const activeClass = "bg-blue-700";

  const renderLink = (to, label, icon) => (
    <NavLink
      to={to}
      className={({ isActive }) =>
        `${linkClass} ${isActive ? activeClass : ""}`
      }
    >
      {icon}
      <span>{label}</span>
    </NavLink>
  );

  return (
    <div className="w-64 bg-slate-900 text-white p-6 min-h-screen">
      <h2 className="text-2xl font-bold mb-8">Hospital</h2>

      {/* ADMIN */}
      {role === "ADMIN" && (
        <>
          {renderLink("/dashboard", "Dashboard", <FiHome />)}
          {renderLink("/departments", "Departments", <FiClipboard />)}
          {renderLink("/doctors", "Doctors", <FiUsers />)}
          {renderLink("/patients", "Patients", <FiUser />)}
        </>
      )}

      {/* DOCTOR */}
      {role === "DOCTOR" && (
        <>
          {renderLink(
            "/doctor/appointments",
            "My Appointments",
            <FiCalendar />,
          )}
          {renderLink("/doctor/records", "Medical Records", <FiUserCheck />)}
        </>
      )}

      {/* PATIENT */}
      {role === "PATIENT" && (
        <>
          {renderLink("/appointments/book", "Book Appointment", <FiCalendar />)}
          {renderLink("/appointments/my", "My Appointments", <FiCalendar />)}
          {renderLink(
            "/appointments/history",
            "Medical History",
            <FiClipboard />,
          )}
        </>
      )}

      {/* COMMON */}
      <div className="mt-8 border-t border-gray-600 pt-4">
        {renderLink("/profile", "My Profile", <FiUser />)}
        {renderLink("/change-password", "Change Password", <FiUserCheck />)}
      </div>
    </div>
  );
}

export default Sidebar;
