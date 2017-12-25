package project.hms.model;

import project.hms.model.enums.EmployeeType;

import javax.persistence.*;

/**
 * 数据库员工类
 */
@Entity
@Table(name = "employees")
public class Employee extends Base {

    /**
     * 相关用户，与用户一对一
     */
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * 员工类型
     */
    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private EmployeeType type;

    /**
     * 员工绩效
     */
    @Column(nullable = false)
    private int performance = 0;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public EmployeeType getType() {
        return type;
    }

    public void setType(EmployeeType type) {
        this.type = type;
    }

    public int getPerformance() {
        return performance;
    }

    public void setPerformance(int performance) {
        this.performance = performance;
    }
}
