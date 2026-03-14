package com.hellFire.JobService.controllers;

import com.hellFire.JobService.dtos.JobDto;
import com.hellFire.JobService.dtos.requests.CreateJobRequest;
import com.hellFire.JobService.dtos.responses.ApiResponse;
import com.hellFire.JobService.dtos.responses.JobResponse;
import com.hellFire.JobService.services.JobService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/job")
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

    @GetMapping("/get-all-jobs")
    public ResponseEntity<ApiResponse<List<JobResponse>>> getJobById() {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(jobService.getAllJobs()));
    }




}
