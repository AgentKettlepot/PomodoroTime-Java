package BeginnerPomo;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.Timer;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Main {
private static JFrame frame; 
private static JPanel panel;
private static double startTime, endTime;
private static FileWriter writer;
private static JLabel timeRemaining;
private static JButton startSession, endSession;
private static JTextField Subjects, SessionTime; 
private static String SessionDate;
private static int timeLeft;
private static Timer timer = new Timer();
	public static void main(String[] args) throws IOException, InterruptedException  {

		BoringBeginning();
	
		SessionTime = new JTextField("Enter Study Session Time (in minutes): ");
		SessionTime.setBounds(720, 50, 720, 25);
		panel.add(SessionTime);

		timeRemaining = new JLabel();
		timeRemaining.setBounds(720, 130, 1000, 25);
		panel.add(timeRemaining);
		
		Subjects = new JTextField("Subjects");
		Subjects.setBounds(0, 720, 1910, 25);
		panel.add(Subjects);
		
		startSession = new JButton("Start Session");
		startSession.setBounds(720, 10, 100, 25);
	    startSession.addActionListener(new ActionListener(){  
	        public void actionPerformed(ActionEvent e){  
	        	DateFormat startOfSession = new SimpleDateFormat("mm.ss");
		    	String timeOfSession = startOfSession.format(new Date()).toString();
				startTime = Double.parseDouble(timeOfSession);
				System.out.println(startTime);
				timeLeft = 60*Integer.parseInt(SessionTime.getText());              
				   timer.scheduleAtFixedRate(new TimerTask() {

			            public void run() {

			                timeRemaining.setText("Time left: " + timeLeft/60 + " Minutes " + timeLeft%60 + " Seconds");
			                timeLeft--;

			                if (timeLeft < 0) {
			                    timer.cancel();
			                    timeRemaining.setText("Time Over");
			                }
			            }
			        }, 0, 1000);
	        }  
	        });  
		panel.add(startSession);
		
		endSession = new JButton("End Session");
		endSession.setBounds(720, 400, 100, 25);
	    endSession.addActionListener(new ActionListener(){  
	        public void actionPerformed(ActionEvent e){  
	        	DateFormat EndOfSession = new SimpleDateFormat("mm.ss");
		    	String timeOfSession = EndOfSession.format(new Date()).toString();
				endTime = Double.parseDouble(timeOfSession);
				System.out.println(endTime);
				timer.cancel();
                timeRemaining.setText("Time Over");
                logFile(startTime, endTime);
	        }  
	        });  
		panel.add(endSession);
			
		frame.setVisible(true);	
	
	}

	public static void BoringBeginning() {
		frame = new JFrame();
		panel = new JPanel();
		frame.setSize(1920,1080);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		panel.setLayout(null);
	}
	
	public static void logFile(double starttime, double endtime) {
		try {
			writer = new FileWriter("logFile.txt", true);
			DateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy hh.mm aa");//put this in start session
	    	String dateString2 = dateFormat2.format(new Date()).toString();
	    	SessionDate = "This Session: "+dateString2 + " \n";
	    	String duration=String.format("%.02f", endtime-starttime);
	    	System.out.println(starttime);
	    	System.out.println(endtime);
	    	String SessionDuration = "This Session Lasted: " + duration + " \n";
	    	String SessionSubjects=Subjects.getText();
	    	String newLine = "\n" + "\n";
	    	BufferedWriter bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write(SessionDate);
            bufferedWriter.write(SessionDuration);
            bufferedWriter.write(SessionSubjects);
            bufferedWriter.write(newLine);
            bufferedWriter.close();
		} catch (IOException e) {e.printStackTrace();}
	}
}
