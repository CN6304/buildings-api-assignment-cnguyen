package com.msr.data;

import com.msr.model.SiteUse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SiteUseRepository extends JpaRepository<SiteUse, Integer> {
    List<SiteUse> findAllBySiteId(int siteId);

    @Query(value = "SELECT SUM(size_sqft) FROM site_use WHERE site_id = ?1", nativeQuery = true)
    long findTotalSqFt(int siteId);

    @Query(value = "SELECT * FROM site_use WHERE size_sqft = (SELECT MAX(size_sqft) FROM site_use WHERE site_id = ?1)",
            nativeQuery = true)
    SiteUse findMaxSqFt(int siteId);

}
