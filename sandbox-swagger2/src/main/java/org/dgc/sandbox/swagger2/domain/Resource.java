package org.dgc.sandbox.swagger2.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

/**
 * Represents a resource.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Resource
{
    private Long id;
    private String resourceType;
    private String code;
}
