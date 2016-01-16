package droneGUI;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;

public class CreateWindow extends JFrame{
	
	//JPanel panel;
	JSplitPane panel;
	JFrame frame;
	boolean manual;
	Connect connexion;
	String option;
	JButton centerButton;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		Connect connexion = new Connect(9600,"/dev/ttyS48");
		/*try {
			connexion.startConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		new CreateWindow(true, connexion);
				
	}
	
	/**
	 * Creates Manual or Parabolic window (if manual = TRUE manual window will be showed,
	 * otherwise, parabolic window will be showed). 
	 * @param manual
	 */
	public CreateWindow(boolean manual, Connect connexion){
			this.connexion = connexion;
			this.manual = manual;
			this.setTitle("Zero-G Drone Control Pannel");
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setBounds(200, 200, 900, 500);	
			panel = new JSplitPane( JSplitPane.VERTICAL_SPLIT, 
	                panelNorth(), panelSouth());
			panel.setDividerLocation(0.5);
			this.setContentPane(panel);
			this.setVisible(true);
	}
	
	public JSplitPane getPanel(){
		return this.panel;
	}
	
	private JSplitPane panelNorthEast(){
		JSplitPane northEast;
		String url;
		if(this.manual){
			url ="/img/manual.jpg";
		}else{
			url	="/img/parabolic.jpg";
		}
		ImageIcon imgManual = new ImageIcon(getClass().getResource(url));
		JButton modeButton = new JButton(scaleIcon(imgManual,300,30));
		modeButton.setBorder(BorderFactory.createEmptyBorder());
		modeButton.addActionListener(changeMode());
		
		if(this.manual){
			
			northEast = new JSplitPane( JSplitPane.VERTICAL_SPLIT, 
               modeButton, panelNorthEastSouthManual());
		}else{
			northEast = new JSplitPane( JSplitPane.VERTICAL_SPLIT, 
		               modeButton, panelNorthEastSouthParabolic());
		}
		
		northEast.setDividerLocation(0.2);
		
		return northEast;
	}
	

	private JSplitPane panelNorthEastSouthParabolic(){
		JButton startFlight = new JButton("Start Parabolic Flight");
		
		JButton endFlight = new JButton("End Flight");
		
		JSplitPane buttonsPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT, 
                startFlight, endFlight);
		
		endFlight.addActionListener(abortFlight());
		startFlight.addActionListener(startFlight());;
		
		return buttonsPanel;
	}
	
	private JPanel panelNorthEastSouthManual(){
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(panelUpDown(),BorderLayout.WEST);
		panel.add(panelButtonsMove(),BorderLayout.EAST);
		return panel;	
	}
	
	private JPanel panelButtonsMove(){
		JPanel buttonsPanel = new JPanel(new BorderLayout());
		
		ImageIcon imgRight = new ImageIcon(getClass().getResource("/img/right.gif"));
		JButton moveRightButton = new JButton(scaleIcon(imgRight, 50, 50));
		moveRightButton.setPressedIcon(scaleIcon(new ImageIcon(getClass().getResource("/img/rightPushed.gif")),50,50));
		moveRightButton.setBorder(BorderFactory.createEmptyBorder());
		
		ImageIcon imgLeft = new ImageIcon(getClass().getResource("/img/left.gif"));
		JButton moveLeftButton = new JButton(scaleIcon(imgLeft, 50, 50));
		moveLeftButton.setPressedIcon(scaleIcon(new ImageIcon(getClass().getResource("/img/leftPushed.gif")),50,50));
		moveLeftButton.setBorder(BorderFactory.createEmptyBorder());
		
		ImageIcon imgBack = new ImageIcon(getClass().getResource("/img/back.gif"));
		JButton moveBackButton = new JButton(scaleIcon(imgBack, 50, 50));
		moveBackButton.setPressedIcon(scaleIcon(new ImageIcon(getClass().getResource("/img/backPushed.gif")),50,50));
		moveBackButton.setBorder(BorderFactory.createEmptyBorder());
		
		ImageIcon imgForward = new ImageIcon(getClass().getResource("/img/forward.gif"));
		JButton moveForwardButton = new JButton(scaleIcon(imgForward, 50, 50));
		moveForwardButton.setPressedIcon(scaleIcon(new ImageIcon(getClass().getResource("/img/forwardPushed.gif")),50,50));
		moveForwardButton.setBorder(BorderFactory.createEmptyBorder());
		
		ImageIcon imgPlain = new ImageIcon(getClass().getResource("/img/plain.gif"));
		centerButton = new JButton(scaleIcon(imgPlain, 50, 50));
		centerButton.setBorder(BorderFactory.createEmptyBorder());
		
		
		moveRightButton.addMouseListener(new MoveDrone("right"));
		
		
		moveLeftButton.addMouseListener(new MoveDrone("left"));
		
		
		moveBackButton.addMouseListener(new MoveDrone("back"));
		
		
		moveForwardButton.addMouseListener(new MoveDrone("forward"));

		
		buttonsPanel.add(moveForwardButton,BorderLayout.NORTH);
		buttonsPanel.add(centerButton, BorderLayout.CENTER);
		buttonsPanel.add(moveRightButton, BorderLayout.EAST);
		buttonsPanel.add(moveLeftButton, BorderLayout.WEST);
		buttonsPanel.add(moveBackButton,BorderLayout.SOUTH);
		
		return buttonsPanel;
	}
	
	private JPanel panelUpDown(){
		JPanel updown = new JPanel( new BorderLayout());
		
		ImageIcon imgUp = new ImageIcon(getClass().getResource("/img/up.gif"));
		JButton moveUpButton = new JButton(scaleIcon(imgUp,50,50));
		//moveUpButton.setPressedIcon(scaleIcon(imgLeft,50,50));
		
		moveUpButton.setBorder(BorderFactory.createEmptyBorder());
		ImageIcon imgDown = new ImageIcon(getClass().getResource("/img/down.gif"));
		JToggleButton moveDownButton = new JToggleButton(scaleIcon(imgDown,50,50));
		moveDownButton.setBorder(BorderFactory.createEmptyBorder());
		//moveDownButton.setPressedIcon(scaleIcon(imgLeft,50,50));
		
		moveUpButton.addMouseListener(new MoveDrone("up"));
	
		moveDownButton.addMouseListener(new MoveDrone("down"));
		
		updown.add(moveUpButton, BorderLayout.NORTH);
		updown.add(moveDownButton, BorderLayout.SOUTH);
		
		return updown;
	}
	
	private JPanel panelNorthWest(){
		JPanel west = new JPanel(new BorderLayout());
		JButton rcvGPSButton = new JButton("Receive GPS Data");
		JButton connectionTestButton = new JButton("Connection Test");
		
		rcvGPSButton.setSize(75, 35);
		rcvGPSButton.addActionListener(gpsData());
		
		connectionTestButton.setSize(75, 35);
		connectionTestButton.addActionListener(connectionTest());
		
		
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
	
	private void rePaintJFrame(){
		this.manual = !this.manual;
		this.dispose();
		this.setContentPane(new CreateWindow(this.manual, this.connexion));
    	this.repaint();
	}
	
	/*
	 * ******************ACTION LISTENERS METHODS**************************
	 */
	
	private ActionListener changeMode() {
        ActionListener action = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	rePaintJFrame();
            }
        };
        return action;
    }
	
	private ActionListener gpsData() {
        ActionListener action = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	System.out.println("Sending GPS data, waiting for data,...");
            	//connexion.sendData("gps");
            }
        };
        return action;
    }
	
	private ActionListener connectionTest() {
        ActionListener action = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	System.out.println("Connection test, waiting for data,...");
            	//connexion.sendData("ping");
            	
            }
        };
        return action;
    }
	
	
	private ActionListener startFlight() {
        ActionListener action = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	System.out.println("Start Zero-G flight");
            	//connexion.sendData("gps");
            }
        };
        return action;
    }
	private ActionListener abortFlight() {
	
        ActionListener action = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	System.out.println("Abort Zero-G flight");
            	//connexion.sendData("gps");
            }
        };
        return action;
    }
	
	/*
	 * 					CREATE PRIVATE CLASS TO MOVE DRONE
	 */
	private class MoveDrone implements MouseListener{
		String move=null;
		boolean pressed;
		
		public MoveDrone(String move){
			this.move=move;
		}
		

		@Override
        public void mousePressed(MouseEvent e) {
        	
        	switch(this.move){
            	case "back":
            		pressed=true;
            		new Thread() {
                        public void run() {
		            		while(pressed){
			            		System.out.println("Going back");
			            		//connexion.sendData("back");
		            		}
                        }
            		}.start();
            	break;
            	
            	case "forward":
            		pressed=true;
            		new Thread() {
                        public void run() {
		            		while(pressed){
			            		System.out.println("Going forward");
			            		//connexion.sendData("forward");
		            		}
                        }
            		}.start();
            	break;
        	
            	case "right":
            		pressed=true;
            		new Thread() {
                        public void run() {
		            		while(pressed){
			            		System.out.println("Going right");
			            		//connexion.sendData("right");
		            		}
                        }
            		}.start();
            	break;
        		
            	case "left":
            		pressed=true;
            		new Thread() {
                        public void run() {
		            		while(pressed){
			            		System.out.println("Going left");
			            		//connexion.sendData("Left");
		            		}
                        }
            		}.start();
            	break;
            	
            	case "up":
            		pressed=true;
            		new Thread() {
                        public void run() {
		            		while(pressed){
			            		System.out.println("Up");
			            		//connexion.sendData("up");
		            		}
                        }
            		}.start();
            	break;
        	
            	case "down":
            		pressed=true;
            		new Thread() {
                        public void run() {
		            		while(pressed){
			            		System.out.println("down");
			            		//connexion.sendData("down");
		            		}
                        }
            		}.start();
            	break;
            	
            	default:
            		System.out.println("Order unknown");
            	break;
        	}
		}
		
		@Override
		public void mouseReleased(MouseEvent e) {
			pressed=false;
			
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
    }
	
}
