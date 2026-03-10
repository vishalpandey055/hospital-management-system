
import axios from "axios";
import toast from "react-hot-toast";

const api = axios.create({
baseURL: "http://localhost:8080/api",
headers: {
"Content-Type": "application/json",
},
});

// Add token automatically
api.interceptors.request.use((config) => {
const token = localStorage.getItem("token");

if (token) {
config.headers.Authorization = `Bearer ${token}`;
}

return config;
});

// Handle common errors
api.interceptors.response.use(
(response) => response,
(error) => {
const status = error?.response?.status;

if (status === 401) {
  toast.error("Please login again");
  localStorage.removeItem("token");
  window.location.href = "/login";
}

if (status === 403) {
  toast.error("Access denied");
}

if (status === 500) {
  toast.error("Server error");
}

return Promise.reject(error);


}
);

export default api;
