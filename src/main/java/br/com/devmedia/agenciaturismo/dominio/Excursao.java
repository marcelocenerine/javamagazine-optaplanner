package br.com.devmedia.agenciaturismo.dominio;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Excursao {

    private int codigo;
    private Date data;
    private Destino destino;

    public Excursao(int codigo, Date data, Destino destino) {
        this.codigo = codigo;
        this.data = data;
        this.destino = destino;
    }

    public int getCodigo() {
        return codigo;
    }

    public Date getData() {
        return data;
    }

    public Destino getDestino() {
        return destino;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(codigo);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;

        return this.codigo == ((Excursao) obj).codigo;
    }

    @Override
    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return "codigo=" + codigo + ", data=" + formatter.format(data) + ", destino=[" + destino + "]";
    }
}
