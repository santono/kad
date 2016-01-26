package kad.reports.domain;

import java.io.Serializable;
import java.sql.Date;


public class UserEntity implements Serializable {
    private static final long serialVersionUID = -5527566248002296042L;

    private long id;
    private String name;
    private Date dataCreate;
    private Date dataDelete;
    private boolean active;
    private String login;
    private String password;
    private int tabno;
    private int shifrPodr;
    private StatisticEntity sEntity;

    public UserEntity() {
    }

    public int getTabno() {
        return tabno;
    }

    public void setTabno(int tabno) {
        this.tabno = tabno;
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


    public Date getDataCreate() {
        return dataCreate;
    }

    public void setDataCreate(Date dataCreate) {
        this.dataCreate = dataCreate;
    }

    public Date getDataDelete() {
        return dataDelete;
    }

    public void setDataDelete(Date dateDelete) {
        this.dataDelete = dateDelete;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public int getShifrPodr() {
        return shifrPodr;
    }

    public void setShifrPodr(int shifrPodr) {
        this.shifrPodr = shifrPodr;
    }

    public StatisticEntity getsEntity() {
        return sEntity;
    }

    public void setsEntity(StatisticEntity sEntity) {
        this.sEntity = sEntity;
    }
}
