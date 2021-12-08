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
public class CruzamentoBaixoEsquerda extends Cruzamento{

    public CruzamentoBaixoEsquerda(CoordenadaDeMalha coordenadaDeMalha) {
        super(coordenadaDeMalha);
    }

    @Override
    public Via getProximaVia() {
        List<String> direcoes = new ArrayList<>();
        direcoes.add("baixo");        
        direcoes.add("esquerda");
        return super.factoryProximaVia(direcoes);
    }
    
}
