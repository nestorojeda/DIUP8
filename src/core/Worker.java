/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.swing.SwingWorker;

/**
 *
 * @author nestorojeda
 */
public class Worker extends SwingWorker<Double, Integer> {
   // Esta etiqueta se recibe en el constructor o a través de un
   // metodo setEtiqueta().


   private List<String> files;
   private String destination;
   private final int BUFFER_SIZE = 4096;
    
    @Override
    public Double doInBackground() throws Exception {
   // Mostramos el nombre del hilo, para ver que efectivamente
   // esto NO se ejecuta en el hilo de eventos.
        System.out.println("doInBackground() esta en el hilo "
        + Thread.currentThread().getName()); 
        
        /********** Algo de procesamiento costoso ***********/
//        for (int i = 0; i < 10; i++) {
//            try {
//                Thread.sleep(1000);
//                System.out.print(i);
//            } catch (InterruptedException e) {
//                System.out.println("interrumpido");
//            }
//            publish(i);
//        }      
        try{
            // Objeto para referenciar a los archivos que queremos comprimir
            BufferedInputStream origin = null;
            // Objeto para referenciar el archivo zip de salida
            FileOutputStream dest = new FileOutputStream(destination);
            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));
            // Buffer de transferencia para mandar datos a comprimir
            byte[] data = new byte[BUFFER_SIZE];
            for (String filename : files) {
                FileInputStream fi = new FileInputStream(filename);
                origin = new BufferedInputStream(fi, BUFFER_SIZE);
                ZipEntry entry = new ZipEntry( filename );
                out.putNextEntry( entry );
                // Leemos datos desde el archivo origen y los mandamos al archivo destino
                int count;
                while((count = origin.read(data, 0, BUFFER_SIZE)) != -1) {
                    out.write(data, 0, count);
                }
                // Cerramos el archivo origen, ya enviado a comprimir
                origin.close();
            }
            // Cerramos el archivo zip
            out.close();
        }
        catch( IOException e ) {
            e.printStackTrace();
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
    
    
    
    public void setDestination(String s){destination = s;}
    public void setFiles(File folder, List<String> result){
        for (final File f : folder.listFiles()) {
            if (f.isDirectory()) {
                setFiles(f, result);
            }
            if (f.isFile()) {
                result.add(f.getAbsolutePath());
            }
        }
        files = result;
    }

}
