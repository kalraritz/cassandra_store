public class District {
	private String D_W_ID;
	private String D_ID;
	private String D_NAME;
	public String getD_W_ID() {
		return D_W_ID;
	}

	public void setD_W_ID(String d_W_ID) {
		D_W_ID = d_W_ID;
	}

	public String getD_ID() {
		return D_ID;
	}

	public void setD_ID(String d_ID) {
		D_ID = d_ID;
	}

	public String getD_NAME() {
		return D_NAME;
	}

	public void setD_NAME(String d_NAME) {
		D_NAME = d_NAME;
	}

	public String getD_STREET_1() {
		return D_STREET_1;
	}

	public void setD_STREET_1(String d_STREET_1) {
		D_STREET_1 = d_STREET_1;
	}

	public String getD_STREET_2() {
		return D_STREET_2;
	}

	public void setD_STREET_2(String d_STREET_2) {
		D_STREET_2 = d_STREET_2;
	}

	public String getD_CITY() {
		return D_CITY;
	}

	public void setD_CITY(String d_CITY) {
		D_CITY = d_CITY;
	}

	public String getD_STATE() {
		return D_STATE;
	}

	public void setD_STATE(String d_STATE) {
		D_STATE = d_STATE;
	}

	public String getD_ZIP() {
		return D_ZIP;
	}

	public void setD_ZIP(String d_ZIP) {
		D_ZIP = d_ZIP;
	}

	public String getD_TAX() {
		return D_TAX;
	}

	public void setD_TAX(String d_TAX) {
		D_TAX = d_TAX;
	}

	public String getD_YTD() {
		return D_YTD;
	}

	public void setD_YTD(String d_YTD) {
		D_YTD = d_YTD;
	}

	public String getD_NEXT_O_ID() {
		return D_NEXT_O_ID;
	}

	public void setD_NEXT_O_ID(String d_NEXT_O_ID) {
		D_NEXT_O_ID = d_NEXT_O_ID;
	}

	private String D_STREET_1;
	private String D_STREET_2;
	private String D_CITY;
	private String D_STATE;
	private String D_ZIP;
	private String D_TAX;
	private String D_YTD;
	private String D_NEXT_O_ID;
	
	public District(String d_W_ID, String d_ID, String d_NAME, String d_STREET_1, String d_STREET_2, String d_CITY,
			String d_STATE, String d_ZIP, String d_TAX, String d_YTD, String d_NEXT_O_ID) {
		super();
		D_W_ID = d_W_ID;
		D_ID = d_ID;
		D_NAME = d_NAME;
		D_STREET_1 = d_STREET_1;
		D_STREET_2 = d_STREET_2;
		D_CITY = d_CITY;
		D_STATE = d_STATE;
		D_ZIP = d_ZIP;
		D_TAX = d_TAX;
		D_YTD = d_YTD;
		D_NEXT_O_ID = d_NEXT_O_ID;
	}
}