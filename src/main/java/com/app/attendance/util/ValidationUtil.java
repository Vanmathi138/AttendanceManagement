package com.app.attendance.util;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.app.attendance.enumeration.UserRole;

public class ValidationUtil {

    private ValidationUtil() {
    }

    public static boolean isNullOrEmpty(String value) {
        return null == value || value.trim().isEmpty();
    }
    
    public static boolean isNullOrEmpty(LocalDate date) {
        return date == null;
    }


    public static boolean isNull(UUID value) {
        return null == value;
    }

    public static boolean isNull(Double value) {
        return null == value || value == 0;
    }

    public static Boolean isValidPhoneNo(String phoneNo) {
        return phoneNo.length() != 10;
    }

    public static boolean isValidMobileNumber(String value) {
        String regex = "(?:\\s+|)((0|(?:(\\+|)91))(?:\\s|-)*(?:(?:\\d(?:\\s|-)*\\d{9})|(?:\\d{2}(?:\\s|-)*\\d{8})|(?:\\d{3}(?:\\s|-)*\\d{7}))|\\d{10})(?:\\s+|)";
        Pattern p = Pattern.compile(regex);
        String s = String.valueOf(value);
        Matcher m = p.matcher(s);
        return m.matches();
    }

    public static boolean isValidEmailId(String value) {
        String regex = "^(?=.{1,64}@)[a-zA-Z][a-zA-Z0-9]+@[a-zA-Z0-9.-]+\\.[a-zA-Z0-9-.]+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(value);
        return m.matches();
    }

    public static boolean isNameValid(String name) {
        return name != null && !name.isEmpty();
    }

    public static boolean isValidName(String name) {
        if (name == null || name.isEmpty()) {
            return false;
        }

        String namePattern = "^[\\p{L} '-]+$";
        if (!name.matches(namePattern)) {
            return false;
        }

        return name.length() >= 1 && name.length() <= 50;
    }

    public static boolean isUserNameValid(String userName) {
        if (userName == null || userName.length() < 3 || userName.length() > 20) {
            return false;
        }
        return userName.matches("^[a-zA-Z][a-zA-Z0-9]+$");
    }

    public static boolean isRoleRequired(UserRole role) {
        return role == null;
    }

    public static boolean isRoleValid(UserRole role) {
        String roleTypeToString = role.toString();
        return roleTypeToString.equalsIgnoreCase("admin") || roleTypeToString.equalsIgnoreCase("user");
    }

    public static boolean isValidPassword(String password) {
        if (password == null) {
            return false;
        }

        String passwordRegex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,}$";
        if (!Pattern.matches(passwordRegex, password)) {
            return false;
        }

        return password.contains("@");
    }

    public static boolean isPasswordValid(String password, String confirmPassword) {
        if (password == null || confirmPassword == null || !password.equals(confirmPassword)) {
            return false;
        }

        String passwordRegex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,}$";
        if (!Pattern.matches(passwordRegex, password)) {
            return false;
        }

        return password.contains("@");
    }

    public static boolean isDateOfBirthRequired(String dateOfBirth) {
        return dateOfBirth == null || dateOfBirth.isEmpty();
    }

    public static boolean isValueNegative(Number values) {
        if (values instanceof Double) {
            Double value = (Double) values;
            return value < 0;
        }
        if (values instanceof Integer) {
            Integer value = (Integer) values;
            return value < 0;
        }
        return false;
    }

    public static String getFormattedString(String value) {
        if (value != null) {
            return value.trim();
        }
        return value;
    }

    public static boolean isDateOfBirthValid(String dob) {
        final String DATE_PATTERN = "dd-MM-yyyy";

        if (dob == null || dob.isEmpty()) {
            return false;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        LocalDate date;
        try {
            date = LocalDate.parse(dob, formatter);
        } catch (Exception e) {
            return false;
        }

        return !date.isAfter(LocalDate.now()) && isAbove17Years(date);
    }

    public static boolean isAbove17Years(LocalDate dateOfBirth) {
        return Period.between(dateOfBirth, LocalDate.now()).getYears() >= 17;
    }
}