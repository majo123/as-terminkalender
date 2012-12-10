package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */

/*
 * GUI_Monatsansicht.java
 *
 * Created on 26.11.2012, 09:09:21
 */
/**
 *
 * @author Mario Hoffmann
 */
public class GUI_Monatsansicht extends javax.swing.JFrame {

    //private Date monatDate;
    private Calendar cal;
    private int wochentagNr;
    private int anzahlTage;
    private Date clickedDate;

    /**
     * Creates new form GUI_Monatsansicht
     */
    public GUI_Monatsansicht() {
        initComponents();
        cal = Calendar.getInstance(Locale.GERMAN);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        initKalender();
        initWochentage();
        this.setTitle("Terminkalender");
        pnlMonatsansicht.setVisible(true);
        pnlTagesansicht.setVisible(false);
        this.setSize(new Dimension(970, 650));
        this.setMinimumSize(new Dimension(970, 650));
        this.setLocationRelativeTo(null);
    }

    /**
     * Erstellt die Monatsansicht, für den aktuellen, ausgewählten Monat
     */
    public void initKalender() {
        for (int i = 0; i < this.getComponentCount(); i++) {
            System.out.println(this.getComponent(i));
        }

        wochentagNr = cal.get(Calendar.DAY_OF_WEEK);
        anzahlTage = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        final int WOCHENTAGE = 7;
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy");

        /////////////////KAKA && PIPI/////////////////////////
        System.out.println("MinimalDaysInFirstWeek: " + cal.getMinimalDaysInFirstWeek());
        System.out.println("WochentagNr: " + wochentagNr + "\nAnzahl Tage: " + anzahlTage);
        System.out.println("Time: " + cal.getTime());
        System.out.println(sdf.format(cal.getTime()));
        /////////////////////////////////////


        lblMonat.setText(sdf.format(cal.getTime()));

        pnlKalender.removeAll();
        pnlKalender.setLayout(new GridLayout(0, WOCHENTAGE));

        // Wenn die Woche nicht mit Sonntag anfängt, werden die Wochentage aus dem Vormonat abgebildet.

        if (wochentagNr != 1) {
            Calendar tmpCal = (Calendar) cal.clone();
            tmpCal.add(Calendar.MONTH, -1);

            //int tmpCalMaxDays = tmpCal.getActualMaximum(Calendar.DAY_OF_MONTH);
            //int diff = tmpCalMaxDays - (wochentagNr);


            // "Leere" Jlabes bis einen Tag vor Beginn des Monats einfügen.
            for (int i = 1; i < (wochentagNr); i++) {
                JLabel lblTag = new JLabel("-");
                lblTag.setOpaque(true);
                lblTag.setBackground(Color.LIGHT_GRAY);
                lblTag.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 4, false));
                lblTag.setHorizontalAlignment(JLabel.CENTER);
                pnlKalender.add(lblTag);
            }
        }


        // Berechnen der übrigen Tage, in der letzten Woche des Monats, welche schon
        // zum folge Monat gehören.
        int restTage = 7 - ((anzahlTage + (wochentagNr - 1)) % 7);
        System.out.println(restTage);

        // Füllen der JLabels mit den entsprechenden Tageszahlen.
        for (int i = 1; i <= (anzahlTage); i++) {
            JPanel tmpPanel = new JPanel();
            JLabel lblTag = new JLabel(Integer.toString(i + 1));
            final JWochentagBtn btnTmp = new JWochentagBtn(i, cal);


            btnTmp.setTermin("Termin Nr:" + i);
            btnTmp.setTermin("Termin Nr: " + (i + i));
            pnlKalender.add(btnTmp);

            btnTmp.addMouseListener(new MouseListener() {

                @Override
                public void mouseClicked(MouseEvent e) {

                    changeView();
                    setClickedDate(btnTmp.getDatum());
                    tagesansicht.init();
                    // debug 
                    ArrayList<String> tmpTermine = btnTmp.getTermine();
                    for (int n = 0; n < tmpTermine.size(); n++) {
                        System.out.println(tmpTermine.get(n).toString());
                    }
                    System.out.println(btnTmp.getDatum());
                    // end debug
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet.");
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet.");
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet.");
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet.");
                }
            });
        }


        // Erstellen von "leeren" JLabens, um die letzte Reihe im Grid zu füllen
        if ((restTage > 0) && (restTage < 7)) {
            for (int n = 0; n < restTage; n++) {
                JLabel lblTag = new JLabel("-");
                lblTag.setOpaque(true);
                lblTag.setBackground(Color.LIGHT_GRAY);
                lblTag.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 4, false));
                lblTag.setHorizontalAlignment(JLabel.CENTER);
                pnlKalender.add(lblTag);
            }
        }

        pnlKalender.revalidate();
        pnlKalender.repaint();
    }

    public void changeView() {
        if (pnlMonatsansicht.isVisible()) {
            pnlMonatsansicht.setVisible(false);
            pnlTagesansicht.setVisible(true);
            tagesansicht.setMonatsansicht(this);
        } else {
            pnlTagesansicht.setVisible(false);
            pnlMonatsansicht.setVisible(true);
        }
    }

    public void initWochentage() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Calendar tmpCal = Calendar.getInstance(Locale.GERMAN);

        for (int i = 1; i <= 7; i++) {
            tmpCal.set(Calendar.DAY_OF_WEEK, i);
            JLabel lblWochentag = new JLabel(sdf.format(tmpCal.getTime()));
            lblWochentag.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 4, false));
            lblWochentag.setOpaque(true);
            lblWochentag.setBackground(Color.WHITE);
            lblWochentag.setHorizontalAlignment(JLabel.CENTER);
            lblWochentag.setFont(new Font("Arial", Font.BOLD, 20));
            if (i == 1 || i == 7) {
                lblWochentag.setForeground(Color.red);
            } else {
                lblWochentag.setForeground(Color.black);
            }

            pnlWochentage.add(lblWochentag);
        }
    }

    public Date getClickedDate() {
        return clickedDate;
    }

    public void setClickedDate(Date clickedDate) {
        this.clickedDate = clickedDate;
    }

    private void nextMonat() {
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        System.out.println(cal.get(Calendar.MONTH));
        initKalender();
    }

    private void prevMonat() {
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        System.out.println(cal.get(Calendar.MONTH));
        initKalender();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlMonatsansicht = new javax.swing.JPanel();
        pnlLogin = new javax.swing.JPanel();
        btnLogin = new javax.swing.JButton();
        txtLogin = new javax.swing.JTextField();
        txtPasswort = new javax.swing.JTextField();
        pnlWochentage = new javax.swing.JPanel();
        pnlTerminListe = new javax.swing.JPanel();
        pnlKalender = new javax.swing.JPanel();
        pnlBlaettern = new javax.swing.JPanel();
        btnMonatZurueck = new javax.swing.JButton();
        lblMonat = new javax.swing.JLabel();
        btnMonatVor = new javax.swing.JButton();
        pnlTagesansicht = new javax.swing.JPanel();
        tagesansicht = new view.GUI_Tagesansicht();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.CardLayout());

        btnLogin.setText("Login");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        txtLogin.setText("Login");

        txtPasswort.setText("Passwort");

        javax.swing.GroupLayout pnlLoginLayout = new javax.swing.GroupLayout(pnlLogin);
        pnlLogin.setLayout(pnlLoginLayout);
        pnlLoginLayout.setHorizontalGroup(
            pnlLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLoginLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtLogin)
                    .addComponent(txtPasswort)
                    .addComponent(btnLogin, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlLoginLayout.setVerticalGroup(
            pnlLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLoginLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtPasswort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnLogin)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlWochentage.setLayout(new java.awt.GridLayout(1, 0));

        javax.swing.GroupLayout pnlTerminListeLayout = new javax.swing.GroupLayout(pnlTerminListe);
        pnlTerminListe.setLayout(pnlTerminListeLayout);
        pnlTerminListeLayout.setHorizontalGroup(
            pnlTerminListeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        pnlTerminListeLayout.setVerticalGroup(
            pnlTerminListeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        pnlKalender.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                pnlKalenderMouseWheelMoved(evt);
            }
        });

        javax.swing.GroupLayout pnlKalenderLayout = new javax.swing.GroupLayout(pnlKalender);
        pnlKalender.setLayout(pnlKalenderLayout);
        pnlKalenderLayout.setHorizontalGroup(
            pnlKalenderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        pnlKalenderLayout.setVerticalGroup(
            pnlKalenderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 159, Short.MAX_VALUE)
        );

        btnMonatZurueck.setText("<<");
        btnMonatZurueck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMonatZurueckActionPerformed(evt);
            }
        });

        lblMonat.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblMonat.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMonat.setText("Monat Jahr");

        btnMonatVor.setText(">>");
        btnMonatVor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMonatVorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlBlaetternLayout = new javax.swing.GroupLayout(pnlBlaettern);
        pnlBlaettern.setLayout(pnlBlaetternLayout);
        pnlBlaetternLayout.setHorizontalGroup(
            pnlBlaetternLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBlaetternLayout.createSequentialGroup()
                .addComponent(btnMonatZurueck, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblMonat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnMonatVor, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlBlaetternLayout.setVerticalGroup(
            pnlBlaetternLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBlaetternLayout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(pnlBlaetternLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnMonatVor, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlBlaetternLayout.createSequentialGroup()
                        .addComponent(btnMonatZurueck, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(pnlBlaetternLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblMonat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlMonatsansichtLayout = new javax.swing.GroupLayout(pnlMonatsansicht);
        pnlMonatsansicht.setLayout(pnlMonatsansichtLayout);
        pnlMonatsansichtLayout.setHorizontalGroup(
            pnlMonatsansichtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 417, Short.MAX_VALUE)
            .addGroup(pnlMonatsansichtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlMonatsansichtLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(pnlMonatsansichtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(pnlBlaettern, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pnlWochentage, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pnlKalender, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(pnlMonatsansichtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(pnlTerminListe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pnlLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap()))
        );
        pnlMonatsansichtLayout.setVerticalGroup(
            pnlMonatsansichtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
            .addGroup(pnlMonatsansichtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlMonatsansichtLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(pnlMonatsansichtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlMonatsansichtLayout.createSequentialGroup()
                            .addComponent(pnlLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(pnlTerminListe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(pnlMonatsansichtLayout.createSequentialGroup()
                            .addComponent(pnlBlaettern, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(pnlWochentage, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(pnlKalender, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addContainerGap()))
        );

        getContentPane().add(pnlMonatsansicht, "card2");

        javax.swing.GroupLayout pnlTagesansichtLayout = new javax.swing.GroupLayout(pnlTagesansicht);
        pnlTagesansicht.setLayout(pnlTagesansichtLayout);
        pnlTagesansichtLayout.setHorizontalGroup(
            pnlTagesansichtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tagesansicht, javax.swing.GroupLayout.DEFAULT_SIZE, 417, Short.MAX_VALUE)
        );
        pnlTagesansichtLayout.setVerticalGroup(
            pnlTagesansichtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tagesansicht, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );

        getContentPane().add(pnlTagesansicht, "card3");

        pack();
    }// </editor-fold>//GEN-END:initComponents

  private void btnMonatVorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMonatVorActionPerformed
      // TODO add your handling code here:
      nextMonat();
  }//GEN-LAST:event_btnMonatVorActionPerformed

  private void btnMonatZurueckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMonatZurueckActionPerformed
      // TODO add your handling code here:
      prevMonat();
  }//GEN-LAST:event_btnMonatZurueckActionPerformed

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        changeView();
    }//GEN-LAST:event_btnLoginActionPerformed

    private void pnlKalenderMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_pnlKalenderMouseWheelMoved
        // TODO add your handling code here:
        int upDown = evt.getWheelRotation();

        if (upDown < 0) {
            //mouseWheel moved UP
            prevMonat();
        } else {
            // upDown > 0
            // mouseWheel moved Down
            nextMonat();
        }
    }//GEN-LAST:event_pnlKalenderMouseWheelMoved

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUI_Monatsansicht.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI_Monatsansicht.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI_Monatsansicht.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI_Monatsansicht.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new GUI_Monatsansicht().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogin;
    private javax.swing.JButton btnMonatVor;
    private javax.swing.JButton btnMonatZurueck;
    private javax.swing.JLabel lblMonat;
    private javax.swing.JPanel pnlBlaettern;
    private javax.swing.JPanel pnlKalender;
    private javax.swing.JPanel pnlLogin;
    private javax.swing.JPanel pnlMonatsansicht;
    private javax.swing.JPanel pnlTagesansicht;
    private javax.swing.JPanel pnlTerminListe;
    private javax.swing.JPanel pnlWochentage;
    private view.GUI_Tagesansicht tagesansicht;
    private javax.swing.JTextField txtLogin;
    private javax.swing.JTextField txtPasswort;
    // End of variables declaration//GEN-END:variables
}
