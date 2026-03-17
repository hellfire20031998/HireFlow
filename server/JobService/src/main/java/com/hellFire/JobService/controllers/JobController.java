package com.hellFire.JobService.controllers;

import com.hellFire.JobService.dtos.JobDto;
import com.hellFire.JobService.dtos.requests.CreateJobRequest;
import com.hellFire.JobService.dtos.requests.UpdateJobRequest;
import com.hellFire.JobService.dtos.responses.ApiResponse;
import com.hellFire.JobService.dtos.responses.EnumsResponse;
import com.hellFire.JobService.dtos.responses.JobResponse;
import com.hellFire.JobService.models.enums.*;
import com.hellFire.JobService.services.JobService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<JobDto>> createJob(@RequestBody @Valid CreateJobRequest request) {
        JobDto jobDto = jobService.createJob(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(jobDto));
    }

    @GetMapping("/get-job/{id}")
    public ResponseEntity<ApiResponse<JobResponse>> getJobById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(jobService.getJobByID(id)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<JobDto>> updateJob(@PathVariable Long id, @RequestBody UpdateJobRequest request) {
        return ResponseEntity.ok(ApiResponse.success(jobService.updateJob(id, request)));
    }

    @GetMapping("/get-all-jobs")
    public ResponseEntity<ApiResponse<List<JobResponse>>> getAllJobs() {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(jobService.getAllJobs()));
    }

    @GetMapping("/get-all-jobs/by-created-by/{createdBy}")
    public ResponseEntity<ApiResponse<List<JobResponse>>> getAllJobsByCreatedBy(@PathVariable Long createdBy) {
        return ResponseEntity.ok(ApiResponse.success(jobService.getAllJobsByCreatedBy(createdBy)));
    }

    @GetMapping("/get-all-jobs/by-tenant/{tenantId}")
    public ResponseEntity<ApiResponse<List<JobResponse>>> getAllJobsByTenantId(@PathVariable Long tenantId) {
        return ResponseEntity.ok(ApiResponse.success(jobService.getAllJobsByTenantId(tenantId)));
    }

    @GetMapping("/enums")
    public ResponseEntity<ApiResponse<EnumsResponse>> getEnums() {
        EnumsResponse enums = EnumsResponse.builder()
                .departments(toNames(Department.values()))
                .locationTypes(toNames(LocationType.values()))
                .jobTypes(toNames(JobType.values()))
                .employmentTypes(toNames(EmploymentType.values()))
                .seniorityLevels(toNames(SeniorityLevel.values()))
                .industries(toNames(Industry.values()))
                .jobStatuses(toNames(JobStatus.values()))
                .salaryCurrencies(toNames(SalaryCurrency.values()))
                .salaryTypes(toNames(SalaryType.values()))
                .build();
        return ResponseEntity.ok(ApiResponse.success(enums));
    }

    private static <E extends Enum<E>> List<String> toNames(E[] values) {
        return Arrays.stream(values).map(Enum::name).collect(Collectors.toList());
    }
}
