package br.com.devmedia.agenciaturismo.planejamento.score;

import static br.com.devmedia.agenciaturismo.dominio.Grupo.TipoGrupo.ESTUDANTES;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.optaplanner.core.api.score.buildin.hardmediumsoft.HardMediumSoftScore;
import org.optaplanner.core.impl.score.director.easy.EasyScoreCalculator;

import br.com.devmedia.agenciaturismo.dominio.Excursao;
import br.com.devmedia.agenciaturismo.dominio.Grupo;
import br.com.devmedia.agenciaturismo.dominio.Grupo.TipoGrupo;
import br.com.devmedia.agenciaturismo.dominio.Veiculo;
import br.com.devmedia.agenciaturismo.planejamento.AlocacaoPassageiros;

/**
 * Bônus: implementação da interface {@link EasyScoreCalculator} para cálculo do score. É uma alternativa 
 * às regras do Drools implementadas no arquivo 'alocacaoPassageirosScoreRules.drl'. Para ver seu funcionamento,
 * edite o arquivo de configuração do solver (parte 2 do artigo). 
 */
public class AlocacaoPassageirosScoreCalculator implements EasyScoreCalculator<AlocacaoPassageiros> {

    @Override
    public HardMediumSoftScore calculateScore(AlocacaoPassageiros solution) {
        // Verifica se todas as planning variables foram inicializadas
        if (contemVariaveisNaoInicializadas(solution)) 
            return HardMediumSoftScore.valueOf(0, 0, 0);
        
        // Agrupa os grupos de passageiros por veículo
        Map<Veiculo, List<Grupo>> gruposPorVeiculos = agruparGruposPorVeiculos(solution);

        // Hard - Cada veiculo deve transportar passageiros apenas de uma unica excursao
        int excursoesMisturadas = calcularQtdExcursoesMisturadasPorVeiculo(gruposPorVeiculos);

        // Hard - Todos os integrantes de um mesmo grupo devem viajar juntos
        int passageirosExcedentes = calcularPassageirosExcedentes(gruposPorVeiculos);

        // Medium - Custo com consumo de combustivel deve ser reduzido
        int quantidadeCombustivel = calcularConsumoCombustivel(solution);

        // Soft - Grupos de estudantes devem ser alocados em veiculos exclusivos quando possivel
        int veiculosComGruposEstudantesMisturados = calcularQtdVeiculosComGruposEstudantesMisturados(gruposPorVeiculos);

        // Cálculo do score
        int hard = Math.negateExact(excursoesMisturadas + passageirosExcedentes);
        int medium = Math.negateExact(quantidadeCombustivel);
        int soft = Math.negateExact(veiculosComGruposEstudantesMisturados);

        return HardMediumSoftScore.valueOf(hard, medium, soft);
    }

    private boolean contemVariaveisNaoInicializadas(AlocacaoPassageiros solution) {
        return solution.getGrupos().stream().anyMatch(g -> isNull(g.getVeiculo()));
    }
    
    private Map<Veiculo, List<Grupo>> agruparGruposPorVeiculos(AlocacaoPassageiros solution) {
        return solution.getGrupos().stream().collect(groupingBy(Grupo::getVeiculo));
    }

    private int calcularQtdExcursoesMisturadasPorVeiculo(Map<Veiculo, List<Grupo>> gruposPorVeiculo) {
        return gruposPorVeiculo.values().stream().mapToInt(this::calcularNumeroExcursoes).filter(qtd -> qtd > 1).sum();
    }

    private int calcularNumeroExcursoes(List<Grupo> grupos) {
        return (int) grupos.stream().map(g -> g.getExcursao()).distinct().count();
    }
    
    private int calcularPassageirosExcedentes(Map<Veiculo, List<Grupo>> gruposPorVeiculo) {
        return gruposPorVeiculo.keySet().stream()
                .mapToInt(veiculo -> calcularExcedentePorVeiculo(veiculo, gruposPorVeiculo.get(veiculo))).sum();
    }

    private int calcularExcedentePorVeiculo(Veiculo veiculo, List<Grupo> grupos) {
        int qtdPassageiros = somaQuantidadePassageiros(grupos);
        int excedente = Math.min(veiculo.getCapacidade() - qtdPassageiros, 0);
        return Math.negateExact(excedente);
    }

    private int somaQuantidadePassageiros(List<Grupo> grupos) {
        return grupos.stream().mapToInt(Grupo::getQuantidadePassageiros).sum();
    }

    private int calcularConsumoCombustivel(AlocacaoPassageiros solution) {
        Map<Excursao, Set<Veiculo>> veiculosPorExcursao = solution.getGrupos().stream()
                .collect(groupingBy(Grupo::getExcursao, mapping(Grupo::getVeiculo, toSet())));
        
        int quantidadeCombustivel = veiculosPorExcursao.keySet().stream()
                .mapToInt(excursao -> calcularConsumoPorExcursao(excursao, veiculosPorExcursao.get(excursao))).sum();
        return quantidadeCombustivel;
    }
    
    private int calcularConsumoPorExcursao(Excursao excursao, Set<Veiculo> veiculos) {
        int distancia = excursao.getDestino().getDistancia();
        return veiculos.stream().mapToInt(veiculo -> distancia / veiculo.getKmPorLitro()).sum();
    }

    private int calcularQtdVeiculosComGruposEstudantesMisturados(Map<Veiculo, List<Grupo>> gruposPorVeiculo) {
        return (int) gruposPorVeiculo.keySet().stream()
                .filter(veiculo -> existeGruposEstudantesMisturados(gruposPorVeiculo.get(veiculo))).count();
    }

    private boolean existeGruposEstudantesMisturados(List<Grupo> grupos) {
        List<TipoGrupo> tipos = grupos.stream().map(Grupo::getTipo).distinct().collect(toList());

        return tipos.size() > 1 && tipos.contains(ESTUDANTES);
    }
}