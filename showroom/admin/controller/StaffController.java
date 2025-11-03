package showroom.admin.controller;

import showroom.admin.dao.StaffDAO;
import showroom.admin.model.Staff;
import java.util.List;

public class StaffController {
    private StaffDAO staffDAO;

    public StaffController() {
        this.staffDAO = new StaffDAO();
    }

    public boolean addStaff(Staff staff) {
        if (staff == null || staff.getName() == null || staff.getEmail() == null) {
            return false;
        }
        return staffDAO.addStaff(staff);
    }

    public List<Staff> getAllStaff() {
        return staffDAO.getAllStaff();
    }

    public Staff getStaffByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return null;
        }
        return staffDAO.getStaffByEmail(email);
    }

    public boolean updateStaff(String email, Staff staff) {
        if (email == null || email.trim().isEmpty() || staff == null) {
            return false;
        }
        return staffDAO.updateStaff(email, staff);
    }

    public boolean deleteStaff(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return staffDAO.deleteStaff(email);
    }

    public void closeConnection() {
        staffDAO.closeConnection();
    }
}
