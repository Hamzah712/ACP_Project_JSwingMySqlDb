package model;

public class Member {
    private int id;
    private String firstName;
    private String lastName;
    private int age;
    private String gender;
    private String membershipDate;

    public Member(int id, String firstName, String lastName, int age, String gender, String membershipDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.membershipDate = membershipDate;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMembershipDate() {
        return membershipDate;
    }

    public void setMembershipDate(String membershipDate) {
        this.membershipDate = membershipDate;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", membershipDate='" + membershipDate + '\'' +
                '}';
    }
}
