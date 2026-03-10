import React from "react";

function StatCard({ title, value, color }) {
  return (
    <div className="bg-white p-6 rounded shadow">

      <h3 className="text-gray-500 text-sm">
        {title}
      </h3>

      <p className={`text-3xl font-bold mt-2 text-${color}-600`}>
        {value}
      </p>

    </div>
  );
}

export default StatCard;