import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import AuthService from "../../services/AuthService";
import "./Login.css";
import "typeface-roboto";

const Login = () => {
  const navigate = useNavigate();
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  const handleLogin = async (e) => {
    e.preventDefault(); // previne recarregamento da página
    try {
      const response = await AuthService.login(username, password);
      const accessToken = response.access_token;

      console.log("Resposta completa:", response);

      if (accessToken) {
        localStorage.setItem("accessToken", accessToken);
        navigate("/index");
      } else {
        setError("Erro ao autenticar.");
      }
    } catch (error) {
      console.error("Erro na autenticação:", error);
      if (error.response) {
        console.error("Resposta da API:", error.response);
      }
      setError("Usuário ou senha inválidos");

    }
  };

  return (
    <div className="login-container">
      <div className="login-content-left">
        <form className="login-form" onSubmit={handleLogin}>
          <img
            src={process.env.PUBLIC_URL + "/images/logo.png"}
            alt="Logo"
            className="logo"
          />
          <h4>Bem-Vindo</h4>
          <h6 className="welcome-message">
            Entre com sua conta de Administrador
          </h6>
          <div className="text-danger form-text mb-2 fw-bold fs-5">{error}</div>
          <div className="form-group">
            <input
              type="text"
              placeholder="Nome de usuário"
              className="form-control fs-6"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              required
            />
          </div>
          <div className="form-group">
            <input
              type="password"
              placeholder="Senha"
              className="form-control fs-6"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </div>
          <button type="submit" className="btn btn-primary">
            Entrar
          </button>
        </form>
      </div>
    </div>
  );
};

export default Login;
