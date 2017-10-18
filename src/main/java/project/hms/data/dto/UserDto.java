package project.hms.data.dto;

import org.hibernate.validator.constraints.NotEmpty;
import project.hms.data.validation.ValidIDNum;
import project.hms.model.enums.Gender;

import javax.validation.constraints.NotNull;

public class UserDto {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;
    private String matchingPassword;

    @NotEmpty
    private String name;

    @NotNull
    private Gender gender;

    @NotEmpty
    @ValidIDNum
    private String idNum;

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

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
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
}
