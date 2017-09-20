package org.dgc.sandbox.swagger2.service;

import org.dgc.sandbox.swagger2.domain.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


/**
 * Resource service.
 */
@Service
public class ResourceService
{
    /**
     * Search a resource given its unique code.
     * @param code is the unique code
     * @return the resource
     */
    public Mono<Resource> getResourceByCode(final String code)
    {
        return Mono.just(Resource.builder().code(code).build());
    }
}
