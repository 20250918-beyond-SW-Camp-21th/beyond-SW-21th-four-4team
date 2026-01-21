import axios from 'axios';

const api = axios.create({
    baseURL: 'http://localhost:8080/api/v1', // Usage of proxy in vite.config.js
});

// Interceptor to add Authorization header if token exists
api.interceptors.request.use(config => {
    const token = localStorage.getItem('accessToken');
    if (token) {
        config.headers.Authorization = token; // The backend might expect "Bearer " or just the token.
        // AuthController.logout expects: @RequestHeader("Authorization") String authorization
        // Usually standard is "Bearer <token>".
        // Let's check AuthController again or assume standard.
        // UserController.logout takes String authorization.
        // Let's check TokenResponseDto. It just has accessToken.
        // Typically Spring Security expects "Bearer ".
        // If I look at UserController:
        // userService.logout(authorization);
        // Usually it's safer to send "Bearer " + token.
        // But if the backend just takes the string, it might be parsing it.
        // Let's assume standard Bearer token for now.
        // Wait, let me check if I can see any existing usage? No existing frontend code uses it.
        // Backend: CustomUser user argument in controllers suggests a filter is processing it.
        // Standard JWT filter usually expects Bearer.
        if (!token.startsWith('Bearer ')) {
            config.headers.Authorization = `Bearer ${token}`;
        } else {
            config.headers.Authorization = token;
        }
    }
    return config;
});

export default api;
