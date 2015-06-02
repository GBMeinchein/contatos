package model;

/**
 * Created by maq8 on 07/05/2015.
 */
public class Contato {

    private int codigo;
    private String razao;
    private String nome;
    private String telefone;
    private String celular;
    private String email;
    private String origem;

    public Contato(){

    }

    public Contato(int codigo, String razao, String nome, String telefone, String celular, String email, String origem) {
        this.codigo = codigo;
        this.razao = razao;
        this.nome = nome;
        this.telefone = telefone;
        this.celular = celular;
        this.email = email;
        this.origem = origem;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getRazao() {
        return razao;
    }

    public void setRazao(String razao) {
        this.razao = razao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    @Override
    public String toString() {
        return "Contato{" +
                "codigo=" + codigo +
                ", razao='" + razao + '\'' +
                ", nome='" + nome + '\'' +
                ", telefone='" + telefone + '\'' +
                '}';
    }
}
