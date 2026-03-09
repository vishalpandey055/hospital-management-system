function StatCard({ title, value, color = "blue" }) {

  return (
    <div className="bg-white dark:bg-slate-800 p-6 rounded-xl shadow hover:shadow-lg transition">

      <h3 className="text-gray-500 dark:text-gray-300 text-sm font-medium">
        {title}
      </h3>

      <p className={`text-3xl font-bold mt-2 text-${color}-600`}>
        {value ?? 0}
      </p>

    </div>
  );
}

export default StatCard;