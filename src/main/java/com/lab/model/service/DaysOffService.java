package com.lab.model.service;

import com.lab.model.model.DaysOff;
import com.lab.model.repository.DaysOffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DaysOffService {
    private DaysOffRepository daysOffRepository;

    @Autowired
    public DaysOffService(DaysOffRepository daysOffRepository){
        this.daysOffRepository = daysOffRepository;
    }

    public Optional<List<DaysOff>> findAll(){
        return this.findAll();
    }

}
