package com.hellFire.AuthService.services;

import com.hellFire.AuthService.dto.IndustryDto;
import com.hellFire.AuthService.mapper.IIndustryMapper;
import com.hellFire.AuthService.respositories.IIndustryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndustryService {

    private final IIndustryRepository industryRepository;
    private final IIndustryMapper industryMapper;

    public IndustryService(IIndustryRepository industryRepository, IIndustryMapper industryMapper) {
        this.industryRepository = industryRepository;
        this.industryMapper = industryMapper;
    }

    public List<IndustryDto> getLatest10() {
        return industryRepository.findTop10ByDeletedFalseOrderByCreatedAtDesc().stream()
                .map(industryMapper::toDto)
                .toList();
    }

    public List<IndustryDto> search(String query) {
        if (query == null || query.isBlank()) {
            return getLatest10();
        }
        return industryRepository.search(query.trim()).stream()
                .map(industryMapper::toDto)
                .toList();
    }
}
