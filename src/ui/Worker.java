/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.util.List;
import javax.swing.SwingWorker;

/**
 *
 * @author Néstor Ojeda
 */
public class Worker extends SwingWorker<Double, Integer> {
   // Esta etiqueta se recibe en el constructor o a través de un
   // metodo setEtiqueta().

    private CompressorUI frame;
    Worker(CompressorUI parentFrame){
       frame = parentFrame;
    }

    @Override
    public Double doInBackground() throws Exception {
   // Mostramos el nombre del hilo, para ver que efectivamente
   // esto NO se ejecuta en el hilo de eventos.
        System.out.println("doInBackground() esta en el hilo "
        + Thread.currentThread().getName());
        frame.compress(frame.getOriginPath(), frame.getDestinationPath());
        return 0.0;
    }
    
        @Override
    protected void done() {
        frame.finishProcess();
    }

    @Override
    protected void process(List<Integer> chunks) {

    }
}
