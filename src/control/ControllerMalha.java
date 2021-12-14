/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.TableColumn;
import model.CoordenadaDeMalha;
import model.Malha;
import model.MalhaTableModel;
import model.vias.Via;
import model.vias.ViaFactory;
import view.ViewMalha;

/**
 *
 * @author Danton e Edianez
 */
public class ControllerMalha {

    private view.ViewMalha view;
    private JTable malhaTable;
    private MalhaTableModel malhaTableModel;
    private String reservaTipoAtivo;
    private boolean executando;

    public ControllerMalha() {
        view = new ViewMalha();
        malhaTable = view.getTable();
        reservaTipoAtivo = "Semaforo";
        inicializarListeners();
        configurarTabela();
    }

    public void showView() {
        view.setVisible(true);
    }

    public void showMessage(String s) {
        JOptionPane.showMessageDialog(view, s);
    }

    public void inicializarListeners() {
        btnArquivoListener();
        btnIniciarListener();
        btnPararListener();
        tfMaxVeiculosListener();
        tBtnTipoListener();
    }

    public void btnArquivoListener() {
        view.setBtnLerArquivoListener((e) -> {
            lerArquivo();
        });
    }

    public void btnIniciarListener() {
        view.setBtnIniciarListener((e) -> {
            iniciarSimulacao();
        });
    }

    public void btnPararListener() {
        view.setBtnPararListener((e) -> {
            pararSimulacao();
            showMessage("Aguarde os veículos saírem da malha");
        });
    }

    public void tBtnTipoListener() {
        view.setTBtnTipoListener((e) -> {
            if (malhaTableModel != null) {
                if (reservaTipoAtivo.equals("Monitor")) {
                    reservaTipoAtivo = "Semaforo";
                    setLabelTipoText("Semáforo");
                } else {
                    reservaTipoAtivo = "Monitor";
                    setLabelTipoText("Monitor");
                }
                malhaTableModel.getMalha().transformaViasEm(reservaTipoAtivo);
            } else {
                showMessage("Carregue um arquivo!");
            }
        });
    }

    public void setLabelTipoText(String value) {
        view.getLabelTipo().setText(value);
    }

    private void setMalhaMaxVeiculos(String v) {
        if (!v.isEmpty() && malhaTableModel != null) {
            malhaTableModel.getMalha().setMaxVeiculos(Integer.parseInt(v));
        }
    }

    public void tfMaxVeiculosListener() {
        view.setTfMaxVeiculosListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                setMalhaMaxVeiculos(view.getTfMaxVeiculos().getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                setMalhaMaxVeiculos(view.getTfMaxVeiculos().getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                setMalhaMaxVeiculos(view.getTfMaxVeiculos().getText());
            }
        });
    }

    public void configurarTabela() {
        malhaTable.setTableHeader(null);
        malhaTable.setRowHeight(19);
    }

    public void lerArquivo() {
        String current = "";
        try {
            current = new java.io.File(".").getCanonicalPath();
        } catch (IOException ex) {
        }
        JFileChooser explorer = new JFileChooser(current);

        explorer.setFileSelectionMode(JFileChooser.FILES_ONLY);
        explorer.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                } else {
                    return f.getName().toLowerCase().endsWith(".txt");
                }
            }

            @Override
            public String getDescription() {
                return "Arquivo .txt";
            }
        });
        explorer.showOpenDialog(view);
        File file = explorer.getSelectedFile();

        FileReader fileReader;
        try {
            fileReader = new FileReader(file);
            BufferedReader br = new BufferedReader(fileReader);
            String s;
            int line = 0;
            int altura = Integer.parseInt(br.readLine().trim());
            int largura = Integer.parseInt(br.readLine().trim());
            Malha malha = new Malha();
            Via[][] vias = new Via[altura][largura];
            while (((s = br.readLine()) != null)) {
                String[] lineAux = s.split("\t");
                for (int i = 0; i < largura; i++) {
                    CoordenadaDeMalha cdm = new CoordenadaDeMalha(i, line, malha);
                    vias[line][i] = ViaFactory.getVia(Integer.parseInt(lineAux[i]), cdm);
                }
                line++;
            };
            malha.setVias(vias);
            gerarTableModel(malha);
        } catch (IOException e) {
            System.out.println("Problema ao ler arquivo");
        } catch (Exception e) {
            System.out.println("Erro de execução, formato incorreto de arquivo");
        }
    }

    public void iniciarSimulacao() {
        if (malhaTableModel.getMalha().getMaxVeiculos() != 0) {
            if (!reservaTipoAtivo.equals("")) {
                executando = true;
                SwingWorker atualizador = new SwingWorker() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        do {
                            atualizarTableModel();
                            Thread.sleep(1);
                        } while (executando || malhaTableModel.getMalha().getVeiculos().size() > 0);
                        return null;
                    }
                };
                SwingWorker worker = new SwingWorker() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        while (executando) {
                            malhaTableModel.getMalha().inserirVeiculo();
                            Thread.sleep(100);
                        }
                        return null;
                    }
                };
                atualizador.execute();
                worker.execute();
            } else {
                showMessage("Você precisa trocar o tipo para Reserva");
            }
        } else {
            showMessage("Você precisa definir o máximo de veículos");
        }

    }

    public void pararSimulacao() {
        executando = false;
    }

    private void atualizarTableModel() {
        malhaTableModel.fireTableDataChanged();
    }

    private void gerarTableModel(Malha m) {
        malhaTableModel = new MalhaTableModel(m);
        malhaTable.setModel(malhaTableModel);
        for (int i = 0; i < malhaTableModel.getColumnCount(); i++) {
            TableColumn column = malhaTable.getColumnModel().getColumn(i);
            column.setMinWidth(19);
            column.setMaxWidth(19);
            column.setPreferredWidth(19);
        }
    }

}
