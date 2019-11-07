/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import static java.lang.Math.random;
import java.util.List;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.swing.JDialog;
import javax.swing.JProgressBar;
import javax.swing.JFrame;
import javax.swing.SwingWorker;

/**
 *
 * @author nestorojeda
 */
public class Worker extends SwingWorker<Double, Integer> {
   // Esta etiqueta se recibe en el constructor o a través de un
   // metodo setEtiqueta().
    
    public Worker(JFrame parentFrame){
       
    }


   private List<File> files;
   private File destination;
   private final int BUFFER_SIZE = 4096;
   private String name;
    
    @Override
    public Double doInBackground() throws Exception {
   // Mostramos el nombre del hilo, para ver que efectivamente
   // esto NO se ejecuta en el hilo de eventos.
        System.out.println("doInBackground() esta en el hilo "
        + Thread.currentThread().getName());        

        try{
            // Objeto para referenciar a los archivos que queremos comprimir
            BufferedInputStream origin = null;
            // Objeto para referenciar el archivo zip de salida
            FileOutputStream dest = new FileOutputStream(destination.getPath()+"/"+ name+".zip");
            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));
            // Buffer de transferencia para mandar datos a comprimir
            byte[] data = new byte[BUFFER_SIZE];
            int i = 0;
            setProgress(i);

            for (File file : files) {
                i++;
                
                setProgress((i/files.size())*100);
                FileInputStream fi = new FileInputStream(file);
                origin = new BufferedInputStream(fi, BUFFER_SIZE);
                ZipEntry entry = new ZipEntry( file.getName() );
                out.putNextEntry( entry );
                // Leemos datos desde el archivo origen y los mandamos al archivo destino
                int count;
                while((count = origin.read(data, 0, BUFFER_SIZE)) != -1) {
                    out.write(data, 0, count);
                }
                // Cerramos el archivo origen, ya enviado a comprimir
                origin.close();
            }
            out.close();
        }
        catch(IOException e ) {}
        return 100.0;
    }
    
   @Override
    protected void done() {
       // Mostramos el nombre del hilo para ver que efectivamente esto
       // se ejecuta en el hilo de eventos.
       System.out.println("done() esta en el hilo " + Thread.currentThread().getName());     
    }

    public void setName(String s){name=s;}
    
    @Override
    protected void process(List<Integer> chunks) {
        System.out.println("process() esta en el hilo "
                + Thread.currentThread().getName());
        System.out.print(chunks);        
    }
    
    
    
    public void setDestination(File f){destination = f;}
    public void setFiles(File folder, List<File> result){
        
        for (final File f : folder.listFiles()) {
            if (f.isDirectory()) {
                setFiles(f, result);
            }
            if (f.isFile()) {
                result.add(f);
            }
        }
        
        files = result;
        
    }

}
