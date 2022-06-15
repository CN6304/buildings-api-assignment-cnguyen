package com.msr.service;

import com.msr.data.SiteUseRepository;
import com.msr.model.SiteUse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SiteUseService {
    private SiteUseRepository siteUseRepository;

    @Autowired
    public void setSiteUseRepository(SiteUseRepository siteUseRepository) {
        this.siteUseRepository = siteUseRepository;
    }

    public long getTotalSqFt(int siteId) {
        return siteUseRepository.findTotalSqFt(siteId);
    }

    public SiteUse getMaxSqFtSiteUse(int siteId) {
        return siteUseRepository.findMaxSqFt(siteId);
    }

    public Iterable<SiteUse> save(List<SiteUse> siteUses) {
        return siteUseRepository.saveAll(siteUses);
    }
}
