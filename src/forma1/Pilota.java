package forma1;

public class Pilota {
	private String name;

	private String nationCode;
	private Integer startNum;

	public Pilota(int startNum,String nationCode,String name) {
		this.name = name;
		this.nationCode = nationCode;
		this.startNum = startNum;
	}

	public String getName() {
		return name;
	}

	public String getNationCode() {
		return nationCode;
	}

	public Integer getStartNum() {
		return startNum;
	}

}
