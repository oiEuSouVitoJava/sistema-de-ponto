// src/pages/Cadastro.tsx
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

const Cadastro: React.FC = () => {
  const [nome, setNome] = useState("");
  const [email, setEmail] = useState("");
  const [senha, setSenha] = useState("");
  const navigate = useNavigate();

  const handleCadastro = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      const response = await fetch("http://localhost:8080/auth/register", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ nome, email, senha }),
      });

      if (!response.ok) {
        throw new Error("Erro ao cadastrar");
      }

      alert("Cadastro realizado com sucesso!");
      navigate("/");
    } catch (err) {
      alert("Falha no cadastro: " + err);
    }
  };

  return (
    <div style={{ maxWidth: "400px", margin: "0 auto", paddingTop: "100px" }}>
      <h2>Cadastro</h2>
      <form onSubmit={handleCadastro}>
        <div>
          <label>Nome:</label>
          <input
            type="text"
            required
            value={nome}
            onChange={(e) => setNome(e.target.value)}
          />
        </div>
        <div style={{ marginTop: "10px" }}>
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
          Cadastrar
        </button>
      </form>
    </div>
  );
};

export default Cadastro;
