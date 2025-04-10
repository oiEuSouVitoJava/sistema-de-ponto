// src/pages/Login.tsx
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

const Login: React.FC = () => {
  const [email, setEmail] = useState("");
  const [senha, setSenha] = useState("");
  const navigate = useNavigate();

  const handleLogin = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      // Substituir quando a autenticação com JWT estiver pronta
      const response = await fetch("http://localhost:8080/auth/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, senha }),
      });

      if (!response.ok) {
        throw new Error("Login inválido");
      }

      const data = await response.json();
      localStorage.setItem("token", data.token); // salvar o JWT no localStorage
      navigate("/dashboard");
    } catch (err) {
      alert("Falha no login: " + err);
    }
  };

  return (
    <div style={{ maxWidth: "400px", margin: "0 auto", paddingTop: "100px" }}>
      <h2>Login</h2>
      <form onSubmit={handleLogin}>
        <div>
          <label>Email:</label>
          <input
            type="email"
            required
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
        </div>
        <div style={{ marginTop: "10px" }}>
          <label>Senha:</label>
          <input
            type="password"
            required
            value={senha}
            onChange={(e) => setSenha(e.target.value)}
          />
        </div>
        <button type="submit" style={{ marginTop: "20px" }}>
          Entrar
        </button>
      </form>
      <p style={{ marginTop: "10px" }}>
  Não tem conta? <a href="/cadastro">Cadastre-se</a>
</p>

    </div>
    
  );

  
};

export default Login;
