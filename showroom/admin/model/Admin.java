package showroom.admin.model;

/**
 * Admin model class representing an admin user in the system
 */
public class Admin {
    private String adminId;
    private String name;
    private String email;
    private String phone;
    private String role; // e.g., "SuperAdmin", "Manager"
    private String status; // e.g., "Active", "Inactive"
    private String createdDate;
    private String lastLogin;

    public Admin() {
    }

    public Admin(String name, String email, String phone, String role) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.status = "Active";
    }

    public Admin(String adminId, String name, String email, String phone, String role, String status, String createdDate, String lastLogin) {
        this.adminId = adminId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.status = status;
        this.createdDate = createdDate;
        this.lastLogin = lastLogin;
    }

    // Getters
    public String getAdminId() {
        return adminId;
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

    public String getRole() {
        return role;
    }

    public String getStatus() {
        return status;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    // Setters
    public void setAdminId(String adminId) {
        this.adminId = adminId;
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

    public void setRole(String role) {
        this.role = role;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "adminId='" + adminId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", role='" + role + '\'' +
                ", status='" + status + '\'' +
                ", createdDate='" + createdDate + '\'' +
                ", lastLogin='" + lastLogin + '\'' +
                '}';
    }
}
