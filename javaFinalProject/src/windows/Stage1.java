package windows;

import items.*;
import aircraft.*;
import destination.*;
import disturbance.*;
import disturbance.Spring;

import javax.swing.*;
import java.awt.event.*;

public class Stage1 extends Scene implements ActionListener {
	
	public Stage1() {

		super();

		// Timer
		timer = new Timer(10, this);
		
		// declare concrete factory
		factory = new Stage1Factory();
		
		// create person
		persons = factory.createPerson();
		for (int i = 0; i < persons.size(); ++i)
			imagePanel.add(persons.get(i).lb);
		
		// create aircraft
		aircrafts = factory.createAircraft();
		for (int i = 0; i < aircrafts.size(); ++i)
			imagePanel.add(aircrafts.get(i).lb);
		
		// create destination
		destinations = factory.createDestination();
		for (int i = 0; i < destinations.size(); ++i) {
			imagePanel.add(destinations.get(i).lbSuccess);
			imagePanel.add(destinations.get(i).lbFail);
			destinations.get(i).lbSuccess.setVisible(false);
			destinations.get(i).lbFail.setVisible(false);
			imagePanel.add(destinations.get(i).lb);
		}
		
		// create disturbance
		disturbances = factory.createDisturbance();
		for (int i = 0; i < disturbances.size(); ++i)
			imagePanel.add(disturbances.get(i).lb);
			
		// set background
		bgImagePath = "https://i.imgur.com/NEDwmd1.png";
		setWindow(bgImagePath);
		
		// start timer
		timer.start();
	}
	
	@Override
	public Scene getCurrentStage() {
		return new Stage1();
	}

	@Override
	public Scene getNextStage() {
		return new Stage2();
//		return new EndScene();
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		wp.brush();
		for (int i = 0; i < persons.size(); ++i) {
			persons.get(i).move();
		}
		for (int i = 0; i < aircrafts.size(); ++i) {
			aircrafts.get(i).move();
			if (aircrafts.get(i).getPositionX() > bgWidth) {
				aircrafts.get(i).setPositionX(-aircrafts.get(i).imageWidth);
			}
		}
		for (int i = 0; i < disturbances.size(); ++i) {
			disturbances.get(i).effect(persons);
		}
		for (int i = 0; i < destinations.size(); ++i) {
			destinations.get(i).effect(persons, this);
		}
	}

}
