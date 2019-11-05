
package ui;
import core.Worker;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    /**
     * Creates new form NewJFrame
     */
    public CompressorUI() {
        initComponents();
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
        menuBar = new javax.swing.JMenuBar();
        fileMenuItem = new javax.swing.JMenu();
        openMenuItem = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();
        editMenuItem = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        compressButton.setText("Comprimir");
        compressButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                compressButtonActionPerformed(evt);
            }
        });

        originLabel.setText("Origen:");

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

        editMenuItem.setText("Editar");
        menuBar.add(editMenuItem);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(145, Short.MAX_VALUE)
                .addComponent(compressButton)
                .addGap(148, 148, 148))
            .addGroup(layout.createSequentialGroup()
                .addGap(106, 106, 106)
                .addComponent(originLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(originLabel)
                .addGap(45, 45, 45)
                .addComponent(compressButton)
                .addContainerGap(130, Short.MAX_VALUE))
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

    private boolean folderSelected = false;
    private String selectedPath = "";
    
    private void compressButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_compressButtonActionPerformed
       
       if(folderSelected){
            destinationChooser.setCurrentDirectory(new java.io.File("."));
            destinationChooser.setDialogTitle("Selecciona una carpeta de destino");
            destinationChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            destinationChooser.setAcceptAllFileFilterUsed(false);
            if (destinationChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
                if(selectedPath.equals(destinationChooser.getCurrentDirectory().toString())){
                    warningDialog("La carpeta de origen y destino no puede ser la misma");
                    compressButtonActionPerformed(evt);
                }
                
                File f = destinationChooser.getCurrentDirectory();
       
                
                try {
                    new Worker().doInBackground();
                } catch (Exception ex) {
                    Logger.getLogger(CompressorUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                
             
            } else {
                System.out.println("Error");
                
            }
       } else{
           warningDialog("Primero debes seleccionar una carpeta");
       }
        

        
        
    }//GEN-LAST:event_compressButtonActionPerformed

    private void openMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openMenuItemActionPerformed
        // TODO add your handling code here:
        
        originChooser.setCurrentDirectory(new java.io.File("."));
        originChooser.setDialogTitle("Selecciona una carpeta que quieres comprimir");
        originChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        originChooser.setAcceptAllFileFilterUsed(false);
       
        
        if (originChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
               setSelectedPath(originChooser.getCurrentDirectory().toString());
               originLabel.setText( originChooser.getCurrentDirectory().toString());
               folderSelected = true;
               
        } else {
            System.out.println("No Selection ");
        }
        
    }//GEN-LAST:event_openMenuItemActionPerformed

    private void setSelectedPath(String s){
        selectedPath = s;
    }
    
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
    private javax.swing.JMenu editMenuItem;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenuItem;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem openMenuItem;
    private javax.swing.JFileChooser originChooser;
    private javax.swing.JLabel originLabel;
    // End of variables declaration//GEN-END:variables
}



