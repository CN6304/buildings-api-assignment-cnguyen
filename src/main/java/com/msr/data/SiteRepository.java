package com.msr.data;

import com.msr.model.Site;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * A sample JPA repository for querying and storing sites
 */
public interface SiteRepository extends PagingAndSortingRepository<Site, Integer> {
    Site findById(int id);
    List<Site> findAllSiteByState(String state);
}