package utilities;

public class Validator {
    private static final String EMAIL_PATTERN = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private static final String NAME_PATTERN = "[a-zA-Z]+";

    public static boolean validateFirstName(String firstName) {
        return firstName.length() > 0 && firstName.matches(NAME_PATTERN);
    }

    public static boolean validateLastName(String lastName) {
        return lastName.length() > 0 && lastName.matches(NAME_PATTERN);
    }

    public static boolean validateEmail(String email) {
        return email.length() > 5 && email.matches(EMAIL_PATTERN);
    }

    public static boolean validatePassword(String password) {
        return password.length() > 7;
    }

    public static boolean validateConfirmPassword(String password, String confirmPassword) {
        return validatePassword(password) && password.equals(confirmPassword);
    }
}
