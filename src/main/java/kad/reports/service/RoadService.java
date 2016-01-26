package kad.reports.service;

import kad.reports.dao.RoadDAO;
import kad.reports.domain.RoadEntity;
import kad.reports.dto.RoadRecDTO;
import kad.reports.util.forbsgridcontroller.SortingClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoadService {

    @Autowired
    private RoadDAO roadDAO;

    @Autowired
    private RoadPointService rpService;

    public RoadEntity getById(final int wantedId) {
        return roadDAO.getById(wantedId);
    }

    public List<RoadEntity> getAll() {
        return roadDAO.getAll();
    }

    public List<RoadEntity> getAllForType(int kodtype) {
        return roadDAO.getAllForType(kodtype);
    }

    public void saveRecord(RoadEntity roadEntity) {
        roadDAO.saveRecord(roadEntity);
    }

    public void deleteRecord(final int wantedId) {
        roadDAO.deleteRecord(wantedId);
    }

    public void insertRecord(final RoadEntity roadEntity) {
        roadDAO.insertRecord(roadEntity);
    }

    public List<RoadRecDTO> getListRoads() {
        List<RoadRecDTO> l = new ArrayList<RoadRecDTO>();
        for (RoadEntity r : getAll()) {
            RoadRecDTO rDTO = new RoadRecDTO();
            rDTO.setId(r.getId());
            rDTO.setKod(r.getKod());
            rDTO.setCode(r.getCode());
            rDTO.setName(r.getName());
            rDTO.setKodtype(r.getKodtype());
            rDTO.setDlina(r.getDlina());
            rDTO.setMenur("");
            l.add(rDTO);
        }
        return l;
    }

    public List<RoadRecDTO> getListRoadsGos() {
        List<RoadRecDTO> l = new ArrayList<RoadRecDTO>();
        for (RoadEntity r : getAllForType(1)) {
            RoadRecDTO rDTO = new RoadRecDTO();
            rDTO.setId(r.getId());
            rDTO.setKod(r.getKod());
            rDTO.setCode(r.getCode());
            rDTO.setName(r.getName());
            rDTO.setKodtype(r.getKodtype());
            rDTO.setDlina(r.getDlina());
            rDTO.setMenur("");
            l.add(rDTO);
        }
        return l;
    }


    public List<RoadRecDTO> getPageListRoad(final int pageNo, final int pageRows, List<SortingClass> sorting, String whereStmnt) {
        //   logger.debug("Count of rec="+groupDAO.getListGroup().size());
        StringBuilder sb;
        List<RoadRecDTO> grlDTO = roadDAO.getPageListGroup(pageNo, pageRows, sorting, whereStmnt);
        for (RoadRecDTO g : grlDTO) {
            int countPoints = rpService.getCountPointsForRoad(g.getId());
            sb = new StringBuilder();
            sb.append("<nobr>");
            sb.append("<button type=\"button\" class=\"btn btn-primary btn-xs editRoad\" onclick=\"javascript:return editRoad(" + g.getId() + ");\"><span class=\"glyphicon glyphicon-pencil\"></span></button>");
            sb.append("<button type=\"button\" class=\"btn btn-primary btn-xs browseRoadPoint\" idroad=\"" + g.getId() + "\" onclick=\"javascript:return browseRoadPoints(" + g.getId() + ");\"><span class=\"glyphicon glyphicon-th\"></span></button>");
            if (countPoints > 0) {
                sb.append("<button type=\"button\" class=\"btn btn-primary btn-xs browseRoadMap\" idroad=\"" + g.getId() + "\" dlina=\"" + g.getDlina() + "\"><span class=\"glyphicon glyphicon-globe\"></span></button>");
            }
            //     sb.append("<button type=\"button\" class=\"btn btn-xs delrecbutton\" onclick=\"deleteRoad("+g.getId()+","+g.getName()+")\"   idroad=\""+g.getId()+"\" nameroad=\""+g.getName()+"\"><span class=\"glyphicon glyphicon-remove\"></span></button>");
            sb.append("</nobr>");
            g.setMenur(sb.toString());
        }
        return grlDTO;
    }

    public int getCountRec() {
        return roadDAO.getCountRec();
    }

    public int getCountRecWithFilter(String whereStmnt) {
        return roadDAO.getCountRecWithFilter(whereStmnt);
    }


}
