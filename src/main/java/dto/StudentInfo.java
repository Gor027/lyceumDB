package dto;

public class StudentInfo extends LoginInfo {

    private final int userId;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String phoneNumber;
    private final String address;
    private final GENDER gender;

    public StudentInfo(int userId, String firstName, String lastName, String email, String phoneNumber, String address, GENDER gender) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.gender = gender;
    }

    public StudentInfo(String firstName, GENDER gender) {
        this.userId = 0;
        this.firstName = firstName;
        this.lastName = "";
        this.email = "";
        this.phoneNumber = "";
        this.address = "";
        this.gender = gender;

    }

    public GENDER getGender() {
        return gender;
    }

    public int getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public enum GENDER {
        MALE,
        FEMALE
    }
}
