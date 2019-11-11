/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.io.File;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

/**
 *
 * @author nestorojeda
 */
public class Worker extends SwingWorker<Double, Integer> {
   // Esta etiqueta se recibe en el constructor o a través de un
   // metodo setEtiqueta().
    
    CompressorUI frame;
    private boolean running;
    
    public Worker(CompressorUI parentFrame){
       frame = parentFrame;
    }


    private boolean finished;
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
        if(finished)JOptionPane.showMessageDialog(null, "Archivo ZIP creado correctamente !","Finalizado",JOptionPane.INFORMATION_MESSAGE);
    }

    
    @Override
    protected void process(List<Integer> chunks) {
     
    }
    

}
