package mydtos;

import java.util.Date;

import validations.Fields;

public class LoginDto{

	public String id;
	public String name;
	public Date now;
	public String emailId;
	public String phoneNo;
	public Long someOtherId;

	
	public LoginDto() {}
	
	public LoginDto(String id, String name, Date now, String emailId, String phoneNo, Long someOtherId ) {
		this.id = id;
		this.name = name;
		this.now = now;
		this.emailId = emailId;
		this.phoneNo = phoneNo;
		this.someOtherId = someOtherId;
	}
	
	
	public static enum LoginDtoFields implements Fields {
		id,
		name,
		now,
		emailId,
		phoneNo,
		someOtherId;
		
		@Override
		public String getStringValue() {
			return this.name();
		}
	}
}
