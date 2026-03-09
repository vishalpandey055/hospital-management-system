import Sidebar from "./Sidebar";
import Navbar from "./Navbar";
import { Outlet } from "react-router-dom";

function Layout({ role }) {
  return (
    <div className="flex min-h-screen bg-gray-100 dark:bg-slate-900">

      {/* Sidebar */}
      <Sidebar role={role} />

      {/* Main Content Area */}
      <div className="flex flex-col flex-1 min-h-screen">

        {/* Top Navbar */}
        <Navbar />

        {/* Page Content */}
        <main className="flex-1 p-4 md:p-6 overflow-y-auto">
          <Outlet />
        </main>

      </div>

    </div>
  );
}

export default Layout;