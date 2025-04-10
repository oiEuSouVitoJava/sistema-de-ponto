import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

// Função utilitária para extrair e-mail do JWT
function extractEmailFromToken(token: string | null): string | null {
  if (!token) return null;

  try {
    const payload = JSON.parse(atob(token.split(".")[1]));
    return payload.sub;
  } catch (e) {
    console.error("Erro ao decodificar token:", e);
    return null;
  }
}

interface Meta {
  id: number;
  nome: string;
  descricao: string;
  alvoAlcancado: boolean;
  usuario: {
    id: number;
    email: string;
    nome: string;
  };
}

interface Mensagem {
  id: number;
  conteudo: string;
  dataHora: string;
  usuario: {
    nome: string;
  };
}

const Dashboard: React.FC = () => {
  const [metas, setMetas] = useState<Meta[]>([]);
  const [mensagens, setMensagens] = useState<Mensagem[]>([]);
  const [novaMensagem, setNovaMensagem] = useState("");
  const navigate = useNavigate();

  const token = localStorage.getItem("token");
  const usuarioEmail = extractEmailFromToken(token);

  useEffect(() => {
    const fetchMetas = async () => {
      try {
        const response = await axios.get("http://localhost:8080/metas", {
          headers: { Authorization: `Bearer ${token}` },
        });
        setMetas(response.data);
      } catch (error) {
        console.error("Erro ao buscar metas:", error);
      }
    };

    const fetchMensagens = async () => {
      try {
        const response = await axios.get("http://localhost:8080/mural", {
          headers: { Authorization: `Bearer ${token}` },
        });
        setMensagens(response.data);
      } catch (error) {
        console.error("Erro ao buscar mensagens do mural:", error);
      }
    };

    fetchMetas();
    fetchMensagens();
  }, [token]);

  const toggleMetaStatus = async (metaId: number, currentStatus: boolean) => {
    try {
      await axios.put(
        `http://localhost:8080/metas/${metaId}/status`,
        { alvoAlcancado: !currentStatus },
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      );

      setMetas((prev) =>
        prev.map((m) =>
          m.id === metaId ? { ...m, alvoAlcancado: !currentStatus } : m
        )
      );
    } catch (error) {
      console.error("Erro ao atualizar status da meta:", error);
      alert("Você só pode alterar o status das suas próprias metas.");
    }
  };

  const postarMensagem = async () => {
    if (!novaMensagem.trim()) return;

    try {
      await axios.post("http://localhost:8080/mural", novaMensagem, {
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
      });

      setNovaMensagem("");
      // Atualiza mural após o post
      const response = await axios.get("http://localhost:8080/mural", {
        headers: { Authorization: `Bearer ${token}` },
      });
      setMensagens(response.data);
    } catch (error) {
      console.error("Erro ao postar mensagem:", error);
    }
  };

  return (
    <div style={{ padding: "20px" }}>
      <h1>Dashboard de Metas</h1>
      <button onClick={() => navigate("/nova-meta")}>Nova Meta</button>

      {metas.map((meta) => (
        <div
          key={meta.id}
          style={{
            marginBottom: "20px",
            padding: "10px",
            border: "1px solid #ccc",
          }}
        >
          <h3>{meta.nome}</h3>
          <p><strong>Descrição:</strong> {meta.descricao}</p>
          <p><strong>Criada por:</strong> {meta.usuario?.nome}</p>
          <p>
            <strong>Status:</strong>{" "}
            {meta.alvoAlcancado ? "✅ Alcançada" : "⏳ Em andamento"}
          </p>

          {meta.usuario?.email === usuarioEmail && (
            <button onClick={() => toggleMetaStatus(meta.id, meta.alvoAlcancado)}>
              {meta.alvoAlcancado ? "Desmarcar como alcançada" : "Marcar como alcançada"}
            </button>
          )}
        </div>
      ))}

      <h2 style={{ marginTop: "40px" }}>🧱 Mural de Mensagens</h2>
      <div style={{ marginBottom: "20px" }}>
        <textarea
          value={novaMensagem}
          onChange={(e) => setNovaMensagem(e.target.value)}
          placeholder="Escreva algo motivador para o mural..."
          style={{ width: "100%", height: "80px", marginBottom: "10px" }}
        />
        <button onClick={postarMensagem}>Postar no mural</button>
      </div>

      {mensagens.map((msg) => (
        <div
          key={msg.id}
          style={{
            marginBottom: "15px",
            padding: "10px",
            border: "1px solid #ccc",
          }}
        >
          <p><strong>{msg.usuario?.nome || "Anônimo"}:</strong> {msg.conteudo}</p>
          <small>{new Date(msg.dataHora).toLocaleString()}</small>
        </div>
      ))}
    </div>
  );
};

export default Dashboard;
