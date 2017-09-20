package org.dgc.sandbox.swagger2.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.dgc.sandbox.swagger2.domain.Resource;
import org.dgc.sandbox.swagger2.service.ResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Api(value = "/resource", description = "Operations about resources")
@RestController
public class ResourceController
{
    private static Logger logger = LoggerFactory.getLogger(ResourceController.class);

    @Autowired
    private ResourceService resourceService;


    /**
     * Get a resource given its code.
     * TODO set error codes, for instance, NOT_FOUND.
     *
     * @param code is the resource unique code
     * @return the resource
     */
    @ApiOperation(value = "Find resource by code", response = Resource.class)
    @GetMapping(value = "/resource/{code}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Resource getResourceById(@PathVariable @ApiParam(value = "resource code", required = true) String code)
    {
        logger.debug("Requested resource code '%s'", code);

        return resourceService.getResourceByCode(code).block();

    }
}
