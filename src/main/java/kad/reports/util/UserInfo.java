package kad.reports.util;


import kad.reports.dto.UserDTO;
import kad.reports.dto.UserDTOService;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.Serializable;

public class UserInfo implements Serializable {
    private UserDTO user;

    public UserInfo() {
        UserDTOService uDTOService = (UserDTOService) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user = uDTOService.getUserDTO();
    }


    public long getShifrWrk() {
        return user.getId();
    }

    public String getFIO() {
        return user.getFIO();
    }

    public UserDTO getUserDTO() {
        return user;
    }

//    public TableInfo getTableInfo() {
//        return tableInfo;
//    }
//
//    public void setTableInfo(TableInfo tableInfo) {
//        this.tableInfo = tableInfo;
//    }
}
