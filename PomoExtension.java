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

public class PomoExtension {
private static JFrame frame; 
private static JPanel panel;
private static double startTime, endTime;
private static FileWriter writer;
private static JLabel timeRemaining;
private static JButton startSession, endSession;
private static JTextField Subjects, SessionTimeLength; 
private static String SessionDate;
private static int timeLeft;
private static Timer timer = new Timer();
	public static void main(String[] args) throws IOException, InterruptedException  {

		FrameSetUp();
		
		SessionTimeLength = new JTextField("Enter Study Session Time (in minutes): ");
		SessionTimeLength.setBounds(260, 50, 300, 25);
		panel.add(SessionTimeLength);

		timeRemaining = new JLabel();
		timeRemaining.setBounds(260, 130, 300, 25);
		panel.add(timeRemaining);
		
		Subjects = new JTextField("Subjects: ");
		Subjects.setBounds(260, 300, 300, 50);
		panel.add(Subjects);
		
		startSession = new JButton("Start Session");
		startSession.setBounds(260, 10, 300, 25);
	    startSession.addActionListener(new ActionListener(){  
	        public void actionPerformed(ActionEvent e){  
	        	DateFormat startOfSession = new SimpleDateFormat("mm.ss");
		    	String timeOfSession = startOfSession.format(new Date()).toString();
				startTime = Double.parseDouble(timeOfSession);
				timeLeft = 60*Integer.parseInt(SessionTimeLength.getText());
				double TIMELEFTCopy = timeLeft;
				   timer.scheduleAtFixedRate(new TimerTask() {
			            public void run() {
			                timeRemaining.setText("Time left: " + timeLeft/60 + " Minutes " + timeLeft%60 + " Seconds");
			                timeLeft--;
			                if (timeLeft < 0) {
			                    timer.cancel();
			                    PlayDing();
			                    timeRemaining.setText("Time Over");
			                    logFile(startTime, startTime+(TIMELEFTCopy/60));
			                    EndFrame();
			                }}}, 0, 1000);}});  
		panel.add(startSession);
		
		endSession = new JButton("End Session");
		endSession.setBounds(260, 200, 300, 25);
	    endSession.addActionListener(new ActionListener(){  
	        public void actionPerformed(ActionEvent e){  
	        	DateFormat EndOfSession = new SimpleDateFormat("mm.ss");
		    	String timeOfSession = EndOfSession.format(new Date()).toString();
				endTime = Double.parseDouble(timeOfSession);
				timer.cancel();
                timeRemaining.setText("Time Over");
                logFile(startTime, endTime);
                PlayDing();
                EndFrame();
	        }});  
		panel.add(endSession);			
		frame.setVisible(true);		
	}
	public static void FrameSetUp() {
		frame = new JFrame();
		panel = new JPanel();
		frame.setSize(700,500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		panel.setLayout(null);
	}
	public static void PlayDing() {
		try{
			String filePath = HiddenInfo.filepathForSound; //File Path to Downloaded WAV
			PlaySound audioPlayer = new PlaySound(filePath);
			audioPlayer.play();
			System.out.println("Sound Played");
		}catch (Exception ex) {
			System.out.println("ERROR");
			ex.printStackTrace();
		}}
	public static void logFile(double starttime, double endtime) {
		try {
			writer = new FileWriter("LogPomoFile.txt", true);
			DateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy hh.mm aa");
	    	String dateString2 = dateFormat2.format(new Date()).toString();
	    	SessionDate = "This Session: "+dateString2 + " \n";
	    	String duration=String.format("%.02f", endtime-starttime);
	    	System.out.println(starttime);
	    	System.out.println(endtime);
	    	String SessionDuration = "This Session Lasted (min.sec): " + duration + " \n";
	    	String SessionSubjects=Subjects.getText();
	    	String newLine = "\n" + "\n";
	    	BufferedWriter bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write(SessionDate);
            bufferedWriter.write(SessionDuration);
            bufferedWriter.write(SessionSubjects);
            bufferedWriter.write(newLine);
            bufferedWriter.close();
		} catch (IOException e) {e.printStackTrace();}}
	public static void EndFrame() {
		JFrame Endframe = new JFrame();
		JPanel Endpanel = new JPanel();
		Endframe.setSize(700,700);
		Endframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Endframe.add(Endpanel);
		JLabel ThankYouForUsing = new JLabel("Thank You For Using My Timer!");
		ThankYouForUsing.setBounds(30, 50, 300, 25);
		Endpanel.add(ThankYouForUsing);
		Endpanel.setLayout(null);	
		Endframe.setVisible(true);
		}
}
