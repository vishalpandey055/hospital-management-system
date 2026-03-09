// import axios from "axios";
// 
// const api = axios.create({
//   baseURL: "http://localhost:8080/api",
// });
// 
// api.interceptors.request.use((config) => {
// 
//   const token = localStorage.getItem("token");
// 
//   if (token) {
//     config.headers.Authorization = `Bearer ${token}`;
//   }
// 
//   return config;
// });
// 
// export default api;
import axios from "axios";
import toast from "react-hot-toast";

const api = axios.create({
  baseURL: import.meta.env.VITE_API_URL || "http://localhost:8080/api",
  headers: {
    "Content-Type": "application/json",
  },
});

// REQUEST INTERCEPTOR
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("token");

    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }

    return config;
  },
  (error) => Promise.reject(error)
);

// RESPONSE INTERCEPTOR
api.interceptors.response.use(
  (response) => response,
  (error) => {
    const status = error?.response?.status;
    const message = error?.response?.data?.message;

    console.error("API ERROR:", message || error.message);

    if (status === 401) {
      toast.error("Session expired. Please login again.");

      localStorage.removeItem("token");

      window.location.href = "/login";
    }

    if (status === 403) {
      toast.error("You do not have permission for this action.");
    }

    if (status === 500) {
      toast.error("Server error. Please try again later.");
    }

    return Promise.reject(error);
  }
);

export default api;