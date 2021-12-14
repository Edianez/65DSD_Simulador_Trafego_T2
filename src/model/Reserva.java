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
public interface Reserva {
    public void ocupar() throws InterruptedException;
    public void desocupar() throws InterruptedException;
    public boolean tentaOcupar() throws InterruptedException;
    public String toString();
}
