# 🚚 Truck Conecta — Sistema de Gestão Logística

O **Truck Conecta** é um sistema desktop completo desenvolvido em Java para o gerenciamento operacional de transportes e logística. O sistema permite realizar a gestão completa de motoristas, veículos e rotas de transporte, efetuando o cálculo automatizado do valor do frete com base na distância e na faixa de peso da carga.

Este projeto foi desenvolvido como requisito de avaliação da Unidade Curricular de Programação de Aplicativos (**Avaliação AV03**).

---

## 🚀 Tecnologias Utilizadas

- **Linguagem:** Java (JDK 17+)
- **Interface Gráfica (UI):** Java Swing (`JOptionPane` com caixas de seleção dinâmicas)
- **Banco de Dados:** PostgreSQL
- **Conectividade:** JDBC (Java Database Connectivity)
- **Testes Automatizados:** JUnit 5 (Jupiter API & Engine)
- **Arquitetura:** MVC (Model-View-Controller) / Service-DAO

---

## 🛠️ Arquitetura e Organização do Projeto

O código-fonte segue os padrões de boas práticas da orientação a objetos e divisão clara de responsabilidades:

```text
sistema_logistica/
 ├── src/
 │    ├── controllers/   # Orquestração do fluxo entre Visão e Serviço
 │    ├── exceptions/    # Trata exceções personalizadas (LogisticaException)
 │    ├── models/        # Entidades do domínio (Motorista, Veiculo, Rota)
 │    ├── services/      # Regras de negócio, cálculo de frete e persistência SQL
 │    ├── utils/         # Conexão com PostgreSQL e conversores utilitários
 │    ├── views/         # Componentes de interface com o usuário (Swing)
 │    └── Principal.java # Ponto de entrada da aplicação
 └── test/
      └── services/      # Suítes de testes unitários automatizados (JUnit 5)
```
✨ Funcionalidades Principais
Módulo de Motoristas:

Cadastro, listagem, atualização e remoção de motoristas.

Registro de CNH e categoria habilitada (A, B, C, D, E).

Módulo de Veículos:

Gestão da frota (marca, modelo e placa).

Vínculo direto e relacional com o motorista responsável.

Módulo de Rotas & Frete:

Definição de origem, destino e distância em quilômetros.

Associação relacional de motorista e veículo à rota.

Cálculo Automático de Frete por faixa de peso da carga (multiplicação dinâmica de taxa por km).

Resiliência e Erros:

Seleção dinâmica de IDs via menus drop-down para evitar falhas de chave estrangeira (FK).

Tratamento centralizado de exceções (LogisticaException).

🧪 Testes Automatizados (JUnit 5)
A aplicação conta com suítes de testes unitários automatizados cobrindo os serviços do sistema:

MotoristaServiceTest: Valida cadastros, buscas e tratamentos de IDs inexistentes.

VeiculoServiceTest: Valida a persistência de veículos e relacionamentos relacionais.

RotaServiceTest: Testa a criação de rotas e homologa matematicamente o cálculo de frete.

⚙️ Como Executar o Projeto
Pré-requisitos
Java Development Kit (JDK) 17 ou superior instalado.

PostgreSQL rodando localmente na porta 5432.

Biblioteca do driver JDBC do PostgreSQL (postgresql-42.x.x.jar) e JUnit 5 configurados no Classpath.

## ⚙️ Configuração do Banco de Dados

Execute o script SQL abaixo no **DBeaver** (ou na sua ferramenta de preferência) conectado ao banco PostgreSQL para criar a estrutura das tabelas e os dados iniciais de teste:

```sql
-- Tabela Motoristas
CREATE TABLE motoristas (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    telefone VARCHAR(20) NOT NULL,
    cnh VARCHAR(20) NOT NULL,
    tipo_cnh VARCHAR(5) NOT NULL
);

-- Tabela Veiculos (Relacionamento com Motoristas)
CREATE TABLE veiculos (
    id SERIAL PRIMARY KEY,
    marca VARCHAR(50) NOT NULL,
    modelo VARCHAR(50) NOT NULL,
    placa VARCHAR(10) NOT NULL UNIQUE,
    id_motorista INT,
    CONSTRAINT fk_veiculo_motorista FOREIGN KEY (id_motorista) REFERENCES motoristas(id) ON DELETE SET NULL
);

-- Tabela Rotas (Relacionamento com Motoristas e Veiculos)
CREATE TABLE rotas (
    id SERIAL PRIMARY KEY,
    origem VARCHAR(100) NOT NULL,
    destino VARCHAR(100) NOT NULL,
    distancia DOUBLE PRECISION NOT NULL,
    carga VARCHAR(50) NOT NULL,
    valor_frete DOUBLE PRECISION NOT NULL,
    id_motorista INT,
    id_veiculo INT,
    CONSTRAINT fk_rota_motorista FOREIGN KEY (id_motorista) REFERENCES motoristas(id) ON DELETE SET NULL,
    CONSTRAINT fk_rota_veiculo FOREIGN KEY (id_veiculo) REFERENCES veiculos(id) ON DELETE SET NULL
);

-- Garantia das Regras de Chave Estrangeira com ON DELETE SET NULL
ALTER TABLE veiculos 
DROP CONSTRAINT IF EXISTS veiculos_id_motorista_fkey,
ADD CONSTRAINT veiculos_id_motorista_fkey 
FOREIGN KEY (id_motorista) REFERENCES motoristas(id) ON DELETE SET NULL;

ALTER TABLE rotas 
DROP CONSTRAINT IF EXISTS rotas_id_motorista_fkey,
ADD CONSTRAINT rotas_id_motorista_fkey 
FOREIGN KEY (id_motorista) REFERENCES motoristas(id) ON DELETE SET NULL;

-- Carga de Dados Iniciais para Teste
INSERT INTO motoristas (nome, cpf, telefone, cnh, tipo_cnh) VALUES 
('Carlos Andrade', '111.222.333-44', '(47) 98888-1111', 'Possui', 'E'),
('Mariana Souza', '555.666.777-88', '(47) 99999-2222', 'Possui', 'D');

INSERT INTO veiculos (marca, modelo, placa, id_motorista) VALUES 
('Volvo', 'FH 540', 'ABC-1D23', 1),
('Scania', 'R 450', 'XYZ-9E87', 2);

INSERT INTO rotas (origem, destino, distancia, carga, valor_frete, id_motorista, id_veiculo) VALUES 
('Joinville - SC', 'Curitiba - PR', 130.0, 'Até 7.000 kg', 715.00, 1, 1);

```
👥 Equipe de Desenvolvimento
Guilherme Michel

Thiago Silva Duarte

Enzo Rodrigues de Souza

Yago Leandro Pinheiro

Marcelo Gonçalves de Souza
