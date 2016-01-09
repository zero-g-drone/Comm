package droneGUI;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class ParabolicWindow {

	JSplitPane panel;

	public ParabolicWindow(){
		panel = new JSplitPane( JSplitPane.VERTICAL_SPLIT, 
                panelNorth(), panelSouth());
		panel.setDividerLocation(0.5);		
	}
	
	public void restoreDefaults() {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                panel.setDividerLocation(panel.getSize().height /2);
            }
        });
    }
	
	public JSplitPane getPanel(){
		return this.panel;
	}
	
	private JSplitPane panelNorthEast(){
		ImageIcon imgManual = new ImageIcon(getClass().getResource("/img/parabolic.jpg"));
		JButton modeButton = new JButton(scaleIcon(imgManual,300,30));
		modeButton.setBorder(BorderFactory.createEmptyBorder());
			 
			  
		
		modeButton.addActionListener(getModeButtonAction());
		JSplitPane northEast = new JSplitPane( JSplitPane.VERTICAL_SPLIT, 
               modeButton, panelNorthEastSouth());
		
		northEast.setDividerLocation(0.2);
		
		return northEast;
		
	}
	
	private JSplitPane panelNorthEastSouth(){
		JButton startFlight = new JButton("Start Parabolic Flight");
		
		JButton endFlight = new JButton("End Flight");
		
		JSplitPane buttonsPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT, 
                startFlight, endFlight);
		
		endFlight.addActionListener(getModeButtonAction());
		startFlight.addActionListener(getModeButtonAction());;
		
		return buttonsPanel;
	}
	
	
	private JPanel panelNorthWest(){
		JPanel west = new JPanel(new BorderLayout());
		JButton rcvGPSButton = new JButton("Receive GPS Data");
		JButton connectionTestButton = new JButton("Connection Test");
		
		rcvGPSButton.setSize(75, 35);
		rcvGPSButton.addActionListener(getModeButtonAction());
		
		connectionTestButton.setSize(75, 35);
		connectionTestButton.addActionListener(getModeButtonAction());
		
		
		west.add(rcvGPSButton, BorderLayout.NORTH);
		west.add(connectionTestButton, BorderLayout.SOUTH);
		
		return west;
	}
	
	private JSplitPane panelNorth(){
		JSplitPane north = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT, 
                panelNorthWest(), panelNorthEast());
		north.setDividerLocation(0.5);
		return north;
	}
	
	private JSplitPane panelSouth(){
		JTextArea map = new JTextArea("adasdas");
		JTextArea log = new JTextArea("oppoiopipo");
		map.setEditable(false);
		log.setEditable(false);
		JSplitPane south = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT, 
                map, log);
		south.setDividerLocation(0.5);
		return south;
	}
	
	private ImageIcon scaleIcon(ImageIcon icon, int width, int height){
		Image img = icon.getImage() ;  
		Image newimg = img.getScaledInstance( width, height,  java.awt.Image.SCALE_SMOOTH ) ;  
		icon = new ImageIcon( newimg );
		return icon;
	}
	
	private ActionListener getModeButtonAction() {
        ActionListener action = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	//sendData("1");
            }
        };
        return action;
    }
}
