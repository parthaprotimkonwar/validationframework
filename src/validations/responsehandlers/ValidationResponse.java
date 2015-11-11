package validations.responsehandlers;

import java.io.Serializable;
import java.util.List;

public class ValidationResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private Boolean isValidated;

	private List<ErrorMessage> errors;

	public ValidationResponse(Boolean isValidated, List<ErrorMessage> errors) {
		this.isValidated = isValidated;
		this.errors = errors;
	}
	
	public Boolean getIsValidated() {
		return isValidated;
	}

	public void setIsValidated(Boolean isValidated) {
		this.isValidated = isValidated;
	}

	public List<ErrorMessage> getErrors() {
		return errors;
	}

	public void setErrors(List<ErrorMessage> errors) {
		this.errors = errors;
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("isValidated : ").append(isValidated);
		if(errors != null) {
			for(ErrorMessage error : errors) {
				sb.append("[");
					sb.append("errorCode : ").append(error.getErrorCode()).append(",");
					sb.append("errorMessage : ").append(error.getErrorMessage());
				sb.append("]").append("\n");
			}
		}
		return sb.toString();
	}
	
}
