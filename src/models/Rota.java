package models;

// Entidade que representa a rota de frete e seus relacionamentos
public class Rota {

    private Integer id;
    private String origem;
    private String destino;
    private Double distancia;
    private String carga;
    private Double valorFrete;
    private Motorista motorista; // Relacionamento com Motorista
    private Veiculo veiculo;     // Relacionamento com Veiculo

    public Rota() {}

    public Rota(Integer id, String origem, String destino, Double distancia, String carga, Double valorFrete, Motorista motorista, Veiculo veiculo) {
        this.id = id;
        this.origem = origem;
        this.destino = destino;
        this.distancia = distancia;
        this.carga = carga;
        this.valorFrete = valorFrete;
        this.motorista = motorista;
        this.veiculo = veiculo;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getOrigem() { return origem; }
    public void setOrigem(String origem) { this.origem = origem; }

    public String getDestino() { return destino; }
    public void setDestino(String destino) { this.destino = destino; }

    public Double getDistancia() { return distancia; }
    public void setDistancia(Double distancia) { this.distancia = distancia; }

    public String getCarga() { return carga; }
    public void setCarga(String carga) { this.carga = carga; }

    public Double getValorFrete() { return valorFrete; }
    public void setValorFrete(Double valorFrete) { this.valorFrete = valorFrete; }

    public Motorista getMotorista() { return motorista; }
    public void setMotorista(Motorista motorista) { this.motorista = motorista; }

    public Veiculo getVeiculo() { return veiculo; }
    public void setVeiculo(Veiculo veiculo) { this.veiculo = veiculo; }

    // Formatação dos dados da rota e do valor calculado do frete
    @Override
    public String toString() {
        String nomeMotorista = (motorista != null) ? motorista.getNome() : "Não alocado";
        String placaVeiculo = (veiculo != null) ? veiculo.getPlaca() : "Não alocado";
        return "ID: " + id + " | " + origem + " -> " + destino +
                " (" + distancia + " km) | Carga: " + carga +
                " | Frete: R$ " + String.format("%.2f", valorFrete) +
                " | Motorista: " + nomeMotorista + " | Veículo: " + placaVeiculo;
    }
}