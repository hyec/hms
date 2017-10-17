package project.hms.data.dto;

import org.hibernate.validator.constraints.NotEmpty;
import project.hms.data.validation.ValidIDNum;

public class UserDto {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;
    private String matchingPassword;

    @NotEmpty
    private String name;

    @NotEmpty
    private String gender;

    @NotEmpty
    private String email;

    @NotEmpty
    @ValidIDNum
    private String idNum;

}
