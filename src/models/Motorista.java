package models;

// Entidade que representa o motorista no sistema de logística
public class Motorista {

    private Integer id;
    private String nome;
    private String cpf;
    private String telefone;
    private String cnh;
    private String tipoCnh;

    public Motorista() {}

    public Motorista(Integer id, String nome, String cpf, String telefone, String cnh, String tipoCnh) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.cnh = cnh;
        this.tipoCnh = tipoCnh;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getCnh() { return cnh; }
    public void setCnh(String cnh) { this.cnh = cnh; }

    public String getTipoCnh() { return tipoCnh; }
    public void setTipoCnh(String tipoCnh) { this.tipoCnh = tipoCnh; }

    // Formatação em texto dos dados do motorista para exibição na tela
    @Override
    public String toString() {
        return "ID: " + id + " | Nome: " + nome + " | CPF: " + cpf +
                " | Tel: " + telefone + " | CNH: " + cnh + " (" + tipoCnh + ")";
    }
}