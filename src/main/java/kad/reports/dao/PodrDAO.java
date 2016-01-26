package kad.reports.dao;


import kad.reports.domain.PodrEntity;

import java.util.List;

public interface PodrDAO {
    public PodrEntity getById(final Integer wantedId);

    public List<PodrEntity> getAll();

    public List<PodrEntity> getAllForOwner(int wantedId);

    public void saveRecord(PodrEntity podrEntity);

    public void deleteRecord(final int wantedId);

    public void insertRecord(final PodrEntity podrEntity);
}
