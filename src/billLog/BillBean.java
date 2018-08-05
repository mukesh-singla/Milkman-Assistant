package billLog;

public class BillBean {
	
	String Mobile;
	String CQty;
	String BQty;
	String CBill;
	String BBill;
	String TotalBill;
	String Status;
	String Month;
	String Year;
	
	public BillBean(String mobile, String cQty, String bQty, String cBill, String bBill, String totalBill, String status, String month, String year) {
		super();
		Mobile = mobile;
		CQty = cQty;
		BQty = bQty;
		CBill = cBill;
		BBill = bBill;
		TotalBill = totalBill;
		Status = status;
		Month = month;
		Year = year;
	}

	public String getMobile() {
		return Mobile;
	}

	public void setMobile(String mobile) {
		Mobile = mobile;
	}

	public String getCQty() {
		return CQty;
	}

	public void setCQty(String cQty) {
		CQty = cQty;
	}

	public String getBQty() {
		return BQty;
	}

	public void setBQty(String bQty) {
		BQty = bQty;
	}

	public String getCBill() {
		return CBill;
	}

	public void setCBill(String cBill) {
		CBill = cBill;
	}

	public String getBBill() {
		return BBill;
	}

	public void setBBill(String bBill) {
		BBill = bBill;
	}

	public String getTotalBill() {
		return TotalBill;
	}

	public void setTotalBill(String totalBill) {
		TotalBill = totalBill;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
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


	
}
