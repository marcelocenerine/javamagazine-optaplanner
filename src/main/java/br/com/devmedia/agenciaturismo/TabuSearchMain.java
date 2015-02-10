package br.com.devmedia.agenciaturismo;

import br.com.devmedia.agenciaturismo.planejamento.AlocacaoPassageiros;
import br.com.devmedia.agenciaturismo.planejamento.Alocador;

import com.thoughtworks.xstream.XStream;

public class TabuSearchMain {

    public static void main(String[] args) {
        AlocacaoPassageiros problema = construirProblema();
        Alocador alocador = new Alocador();
        alocador.resolverUsandoTabuSearch(problema);
    }

    private static AlocacaoPassageiros construirProblema() {
        XStream xStream = new XStream();
        xStream.setMode(XStream.NO_REFERENCES);
        xStream.processAnnotations(AlocacaoPassageiros.class);
        return (AlocacaoPassageiros) xStream.fromXML(TabuSearchMain.class.getResourceAsStream("/data/0050grupos_0010veiculos.xml"));
    }
}
