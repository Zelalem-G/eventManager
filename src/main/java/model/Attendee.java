package model;

public class Attendee {
    private String fullName, email, phone, gender, institution;
    private int age;

    public Attendee(String fullName, String email, String phone, String gender, String institution, int age) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.institution = institution;
        this.age = age;
    }

    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getGender() { return gender; }
    public String getInstitution() { return institution; }
    public int getAge() { return age; }
}
