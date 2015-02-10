package br.com.devmedia.agenciaturismo.planejamento;

import org.optaplanner.core.api.score.buildin.hardmediumsoft.HardMediumSoftScore;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Alocador {

    private static final Logger logger = LoggerFactory.getLogger(Alocador.class);

    public AlocacaoPassageiros resolverUsandoForcaBruta(AlocacaoPassageiros problema) {
        return resolver(problema, "solver/bruteForce_solverConfig.xml");
    }

    public AlocacaoPassageiros resolverUsandoTabuSearch(AlocacaoPassageiros problema) {
        return resolver(problema, "solver/tabuSearch_solverConfig.xml");
    }

    private AlocacaoPassageiros resolver(AlocacaoPassageiros problema, String solverConfig) {
        SolverFactory solverFactory = SolverFactory.createFromXmlResource(solverConfig);
        Solver solver = solverFactory.buildSolver();
        solver.solve(problema);
        AlocacaoPassageiros solucao = (AlocacaoPassageiros) solver.getBestSolution();
        logarSolucao(solucao);
        return solucao;
    }

    private void logarSolucao(AlocacaoPassageiros solucao) {
        HardMediumSoftScore score = solucao.getScore();
        logger.info("Melhor score: {}", score);
        logger.info("Solução é viável? {}", score.isFeasible());
        solucao.getGrupos().stream().forEach(g -> logger.info("Grupo=[{}] -> Veículo=[{}]", g, g.getVeiculo()));
    }
}
