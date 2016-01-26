package kad.reports.dao;

import kad.reports.domain.RoleEntity;

import java.util.List;

public interface RoleDAO {
    public RoleEntity getById(final int id);

    public List<RoleEntity> getAll();

    public List<RoleEntity> getAllForUniFac(int shifrUni, int shifrFac);

    public List<RoleEntity> getAllForUni();

}
