package org.egov.pgr.web.controller;

import java.util.List;

import org.egov.infra.admin.master.entity.Boundary;
import org.egov.infra.admin.master.entity.User;
import org.egov.pgr.service.CommonService;
import org.egov.pims.commons.DesignationMaster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AjaxController {

    private CommonService commonService;

    @Autowired
    public AjaxController(CommonService commonService) {
        this.commonService = commonService;
    }

    @RequestMapping(value = "/ajax-getWards/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Boundary> getWardsForZone(@PathVariable Long id) {
        return commonService.getWards(id);
    }

    @RequestMapping(value = "/ajax-approvalDesignations", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<DesignationMaster> getDesignations(
            @ModelAttribute("designations") @RequestParam Integer approvalDepartment) {
        List<DesignationMaster> designations = commonService.getDesignations(approvalDepartment);
        designations.forEach(designation -> designation.toString());
        return designations;
    }

    @RequestMapping(value = "/ajax-approvalPositions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<User> getPositions(@RequestParam Integer approvalDepartment,
            @RequestParam Integer approvalDesignation) {
        List<User> users = commonService.getPosistions(approvalDepartment, approvalDesignation);
        // below line should be removed once the commonService.getPosistions
        // apis query joins and returns user

        /*
         * Gson jsonCreator = new
         * GsonBuilder().excludeFieldsWithoutExposeAnnotation().create(); String
         * json = jsonCreator.toJson(users, List.class);
         */
        users.forEach(user -> user.toString());
        return users;
    }
}
