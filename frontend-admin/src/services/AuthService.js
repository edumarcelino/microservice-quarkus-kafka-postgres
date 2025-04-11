import axios from "axios";

const API_BASE_URL = "http://localhost:8081";

const AuthService = {
  login: async (email, password) => {
    try {
      const response = await axios.post(`${API_BASE_URL}/api/login`, {
        email,
        password
      });

      const token = response.data.token;

      console.log("Token recebido:", token);

      localStorage.setItem("accessToken", token);
      return { token };
    } catch (error) {
      console.error("Erro ao autenticar:", error);
      throw error;
    }
  },

  isAuthenticated: () => {
    const token = localStorage.getItem("accessToken");
    if (!token) return false;

    try {
      const payload = JSON.parse(atob(token.split(".")[1]));
      return payload.exp * 1000 > Date.now();
    } catch (e) {
      console.error("Erro ao validar token:", e);
      return false;
    }
  },

  getToken: () => localStorage.getItem("accessToken"),

  logout: () => localStorage.removeItem("accessToken"),
};

export default AuthService;
