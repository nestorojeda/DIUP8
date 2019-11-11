
package ui;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author cayetanoguerraartal
 */
public class CompressorUI extends javax.swing.JFrame {

   
    private String originPath, destinationPath;
    private boolean originSelected = false;
    private int maxFiles;
    private File fileToZip;

    
    
    public CompressorUI() {
        initComponents();
        setLocationRelativeTo(null);
        initListeners();
       
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        destinationChooser = new javax.swing.JFileChooser();
        originChooser = new javax.swing.JFileChooser();
        compressButton = new javax.swing.JButton();
        originLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();
        destinationLabel = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        fileMenuItem = new javax.swing.JMenu();
        openMenuItem = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        compressButton.setText("Comprimir");
        compressButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                compressButtonActionPerformed(evt);
            }
        });

        originLabel.setText("Origen:");

        destinationLabel.setText("Destino:");

        fileMenuItem.setText("Archivo");

        openMenuItem.setText("Abrir");
        openMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openMenuItemActionPerformed(evt);
            }
        });
        fileMenuItem.add(openMenuItem);

        exitMenuItem.setText("Salir");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenuItem.add(exitMenuItem);

        menuBar.add(fileMenuItem);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(progressBar, javax.swing.GroupLayout.DEFAULT_SIZE, 455, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(originLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                        .addGap(277, 277, 277))
                    .addComponent(destinationLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(198, 198, 198)
                .addComponent(compressButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(compressButton)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(originLabel)
                .addGap(18, 18, 18)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(destinationLabel)
                .addGap(38, 38, 38))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        // TODO add your handling code here:
        int confirmed = JOptionPane.showConfirmDialog(null, "¿Desea cerrar el programa?", "Cerrar",JOptionPane.YES_NO_OPTION);
                if (confirmed == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void compressButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_compressButtonActionPerformed
        if(originSelected){

            destinationChooser.setDialogTitle("Selecciona una carpeta de destino");
            destinationChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            destinationChooser.setAcceptAllFileFilterUsed(false);

            if (destinationChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {

                if(originPath.equals(destinationChooser.getCurrentDirectory().toString())){
                    warningDialog("La carpeta de origen y destino no puede ser la misma");
                    compressButtonActionPerformed(evt);
                }

                File destinationFolder = destinationChooser.getSelectedFile();
                maxFiles = getFilesCount(fileToZip);
                System.out.println("Log: File number is:"  + maxFiles);


                destinationPath= destinationFolder.toString();
                Worker w = new Worker(this);
                compressButton.setEnabled(false);
                w.execute();

            } else {
                System.out.println("Error");
            }
        } else{
            warningDialog("Primero debes seleccionar una carpeta");
        }
        
        
    }//GEN-LAST:event_compressButtonActionPerformed

    private void openMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openMenuItemActionPerformed
        originChooser.setDialogTitle("Selecciona una carpeta que quieres comprimir");
        originChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        originChooser.setAcceptAllFileFilterUsed(false);


        if (originChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            fileToZip = originChooser.getSelectedFile();
            originPath = fileToZip.toString();
            originLabel.setText("Origen: " + originPath);
            originSelected = true;

        } else {
            System.out.println("No Selection ");
        }
    }//GEN-LAST:event_openMenuItemActionPerformed


    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CompressorUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CompressorUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CompressorUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CompressorUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */

        java.awt.EventQueue.invokeLater(() -> {
            new CompressorUI().setVisible(true);
        });
    }
    
    
    
    private void warningDialog(String s) {
        JOptionPane.showMessageDialog(this,
            s,
            "Aviso",
            JOptionPane.WARNING_MESSAGE);
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton compressButton;
    private javax.swing.JFileChooser destinationChooser;
    private javax.swing.JLabel destinationLabel;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenuItem;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem openMenuItem;
    private javax.swing.JFileChooser originChooser;
    private javax.swing.JLabel originLabel;
    private javax.swing.JProgressBar progressBar;
    // End of variables declaration//GEN-END:variables

    private void initListeners() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int confirmed = JOptionPane.showConfirmDialog(null, "¿Desea cerrar el programa?", "Cerrar",JOptionPane.YES_NO_OPTION);
                if (confirmed == JOptionPane.YES_OPTION) {
                    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                }else{
                    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }
            }
        });
    }
    
    
    String getOriginPath() {
        return originPath;
    }

    String getDestinationPath() {
        return destinationPath;
    }


    private int value = 0;
    void increaseProgressBar(){
        progressBar.setValue((value/maxFiles)*100);
        progressBar.repaint();
        System.out.println("Log: progress at: " + (value/maxFiles)*100 );
        value++;
    }

    void setDestinationLabel(String s){
        destinationLabel.setText(s);
    }


    void finishProcess() {
        destinationLabel.setText("");
        compressButton.setEnabled(true);
        progressBar.setValue(0);
        value = 0;
        System.out.println("Log: Compression proccess finished");
    }


    private static int getFilesCount(File file) {
        File[] files = file.listFiles();
        int count = 0;
        for (File f : files)
            if (f.isDirectory())
                count += getFilesCount(f);
            else
                count++;
        return count;
    }
  
    
}