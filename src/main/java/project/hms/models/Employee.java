package project.hms.models;

import project.hms.models.enums.EmployeeType;

import javax.persistence.*;

@Entity
@Table(name = "employees")
public class Employee extends Base {

    @OneToOne
    @JoinColumn(name = "user_id")
    @Column(unique = true)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private EmployeeType type;

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
