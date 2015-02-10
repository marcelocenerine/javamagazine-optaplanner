package br.com.devmedia.agenciaturismo.planejamento.score;

import java.util.Comparator;

import br.com.devmedia.agenciaturismo.dominio.Grupo;

public class GrupoDifficultyComparator implements Comparator<Grupo> {

    @Override
    public int compare(Grupo g1, Grupo g2) {
        return Integer.compare(g1.getQuantidadePassageiros(), g2.getQuantidadePassageiros());
    }
}
