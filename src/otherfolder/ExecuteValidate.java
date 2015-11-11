package otherfolder;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import mydtos.LoginDto;
import validations.Fields;
import validations.StepValidator;
import validations.responsehandlers.ValidationResponse;
import validations.stepvalidators.EmailValidator;
import validations.stepvalidators.NumberValidator;
import validations.stepvalidators.StringLengthValidator;
import validations.validationengines.LoginDtoValidationEngine;

public class ExecuteValidate {

	public static void main(String[] args) throws NoSuchFieldException, SecurityException, Exception {
		
		LoginDto login = new LoginDto("1d", "myanme", new Date(), "1partha@gmail.com.", "998876654a", 124L);
		LoginDtoValidationEngine validationEngine = new LoginDtoValidationEngine();
		ValidationResponse response = validationEngine.checkForMandatoryFields(login, LoginDto.LoginDtoFields.id, LoginDto.LoginDtoFields.name, LoginDto.LoginDtoFields.now, LoginDto.LoginDtoFields.someOtherId);
		System.out.println(response);
		
		Map<Fields, StepValidator[]> rulesMap = new HashMap<>();
		StepValidator[] emailSteps = {new EmailValidator(login.emailId)};
		StepValidator[] phoneNoSteps = {new NumberValidator(login.phoneNo), new StringLengthValidator(login.phoneNo, 0)};
		
		rulesMap.put(LoginDto.LoginDtoFields.emailId, emailSteps);
		rulesMap.put(LoginDto.LoginDtoFields.phoneNo, phoneNoSteps);
		
		ValidationResponse validationResponse = validationEngine.validate(rulesMap);
		System.out.println(validationResponse);
		
	}
}
