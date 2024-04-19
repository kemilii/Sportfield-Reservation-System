package reservationSystem.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Record {

	private StringProperty classOrSubject;
	private StringProperty name;
	private StringProperty sport;
	private StringProperty place;
	private StringProperty reserveDate;
	private StringProperty reserveTime;
	
	public Record() {
		this.classOrSubject = new SimpleStringProperty();
		this.name = new SimpleStringProperty();
		this.sport = new SimpleStringProperty();
		this.place = new SimpleStringProperty();
		this.reserveDate = new SimpleStringProperty();
		this.reserveTime = new SimpleStringProperty();
	}

	public Record(String classOrSubject, String name, String sport, String place,
			String reserveDate, String reserveTime) {
		this.classOrSubject = new SimpleStringProperty(classOrSubject);
		this.name = new SimpleStringProperty(name);
		this.sport = new SimpleStringProperty(sport);
		this.place = new SimpleStringProperty(place);
		this.reserveDate = new SimpleStringProperty(reserveDate);
		this.reserveTime = new SimpleStringProperty(reserveTime);
	}

	public String getClassOrSubject() {
		return classOrSubject.get();
	}
	
	public StringProperty classOrSubjectProperty() {
		return classOrSubject;
	}
	
	public void setClassOrSubject(String classOrSubject) {
		this.classOrSubject.set(classOrSubject);
	}

	public String getName() {
		return name.get();
	}
	
	public StringProperty nameProperty() {
		return name;
	}

	public void setName(String name) {
		this.name.set(name);;
	}

	public String getSport() {
		return sport.get();
	}
	
	public StringProperty sportProperty() {
		return sport;
	}

	public void setSport(String sport) {
		this.sport.set(sport);
	}

	public String getPlace() {
		return place.get();
	}
	
	public StringProperty placeProperty() {
		return place;
	}

	public void setPlace(String place) {
		this.place.set(place);
	}

	public String getReserveDate() {
		return reserveDate.get();
	}
	
	public StringProperty reserveDateProperty() {
		return reserveDate;
	}

	public void setReserveDate(String reserveDate) {
		this.reserveDate.set(reserveDate);;
	}

	public String getReserveTime() {
		return reserveTime.get();
	}
	
	public StringProperty reserveTimeProperty() {
		return reserveTime;
	}

	public void setReserveTime(String reserveTime) {
		this.reserveTime.set(reserveTime);;
	}	
}
