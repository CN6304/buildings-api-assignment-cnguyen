package com.msr;

import com.msr.model.Site;
import com.msr.model.SiteUse;
import com.msr.model.UseType;
import com.msr.service.SiteService;
import com.msr.service.SiteUseService;
import com.msr.service.UseTypeService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Respond to site requests
 */
@RestController
@RequestMapping("/sites")
public class SitesController {
    private SiteService siteService;
    private SiteUseService siteUseService;
    private UseTypeService useTypeService;

    @Autowired
    public void setSiteService(SiteService siteService) {
        this.siteService = siteService;
    }

    @Autowired
    public void setSiteUseRepository(SiteUseService siteUseService) {
        this.siteUseService = siteUseService;
    }

    @Autowired
    public void setUseTypeService(UseTypeService useTypeService) {
        this.useTypeService = useTypeService;
    }

    /* Sample Output messages. */
    private final static String SAMPLE_RESPONSE_BASE = "This is a sample response to test if SitesController is responding appropriately. ";
    static final String SAMPLE_PARAM_PROVIDED = SAMPLE_RESPONSE_BASE + "The request param you passed was: ";
    static final String NO_SAMPLE_PARAM_PROVIDED = SAMPLE_RESPONSE_BASE + "No request param was provided.";
    static final String SAMPLE_EXCEPTION_MESSAGE = SAMPLE_RESPONSE_BASE + "An expected error was thrown.";

    /**
     * Used simply to check if this controller is responding to requests.
     * Has no function other than echoing.
     *
     * @return A sample message based on the input parameters.
     * @throws RuntimeException Only when 'throwError' is true.
     */
    @ApiOperation("Returns a sample message for baseline controller testing.")
    @GetMapping("/sample")
    public String getSampleResponse(@ApiParam("The message that will be echoed back to the user.")
                                    @RequestParam(required = false) final String message,
                                    @ApiParam("Forces this endpoint to throw a generic error.")
                                    @RequestParam(required = false) final boolean throwError) {
        String response;
        if (throwError) {
            throw new RuntimeException(SAMPLE_EXCEPTION_MESSAGE);
        } else if (!StringUtils.hasLength(message)) {
            response = NO_SAMPLE_PARAM_PROVIDED;
        } else {
            response = SAMPLE_PARAM_PROVIDED + message;
        }
        return response;
    }

    /**
     * A route to return a Site record by its id. The information is supplemented with addtional details:
     *      total_size, by summing the size_sqft associated with the site’s use(s).
     *      primary_type, where the primary type is the largest use_type (by size_sqft) in aggregate per-site.
     *
     * @return A message JSON containing Site information supplemented with total_size and primary_type.
     */
    @ApiOperation("Returns a message JSON containing Site information supplemented with total_size and primary_type.")
    @GetMapping("/info")
    public ResponseEntity<Site> getSiteInfo(@RequestParam(name = "site_id", required = true) final int siteId) {
        // Get the site_id object from database.
        Site site = siteService.getSiteById(siteId);
        if(site == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Use lombok to create a copy of the site object to modify and return.
        Site retSite = site.toBuilder().build();

        // Calculate and grab the total size (sum) of all the associated sites.
        long totalSize = siteUseService.getTotalSqFt(siteId);
        retSite.setTotalSize(totalSize);

        // Find the largest site_use
        SiteUse primarySiteUse = siteUseService.getMaxSqFtSiteUse(siteId);

        // Query for use_type and set it.
        int primaryUseTypeId = primarySiteUse.getUseTypeId();
        UseType primaryUseType = useTypeService.getUseTypeById(primaryUseTypeId);
        retSite.setPrimaryType(primaryUseType);

        return new ResponseEntity<Site>(retSite, HttpStatus.OK);
    }

    /**
     * Get a list of all Sites in the database.
     *
     * @return A list of all Sites in the database.
     */
    @ApiOperation("Returns a message JSON containing a list of all Sites")
    @GetMapping("/all")
    public ResponseEntity<List<Site>> getAllSite() {
        List<Site> sites = siteService.getAllSite();
        if(sites == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<Site>>(sites, HttpStatus.OK);
    }

    /**
     * Get a list of all Sites within a given state.
     *
     * @return A list of all Sites within a given state.
     */
    @GetMapping("/state")
    public ResponseEntity<List<Site>> getAllSitesByState(@RequestParam(required = true) final String state) {
        List<Site> sites = siteService.getAllSiteByState(state);
        if(sites == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<Site>>(sites, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Site> createSite(@RequestParam(required = true) String name,
                           @RequestParam(required = true) String address,
                           @RequestParam(required = true) String city,
                           @RequestParam(required = true) String state,
                           @RequestParam(required = true) String zipcode) {
        Site site = new Site();
        site.setName(name);
        site.setAddress(address);
        site.setCity(city);
        site.setState(state);
        site.setZipcode(zipcode);

        Site saveSite = siteService.save(site);
        if(saveSite == null) {
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Site>(saveSite, HttpStatus.OK);
    }
}