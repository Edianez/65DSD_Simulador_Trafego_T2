/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Danton e Edianez
 */
public class ReservaMonitor implements Reserva {

    private ReentrantLock lock = new ReentrantLock(true);
    
    @Override
    public void ocupar() throws InterruptedException {
        lock.lock();
    }

    @Override
    public void desocupar() throws InterruptedException {
        lock.unlock();
    }

    @Override
    public String toString() {
        return "Monitor";
    }

    @Override
    public boolean tentaOcupar() throws InterruptedException {
        return lock.tryLock((int)(Math.random() * 300 + 100), TimeUnit.MILLISECONDS);
    }

}
