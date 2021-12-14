/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;
import model.cruzamentos.Cruzamento;
import model.vias.Via;

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
        join();
    }

    public void andar() throws InterruptedException {
        List<Via> proximasVias = via.getProximasVias();
        if (!proximasVias.isEmpty()) {
            Via proximaVia = proximasVias.get((int) (Math.random() * proximasVias.size()));
            if (proximaVia instanceof Cruzamento) {
                List<Via> caminho = ((Cruzamento) proximaVia).gerarCaminhoSaidaDeCruzamento();
                boolean tentativa = reservarCruzamento(caminho);
                this.via.desocupar();
                for (Via viaCaminho : caminho) {
                    sleep(velocidade / 2);
                    viaCaminho.setVeiculo(this);
                    this.via.setVeiculo(null);
                    this.via = viaCaminho;
                    viaCaminho.desocupar();
                    sleep(velocidade / 2);
                }
            } else {
                irParaVia(proximaVia);
            }
        }
    }

    public void irParaVia(Via proximaVia) throws InterruptedException {
        proximaVia.ocupar();
        this.via.desocupar();
        this.via.setVeiculo(null);
        this.via = proximaVia;
        this.via.setVeiculo(this);

    }

    public Via getVia() {
        return via;
    }

    private boolean reservarCruzamento(List<Via> caminho) throws InterruptedException {
        boolean tentativa;
        List<Via> reservadas = new ArrayList();
        do {
            tentativa = true;
            for (Via viaCaminho : caminho) {
                tentativa = viaCaminho.tentaOcupar();
                if (!tentativa) {
                    for (Via reservada : reservadas) {
                        reservada.desocupar();
                    }
                    break;
                } else {
                    reservadas.add(viaCaminho);
                }
            }
        } while (!tentativa);
        return tentativa;
    }

}
