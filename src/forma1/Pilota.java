package forma1;

import java.time.LocalDate;


public class Pilota {
private String name;
private LocalDate dateOfBirth;
private String nation;
private Integer startNum;


public Pilota(String name, LocalDate dateOfBirth, String nation) {
	super();
	this.name = name;
	this.dateOfBirth = dateOfBirth;
	this.nation = nation;
	this.startNum = null;
}



public Pilota(String name, LocalDate dateOfBirth, String nation, int startNum) {
	super();
	this.name = name;
	this.dateOfBirth = dateOfBirth;
	this.nation = nation;
	this.startNum = startNum;
}



public String getName() {
	return name;
}



public LocalDate getDateOfBirth() {
	return dateOfBirth;
}



public String getNation() {
	return nation;
}



public Integer getStartNum() {
	return startNum;
}



}
