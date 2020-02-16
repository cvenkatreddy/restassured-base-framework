package validators;

import java.util.regex.Pattern;

import utilities.FrameworkConstants;

/**
 * 
 * Validator to verify the passed email is in valid format or not
 */
public class EmailValidator {

	public boolean isValid(String emailToVerify) 
    { 
        Pattern pat = Pattern.compile(FrameworkConstants.STANDARD_EMAIL_REGEX); 
        return pat.matcher(emailToVerify).matches(); 
    }
}

