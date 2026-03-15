package com.hellFire.AuthService.controllers;

import com.hellFire.AuthService.dto.TenantDto;
import com.hellFire.AuthService.dto.requests.CreateTenantRequest;
import com.hellFire.AuthService.dto.responses.ApiResponse;
import com.hellFire.AuthService.model.User;
import com.hellFire.AuthService.services.TenantService;
import com.hellFire.AuthService.utils.SecurityUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/tenants")
public class TenantController {

    private final TenantService tenantService;

    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<TenantDto>> createTenant(@Valid @RequestBody CreateTenantRequest request) {
        User currentUser = SecurityUtil.getCurrentUser();
        TenantDto tenant = tenantService.createTenant(request, currentUser);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(tenant, "Tenant created successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<TenantDto>> updateTenant(
            @PathVariable Long id,
            @RequestBody CreateTenantRequest request
    ) {
        TenantDto tenant = tenantService.updateTenant(id, request);
        return ResponseEntity.ok(ApiResponse.success(tenant, "Tenant updated successfully"));
    }
}

