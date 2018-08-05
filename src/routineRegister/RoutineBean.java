package routineRegister;

public class RoutineBean {
	
	String Mobile;
	String CQ;
	String BQ;
	String Month;
	String Year;
	String Date;

	public RoutineBean(String mobile, String cQ, String bQ, String month, String year, String date) {
		super();
		Mobile = mobile;
		CQ = cQ;
		BQ = bQ;
		Month = month;
		Year = year;
		Date = date;
	}

	public RoutineBean() {
		
	}

	public String getMobile() {
		return Mobile;
	}

	public void setMobile(String mobile) {
		Mobile = mobile;
	}

	public String getCQ() {
		return CQ;
	}

	public void setCQ(String cQ) {
		CQ = cQ;
	}

	public String getBQ() {
		return BQ;
	}

	public void setBQ(String bQ) {
		BQ = bQ;
	}

	public String getMonth() {
		return Month;
	}

	public void setMonth(String month) {
		Month = month;
	}

	public String getYear() {
		return Year;
	}

	public void setYear(String year) {
		Year = year;
	}

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

	
	
	
}
