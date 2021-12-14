/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.vias;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import model.CoordenadaDeMalha;
import model.Malha;
import model.Reserva;
import model.ReservaMonitor;
import model.ReservaSemaforo;
import model.Veiculo;

/**
 *
 * @author Danton e Edianez
 */
public abstract class Via {

    public abstract List<Via> getProximasVias();

    protected CoordenadaDeMalha coordenadaDeMalha;
    protected Reserva reserva;
    protected ImageIcon imagemVazia;
    protected ImageIcon imagemOcupada;

    protected Via(CoordenadaDeMalha coordenadaDeMalha) {
        this.coordenadaDeMalha = coordenadaDeMalha;
        this.reserva = new ReservaSemaforo();
    }

    public void transformaTipoReservaEm(String classs) {
        if (classs.equals("Semaforo")) {
            trocaTipoReservaParaSemaforo();
        } else {
            trocaTipoReservaParaMonitor();
        }
    }

    private void trocaTipoReservaParaMonitor() {
        this.reserva = new ReservaMonitor();
    }

    private void trocaTipoReservaParaSemaforo() {
        this.reserva = new ReservaSemaforo();
    }

    public CoordenadaDeMalha getCoordenadaDeMalha() {
        return coordenadaDeMalha;
    }

    public boolean isBorda() {
        return coordenadaDeMalha.isBorda();
    }

    public boolean isBordaSaida() {
        return coordenadaDeMalha.isBordaSaida();
    }

    public boolean temVeiculo() {
        return coordenadaDeMalha.temVeiculo();
    }

    public void setVeiculo(Veiculo v) {
        coordenadaDeMalha.setVeiculo(v);
    }

    public boolean tentaOcupar() throws InterruptedException {
        return reserva.tentaOcupar();
    }
        
    public void ocupar() throws InterruptedException {
        reserva.ocupar();
    }

    public void desocupar() throws InterruptedException {
        reserva.desocupar();
    }

    public ImageIcon getImagem() {
        return temVeiculo() ? getImagemOcupada() : getImagemVazia();
    }

    private ImageIcon getImagemVazia() {
        return imagemVazia;
    }

    private ImageIcon getImagemOcupada() {
        return imagemOcupada;
    }

    public void setImagemVazia(ImageIcon imagemVazia) {
        this.imagemVazia = imagemVazia;
    }

    public void setImagemOcupada(ImageIcon imagemOcupada) {
        this.imagemOcupada = imagemOcupada;
    }

    protected List<Via> factoryProximasVias(List<String> direcoes) {
        List<Via> proximas = new ArrayList<>();
        List<Via> ultrapassagens = new ArrayList<>();

        for (String direcao : direcoes) {
            Via via = null;
            Via ultrapassagem = null;
            switch (direcao) {
                case "cima": {
                    via = coordenadaDeMalha.getMalha().getViaNaCoordenada(getX(), getY() - 1);
                    break;
                }
                case "baixo": {
                    via = coordenadaDeMalha.getMalha().getViaNaCoordenada(getX(), getY() + 1);
                    break;
                }
                case "direita": {
                    via = coordenadaDeMalha.getMalha().getViaNaCoordenada(getX() + 1, getY());
                    break;
                }
                case "esquerda": {
                    via = coordenadaDeMalha.getMalha().getViaNaCoordenada(getX() - 1, getY());
                    break;
                }
                case "diagonal-baixo-esquerda": {
                    ultrapassagem = coordenadaDeMalha.getMalha().getViaNaCoordenada(getX() - 1, getY() + 1);
                    break;
                }
                case "diagonal-baixo-direita": {
                    ultrapassagem = coordenadaDeMalha.getMalha().getViaNaCoordenada(getX() + 1, getY() + 1);
                    break;
                }
                case "diagonal-cima-esquerda": {
                    ultrapassagem = coordenadaDeMalha.getMalha().getViaNaCoordenada(getX() - 1, getY() - 1);
                    break;
                }
                case "diagonal-cima-direita": {
                    ultrapassagem = coordenadaDeMalha.getMalha().getViaNaCoordenada(getX() + 1, getY() - 1);
                    break;
                }
                default: {
                    via = null;
                }
            }
            if (via != null && !via.temVeiculo()) {
                proximas.add(via);
            }
            if (ultrapassagem != null && this.getClass() == ultrapassagem.getClass() && !ultrapassagem.temVeiculo()) {
                ultrapassagens.add(ultrapassagem);
            }
        }
        return proximas.isEmpty() ? ultrapassagens : proximas;     
    }

    public int getX() {
        return coordenadaDeMalha.getX();
    }

    public int getY() {
        return coordenadaDeMalha.getY();
    }

    @Override
    public String toString() {
        return "Via{" + getX() + "," + getY() + "}";
    }

}
