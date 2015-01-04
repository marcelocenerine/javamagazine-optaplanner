package br.com.devmedia.agenciaturismo.dominio;

public class Destino {

    private final String descricao;
    private final int distancia;

    public Destino(String descricao, int distancia) {
        this.descricao = descricao;
        this.distancia = distancia;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getDistancia() {
        return distancia;
    }

    @Override
    public String toString() {
        return "descricao=" + descricao + ", distancia=" + distancia;
    }
}
