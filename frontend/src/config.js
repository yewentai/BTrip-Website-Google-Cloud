// src/config.js
export const API_BASE_URL = process.env.NODE_ENV === 'production'
    ? 'https://your-production-api.com/api'
    : 'http://localhost:8080/api';