/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import model.vias.Via;

/**
 *
 * @author Danton e Edianez
 */
public class Malha {

    private Via[][] vias;
    private int maxVeiculos;
    private List<Veiculo> veiculos;

    public Malha() {
        veiculos = new ArrayList<>();
    }

    public int getMaxVeiculos() {
        return maxVeiculos;
    }

    public void setMaxVeiculos(int maxVeiculos) {
        this.maxVeiculos = maxVeiculos;
    }

    public void setVias(Via[][] vias) {
        this.vias = vias;
    }

    public void transformaViasEm(String classs) {
        for (int i = 0; i < vias.length; i++) {
            for (int j = 0; j < vias[0].length; j++) {
                if (vias[i][j] != null) {
                    vias[i][j].transformaTipoReservaEm(classs);
                }
            }
        }
    }

    public void inserirVeiculo() {
        List<Via> acessos = getViasDeAcesso();
        Via via = acessos.get((int) (Math.random() * acessos.size()));
        if (veiculos.size() < maxVeiculos) {
            Veiculo v = new Veiculo((long) (Math.random() * 600 + 200), via);
            via.setVeiculo(v);
            veiculos.add(v);
            v.start();
        }
    }

    public void encerrarSimulacao() {
        for (Veiculo v : veiculos) {
            try {
                v.join();
            } catch (InterruptedException ex) {
                System.out.println("Erro ao finalizar algum veículo");
            }
        }
    }

    public List<Via> getViasDeAcesso() {
        List<Via> acessos = new ArrayList<>();
        for (int i = 0; i < vias.length; i++) {
            for (int j = 0; j < vias[0].length; j++) {
                if (vias[i][j] != null
                        && vias[i][j].getCoordenadaDeMalha().isBordaEntrada()) {
                    acessos.add(vias[i][j]);
                }
            }
        }
        return acessos;
    }

    public Via getViaNaCoordenada(int x, int y) {
        return vias[y][x];
    }

    public int getLargura() {
        return vias[0].length;
    }

    public int getAltura() {
        return vias.length;
    }

    public List<Veiculo> getVeiculos() {
        return veiculos;
    }
}
