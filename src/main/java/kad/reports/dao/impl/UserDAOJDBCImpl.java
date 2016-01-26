package kad.reports.dao.impl;


import kad.reports.dao.UserDAO;
import kad.reports.domain.UserEntity;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository("userDAO")
@Transactional
public class UserDAOJDBCImpl implements UserDAO {
    protected static Logger logger = Logger.getLogger("service");

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final String SQL_SELECT_ALL = "select id, name,active,login,password,tabno,shifrpodr from public.tb_users order by id";
    private static final String SQL_SELECT_ALL_FOR_USER = "select id, name,active,login,password,tabno,shifrpodr from public.tb_users";
    private static final String SQL_SELECT_BY_ID = "select id, name,active,login,password,tabno,shifrpodr from public.tb_users where id=?";
    private static final String SQL_SELECT_BY_LOGIN = "select id, name,active,login,password,tabno,shifrpodr from public.tb_users where login=?";
    private static final String SQL_SELECT_COUNT_BY_LOGIN = "select count(*) from public.tb_users where login=?";
    private static final String SQL_DELETE_USER = "delete from public.tb_users where id=?";
    private static final String SQL_UPDATE_USER = "update public.tb_users set name=?,active=?,login=?,password=?,shifrpodr=?  where id=?";
    private static final String SQL_INSERT_USER = "insert into public.tb_users (name,active,login,password,shifrpodr) values(?,?,?,?,?)";


    public UserDAOJDBCImpl() {

    }

    @Override
    @Transactional(readOnly = true)
    public UserEntity getById(final int id) {
        RowMapper<UserEntity> mapper = new RowMapper<UserEntity>() {
            public UserEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                UserEntity user = new UserEntity();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setActive(rs.getBoolean("active"));
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                user.setTabno(rs.getInt("tabno"));
                user.setShifrPodr(rs.getInt("shifrpodr"));

                //     user.setRole(roleDAO.getById((int) rs.getLong("shifridrole")));
                return user;
            }
        };
        UserEntity user = (UserEntity) jdbcTemplate.queryForObject(
                SQL_SELECT_BY_ID, mapper, new Object[]{id});
        //		sql, new Object[] { wantedId }, mapper);

        return user;
        //   return jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, UserEntity.class, id);
    }

    @Override
    @Transactional(readOnly = true)
    public UserEntity getByLogin(String login) {
        //      logger.debug("inside getByLogin");
        RowMapper<UserEntity> mapper = new RowMapper<UserEntity>() {
            public UserEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                UserEntity user = new UserEntity();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setActive(rs.getBoolean("active"));
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                user.setTabno(rs.getInt("tabno"));
                user.setShifrPodr(rs.getInt("shifrpodr"));
                return user;
            }
        };
        UserEntity user = (UserEntity) jdbcTemplate.queryForObject(
                SQL_SELECT_BY_LOGIN, mapper, new Object[]{login});

        return user;
    }

    public int getCountByLogin(String login) {

        return jdbcTemplate.queryForObject(SQL_SELECT_COUNT_BY_LOGIN, Integer.class, login);
    }

    @Override
    public void saveRecord(UserEntity uEntity) {
        if (uEntity.getId() > 0) {

            jdbcTemplate.update(SQL_UPDATE_USER,
                    new Object[]{uEntity.getName(),
                            uEntity.isActive(),
                            uEntity.getLogin(),
                            uEntity.getPassword(),
                            uEntity.getShifrPodr(),
                            uEntity.getId()
                    });
        } else {
            insertRecord(uEntity);
        }
    }

    @Override
    public void deleteRecord(int wantedId) {
        jdbcTemplate.update(SQL_DELETE_USER, new Object[]{wantedId});
    }

    private void insertRecord(final UserEntity uEntity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();


        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                        PreparedStatement pst =
                                con.prepareStatement(SQL_INSERT_USER, new String[]{"id"});
                        pst.setString(1, uEntity.getName());
                        pst.setBoolean(2, uEntity.isActive());
                        pst.setString(3, uEntity.getLogin());
                        pst.setString(4, uEntity.getPassword());
                        pst.setInt(5, uEntity.getShifrPodr());
                        return pst;
                    }
                },
                keyHolder);
        uEntity.setId(keyHolder.getKey().intValue());
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserEntity> getAll() {
        RowMapper<UserEntity> mapper = new RowMapper<UserEntity>() {
            public UserEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                UserEntity user = new UserEntity();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setActive(rs.getBoolean("active"));
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                user.setTabno(rs.getInt("tabno"));
                user.setShifrPodr(rs.getInt("shifrpodr"));
                return user;
            }
        };
        return jdbcTemplate.query(SQL_SELECT_ALL, mapper);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserEntity> getAllForUser(String whereStmnt) {
        RowMapper<UserEntity> mapper = new RowMapper<UserEntity>() {
            public UserEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                UserEntity user = new UserEntity();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setActive(rs.getBoolean("active"));
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                user.setTabno(rs.getInt("tabno"));
                user.setShifrPodr(rs.getInt("shifrpodr"));
                return user;
            }
        };
        StringBuilder sb = new StringBuilder();
        sb.append(SQL_SELECT_ALL_FOR_USER);
        if (whereStmnt != null) {
            if (whereStmnt.trim().length() > 0) {
                sb.append(" where ");
                sb.append(" " + whereStmnt.trim());
            }
        }
        sb.append(" order by id");
        return jdbcTemplate.query(sb.toString(), mapper);
    }
}
