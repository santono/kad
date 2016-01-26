package kad.reports.domain;

import java.io.Serializable;


public class RoleEntity implements Serializable {
    private static final long serialVersionUID = -5527566248002296042L;
    private long id;
    private String name;
    private String namess;

    public RoleEntity() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNamess() {
        return namess;
    }

    public void setNamess(String namess) {
        this.namess = namess;
    }
}
