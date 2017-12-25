package project.hms.data.dto;

import org.hibernate.validator.constraints.NotEmpty;
import project.hms.data.validation.ValidIDNum;
import project.hms.model.enums.Gender;

import javax.validation.constraints.NotNull;

/**
 * 用户注册表单
 */
public class UserDto {

    /**
     * 用户名
     */
    @NotEmpty(message = "用户名不能为空！")
    private String username;

    /**
     * 用户密码
     */
    @NotEmpty(message = "密码不能为空！")
    private String password;

    /**
     * 密码重复
     */
    private String matchingPassword;

    /**
     * 姓名
     */
    @NotEmpty(message = "姓名不能为空！")
    private String name;

    /**
     * 性别
     */
    @NotNull(message = "性别不能为空！")
    private Gender gender;

    /**
     * 身份证号
     *
     * @see ValidIDNum
     */
    @NotEmpty(message = "身份证号不能为空！")
    @ValidIDNum(message = "身份证号格式不正确！")
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
