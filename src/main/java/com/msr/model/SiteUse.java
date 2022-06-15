package com.msr.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table
public class SiteUse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "site_id")
    @JsonProperty("site_id")
    private int siteId;

    @JsonProperty("description")
    private String description;

    @JsonProperty("size_sqft")
    private long sizeSqft;

    @JsonProperty("use_type_id")
    private int useTypeId;

    //@ManyToOne(optional=false)
    //@JoinColumn(name="useTypeId", referencedColumnName="use_type_id")
    //private UseType useType;
}
