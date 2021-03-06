package disturbance;

import items.*;
import windows.Scene;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.swing.Timer;

public class FireBall extends Disturbance implements ActionListener {

	private static final long serialVersionUID = -2175622363441527169L;
	boolean isStop = false;
	int waitTime = 0;
	Timer timer;
	double resetX;
	double resetY;

	public FireBall(double x, double y, double vx, double vy, double ax, double ay, String imagePath) {
		super(x, y, vx, vy, ax, ay);
		imageWidth = 100;
		imageHeight = 100;
		timer = new Timer(9, this);
		setImage(x, y, imageWidth, imageHeight, imagePath);
		timer.start();
	}

	public void move() {
	}

	@Override
	public void effect(ArrayList<Items> items, Scene currentScene) {
		ArrayList<Items> persons = items.stream().filter(i -> i instanceof Person).collect(Collectors.toCollection(ArrayList::new));
		ArrayList<Items> aircrafts = items.stream().filter(i -> i instanceof Aircraft).collect(Collectors.toCollection(ArrayList::new));
		resetX = aircrafts.get(0).getPositionX() - 20;
		resetY = aircrafts.get(0).getPositionY() + 50;
		for (Items item : persons) {
			Person person = (Person) item;
			// if getting shoot
			if (hasContactWithPerson(person)) {
				persons.get(0).setPositionX(persons.get(0).getPositionX() - 5);
				// relocated
				this.setPositionX(resetX);
				this.setPositionY(resetY);
				this.setLabelLocation((int) this.getPositionX(), (int) this.getPositionY());
				isStop = true;
				// set a random time to wait to restart
				waitTime = (int) (Math.random() * 100 + 1);
				if (person.isDropped)
					person.blood--;
			}

			// if out of bounds, relocated
			if (this.getPositionY() + 100 > 1000 || this.getPositionX() < -100) {
				this.setPositionX(resetX);
				this.setPositionY(resetY);
				this.setLabelLocation((int) this.getPositionX(), (int) this.getPositionY());

				isStop = true;
				// set a random time to wait to restart
				waitTime = (int) (Math.random() * 100 + 1);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// restart or move
		if (isStop && waitTime > 0) {
			waitTime--;
			this.setPositionX(100);
			this.setPositionY(-100);
		} else if (isStop == true && waitTime == 0) {
			this.setPositionX(resetX);
			this.setPositionY(resetY);
			isStop = false;
		} else {
			this.setPositionX(this.getPositionX() - 5);
			this.setPositionY(this.getPositionY() + 5);
		}
		this.setLabelLocation((int) this.getPositionX(), (int) this.getPositionY());
	}

	@Override
	public boolean hasContactWithPerson(Person person) {
		if ((this.getPositionX() + 100 > person.getPositionX()
				&& this.getPositionX() + 100 < person.getPositionX() + 100)
				&& (this.getPositionY() > person.getPositionY()
						&& this.getPositionY() < person.getPositionY() + 100))
			return true;
		return false;
	}
}