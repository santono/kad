package kad.reports.dao;


import kad.reports.domain.UserEntity;

import java.util.List;


public interface UserDAO {
    public UserEntity getById(final int id);

    public UserEntity getByLogin(final String login);

    public int getCountByLogin(final String login);

    public void saveRecord(UserEntity uEntity);

    public void deleteRecord(final int wantedId);

    public List<UserEntity> getAll();

    public List<UserEntity> getAllForUser(String whereStmnt);
}
