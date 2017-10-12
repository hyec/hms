package project.hms.models;

import project.hms.models.enums.Gender;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User extends Base {

    @Column(length = 64, unique = true, nullable = false)
    private String username;

    @Column(length = 64, nullable = false)
    private String password;

    @Column(length = 64)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private Gender gender;

    @Column(length = 18, unique = true, nullable = false)
    private String idNum;

    private int integration = 0;

    @OneToOne(mappedBy = "user")
    private Employee employee;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    public int getIntegration() {
        return integration;
    }

    public void setIntegration(int integration) {
        this.integration = integration;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
