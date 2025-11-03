package showroom.admin.model;

/**
 * Staff model class representing a staff member in the showroom
 */
public class Staff {
    private String staffId;
    private String name;
    private String email;
    private String phone;
    private String position; // e.g., "Sales Executive", "Manager", "Mechanic"
    private String department; // e.g., "Sales", "Service", "Administration"
    private String salary;
    private String joiningDate;
    private String status; // e.g., "Active", "Inactive"

    public Staff() {
    }

    public Staff(String name, String email, String phone, String position, String department) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.position = position;
        this.department = department;
        this.status = "Active";
    }

    public Staff(String staffId, String name, String email, String phone, String position, String department, String salary, String joiningDate, String status) {
        this.staffId = staffId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.position = position;
        this.department = department;
        this.salary = salary;
        this.joiningDate = joiningDate;
        this.status = status;
    }

    // Getters
    public String getStaffId() {
        return staffId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getPosition() {
        return position;
    }

    public String getDepartment() {
        return department;
    }

    public String getSalary() {
        return salary;
    }

    public String getJoiningDate() {
        return joiningDate;
    }

    public String getStatus() {
        return status;
    }

    // Setters
    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public void setJoiningDate(String joiningDate) {
        this.joiningDate = joiningDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Staff{" +
                "staffId='" + staffId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", position='" + position + '\'' +
                ", department='" + department + '\'' +
                ", salary='" + salary + '\'' +
                ", joiningDate='" + joiningDate + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
