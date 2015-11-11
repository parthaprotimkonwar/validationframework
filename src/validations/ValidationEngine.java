package validations;

import static validations.ValidationConstants.JAVA_LANG_LONG;
import static validations.ValidationConstants.JAVA_LANG_STRING;
import static validations.ValidationConstants.JAVA_UTIL_DATE;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import validations.responsehandlers.ErrorMessage;
import validations.responsehandlers.ValidationResponse;

/**
 * 
 * Contains all Validation Methods defined here 
 * @author pkonwar
 *
 */
public abstract class ValidationEngine implements ValidationLifecycle{

	private final Map<String, Map<String, Object>> convertObjectToMapWithFields(Object object) throws IllegalArgumentException, IllegalAccessException {
		
		Map<String, Map<String, Object>> mapRepresentation = new HashMap<>();
		
		Field[] field = object.getClass().getFields();
		for(Field f : field) {
			Class<?> clazz = f.getType();
			String name = f.getName();
			Object value = f.get(object);
			Map<String, Object> values = new HashMap<>();
			values.put(ValidationConstants.CLASS_NAME, clazz);
			values.put(ValidationConstants.VALUE, value);
			mapRepresentation.put(name, values);
		}
		return mapRepresentation;
	}
	
	public ValidationResponse validate(Map<Fields, StepValidator[]> additionalRules) throws NoSuchFieldException, SecurityException, Exception {
		List<ErrorMessage> errorMessage = new ArrayList<>();
		ValidationResponse response = null;
		
		for(Map.Entry<Fields, StepValidator[]> oneFieldRules : additionalRules.entrySet()) {
			Fields keyField = oneFieldRules.getKey();
			StepValidator[] steps = oneFieldRules.getValue();
			try {
				validateAllSteps(steps);
			} catch(Exception ex) {
				ErrorMessage error = new ErrorMessage("101", keyField.getStringValue() + " : " + ex.getMessage());
				errorMessage.add(error);
			}
		}
		
		if(!errorMessage.isEmpty()) {
			response = new ValidationResponse(false, errorMessage);
		} else {
			response = new ValidationResponse(true, null);
		}
		return response;
	}
	
	private Boolean validateAllSteps(StepValidator[] steps) throws Exception{
		
		for(StepValidator oneStep : steps) {
			boolean isValid = oneStep.executeValidation();
			if(!isValid)
				throw new Exception(oneStep.getStepName() + " failed");
		}
		return true;
	}
	
	@Override
	public ValidationResponse checkForMandatoryFields(Object object, Fields... fields) throws NoSuchFieldException, SecurityException, Exception {

		Map<String, Map<String, Object>> mapRepresentation = convertObjectToMapWithFields(object);
		List<ErrorMessage> errorMessage = new ArrayList<>();
		ValidationResponse response = null;
		
		for(Fields aField : fields) {
			Map<String, Object> fieldValues = mapRepresentation.get(aField.getStringValue());
			Class<?> clazz = (Class<?>) fieldValues.get(ValidationConstants.CLASS_NAME);
			Object value = fieldValues.get(ValidationConstants.VALUE);
			Boolean isValid = false;
			
			switch (clazz.getName()) {
				case JAVA_LANG_STRING:
					String actualString = value.toString();
					isValid = ValidationUtil.stringNotNullOrEmpty(actualString);
					if(!isValid) { 
						ErrorMessage error = new ErrorMessage("100", aField.getStringValue() + " is Null");
						errorMessage.add(error);
					}
					break;
				
				case JAVA_UTIL_DATE:
					try {
						Date actualDate = (Date) value;
					} catch (Exception e) {
						ErrorMessage error = new ErrorMessage("100", aField + "is Not a Valid Date");
						errorMessage.add(error);
					}
					break;
					
				case JAVA_LANG_LONG:
					try {
						Long actualLong = (Long) value;
					} catch (Exception e) {
						ErrorMessage error = new ErrorMessage("100", aField + "is Not a Valid Long");
						errorMessage.add(error);
					}
					break;
					
				default:
					System.out.println(clazz.getName());
					break;
			}
		}
		
		if(!errorMessage.isEmpty()) {
			response = new ValidationResponse(false, errorMessage);
		} else {
			response = new ValidationResponse(true, null);
		}
		return response;
	}
}
