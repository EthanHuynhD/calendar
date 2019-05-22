import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * @author EthanHuynh
 * DayViewPanel creates a day view of the calendar. This class is a customized JPanel.
 */
public class DayViewPanel extends JPanel implements ChangeListener{
	private DataModel dataModel;
	private ArrayList<Event> data;
	private LocalDate currentTime;
	private JLabel todaysDate = new JLabel();
	private ArrayList<JLabel> todaysLabels = new ArrayList<>();
	/**
	 * Constructs an instance of the DayViewPanel by taking in a dataModel.
	 * @param model receives a dataModel
	 */
	public DayViewPanel(DataModel model) {
		this.dataModel = model;
		data = this.dataModel.getData();
		currentTime = dataModel.getCalendar();
		setLayout(new GridLayout(23,2));
		setPreferredSize(new Dimension(800,800));
		viewByDay(currentTime);
		setBackground(Color.WHITE);
	}
	/**
	 * Fill in the panel with events happening in the day.
	 * @param local receives LocalDate
	 */
	public void viewByDay(LocalDate local) {
		ArrayList<String> todays = new ArrayList<>();
		ArrayList<String> todaysEvents= new ArrayList<>();
		todaysLabels = new ArrayList<>();
		currentTime = local;
		DateTimeFormatter yearFormatter = DateTimeFormatter.ofPattern("yy");
		int year = Integer.valueOf(yearFormatter.format(currentTime));
		// goes over the MyCalendar's arraylist and place events happening today on
		// today's list
		// initialize
		for(int i = 0; i <42;i++){
			todays.add(" ");
		}
		int count = 5;
		for(int i = 2; i < 42; i+=2) {
			if(count<10) {
				todays.set(i,"0"+count+":"+"00");
			}
			else {
				todays.set(i,count+":"+"00");
			}
			count++;
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E, MMMM d, yyyy");
		todaysDate.setText(formatter.format(currentTime));
		todays.set(0," ");
		todays.set(1, formatter.format(currentTime));
		for (Event s : data) {
			if ((currentTime.getDayOfWeek().name().equals(s.getDayOfWeek().toUpperCase())&&currentTime.getDayOfYear() == s.getStartingTotalDays() &&(currentTime.getYear() == s.getYear()||s.getYear() == year))
					|| currentTime.getDayOfWeek().name().equals(s.getDayOfWeek().toUpperCase())&&(currentTime.getDayOfYear() >= s.getStartingTotalDays()
							&& currentTime.getDayOfYear() <= s.getEndingTotalDays() &&(currentTime.getYear() == s.getYear()||s.getYear() == year))) {
				todaysEvents.add(s.getStartingHour()+"");
				todaysEvents.add(s.toString());
			}
		}
		if(!todaysEvents.isEmpty()) {
		for(int i = 0; i<todaysEvents.size();i+=2) {
			for(int j =2; j<todays.size();j+=2) {
				if(Integer.valueOf(todaysEvents.get(i))==Integer.valueOf(todays.get(j).substring(0,2))) {
					todays.set(j+1,todaysEvents.get(i+1));
				}
			}
		}}
		for(int i = 0; i <todays.size();i++) {
			JLabel label = new JLabel(todays.get(i));
			todaysLabels.add(label);
		}
		for(int i=0;i<todaysLabels.size();i++) {
			add(todaysLabels.get(i));
		}
	}
	/**
	 * Overrides the stateChanged method from the implementation of
	 * the ChangeListener's method. Receives notification from the data source.
	 * @param g receives the ChangeEvent from the dataModel.
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		this.data=dataModel.getData();
		this.currentTime = dataModel.getCalendar();
		removeAll();
		viewByDay(currentTime);
		repaint();
	}
	/**
	 * Overrides the paintComponent of the JPanel.
	 * @param g receives graphics
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		RoundRectangle2D box=new RoundRectangle2D.Double(todaysDate.getX(),todaysDate.getY()+12, 800, 20, 10, 10);
		Graphics2D g2 = (Graphics2D) g;
		g2.setPaint(Color.ORANGE);
		g2.fill(box);
	}

}
