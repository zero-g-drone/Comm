package droneGUI;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.InputStream;
import java.io.OutputStream;



public class Connect {
	
	private int datarate;
	private final int TIMEOUT = 2000;
	private String port;
	private SerialPort serialPort;
	
	private OutputStream output;
	private InputStream input;
	
	public Connect(int datarate, String port){
		this.datarate = datarate;
		this.port = port;
	}
	
	public void startConnection() throws Exception {
		CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(port);
        if ( portIdentifier.isCurrentlyOwned() )
        {
            System.out.println("Error: Port is currently in use");
        }
        else
        {
            CommPort commPort = portIdentifier.open(this.getClass().getName(),TIMEOUT);
            
            if ( commPort instanceof SerialPort )
            {
                serialPort = (SerialPort) commPort;
                serialPort.setSerialPortParams(datarate,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);
                output = serialPort.getOutputStream();
                input = serialPort.getInputStream();
                recvDataThread();
            }
            else
            {
                System.out.println("Error: Only serial ports are handled.");
            }
        }
	}
	
	public void sendData(String data){
		try{
			output.write(data.getBytes());
		}catch( Exception e){
			e.printStackTrace();
		}
	}
	
	private void recvDataThread(){
		(new Thread(new SerialReader(input))).start();
	}
}
