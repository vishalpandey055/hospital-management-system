function Pagination({ page = 0, totalPages = 1, onPageChange }) {
  if (totalPages <= 1) return null;

  return (
    <div className="flex justify-center items-center gap-4 mt-6">
      {/* Prev Button */}
      <button
        disabled={page === 0}
        onClick={() => onPageChange(Math.max(page - 1, 0))}
        className="px-4 py-1 rounded bg-gray-200 dark:bg-slate-700 
                   hover:bg-gray-300 dark:hover:bg-slate-600
                   disabled:opacity-40 disabled:cursor-not-allowed
                   transition"
      >
        ← Prev
      </button>

      {/* Page Info */}
      <span className="text-sm font-medium text-gray-700 dark:text-gray-200">
        Page {page + 1} of {totalPages}
      </span>

      {/* Next Button */}
      <button
        disabled={page >= totalPages - 1}
        onClick={() => onPageChange(Math.min(page + 1, totalPages - 1))}
        className="px-4 py-1 rounded bg-gray-200 dark:bg-slate-700 hover:bg-gray-300 dark:hover:bg-slate-600 transition disabled:opacity-40 disabled:cursor-not-allowed"
      >
        Next →
      </button>
    </div>
  );
}

export default Pagination;
