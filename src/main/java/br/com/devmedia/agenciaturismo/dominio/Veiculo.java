package br.com.devmedia.agenciaturismo.dominio;

public class Veiculo {

    private String placa;
    private int capacidade;
    private int kmPorLitro;

    public Veiculo(String placa, int capacidade, int kmPorLitro) {
        this.placa = placa;
        this.capacidade = capacidade;
        this.kmPorLitro = kmPorLitro;
    }

    public String getPlaca() {
        return placa;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public int getKmPorLitro() {
        return kmPorLitro;
    }

    @Override
    public int hashCode() {
        return placa.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;

        return this.placa.equals(((Veiculo) obj).placa);
    }

    @Override
    public String toString() {
        return "placa=" + placa + ", capacidade=" + capacidade + ", kmPorLitro=" + kmPorLitro;
    }
}
