# JobService Request Flow (via API Gateway)

## Architecture Overview

All client requests to JobService go through the **API Gateway** (port 8010). The gateway uses **Eureka** for service discovery and **JWT** for protecting non-public routes.

```
Client → API Gateway (8010) → [Eureka lookup] → Job-Service (8081)
                ↓
         JWT validation (except public auth routes)
```

---

## 1. API Gateway Configuration

**File:** `server/ApiGateway/src/main/resources/application.properties`

- **Route for JobService:**
  - **Predicate:** `Path=/jobs/**`
  - **URI:** `lb://JOB-SERVICE` (load-balanced via Eureka)
- **Discovery:** Gateway uses Eureka at `http://localhost:8000/eureka/` and forwards to the instance registered as `Job-Service`.

So any request to the gateway starting with `/jobs/` is forwarded to the JobService instance.

---

## 2. Security (JWT) at Gateway

**File:** `server/ApiGateway/src/main/java/.../filter/JwtAuthenticationGatewayFilter.java`

- **Public routes** (no JWT): only auth-related paths (login, register, industries, OPTIONS).
- **All other routes**, including **`/jobs/**`**, require a valid `Authorization: Bearer <token>` header.
- If the token is missing or invalid, the gateway responds with `401 Unauthorized` and never calls JobService.

So for JobService:
- Every request to `http://gateway:8010/jobs/*` must include a valid JWT.
- No path under `/jobs/**` is public.

---

## 3. JobService Endpoints

**File:** `server/JobService/src/main/java/.../controllers/JobController.java`

| Method | Path (on JobService) | Description |
|--------|----------------------|-------------|
| POST   | `/job/create`       | Create job |
| GET    | `/job/get-job/{id}` | Get job by ID |
| PATCH  | `/job/{id}`         | Update job |
| GET    | `/job/get-all-jobs` | Get all jobs |
| GET    | `/job/get-all-jobs/by-created-by/{createdBy}` | By creator |
| GET    | `/job/get-all-jobs/by-tenant/{tenantId}`      | By tenant |
| GET    | `/job/enums`        | Get enums (departments, job types, etc.) |

Controller base path: **`/job`** (singular).

---

## 4. Path Mismatch (Gateway vs JobService)

- **Gateway** forwards requests that match **`/jobs/**`** (plural) to Job-Service **with the same path** (no rewrite).
- **JobService** controller is mapped to **`/job/**`** (singular).

So a client call to the gateway like:

- `GET http://localhost:8010/jobs/get-all-jobs`

is sent to JobService as:

- `GET /jobs/get-all-jobs`

while the controller only handles:

- `GET /job/get-all-jobs`

Result: **404 Not Found** for all JobService endpoints when called through the gateway.

---

## 5. Correct Flow (After Path Alignment)

Once the controller uses **`/jobs`** (plural) to match the gateway route:

1. Client sends: `GET http://localhost:8010/jobs/get-all-jobs` with header `Authorization: Bearer <jwt>`.
2. Gateway receives the request; path matches `/jobs/**`.
3. Gateway runs `JwtAuthenticationGatewayFilter`: path is not public → JWT is validated; if invalid → 401.
4. Gateway looks up `JOB-SERVICE` in Eureka and forwards the request to an instance (e.g. `http://<host>:8081/jobs/get-all-jobs`).
5. JobService receives `GET /jobs/get-all-jobs`; controller mapped to `/jobs` handles it and returns the response.
6. Gateway returns that response to the client.

---

## 6. Summary

| Item | Status |
|------|--------|
| Gateway route for JobService | ✅ Configured (`/jobs/**` → `lb://JOB-SERVICE`) |
| JWT required for `/jobs/**` | ✅ All job routes protected |
| Eureka / discovery | ✅ Job-Service registers; gateway uses discovery |
| Path alignment | ❌ Gateway uses `/jobs`, controller uses `/job` → must align |

**Recommendation:** Change JobService controller base path from **`/job`** to **`/jobs`** so that paths match the gateway and clients can call `http://gateway:8010/jobs/...` successfully.
