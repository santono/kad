package kad.reports.dao;


import kad.reports.domain.StatisticEntity;

import java.util.List;

public interface StatisticDAO {
    public List<StatisticEntity> findAll();

    public StatisticEntity getById(int wantedId);

    public void saveRecord(StatisticEntity sEntity);
}
