package boxnow.main;

import java.net.URL;

import java.util.ResourceBundle;

import boxnow.config.Config;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class BoxNowController implements Initializable {
		
	@FXML AnchorPane anchorContent;
	@FXML AnchorPane panelContent;
	
	@FXML Rectangle rectNumRound;
	
	@FXML Circle lightRed;
	@FXML Circle lightYellow;
	@FXML Circle lightGreen;
	
	@FXML Label lblTitleRound;
	@FXML Label lblRound;
	@FXML Label lblBckRound;
	
	@FXML Label lblTitleTimer;
	@FXML Label lblTimer;
	@FXML Label lblBckTimer;
	
	@FXML Label lblTitleTotal;
	@FXML Label lblTotal;
	@FXML Label lblBckTotal;
	
	private Config config;
	private String timer;
	private int numRounds;
	private int preparation;
	private int rest;
	private int durRound;
	private int warning;
	private Timeline timeline;
	
	private String status;
	private String current;
	private int currTime;
	private int currRound;
	private int currTotal;
	
	private AudioClip clipStart;
	private AudioClip clipWarning;
	private AudioClip clipEnd;

	@Override
	public void initialize(URL url, ResourceBundle rb) {		
		
		rectNumRound.setArcHeight(20);
		rectNumRound.setArcWidth(20);
		
		setConfig(new Config());
		getConfig().getPropertiesFromFile();
		
		setAudios();
		
		setTimer("timer1");
		setTimerProperties();
		setStatus("initial");
		
		iniTimeline();
				
	}
	
	private void setAudios() {
				
		setClipStart(new AudioClip(getClass().getResource("/boxnow/resources/sounds/real/start_round.wav").toString()));
		setClipEnd(new AudioClip(getClass().getResource("/boxnow/resources/sounds/real/end_round.wav").toString()));
		setClipWarning(new AudioClip(getClass().getResource("/boxnow/resources/sounds/real/warning.mp3").toString()));
		
	}

	private void setTimerProperties() {
		
		setNumRounds(Integer.parseInt(getConfig().getConfigProperties().getProperty(getTimer() + ".numberround")));
		setPreparation(Integer.parseInt(getConfig().getConfigProperties().getProperty(getTimer() + ".preparation")));
		setRest(Integer.parseInt(getConfig().getConfigProperties().getProperty(getTimer() + ".rest")));
		setDurRound(Integer.parseInt(getConfig().getConfigProperties().getProperty(getTimer() + ".roundduration")));
		setWarning(getDurRound() - Integer.parseInt(getConfig().getConfigProperties().getProperty(getTimer() + ".warning")));
		
	}

	public Config getConfig() {
		return config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}
	
	public void iniTimeline(){
		
		final Timeline timeline = new Timeline(
			new KeyFrame(Duration.ZERO, new EventHandler<ActionEvent>(){
				boolean ended = false;
				int i = 0;
				@Override
				public void handle(ActionEvent event){					
					
					setTotalTime();
					
					switch(getCurrent()){					
					case "PREPARATION":						
						if(getCurrTime() == getPreparation()){
							setCurrent("ROUND");
							setCurrRound(getCurrRound() + 1);
							setCurrentRound();
							setCurrTime(0);
							turnOnGreen();
							turnOffRed();
							getClipStart().play();
						}
						break;
						
					case "ROUND":
						if(getCurrTime() == getDurRound()){
							setCurrent("REST");
							setCurrTime(0);
							turnOffGreen();
							turnOffYellow();
							turnOnRed();
							if(getCurrRound() == getNumRounds()){
								ended = true;
							}
							getClipEnd().play();
						} else if(getCurrTime() == getWarning()){
							getClipWarning().play();
							turnOnYellow();
						}						
						break;
					case "REST":
						if(getCurrTime() == getRest()){
							setCurrent("ROUND");
							setCurrRound(getCurrRound() + 1);
							setCurrentRound();
							setCurrTime(0);
							turnOnGreen();
							turnOffRed();
							getClipStart().play();
						}
						break;
					}
					
					setCurrentTime();
					
					if(!ended){
						setCurrTime(getCurrTime() + 1);
						setCurrTotal(getCurrTotal() + 1);					
					} else {					
						
						if(i == 0){
							setCurrentTime();
							setCurrentRound();
							turnOnRed();
							turnOnYellow();
							turnOnGreen();							
							
						} else if(i == 1){
							lblTimer.setText("");
							lblRound.setText("");
							turnOffRed();
							turnOffYellow();
							turnOffGreen();
							
						} else if(i == 2){
							setCurrentTime();
							setCurrentRound();
							turnOnRed();
							turnOnYellow();
							turnOnGreen();
							
						} else if(i == 4){
							lblTimer.setText("");
							lblRound.setText("");
							turnOffRed();
							turnOffYellow();
							turnOffGreen();
							
						} else if(i == 5){
							setCurrentTime();
							setCurrentRound();
							turnOnRed();
							turnOnYellow();
							turnOnGreen();
							
						} else if(i == 6){
							lblTimer.setText("");
							lblRound.setText("");
							turnOffRed();
							turnOffYellow();
							turnOffGreen();
							
						} else if(i == 7){
							setCurrentTime();
							setCurrentRound();
							turnOnRed();
							turnOnYellow();
							turnOnGreen();	
							setStatus("initial");
							ended = false;
							getTimeline().stop();
						}
						i++;
					}					
														
				}
			}), new KeyFrame(Duration.seconds(1)));
		timeline.setCycleCount(Timeline.INDEFINITE);
		
		setTimeline(timeline);
		
	}
	
	public void setCurrentTime(){
		
		int mm = 0;
		int ss = 0;		
		String lblText = "";
		
		ss = getCurrTime();
		
		if(ss > 59){
			mm = ss / 60;
			ss = ss - (mm * 60);
		}
		
		lblText = mm + ":" + inputZero(ss);
		lblTimer.setText(lblText);
		
	}
	
	public void setTotalTime(){
		
		int hh = 0;
		int mm = 0;
		int ss = 0;		
		String lblText = "";
		
		ss = getCurrTotal();
		
		if(ss > 3599){
			hh = ss / 3600;
			ss = ss - (hh * 3600);
		}
		
		if(ss > 59){
			mm = ss / 60;
			ss = ss - (mm * 60);
		}			
		
		lblText = inputZero(hh) + ":" + inputZero(mm) + ":" + inputZero(ss);
		lblTotal.setText(lblText);
	}
	
	public String inputZero(int i){
		String output = "";
		
		if(i<10)
			output = "0" + i;
		else
			output = String.valueOf(i);
		
		return output;
	}
	
	public void setCurrentRound(){
		if(getCurrRound()<10)
			lblRound.setText(" " + getCurrRound());
		else
			lblRound.setText(String.valueOf(getCurrRound()));
	}
	
	@FXML
	public void actionTimer1(ActionEvent event){
		if(!getStatus().equals("running")){
			setTimer("timer1");
			System.out.println("Timer 1 selected");
			lblTotal.setText("SE LE  1");
			setTimerProperties();
			setStatus("initial");
		} else {
			System.out.println("It's running! first stop the timer!");
		}		
	}
	
	@FXML
	public void actionTimer2(ActionEvent event){
		if(!getStatus().equals("running")){
			setTimer("timer2");
			System.out.println("Timer 2 selected");
			lblTotal.setText("SE LE  2");
			setTimerProperties();
			setStatus("initial");
		} else {
			System.out.println("It's running! first stop the timer!");
		}		
	}
	
	@FXML
	public void actionTimer3(ActionEvent event){
		if(!getStatus().equals("running")){
			setTimer("timer3");
			System.out.println("Timer 3 selected");
			lblTotal.setText("SE LE  3");
			setTimerProperties();
			setStatus("initial");
		} else {
			System.out.println("It's running! first stop the timer!");
		}		
	}
	
	@FXML
	public void actionTimer4(ActionEvent event){
		if(!getStatus().equals("running")){	
			setTimer("timer4");
			System.out.println("Timer 4 selected");
			lblTotal.setText("SE LE  4");
			setTimerProperties();
			setStatus("initial");
		} else {
			System.out.println("It's running! first stop the timer!");
		}
	}
	
	@FXML
	public void actionTimer5(ActionEvent event){
		if(!getStatus().equals("running")){	
			setTimer("timer5");
			System.out.println("Timer 5 selected");
			lblTotal.setText("SE LE  5");
			setTimerProperties();
			setStatus("initial");
		} else {
			System.out.println("It's running! first stop the timer!");
		}
	}
	
	@FXML
	public void actionTimer6(ActionEvent event){
		if(!getStatus().equals("running")){		
			setTimer("timer6");
			System.out.println("Timer 6 selected");
			lblTotal.setText("SE LE  6");
			setTimerProperties();
			setStatus("initial");		
		} else {
			System.out.println("It's running! first stop the timer!");
		}
	}

	@FXML
	public void actionStart(ActionEvent event){
		
		if(getStatus().equals("initial")){		
			System.out.println("Starting " + getTimer());
			setStatus("running");		
			setCurrRound(0);
			setCurrTime(0);
			setCurrTotal(0);		
			
			if(getPreparation()>0){
				setCurrent("PREPARATION");
				turnOnRed();
				turnOffGreen();
				turnOffYellow();
			} else {
				setCurrRound(1);			
				setCurrent("ROUND");
				turnOnGreen();
				turnOffRed();
				turnOffYellow();
			}		
			lblRound.setText(" " + getCurrRound());
			lblTimer.setText("0:00");
			lblTotal.setText("00:00:00");
			getTimeline().play();
		} else {
			setStatus("running");
			getTimeline().play();
		}	
		
	}
	
	@FXML
	public void actionPause(ActionEvent event){
		System.out.println("Paused");
		setStatus("paused");
		getTimeline().pause();
	}
	
	@FXML
	public void actionStop(ActionEvent event){
		System.out.println("Stopped");
		setStatus("initial");
		setCurrTotal(0);
		setCurrTime(0);
		setCurrRound(0);
		lblRound.setText(" 0");
		lblTimer.setText("0:00");
		lblTotal.setText("00:00:00");
		getTimeline().stop();
	}

	@FXML
	public void actionConfig(ActionEvent event){
		System.out.println("Display config");
		getConfig().displayConfig();		
	}
	
	@FXML
	public void actionReset(ActionEvent event){
		System.out.println("Reset");
		setCurrTotal(0);
		setCurrTime(0);
		setCurrRound(0);
		lblRound.setText(" 0");
		lblTimer.setText("0:00");
		lblTotal.setText("00:00:00");
	}
	
	@FXML
	public void actionOff(ActionEvent event){
		System.out.println("Leaving BoxNow");
		System.exit(0);		
	}
	

	
	public void turnOnRed(){
		lightRed.getStyleClass().removeAll("light_red_off");
		lightRed.getStyleClass().add("light_red_on");
	}
	
	public void turnOffRed(){
		lightRed.getStyleClass().removeAll("light_red_on");
		lightRed.getStyleClass().add("light_red_off");
	}
	
	public void turnOnYellow(){
		lightYellow.getStyleClass().removeAll("light_yellow_off");
		lightYellow.getStyleClass().add("light_yellow_on");
	}
	
	public void turnOffYellow(){
		lightYellow.getStyleClass().removeAll("light_yellow_on");
		lightYellow.getStyleClass().add("light_yellow_off");
	}
	
	public void turnOnGreen(){
		lightGreen.getStyleClass().removeAll("light_green_off");
		lightGreen.getStyleClass().add("light_green_on");
	}
	
	public void turnOffGreen(){
		lightGreen.getStyleClass().removeAll("light_green_on");
		lightGreen.getStyleClass().add("light_green_off");
	}

	public String getTimer() {
		return timer;
	}

	public void setTimer(String timer) {
		this.timer = timer;
	}

	public int getNumRounds() {
		return numRounds;
	}

	public void setNumRounds(int numRounds) {
		this.numRounds = numRounds;
	}

	public int getPreparation() {
		return preparation;
	}

	public void setPreparation(int preparation) {
		this.preparation = preparation;
	}

	public int getRest() {
		return rest;
	}

	public void setRest(int rest) {
		this.rest = rest;
	}

	public int getDurRound() {
		return durRound;
	}

	public void setDurRound(int durRound) {
		this.durRound = durRound;
	}

	public Timeline getTimeline() {
		return timeline;
	}

	public void setTimeline(Timeline timeline) {
		this.timeline = timeline;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getCurrTime() {
		return currTime;
	}

	public void setCurrTime(int currTime) {
		this.currTime = currTime;
	}

	public int getCurrRound() {
		return currRound;
	}

	public void setCurrRound(int currRound) {
		this.currRound = currRound;
	}

	public int getCurrTotal() {
		return currTotal;
	}

	public void setCurrTotal(int currTotal) {
		this.currTotal = currTotal;
	}

	public String getCurrent() {
		return current;
	}

	public void setCurrent(String current) {
		this.current = current;
	}

	public int getWarning() {
		return warning;
	}

	public void setWarning(int warning) {
		this.warning = warning;
	}

	public AudioClip getClipStart() {
		return clipStart;
	}

	public void setClipStart(AudioClip clipStart) {
		this.clipStart = clipStart;
	}

	public AudioClip getClipWarning() {
		return clipWarning;
	}

	public void setClipWarning(AudioClip clipWarning) {
		this.clipWarning = clipWarning;
	}

	public AudioClip getClipEnd() {
		return clipEnd;
	}

	public void setClipEnd(AudioClip clipEnd) {
		this.clipEnd = clipEnd;
	}

}
