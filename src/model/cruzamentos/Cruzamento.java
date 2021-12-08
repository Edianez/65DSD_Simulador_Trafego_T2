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
import model.vias.Via;

/**
 *
 * @author Danton e Edianez
 */
public abstract class Cruzamento extends Via {

    public Cruzamento(CoordenadaDeMalha coordenadaDeMalha) {
        super(coordenadaDeMalha);
    }

    public List<Via> gerarCaminhoSaidaDeCruzamento() throws InterruptedException {
        List<Via> caminhoASeguir = new ArrayList<>();
        Via aux = this;
        do {
            aux.ocupar();
            caminhoASeguir.add(aux);
            aux = aux.getProximaVia();
        } while (aux instanceof Cruzamento);
        aux.ocupar();
        caminhoASeguir.add(aux);
        return caminhoASeguir;
    }
}
