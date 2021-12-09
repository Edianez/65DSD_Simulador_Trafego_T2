/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.vias;

import java.util.ArrayList;
import java.util.List;
import model.CoordenadaDeMalha;
import model.Malha;

/**
 *
 * @author Danton e Edianez
 */
public class ViaBaixo extends ViaReta{

    public ViaBaixo(CoordenadaDeMalha coordenadaDeMalha) {
        super(coordenadaDeMalha);
    }

    @Override
    public List<Via> getProximasVias() {
        List<String> direcoes = new ArrayList<>();
        direcoes.add("baixo");
        direcoes.add("diagonal-baixo-esquerda");
        direcoes.add("diagonal-baixo-direita");

        return super.factoryProximasVias(direcoes);
    }
    
}
