/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Danton e Edianez
 */
public class ReservaMonitor implements Reserva {

    private int disponibilidade = 1;

    @Override
    public synchronized void ocupar() throws InterruptedException {
        if (disponibilidade == 0) {
            wait();
        }
        disponibilidade = 0;
        notify();
    }

    @Override
    public synchronized void desocupar() throws InterruptedException {
        disponibilidade = 1;
        notify();
    }

    @Override
    public String toString() {
        return "Monitor";
    }

}
