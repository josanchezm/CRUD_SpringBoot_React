import axios from 'axios';

// URL base de las peticiones
export default axios.create({
    baseURL: 'http://localhost:8080/' 
});