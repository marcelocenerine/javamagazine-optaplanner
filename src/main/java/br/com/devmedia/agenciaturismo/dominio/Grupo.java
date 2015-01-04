package br.com.devmedia.agenciaturismo.dominio;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

@PlanningEntity
public class Grupo {

    public enum TipoGrupo {
        ESTUDANTES, OUTROS
    }

    private Excursao excursao;
    private TipoGrupo tipo;
    private int qtdPassageiros;
    private Veiculo veiculo;

    Grupo() {
    }

    public Grupo(Excursao excursao, TipoGrupo tipo, int qtdPassageiros) {
        this.excursao = excursao;
        this.tipo = tipo;
        this.qtdPassageiros = qtdPassageiros;
    }

    public TipoGrupo getTipo() {
        return tipo;
    }

    public int getQuantidadePassageiros() {
        return qtdPassageiros;
    }

    public Excursao getExcursao() {
        return excursao;
    }

    @PlanningVariable(valueRangeProviderRefs = { "veiculos" })
    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    @Override
    public String toString() {
        return "excursao=[" + excursao + "], tipo=" + tipo + ", qtdPassageiros=" + qtdPassageiros;
    }
}
