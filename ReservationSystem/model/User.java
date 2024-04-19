package reservationSystem.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

public class User {
	private StringProperty classOrSubject;
	private StringProperty name;
	private ObservableList<Record> recordList;
	
	public User() {
		super();
	}
	
	public User(String classOrSubject, String name) {
        this.classOrSubject = new SimpleStringProperty(classOrSubject);
        this.name = new SimpleStringProperty(name);
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
	
	public ObservableList<Record> getRecordList() {
		return recordList;
	}
	
	public void setRecordList(ObservableList<Record> recordList) {
		this.recordList = recordList;
	}
}
