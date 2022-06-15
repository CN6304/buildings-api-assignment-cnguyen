package com.msr.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Site {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String address;

    private String city;

    private String state;

    private String zipcode;

    @Transient
    private List<SiteUse> siteUses;

    // Additional fields used for Task #1
    @Transient
    private long totalSize;

    @Transient
    private UseType primaryType;
}
