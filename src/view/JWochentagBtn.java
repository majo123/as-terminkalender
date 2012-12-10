/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Klasse erbt von JButton, um ein einheitlichen Button fuer die GUI des
 * Kalenders zu erstellen und die entsprechenden Termine auf den Buttons
 * anzeigen zu koennen.
 *
 * @author hoffmannma
 */
public class JWochentagBtn extends JButton {

    private JLabel wochentag;
    private JPanel terminPanel;
    private Date datum;
    private int terminCount;
    private ArrayList<String> termine;

    JWochentagBtn(int wochentagNr, Calendar cal) {
        super();
        this.setLayout(new BorderLayout());
        
        // init
        terminCount = 0;
        terminPanel = new JPanel();
        wochentag = new JLabel(Integer.toString(wochentagNr));
        termine = new ArrayList<String>();
        cal.set(Calendar.DAY_OF_MONTH, wochentagNr);
        datum = cal.getTime();
        
        initStyles();
        
        this.add(BorderLayout.NORTH, wochentag);
        this.add(BorderLayout.CENTER, terminPanel);
    }
    
    
    // aendert das Aussehen, des neu initialisierten Buttons.
    private void initStyles() {
        this.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 4, false));
        this.setBackground(Color.WHITE);
        
        terminPanel.setBorder(BorderFactory.createDashedBorder(Color.BLUE, 1, 1, 3, true));
        terminPanel.setLayout(new GridLayout(3, 1));
        terminPanel.setOpaque(false);
            
        wochentag.setOpaque(true);
        wochentag.setFont(new Font("Arial", Font.BOLD, 20));
        wochentag.setHorizontalAlignment(CENTER);
        wochentag.setVerticalAlignment(TOP);
    }

    // gibt den aktuellen Wochentag zurück
    public String getWochentag() {
        return wochentag.getText();
    }

    // Zeigt maximal 2 Termine als Vorschau in der Uebersicht. Sind mehr als
    // 2 Termine vorhanden, so wird lediglich die Anzahl der Termine dargestellt.
    public void setTermin(String terminName) {
        terminCount++;
        termine.add(terminName);
        if (terminCount > 2) {
            terminPanel.removeAll();
            terminPanel.add(new JLabel(Integer.toString(terminCount) + " Termine"));
        } else {
            terminPanel.add(new JLabel(terminName));
        }
    }
    
    public ArrayList<String> getTermine() {
        return termine;
    }
    
    public Date getDatum() {
        return datum;
    }
}
