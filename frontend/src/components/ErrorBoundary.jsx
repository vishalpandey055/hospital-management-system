import React from "react";

class ErrorBoundary extends React.Component {

  constructor(props) {
    super(props);
    this.state = { hasError: false };
  }

  static getDerivedStateFromError() {
    return { hasError: true };
  }

  componentDidCatch(error, errorInfo) {
    console.error("Error:", error, errorInfo);
  }

  render() {

    if (this.state.hasError) {
      return (
        <div className="flex flex-col items-center justify-center min-h-screen bg-gray-100 dark:bg-slate-900">

          <h1 className="text-3xl font-bold mb-4 text-red-500">
            Something went wrong ⚠️
          </h1>

          <button
            onClick={() => window.location.reload()}
            className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
          >
            Reload Page
          </button>

        </div>
      );
    }

    return this.props.children;
  }
}

export default ErrorBoundary;