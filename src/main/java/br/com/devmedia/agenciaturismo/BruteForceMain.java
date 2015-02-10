package br.com.devmedia.agenciaturismo;

import static br.com.devmedia.agenciaturismo.dominio.Grupo.TipoGrupo.ESTUDANTES;
import static br.com.devmedia.agenciaturismo.dominio.Grupo.TipoGrupo.OUTROS;
import static java.util.Arrays.asList;

import java.util.Date;

import br.com.devmedia.agenciaturismo.dominio.Destino;
import br.com.devmedia.agenciaturismo.dominio.Excursao;
import br.com.devmedia.agenciaturismo.dominio.Grupo;
import br.com.devmedia.agenciaturismo.dominio.Veiculo;
import br.com.devmedia.agenciaturismo.planejamento.AlocacaoPassageiros;
import br.com.devmedia.agenciaturismo.planejamento.Alocador;

public class BruteForceMain {

    public static void main(String[] args) {
        AlocacaoPassageiros problema = construirProblema();
        Alocador alocador = new Alocador();
        alocador.resolverUsandoForcaBruta(problema);
    }

    private static AlocacaoPassageiros construirProblema() {
        Destino d1 = new Destino("São Paulo", 600);
        Destino d2 = new Destino("Curitiba", 300);

        Excursao ex1 = new Excursao(1, new Date(), d1);
        Excursao ex2 = new Excursao(2, new Date(), d2);

        Grupo g01 = new Grupo(ex1, OUTROS, 15);
        Grupo g02 = new Grupo(ex1, OUTROS, 15);
        Grupo g03 = new Grupo(ex1, ESTUDANTES, 20);
        Grupo g04 = new Grupo(ex1, ESTUDANTES, 10);
        Grupo g05 = new Grupo(ex2, OUTROS, 30);
        Grupo g06 = new Grupo(ex2, ESTUDANTES, 15);
        Grupo g07 = new Grupo(ex2, ESTUDANTES, 15);

        Veiculo v1 = new Veiculo("V1", 60, 3);
        Veiculo v2 = new Veiculo("V2", 60, 4);
        Veiculo v3 = new Veiculo("V3", 30, 7);
        Veiculo v4 = new Veiculo("V4", 30, 9);
        Veiculo v5 = new Veiculo("V5", 30, 9);

        return new AlocacaoPassageiros(asList(g01, g02, g03, g04, g05, g06, g07), 
                asList(v1, v2, v3, v4, v5));
    }
}
