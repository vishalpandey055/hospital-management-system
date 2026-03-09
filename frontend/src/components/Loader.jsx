function Loader() {
  return (
    <div className="flex justify-center items-center min-h-[40vh]">
      <div
        className="h-12 w-12 border-4 border-blue-600 border-t-transparent rounded-full animate-spin"
        aria-label="Loading"
      ></div>
    </div>
  );
}

export default Loader;