package br.com.devmedia.agenciaturismo.planejamento;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.Solution;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardmediumsoft.HardMediumSoftScore;

import br.com.devmedia.agenciaturismo.dominio.Grupo;
import br.com.devmedia.agenciaturismo.dominio.Veiculo;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@PlanningSolution
@XStreamAlias("alocacaoPassageiros")
public class AlocacaoPassageiros implements Solution<HardMediumSoftScore> {

    @XStreamImplicit(itemFieldName = "grupo")
    private List<Grupo> grupos;

    @XStreamImplicit(itemFieldName = "veiculo")
    private List<Veiculo> veiculos;

    private HardMediumSoftScore score;

    AlocacaoPassageiros() {
    }

    public AlocacaoPassageiros(List<Grupo> grupos, List<Veiculo> veiculos) {
        this.grupos = grupos;
        this.veiculos = veiculos;
    }

    @Override
    public HardMediumSoftScore getScore() {
        return score;
    }

    @Override
    public void setScore(HardMediumSoftScore score) {
        this.score = score;
    }

    @ValueRangeProvider(id = "veiculos")
    public List<Veiculo> getVeiculos() {
        return veiculos;
    }

    @PlanningEntityCollectionProperty
    public List<Grupo> getGrupos() {
        return grupos;
    }

    @Override
    public Collection<? extends Object> getProblemFacts() {
        List<Object> facts = new ArrayList<>();
        facts.addAll(veiculos);
        return facts;
    }
}
