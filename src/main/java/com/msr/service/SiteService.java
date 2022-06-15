package com.msr.service;

import com.msr.data.SiteRepository;
import com.msr.model.Site;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SiteService {
    private SiteRepository siteRepository;

    @Autowired
    public void setSiteRepository(SiteRepository siteRepository) {
        this.siteRepository = siteRepository;
    }

    public Site getSiteById(int id) {
        return siteRepository.findById(id);
    }

    public List<Site> getAllSite() {
        List<Site> sites = new ArrayList<>();
        siteRepository.findAll().forEach(site -> sites.add(site));
        return sites;
    }

    public List<Site> getAllSiteByState(String state) {
        List<Site> sites = new ArrayList<>();
        siteRepository.findAllSiteByState(state).forEach(site -> sites.add(site));
        return sites;
    }

    public Iterable<Site> save(List<Site> sites) {
        return siteRepository.saveAll(sites);
    }

    public Site save(Site site) {
        return siteRepository.save(site);
    }
}
