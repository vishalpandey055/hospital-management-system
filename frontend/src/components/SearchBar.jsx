function SearchBar({ value, onChange, placeholder = "Search..." }) {
  return (
    <div className="relative w-full md:w-64">
      <input
        type="text"
        value={value}
        onChange={(e) => onChange(e.target.value)} // ⭐ FIX
        placeholder={placeholder}
        className="w-full border p-2 pl-10 rounded-lg 
                   dark:bg-slate-700 dark:text-white
                   focus:outline-none focus:ring-2 focus:ring-blue-500"
      />

      {/* Search Icon */}
      <span className="absolute left-3 top-2.5 text-gray-400">🔍</span>
    </div>
  );
}

export default SearchBar;
