/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.io.File;
import java.util.List;
import javax.swing.SwingWorker;

/**
 *
 * @author nestorojeda
 */
public class Worker extends SwingWorker<Double, Integer> {
   // Esta etiqueta se recibe en el constructor o a través de un
   // metodo setEtiqueta().


   
   @Override
    public Double doInBackground() throws Exception {
   // Mostramos el nombre del hilo, para ver que efectivamente
   // esto NO se ejecuta en el hilo de eventos.
        System.out.println("doInBackground() esta en el hilo "
        + Thread.currentThread().getName()); 
        
        /********** Algo de procesamiento costoso ***********/
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(1000);
                System.out.print(i);
            } catch (InterruptedException e) {
                System.out.println("interrumpido");
            }
            publish(i);
        }      
        return 100.0;
    }
    
   @Override
    protected void done() {
       // Mostramos el nombre del hilo para ver que efectivamente esto
       // se ejecuta en el hilo de eventos.
       System.out.println("done() esta en el hilo " + Thread.currentThread().getName());     
    }

    
    @Override
    protected void process(List<Integer> chunks) {
        System.out.println("process() esta en el hilo "
                + Thread.currentThread().getName());
        System.out.print(chunks);        
    }
    
    
    public  List<String> getFiles(File folder, List<String> result){
        for (final File f : folder.listFiles()) {
            if (f.isDirectory()) {
                getFiles(f, result);
            }
            if (f.isFile()) {
                result.add(f.getAbsolutePath());
            }
        }
        return result;
    }
}
