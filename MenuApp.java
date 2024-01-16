package menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class MenuApp extends JFrame {
    private ArrayList<String> midi;
    private ArrayList<String> soir;
    private int j1;
    private int j2;
    private static int numDays;
    private ArrayList<String> semaine = new ArrayList<String>();
    private JFrame frame;
    private static JPanel gridPanel;
    

    public MenuApp(int j1, int j2) {
        midi = new ArrayList<String>();
        soir = new ArrayList<String>();
        this.j1 = j1;	
        this.j2 = j2;
        
        semaine.add("lundi");
        semaine.add("mardi");
        semaine.add("mercredi");
        semaine.add("jeudi");
        semaine.add("vendredi");
        semaine.add("samedi");
        semaine.add("dimanche");
        
        frame = new JFrame("MenuApp");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.numDays = j2 - j1;
        if (numDays<0) {
        	numDays+=8;
        }
        gridPanel=new JPanel(new GridLayout(3, numDays));
        
        // Ajoutez les jours de la semaine à la première ligne
        int i=j1;
        int cpt=0;
        while (cpt<numDays) {
            JLabel label = new JLabel(semaine.get(i));
            label.setFont(new Font("Serif", Font.BOLD, 22));
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setVerticalAlignment(JLabel.CENTER);
            label.setBorder(new LineBorder(Color.BLACK));
            gridPanel.add(label);
            if(i==6) {
            	i=-1;
            }
            i++;
            cpt++;
        }

        
        for (int j = 0; j < 2*numDays; j++) { 
            JLabel lab = new JLabel("");
            lab.setFont(new Font("Serif", Font.BOLD, 14));
            lab.setHorizontalAlignment(JLabel.CENTER);
            lab.setVerticalAlignment(JLabel.CENTER);
            lab.setBorder(new LineBorder(Color.BLACK)); 
            gridPanel.add(lab);
        }
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 500);
        frame.setVisible(true);
        frame.add(gridPanel);
    }
    
    public void create(ArrayList<String> list, String repas) {
        midi.clear();
        soir.clear();
        for (int k = 0; k < numDays; k++) {
            if (!list.isEmpty()) {
            	int index = (int) (Math.random() * list.size());
                String plat = list.get(index);
            	while(midi.contains(plat) || soir.contains(plat)) {
            		index = (int) (Math.random() * list.size());
            		plat = list.get(index);
            	}
                
                if (repas.equals("midi")) {
                	midi.add(plat);
                    JLabel label = (JLabel) gridPanel.getComponent(k + numDays); // Obtenez l'étiquette correspondante
                    label.setText("<html>" + plat.replace("\n", "<br>") + "</html>");
                } else {
                    soir.add(plat);
                    JLabel label = (JLabel) gridPanel.getComponent(k + 2 * numDays); // Obtenez l'étiquette correspondante
                    label.setText("<html>" + plat.replace("\n", "<br>") + "</html>");
                }
            }
        }

       
    }

    
    public static void main(String[] args) {
    	int j1 = Integer.parseInt(args[0]);
        int j2 = Integer.parseInt(args[1]);
        for(String str : args) {
        	if(str.equals("G")) {
        		JLabel label = (JLabel) gridPanel.getComponent(2 + 2*numDays); // Obtenez l'étiquette correspondante
                label.setText("Galettes Crepes");
        	}
        }
        
        //midi
        File midi = new File("./plat_midi.txt");
        ArrayList<String> repas_midi = new ArrayList<String>();
        
        //soir
        File soir = new File("./plat_soir.txt");
        ArrayList<String> repas_soir = new ArrayList<String>();
        
    	try {
    		BufferedReader brm = new BufferedReader(new FileReader(midi));
    		String strm;
    		while ((strm = brm.readLine()) != null) {
    			repas_midi.add(strm);
    		}
        
    		BufferedReader brs = new BufferedReader(new FileReader(soir));
            String strs;
            while ((strs = brs.readLine()) != null) {
            	repas_soir.add(strs);
            }
        }
        catch(IOException e) {
        	System.out.println("not found file");
        }
    	
        MenuApp frame = new MenuApp(j1, j2);
        frame.create(repas_midi,"midi");
        frame.create(repas_soir,"soir");
        
    }
}
