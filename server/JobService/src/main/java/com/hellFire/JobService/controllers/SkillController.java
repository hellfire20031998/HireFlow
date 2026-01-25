package com.hellFire.JobService.controllers;

import com.hellFire.JobService.dtos.SkillDto;
import com.hellFire.JobService.dtos.requests.CreateSkillRequest;
import com.hellFire.JobService.dtos.responses.ApiResponse;
import com.hellFire.JobService.services.SkillService;
import jakarta.validation.Valid;
import org.apache.kafka.shaded.com.google.protobuf.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/skill")
public class SkillController {

    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<ApiResponse<SkillDto>> createSkill(@RequestBody @Valid CreateSkillRequest request) {
        SkillDto skillDto = skillService.createSkill(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(skillDto));
    }

    @GetMapping("/get-skills")
    public ResponseEntity<ApiResponse<List<SkillDto>>> getAllSkills() {
        List<SkillDto> skillDtos = skillService.getAllSkills();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(skillDtos));
    }
}
