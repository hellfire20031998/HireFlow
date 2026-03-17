package com.hellFire.JobService.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnumsResponse {
    private List<String> departments;
    private List<String> locationTypes;
    private List<String> jobTypes;
    private List<String> employmentTypes;
    private List<String> seniorityLevels;
    private List<String> industries;
    private List<String> jobStatuses;
    private List<String> salaryCurrencies;
    private List<String> salaryTypes;
}
