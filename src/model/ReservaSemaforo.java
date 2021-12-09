/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.concurrent.Semaphore;

/**
 *
 * @author Danton e Edianez
 */
public class ReservaSemaforo extends Semaphore implements Reserva {

    public ReservaSemaforo() {
        super(1);
    }

    @Override
    public void ocupar() throws InterruptedException {
        acquire();
    }

    @Override
    public void desocupar() throws InterruptedException {
        release();
    }

    @Override
    public String toString() {
        return "Semáforo";
    }
}
