/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author nestorojeda
 */
public class Worker extends SwingWorker<Double, Integer> {
   // Esta etiqueta se recibe en el constructor o a través de un
   // metodo setEtiqueta().

    private CompressorUI frame;
    private String destination;
    Worker(CompressorUI parentFrame){
       frame = parentFrame;
    }

    @Override
    public Double doInBackground() throws Exception {
   // Mostramos el nombre del hilo, para ver que efectivamente
   // esto NO se ejecuta en el hilo de eventos.
        System.out.println("doInBackground() esta en el hilo "
        + Thread.currentThread().getName());
        compress(frame.getOriginPath(), frame.getDestinationPath());
        return 0.0;
    }


    private void compress(String origin, String destination) throws IOException {
        System.out.println("Log: Compression proccess started in: " +  origin + " to: "+ destination);
        destination = destination+"\\"+fileNameCreator();
        this.destination = destination;
        frame.setDestinationLabel("Destino: " + destination);
        System.out.println("Log: Full destination path: " + destination);
        int value = 0;
        ZipOutputStream zip = new ZipOutputStream(new FileOutputStream(destination));
        addFolder("",origin, zip);
        zip.flush();
        zip.close();
    }


    private void addFolder(String path, String folder, ZipOutputStream zip) throws IOException{
        File f = new File(folder);
        for (String fileName : Objects.requireNonNull(f.list())) {
            publish();
            if (path.equals("")) addFile(f.getName(), folder + "/" + fileName, zip);
            else {
                addFile(path + "/" + f.getName(), folder + "/" + fileName, zip);
            }
        }
    }

    private void addFile(String path, String folder, ZipOutputStream zip) throws IOException{
        publish();
        File f = new File(folder);
        if (f.isDirectory()) {
            addFolder(path, folder, zip);
        } else {
            int BUFFER_SIZE = 4096;
            byte[] buffer = new byte[BUFFER_SIZE];
            int count;
            FileInputStream input = new FileInputStream(f);
            zip.putNextEntry(new ZipEntry(path + "/" + f.getName()));
            while ((count = input.read(buffer)) > 0) {
                zip.write(buffer, 0, count);
            }
        }
    }

    private String fileNameCreator(){
        String aux = frame.getOriginPath().concat(".zip");
        String[] split = aux.split("\\\\");
        System.out.println("Log: zip name: " + split[split.length-1] );
        return split[split.length-1];
    }


    @Override
    protected void done() {
        JOptionPane.showMessageDialog(null,"El archivo generado se enceuntra en: " + destination, "Proceso finalizado",JOptionPane.INFORMATION_MESSAGE);
        frame.finishProcess();
    }

    @Override
    protected void process(List<Integer> chunks) {
        frame.increaseProgressBar();

    }
    

}
