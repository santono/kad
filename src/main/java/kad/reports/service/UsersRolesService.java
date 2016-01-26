package kad.reports.service;

import kad.reports.dao.UsersRolesDAO;
import kad.reports.domain.UsersRolesEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UsersRolesService {
    @Autowired
    UsersRolesDAO urDAO;
    @Autowired
    RoleService roleService;

    public UsersRolesEntity getById(final Integer wantedId) {
        return urDAO.getById(wantedId);
    }

    public List<UsersRolesEntity> getAll() {
        return urDAO.getAll();
    }

    public List<UsersRolesEntity> getAllForUser(final Integer wantedId) {
        return urDAO.getAllForUser(wantedId);
    }

    public int[] getAllForUserAsArray(final Integer wantedId) {
        List<UsersRolesEntity> urList = urDAO.getAllForUser(wantedId);
        if (urList == null) return new int[0];
        if (urList.size() == 0) {
            return new int[0];
        }
        int[] rolesShifry = new int[urList.size()];
        for (int i = 0; i < urList.size(); i++) {
            rolesShifry[i] = urList.get(i).getShifrIdRole();
        }
        return rolesShifry;
    }

    public List<String> getAllRoleNamesForUser(Integer wantedId) {
        return urDAO.getAllRoleNamesForUser(wantedId);
    }

    public void saveRecord(UsersRolesEntity urEntity) {
        urDAO.saveRecord(urEntity);
    }

    public void deleteRecord(final int wantedId) {
        urDAO.deleteRecord(wantedId);
    }

    public void insertRecord(final UsersRolesEntity urEntity) {
        urDAO.insertRecord(urEntity);
    }

    public void deleteRecordsForUser(final int userId) {
        urDAO.deleteRecordsForUser(userId);
    }

    ;


    public void saveRolesForUser(int userId, int[] roles) {
        deleteRecordsForUser(userId);
        if (roles != null) {
            if (roles.length > 0) {
                UsersRolesEntity urEntity;
                for (int i = 0; i < roles.length; i++) {
                    urEntity = new UsersRolesEntity();
                    urEntity.setShifrIdRole(roles[i]);
                    urEntity.setShifrIdUser(userId);
                    saveRecord(urEntity);
                }
            }
        }
    }

    public boolean isKFUDepartmentRole(final Integer wantedId) {
        String[] deps = {"USER_DEP_1", "USER_DEP_2", "USER_DEP_3", "USER_DEP_4", "USER_UMR", "USER_UD", "USER_BUH", "USER_OK"};
        boolean retVal;
        retVal = false;
        for (UsersRolesEntity urEntity : getAllForUser(wantedId)) {
            for (String item : deps) {
                if (roleService.getById(urEntity.getShifrIdRole()).getNamess().equals(item.trim())) {
                    retVal = true;
                    break;
                }
            }
            if (retVal) break;

        }
        return retVal;
    }


}
