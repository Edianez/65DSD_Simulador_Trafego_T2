/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.vias;

import model.cruzamentos.CruzamentoBaixoEsquerda;
import model.cruzamentos.CruzamentoDireita;
import model.cruzamentos.CruzamentoCimaEsquerda;
import model.cruzamentos.CruzamentoCima;
import model.cruzamentos.CruzamentoBaixoDireita;
import model.cruzamentos.CruzamentoCimaDireita;
import model.cruzamentos.CruzamentoBaixo;
import model.cruzamentos.CruzamentoEsquerda;
import javax.swing.ImageIcon;
import model.CoordenadaDeMalha;
import model.Malha;

/**
 *
 * @author Danton e Edianez
 */
public class ViaFactory {

    public static Via getVia(int codigo, CoordenadaDeMalha coordenadaDeMalha) {
        Via via;

        switch (codigo) {
            case 1: {
                via = new ViaCima(coordenadaDeMalha);
                break;
            }
            case 2: {
                via = new ViaDireita(coordenadaDeMalha);
                break;
            }
            case 3: {
                via = new ViaBaixo(coordenadaDeMalha);
                break;
            }
            case 4: {
                via = new ViaEsquerda(coordenadaDeMalha);
                break;
            }
            case 5: {
                via = new CruzamentoCima(coordenadaDeMalha);
                break;
            }
            case 6: {
                via = new CruzamentoDireita(coordenadaDeMalha);
                break;
            }
            case 7: {
                via = new CruzamentoBaixo(coordenadaDeMalha);
                break;
            }
            case 8: {
                via = new CruzamentoEsquerda(coordenadaDeMalha);
                break;
            }
            case 9: {
                via = new CruzamentoCimaDireita(coordenadaDeMalha);
                break;
            }
            case 10: {
                via = new CruzamentoCimaEsquerda(coordenadaDeMalha);
                break;
            }
            case 11: {
                via = new CruzamentoBaixoDireita(coordenadaDeMalha);
                break;
            }
            case 12: {
                via = new CruzamentoBaixoEsquerda(coordenadaDeMalha);
                break;
            }
            default: {
                via = null;
            }
        }
        if (via != null) {
            if (codigo <= 4) {
                via.setImagemVazia(new ImageIcon("img/" + (codigo) + "/vazia.jpg"));
                via.setImagemOcupada(new ImageIcon("img/" + (codigo) + "/ocupada.jpg"));
            } else {
                via.setImagemVazia(new ImageIcon("img/5/vazia.jpg"));
                via.setImagemOcupada(new ImageIcon("img/5/ocupada.jpg"));
            }
        }

        return via;
    }
}
