package kad.reports.dao.impl;


import kad.reports.dao.PodrDAO;
import kad.reports.domain.PodrEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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

@Repository
@Transactional
public class PodrDAOJDBCImpl implements PodrDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String SQL_SELECT_BY_ID = "select name,shifridowner from public.tb_podr where id=?";
    private static final String SQL_SELECT_ALL = "select id,shifridowner,name from public.tb_podr order by id";
    private static final String SQL_SELECT_ALL_FOR_OWNER = "select id,name from public.tb_podr where coalesce(shifridowner,0)=?";
    private static final String SQL_DELETE_POD = "delete from public.tb_podr where id=?";
    private static final String SQL_UPDATE_POD = "update public.tb_podr set name=?,shifridowner=? where id=?";
    private static final String SQL_INSERT_POD = "insert into public.tb_podr (name,shifridowner) values (?,?)";

    @Override
    @Transactional(readOnly = true)
    public PodrEntity getById(final Integer wantedId) {
        RowMapper<PodrEntity> mapper = new RowMapper<PodrEntity>() {
            public PodrEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                PodrEntity podr = new PodrEntity();
                podr.setId(wantedId);
                podr.setName(rs.getString("name"));
                podr.setShifrIdOwner(rs.getInt("shifridowner"));
                return podr;
            }
        };
        PodrEntity podr;
        try {
            podr = (PodrEntity) jdbcTemplate.queryForObject(
                    SQL_SELECT_BY_ID, mapper, new Object[]{wantedId});
            //		sql, new Object[] { wantedId }, mapper);
        } catch (EmptyResultDataAccessException e) {
            podr = null;
        }
        return podr;

    }

    @Override
    @Transactional(readOnly = true)
    public List<PodrEntity> getAll() {

        // Maps a SQL result to a Java object
        RowMapper<PodrEntity> mapper = new RowMapper<PodrEntity>() {
            public PodrEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                PodrEntity podr = new PodrEntity();
                podr.setId(rs.getLong("id"));
                podr.setName(rs.getString("name"));
                podr.setShifrIdOwner(rs.getInt("shifridowner"));
                return podr;
            }
        };
        return jdbcTemplate.query(SQL_SELECT_ALL, mapper);

    }

    @Override
    @Transactional(readOnly = true)
    public List<PodrEntity> getAllForOwner(int wantedId) {

        // Maps a SQL result to a Java object
        RowMapper<PodrEntity> mapper = new RowMapper<PodrEntity>() {
            public PodrEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                PodrEntity podr = new PodrEntity();
                podr.setId(rs.getLong("id"));
                podr.setName(rs.getString("name"));
                return podr;
            }
        };
        return jdbcTemplate.query(SQL_SELECT_ALL_FOR_OWNER, mapper, new Object[]{wantedId});

    }


    @Override
    public void deleteRecord(final int wantedId) {
        jdbcTemplate.update(SQL_DELETE_POD, new Object[]{wantedId});
    }

    @Override
    public void insertRecord(final PodrEntity podrEntity) {
        //   KeyHolder keyHolder = new GeneratedKeyHolder();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                        PreparedStatement pst =
                                con.prepareStatement(SQL_INSERT_POD, new String[]{"id"});
                        pst.setString(1, podrEntity.getName());
                        pst.setInt(2, (int) podrEntity.getShifrIdOwner());
                        return pst;
                    }
                },
                keyHolder);
        podrEntity.setId(keyHolder.getKey().intValue());
    }

    @Override
    public void saveRecord(PodrEntity podrEntity) {
        if (podrEntity.getId() > 0) {

            jdbcTemplate.update(SQL_UPDATE_POD,
                    new Object[]{podrEntity.getName(),
                            podrEntity.getShifrIdOwner(),
                            podrEntity.getId()
                    });
        } else {
            insertRecord(podrEntity);
        }

    }


}
