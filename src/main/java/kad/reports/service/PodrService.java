package kad.reports.service;

import kad.reports.dao.PodrDAO;
import kad.reports.domain.PodrEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PodrService {
    @Autowired
    private PodrDAO podrDAO;

    public PodrEntity getById(final Integer wantedId) {
//        logger.debug("Retrieving podra "+wantedId);
        if (wantedId < 1) {
            PodrEntity podr = new PodrEntity();
            podr.setId(0);
            podr.setName("");
            podr.setShifrIdOwner(0);
            return podr;
        }
        return podrDAO.getById(wantedId);
    }

    public List<PodrEntity> getAll() {
        //      logger.debug("Retrieving all kafedras ");
        return podrDAO.getAll();
    }


    public List<PodrEntity> getAllForFac(int wantedId) {
        //      logger.debug("Retrieving all kafedras ");
        return podrDAO.getAllForOwner(wantedId);
    }


    public void saveRecord(PodrEntity podrEntity) {
        podrDAO.saveRecord(podrEntity);
    }

    public void deleteRecord(final int wantedId) {
        podrDAO.deleteRecord(wantedId);
    }

    public void insertRecord(final PodrEntity kafEntity) {
        podrDAO.saveRecord(kafEntity);
    }


}
