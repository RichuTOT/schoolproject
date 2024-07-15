import axios from 'axios';

const instance = axios.create({
    baseURL: 'http://localhost:8088', // Spring Boot 后端地址
    withCredentials: true,
    headers: {
        'Content-Type': 'application/json'
    }
});

export default instance;
