package com.hellFire.AuthService.controllers;

import com.hellFire.AuthService.dto.IndustryDto;
import com.hellFire.AuthService.dto.responses.ApiResponse;
import com.hellFire.AuthService.services.IndustryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth/industries")
public class IndustryController {

    private final IndustryService industryService;

    public IndustryController(IndustryService industryService) {
        this.industryService = industryService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<IndustryDto>>> getLatestIndustries() {
        List<IndustryDto> industries = industryService.getLatest10();
        return ResponseEntity.ok(ApiResponse.success(industries, "Latest 10 industries"));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<IndustryDto>>> searchIndustries(@RequestParam(required = false) String q) {
        List<IndustryDto> industries = industryService.search(q);
        return ResponseEntity.ok(ApiResponse.success(industries, "Industries matching search"));
    }
}
