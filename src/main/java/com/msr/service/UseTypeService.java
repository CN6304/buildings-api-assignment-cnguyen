package com.msr.service;

import com.msr.data.UseTypeRepository;
import com.msr.model.UseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UseTypeService {
    private UseTypeRepository useTypeRepository;

    @Autowired
    public void setUseTypeRepository(UseTypeRepository useTypeRepository) {
        this.useTypeRepository = useTypeRepository;
    }

    public UseType getUseTypeById(int id) {
        return useTypeRepository.findById(id);
    }

    public Iterable<UseType> save(List<UseType> useTypes) {
        return useTypeRepository.saveAll(useTypes);
    }
}
