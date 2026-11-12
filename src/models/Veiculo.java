package models;

// Entidade que representa o veículo e seu vínculo com o motorista responsável
public class Veiculo {

    private Integer id;
    private String marca;
    private String modelo;
    private String placa;
    private Motorista motorista; // Relacionamento com a classe Motorista

    public Veiculo() {}

    public Veiculo(Integer id, String marca, String modelo, String placa, Motorista motorista) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.placa = placa;
        this.motorista = motorista;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }

    public Motorista getMotorista() { return motorista; }
    public void setMotorista(Motorista motorista) { this.motorista = motorista; }

    // Formatação dos dados do veículo e do motorista responsável
    @Override
    public String toString() {
        String nomeMotorista = (motorista != null) ? motorista.getNome() : "Sem motorista";
        return "ID: " + id + " | Marca: " + marca + " | Modelo: " + modelo +
                " | Placa: " + placa + " | Responsável: " + nomeMotorista;
    }
}