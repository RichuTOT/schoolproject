// apiClient.js
import axios from 'axios';

const apiClient = axios.create({
  baseURL: 'http://localhost:8088/api',
  withCredentials: true,
  headers: {
    'Content-Type': 'application/json',
  },
});

export default apiClient;
