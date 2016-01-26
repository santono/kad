package kad.reports.dao;

import kad.reports.domain.RoadEntity;
import kad.reports.dto.RoadRecDTO;
import kad.reports.util.forbsgridcontroller.SortingClass;

import java.util.List;


public interface RoadDAO {
    public RoadEntity getById(final int wantedId);

    public List<RoadEntity> getAll();

    public List<RoadEntity> getAllForType(int kodtype);

    public void saveRecord(RoadEntity roadEntity);

    public void deleteRecord(final int wantedId);

    public void insertRecord(final RoadEntity roadEntity);

    public List<RoadRecDTO> getListGroup();

    public List<RoadRecDTO> getPageListGroup(final int pageNo, final int pageRows, List<SortingClass> sorting, String whereStmnt);

    public int getCountRec();

    public int getCountRecWithFilter(String whereStmnt);
}
