package kad.reports.controller;

import kad.reports.domain.RoadEntity;
import kad.reports.domain.RoadPointEntity;
import kad.reports.dto.ItemErrorDTO;
import kad.reports.dto.ItemXXYYDTO;
import kad.reports.dto.RoadRecDTO;
import kad.reports.grid.RoadGrid;
import kad.reports.service.CityService;
import kad.reports.service.RoadPointService;
import kad.reports.service.RoadService;
import kad.reports.service.validators.RoadValidator;
import kad.reports.util.forbsgridcontroller.FilterRuleClass;
import kad.reports.util.forbsgridcontroller.SortAndRulesFromRequestClass;
import kad.reports.util.forbsgridcontroller.SortingClass;
import kad.reports.util.forbsgridcontroller.WhereSQLClass;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/roads")
public class RoadController {

    class Columns {
        String field;
        String header;
        String visible;
    }


    @Autowired
    private RoadService roadService;
    @Autowired
    private CityService cityService;
    @Autowired
    private RoadPointService rpService;
    @Autowired
    private RoadValidator roadValidator;

    protected static Logger logger = Logger.getLogger("controller");

    @RequestMapping(method = RequestMethod.GET)
    public String browseRoadsList(Model model, HttpSession session) {
        List<RoadRecDTO> roadList = roadService.getListRoads();
        model.addAttribute("citList", cityService.getAll());
//        model.addAttribute("speciList",speciService.getAllForNaprav((int) group.getShifrnapr()));

        model.addAttribute("rList", roadList);
        logger.debug("Amnt of rec=" + roadList.size());
        return "browseAllRoads";
    }

    @RequestMapping(value = "/getgrid", method = RequestMethod.POST,
            produces = "application/json")
    @ResponseBody
    public RoadGrid listGrid(@RequestParam(value = "page_num",
            required = false) Integer page,
                             @RequestParam(value = "rows_per_page", required = false) Integer rows,
                             //       @RequestParam(value = "sorting[][]",required = false) Sorting[] sorting,
                             @RequestParam(value = "columns[][]", required = false) Columns[] columns,
                             HttpServletRequest request, HttpSession httpSession) {
        logger.debug("Listing roads for grid with page: " + page + " rows per page " + rows);
        /*
           Enumeration e=request.getParameterNames();
           while (e.hasMoreElements()) {
               String s=(String) e.nextElement();
               String sVal;
               if (s.startsWith("filter_rules")) {
                   sVal=request.getParameter(s);
                  logger.debug(s+"->"+sVal);
               }
           }
        */
        String whereStmnt = null;
        SortAndRulesFromRequestClass sarr = new SortAndRulesFromRequestClass();
        List<SortingClass> sorting = sarr.getSorting(request);
        List<FilterRuleClass> filter = sarr.getFilterRules(request);
        if (sorting != null)
            logger.debug(" Anmt of Sorting=" + sorting.size());
        if (filter != null) {
            logger.debug(" Amnt of rules=" + filter.size());
            logger.debug(filter.toString());
            WhereSQLClass wsc = new WhereSQLClass();
            whereStmnt = wsc.parseRules(filter, false);
            logger.debug(" where=" + whereStmnt);
        }
        //   Sorting[] sorting1 = request.getParameter("sorting");
        int needRestPosition;
        if (httpSession.getAttribute("needRestPosition") == null) {
            needRestPosition = 0;
        } else {
            needRestPosition = (Integer) httpSession.getAttribute("needRestPosition");
        }
        logger.debug("needRestPosition=" + needRestPosition);
        if (needRestPosition == 1) {
            if (!((httpSession.getAttribute("currSorting") == null) || (httpSession.getAttribute("currFilter") == null))) {
                //!! (httpSession.getAttribute("currFilter")==null))) {
                logger.debug("inside reading table parameters from session=" + needRestPosition);
                sorting = (List<SortingClass>) httpSession.getAttribute("currSorting");
                filter = (List<FilterRuleClass>) httpSession.getAttribute("currFilter");
                page = (Integer) httpSession.getAttribute("page");
                rows = (Integer) httpSession.getAttribute("rows");
                if (filter != null) {
                    whereStmnt = new WhereSQLClass().parseRules(filter, false);
                }
            }
        }
        httpSession.setAttribute("currSorting", sorting);
        httpSession.setAttribute("currFilter", filter);
        httpSession.setAttribute("page", page);
        httpSession.setAttribute("rows", rows);
        httpSession.setAttribute("needRestPosition", 0);
        RoadGrid gg = new RoadGrid();
        gg.setPage_data(roadService.getPageListRoad(page, rows, sorting, whereStmnt));
        //  gg.setPage_data(groupService.getListGroup());
        gg.setTotal_rows("" + roadService.getCountRecWithFilter(whereStmnt));
        gg.setError(null);
        gg.setFilter_error(new String[]{});
        gg.setDebug_message(new String[]{});
        return gg;
    }

    @RequestMapping(value = "/getcoords", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public List<ItemXXYYDTO> getRoadCoords(@RequestParam int idroad) {
        logger.debug("getRoadCoords for road=" + idroad);
        List<RoadPointEntity> rpList = rpService.getAllForRoad(idroad);
        logger.debug("amount of points " + rpList.size());
        int i = 0;
        String xx, yy;
        List<ItemXXYYDTO> okList = new ArrayList<ItemXXYYDTO>();

        for (RoadPointEntity rp : rpList) {
            i++;
            xx = String.format("%18.6f", rp.getXx()).trim().replace(',', '.');
            yy = String.format("%18.6f", rp.getYy()).trim().replace(',', '.');
            okList.add(new ItemXXYYDTO(i, xx, yy, rp.getBalloon()));
        }
        return okList;
    }

    @RequestMapping(value = "/getrec", method = RequestMethod.POST)
    //  @ResponseBody
    public String getTableRow(@RequestParam int id, Model model, HttpSession session) {
        //      logger.debug("Request Ajax for group. For id = " + id);
        int idroad = id;
        logger.debug(" id road for edit =" + idroad);
        RoadEntity tDet;
        if (id > 0) {
            tDet = roadService.getById(id);
        } else {
            tDet = new RoadEntity();
            tDet.setKodtype(2);
        }
        model.addAttribute("tDet", tDet);
        logger.debug("Response Ajax  " + tDet.toString());

        String viewName = "tRoadRowFormBootstrap";
        return viewName;
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public List<ItemErrorDTO> saveTableR1P1Row(@ModelAttribute("RoadEntity") RoadEntity tDet, BindingResult result, HttpSession session, Model model) {
        //     if (result.hasErrors()) {
        //         return "browseAllGroups";
        //     }
        roadValidator.validate(tDet, result);
        if (result.hasErrors()) {
            return getErrList(result);
        }
        logger.debug("save RoadPoint row for " + tDet.toString());
        roadService.saveRecord(tDet);
        List<ItemErrorDTO> okList = new ArrayList<ItemErrorDTO>();
        okList.add(new ItemErrorDTO(0, "Ok", "Ok"));
        session.setAttribute("needRestPosition", 1);
        return okList;
    }

    private List<ItemErrorDTO> getErrList(BindingResult result) {
        if (result.hasErrors()) {
            List<ItemErrorDTO> errList = new ArrayList<ItemErrorDTO>();
            int errCnt = result.getErrorCount();
            if (errCnt > 0) {
                int i;
                i = 0;
                for (ObjectError oError : result.getAllErrors()) {
                    ItemErrorDTO errDTO = new ItemErrorDTO(i++, oError.getDefaultMessage(), oError.getCode());
                    errList.add(errDTO);
                }
            }
            return errList;
        } else
            return null;

    }


}
