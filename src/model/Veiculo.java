/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.cruzamentos.Cruzamento;
import model.vias.Via;
import model.vias.ViaReta;

/**
 *
 * @author Danton e Edianez
 */
public class Veiculo extends Thread {

    private long velocidade;
    private Via via;

    public Veiculo(long velocidade, Via via) {
        super();
        this.velocidade = velocidade;
        this.via = via;
    }

    @Override
    public void run() {
        try {
            via.ocupar();
            do {
                sleep(velocidade / 2);
                andar();
                sleep(velocidade / 2);
            } while (!via.isBordaSaida());
            encerrar();
        } catch (InterruptedException ex) {
            System.out.println("Carro bateu");
        }
    }

    public void encerrar() throws InterruptedException {
        via.desocupar();
        via.setVeiculo(null);
        via.getCoordenadaDeMalha().getMalha().getVeiculos().remove(this);
        this.via = null;
    }

    public void andar() throws InterruptedException {
        List<Via> proximasVias = via.getProximasVias();
        if (!proximasVias.isEmpty()) {
            Via proximaVia = proximasVias.get((int) (Math.random() * proximasVias.size()));
            if (proximaVia instanceof Cruzamento) {
                List<Via> caminho = ((Cruzamento) proximaVia).gerarCaminhoSaidaDeCruzamento();
                for (Via viaCaminho : caminho) {
                    viaCaminho.ocupar();
                }
                for (Via viaCaminho : caminho) {
                    sleep(velocidade / 2);
                    irParaVia(viaCaminho);
                    sleep(velocidade / 2);
                }
            } else {
                proximaVia.ocupar();
                irParaVia(proximaVia);
            }
        }
    }

    public void irParaVia(Via proximaVia) throws InterruptedException {
        this.via.desocupar();
        this.via.setVeiculo(null);
        this.via = proximaVia;
        this.via.setVeiculo(this);

    }

    public Via getVia() {
        return via;
    }

}
