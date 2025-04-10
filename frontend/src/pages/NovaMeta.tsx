// src/pages/NovaMeta.tsx
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

const NovaMeta: React.FC = () => {
  const [nome, setNome] = useState("");
  const [descricao, setDescricao] = useState("");
  const navigate = useNavigate();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      const token = localStorage.getItem("token");
      await axios.post(
        "http://localhost:8080/metas",
        { nome, descricao },
        { headers: { Authorization: `Bearer ${token}` } }
      );

      alert("Meta criada com sucesso!");
      navigate("/dashboard");
    } catch (err) {
      alert("Erro ao criar meta.");
      console.error(err);
    }
  };

  return (
    <div style={{ maxWidth: "400px", margin: "0 auto", paddingTop: "40px" }}>
      <h2>Criar Nova Meta</h2>
      <form onSubmit={handleSubmit}>
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
          <label>Descrição:</label>
          <input
            type="text"
            value={descricao}
            onChange={(e) => setDescricao(e.target.value)}
          />
        </div>
        <button type="submit" style={{ marginTop: "20px" }}>
          Criar
        </button>
      </form>
    </div>
  );
};

export default NovaMeta;
