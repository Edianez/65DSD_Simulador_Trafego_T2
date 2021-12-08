/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import model.vias.ViaBaixo;
import model.vias.ViaCima;
import model.vias.ViaDireita;
import model.vias.ViaEsquerda;

/**
 *
 * @author Danton e Edianez
 */
public class CoordenadaDeMalha {

    private int x, y;
    private Malha malha;
    private Veiculo veiculo;

    public CoordenadaDeMalha(int x, int y, Malha malha) {
        this.x = x;
        this.y = y;
        this.malha = malha;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Malha getMalha() {
        return malha;
    }

    public boolean isBorda() {
        return (x == 0)
                || (y == 0)
                || (x == malha.getLargura() - 1)
                || (y == malha.getAltura() - 1);
    }

    public boolean isBordaSaida() {
        return (x == 0) && malha.getViaNaCoordenada(x, y).getClass() == ViaEsquerda.class
                || (y == 0) && malha.getViaNaCoordenada(x, y).getClass() == ViaCima.class
                || (x == malha.getLargura() - 1) && malha.getViaNaCoordenada(x, y).getClass() == ViaDireita.class
                || (y == malha.getAltura() - 1) && malha.getViaNaCoordenada(x, y).getClass() == ViaBaixo.class;
    }

    public boolean isBordaEntrada() {
        return (x == 0) && malha.getViaNaCoordenada(x, y).getClass() == ViaDireita.class
                || (y == 0) && malha.getViaNaCoordenada(x, y).getClass() == ViaBaixo.class
                || (x == malha.getLargura() - 1) && malha.getViaNaCoordenada(x, y).getClass() == ViaEsquerda.class
                || (y == malha.getAltura() - 1) && malha.getViaNaCoordenada(x, y).getClass() == ViaCima.class;
    }

    public synchronized boolean temVeiculo() {
        return this.veiculo != null;
    }  
    
    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    @Override
    public String toString() {
        return "CoordenadaDeMalha{" + "x=" + x + ", y=" + y + '}';
    }

}
