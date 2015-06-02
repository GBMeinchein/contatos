package model;

/**
 * Created by maq8 on 28/05/2015.
 */
public class Senha {
    private int codigo;
    private String valor;

    public Senha() {
    }

    public Senha(int codigo, String valor) {
        this.codigo = codigo;
        this.valor = valor;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Senha{" +
                "codigo=" + codigo +
                ", valor='" + valor + '\'' +
                '}';
    }
}
