/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.cruzamentos;

import java.util.ArrayList;
import java.util.List;
import model.CoordenadaDeMalha;
import model.Malha;
import model.Reserva;
import model.ReservaSemaforo;
import model.vias.Via;
import model.vias.ViaReta;

/**
 *
 * @author Danton e Edianez
 */
public abstract class Cruzamento extends Via {

    public Cruzamento(CoordenadaDeMalha coordenadaDeMalha) {
        super(coordenadaDeMalha);
    }

    public List<List<Via>> calculaSaidasCruzamento() throws InterruptedException {
        List<List<Via>> caminhos = new ArrayList<>();

        List<Via> inicio = new ArrayList<>();
        inicio.add(this);

        calculaSaidasCruzamentoRecursive(caminhos, inicio, new ArrayList<Via>(), this);

        return caminhos;
    }

    public void calculaSaidasCruzamentoRecursive(List<List<Via>> caminhos, List<Via> caminhoPercorrido, List<Via> viasVisitadas, Via v) throws InterruptedException {
        if(v instanceof ViaReta) {
            List<Via> copy = new ArrayList<>(caminhoPercorrido);
            caminhos.add(copy);
        } else {
            if (!viasVisitadas.contains(v)) {
                viasVisitadas.add(v);
                for (Via via : v.getProximasVias()) {
                    List<Via> copy = new ArrayList<>(caminhoPercorrido);
                    copy.add(via);
                    calculaSaidasCruzamentoRecursive(caminhos, copy, viasVisitadas, via);
                }
            }
        }
    } 

    public List<Via> gerarCaminhoSaidaDeCruzamento() throws InterruptedException {
        List<List<Via>> caminhosASeguir = calculaSaidasCruzamento();
        List<Via> caminhoASeguir = caminhosASeguir.get((int) (Math.random() * caminhosASeguir.size()));
        return caminhoASeguir;
    }
    
    @Override
    protected List<Via> factoryProximasVias(List<String> direcoes) {
        List<Via> proximas = new ArrayList<>();
        for (String direcao : direcoes) {
            Via via = null;
            switch (direcao) {
                case "cima": {
                    via = coordenadaDeMalha.getMalha().getViaNaCoordenada(getX(), getY() - 1);
                    break;
                }
                case "baixo": {
                    via = coordenadaDeMalha.getMalha().getViaNaCoordenada(getX(), getY() + 1);
                    break;
                }
                case "direita": {
                    via = coordenadaDeMalha.getMalha().getViaNaCoordenada(getX() + 1, getY());
                    break;
                }
                case "esquerda": {
                    via = coordenadaDeMalha.getMalha().getViaNaCoordenada(getX() - 1, getY());
                    break;
                }
                default: {
                    via = null;
                }
            }
            if (via != null) {
                proximas.add(via);
            }
        }
        return proximas;     
    }
}
