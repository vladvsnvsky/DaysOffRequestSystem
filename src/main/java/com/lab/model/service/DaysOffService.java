package com.lab.model.service;

import com.lab.model.dto.DaysOffDTO;
import com.lab.model.model.DaysOff;
import com.lab.model.model.UserEntity;
import com.lab.model.repository.DaysOffRepository;
import com.lab.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class DaysOffService {
    private DaysOffRepository daysOffRepository;
    private UserRepository userRepository;

    @Autowired
    public DaysOffService(DaysOffRepository daysOffRepository, UserRepository userRepository){

        this.daysOffRepository = daysOffRepository;
        this.userRepository = userRepository;
    }

    public Optional<List<DaysOff>> findAll(){
        return this.findAll();
    }

    public boolean hasOverlappingRequest(Long userId, LocalDate startDate, LocalDate endDate) {
        List<DaysOff> existingRequests = daysOffRepository.findByUserId(userId);
        return existingRequests.stream()
                .anyMatch(request ->
                        startDate.isBefore(request.getStartDate()) && endDate.isAfter(request.getStartDate()) ||
                        startDate.isBefore(request.getEndDate()) && endDate.isAfter(request.getEndDate()) ||
                                startDate.isEqual(request.getStartDate()) || startDate.isEqual(request.getEndDate()) ||
                                endDate.isEqual(request.getStartDate()) || endDate.isEqual(request.getEndDate())
                );
    }

    public boolean saveDaysOffRequestIfNoOverlap(DaysOffDTO dto) {
        Optional<UserEntity> userOptional = userRepository.findByEmail(dto.getUserEmail());
        UserEntity user;
        if(userOptional.isEmpty())
            return false;
        else user = userOptional.get();


        if (!hasOverlappingRequest(user.getId(), dto.getStartDate(), dto.getEndDate())) {
            DaysOff entity = new DaysOff();
            entity.setApproved(null);
            entity.setUser(user);
            entity.setStartDate(dto.getStartDate());
            entity.setEndDate(dto.getEndDate());
            entity.setMessage(dto.getMessage());
            daysOffRepository.save(entity);
            return true;
        }
        return false;
    }

}

