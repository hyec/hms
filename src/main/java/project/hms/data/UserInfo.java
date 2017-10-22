package project.hms.data;

import project.hms.model.Employee;
import project.hms.model.User;

public class UserInfo {

    private String username;
    private String password;
    private String name;

    private boolean valid = false;
    private boolean cleaner;
    private boolean cashier;
    private boolean manager;

    public UserInfo() {
    }

    public UserInfo(User user) {
        if (user == null)
            return;

        this.valid = true;
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.name = user.getName();

        Employee employee = user.getEmployee();

        if (employee == null)
            return;

        switch (employee.getType()) {
            case MANAGER:
                this.manager = true;
            case CASHIER:
                this.cashier = true;
            case CLEANER:
                this.cleaner = true;
            default:
        }
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public boolean isValid() {
        return valid;
    }

    public boolean isCleaner() {
        return cleaner;
    }

    public boolean isCashier() {
        return cashier;
    }

    public boolean isManager() {
        return manager;
    }

    public boolean isEmployee() {
        return cleaner || cashier || manager;
    }
}
