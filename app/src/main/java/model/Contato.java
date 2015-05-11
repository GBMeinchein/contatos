package model;

/**
 * Created by maq8 on 07/05/2015.
 */
public class Contato {

    private int codigo;
    private String razao;
    private String nome;
    private String telefone;

    public Contato(){

    }

    public Contato(int codigo, String razao, String nome, String telefone) {
        this.codigo = codigo;
        this.razao = razao;
        this.nome = nome;
        this.telefone = telefone;
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
