package kad.reports.dao;

import kad.reports.domain.CityEntity;

import java.util.List;

public interface CityDAO {
    public CityEntity getById(final int wantedId);

    public List<CityEntity> getAll();

    public void saveRecord(CityEntity cityEntity);

    public void deleteRecord(final int wantedId);

    public void insertRecord(final CityEntity cityEntity);

}
