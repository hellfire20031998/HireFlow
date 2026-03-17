# Job Service – UI Page Implementation Prompt

Use this spec to build a **UI (web page)** for the HireFlow Job Service. Implement all listed API calls and use the request/response structures below. Assume the Job Service base URL is configurable (e.g. `http://localhost:8081` or via env), and that the backend expects these headers for authenticated requests: **`X-User-Id`**, **`X-User-Name`**, **`X-Tenant-Id`**, **`X-Roles`** (comma-separated).

---

## 1. API base and wrapper

- **Base URL:** `{BASE_URL}/job` (e.g. `http://localhost:8081/job`).
- Every response is wrapped in **ApiResponse<T>**:

```json
{
  "success": true,
  "message": "Success",
  "errorCode": null,
  "timestamp": "2025-03-16T10:00:00Z",
  "data": { ... }
}
```

On error, `success` is `false`, `message` and optionally `errorCode` are set, and `data` may be null or an object (e.g. validation errors).

---

## 2. API endpoints

### 2.1 Get dropdown enums

**GET** `{BASE_URL}/job/enums`

**Response (data):** `EnumsResponse`

```json
{
  "departments": ["ENGINEERING", "PRODUCT", "DESIGN", "MARKETING", "SALES", "HR", "OPERATIONS", "FINANCE", "CUSTOMER_SUCCESS", "OTHER"],
  "locationTypes": ["REMOTE", "ONSITE", "HYBRID"],
  "jobTypes": ["FULL_TIME", "PART_TIME", "CONTRACT", "INTERNSHIP", "TEMPORARY", "FREELANCE"],
  "employmentTypes": ["PERMANENT", "CONTRACT", "INTERN", "TEMPORARY"],
  "seniorityLevels": ["INTERN", "JUNIOR", "MID", "SENIOR", "LEAD", "MANAGER", "DIRECTOR"],
  "industries": ["INFORMATION_TECHNOLOGY", "HEALTHCARE", "FINANCE", "EDUCATION", "ECOMMERCE", "MANUFACTURING", "CONSULTING", "TELECOMMUNICATION", "OTHER"],
  "jobStatuses": ["DRAFT", "PUBLISHED", "CLOSED"]
}
```

Use this once (e.g. on load) to populate all dropdowns.

---

### 2.2 Create job

**POST** `{BASE_URL}/job/create`

**Request body:** `CreateJobRequest`

| Field            | Type     | Required | Description |
|------------------|----------|----------|-------------|
| title            | string   | Yes      | Job title   |
| description      | string   | Yes      | Job description |
| aboutRole        | string   | No       | About the role |
| department       | string   | No       | One of enums: departments |
| location         | string   | No       | Location text |
| locationType     | string   | No       | One of: REMOTE, ONSITE, HYBRID |
| jobType          | string   | No       | One of enums: jobTypes |
| employmentType   | string   | No       | One of enums: employmentTypes |
| seniorityLevel   | string   | No       | One of enums: seniorityLevels |
| industries       | string[] | No       | Array of enums: industries (multiple allowed) |
| minExperience    | number   | No       | Years |
| maxExperience    | number   | No       | Years |
| minSalary        | number   | No       | |
| maxSalary        | number   | No       | |
| openings         | number   | No       | Number of openings |
| deadline         | string   | No       | ISO-8601 instant, e.g. "2025-12-31T23:59:59Z" |

**Example:**

```json
{
  "title": "Senior Backend Engineer",
  "description": "We are looking for...",
  "aboutRole": "You will work on...",
  "department": "ENGINEERING",
  "location": "Bangalore",
  "locationType": "HYBRID",
  "jobType": "FULL_TIME",
  "employmentType": "PERMANENT",
  "seniorityLevel": "SENIOR",
  "industries": ["INFORMATION_TECHNOLOGY", "ECOMMERCE"],
  "minExperience": 3,
  "maxExperience": 7,
  "minSalary": 1500000,
  "maxSalary": 2500000,
  "openings": 2,
  "deadline": "2025-06-30T23:59:59Z"
}
```

**Response (data):** `JobDto` (see section 3.1). HTTP status **201 Created**.

---

### 2.3 Get job by ID

**GET** `{BASE_URL}/job/get-job/{id}`

**Path:** `id` (number) – job ID.

**Response (data):** `JobResponse`

```json
{
  "job": { ... JobDto ... },
  "skills": [ { ... SkillDto ... }, ... ]
}
```

`JobDto` – section 3.1. `SkillDto` – section 3.2.

---

### 2.4 Update job (partial)

**PATCH** `{BASE_URL}/job/{id}`

**Path:** `id` (number) – job ID.

**Request body:** `UpdateJobRequest` – **all fields optional**. Send only fields that should change.

| Field          | Type     | Description |
|----------------|----------|-------------|
| title          | string   | |
| description    | string   | |
| aboutRole      | string   | |
| department     | string   | Enum: departments |
| location       | string   | |
| locationType   | string   | REMOTE, ONSITE, HYBRID |
| jobType        | string   | Enum: jobTypes |
| employmentType | string   | Enum: employmentTypes |
| seniorityLevel | string   | Enum: seniorityLevels |
| industries     | string[] | Enum array: industries |
| minExperience  | number   | |
| maxExperience  | number   | |
| minSalary      | number   | |
| maxSalary      | number   | |
| openings       | number   | |
| deadline       | string   | ISO-8601 instant |
| status         | string   | DRAFT, PUBLISHED, CLOSED |

**Example (update only status):**

```json
{
  "status": "PUBLISHED"
}
```

**Response (data):** `JobDto`. HTTP **200 OK**.

---

### 2.5 Get all jobs

**GET** `{BASE_URL}/job/get-all-jobs`

**Response (data):** array of `JobResponse`:

```json
[
  { "job": { ... JobDto ... }, "skills": [ ... ] },
  ...
]
```

---

### 2.6 Get all jobs by creator

**GET** `{BASE_URL}/job/get-all-jobs/by-created-by/{createdBy}`

**Path:** `createdBy` (number) – user ID.

**Response (data):** same as 2.5 – array of `JobResponse`.

---

### 2.7 Get all jobs by tenant

**GET** `{BASE_URL}/job/get-all-jobs/by-tenant/{tenantId}`

**Path:** `tenantId` (number) – tenant ID.

**Response (data):** same as 2.5 – array of `JobResponse`.

---

## 3. Response data structures

### 3.1 JobDto

Extends base fields: `id`, `createdAt`, `updatedAt`, `active`, `deleted`.  
Note: backend may serialize base fields in **snake_case** (`created_at`, `updated_at`). UI should handle either camelCase or snake_case for these.

| Field            | Type     |
|------------------|----------|
| id               | number   |
| createdAt        | string   | ISO-8601 |
| updatedAt        | string   | ISO-8601 |
| active           | boolean  |
| deleted          | boolean  |
| title            | string   |
| description      | string   |
| aboutRole        | string   |
| department       | string   |
| location         | string   |
| locationType     | string   |
| jobType          | string   |
| employmentType   | string   |
| seniorityLevel   | string   |
| industries       | string[] |
| minExperience    | number   |
| maxExperience    | number   |
| minSalary        | number   |
| maxSalary        | number   |
| openings         | number   |
| deadlineTime     | string   | ISO-8601 |
| status           | string   | DRAFT, PUBLISHED, CLOSED |
| createdBy        | number   | User ID |
| createdByName    | string   |
| tenantId         | number   |
| lastUpdatedBy    | number   | User ID (nullable) |
| lastUpdatedByName| string   | (nullable) |

### 3.2 SkillDto (used inside JobResponse.skills)

| Field     | Type    |
|-----------|---------|
| id        | number  |
| createdAt | string  |
| updatedAt | string  |
| active    | boolean |
| deleted   | boolean |
| name      | string  |

### 3.3 JobResponse

| Field  | Type           |
|--------|----------------|
| job    | JobDto         |
| skills | SkillDto[]     |

---

## 4. UI requirements (for Cursor)

1. **Configurable base URL** for the Job Service API (e.g. env var or config).
2. **Send headers** on every request (when user/tenant are known):  
   `X-User-Id`, `X-User-Name`, `X-Tenant-Id`, `X-Roles`.
3. **Fetch enums** from `GET /job/enums` once and use them for all dropdowns (department, locationType, jobType, employmentType, seniorityLevel, industries, jobStatus).
4. **Pages/flows to implement:**
   - **List jobs:** Call get-all-jobs (or by-created-by / by-tenant if filtered). Show table/cards with key fields and link to detail.
   - **Job detail:** Call get-job/{id}. Show full job + skills. Provide “Edit” that loads the same job into an update form.
   - **Create job:** Form with all CreateJobRequest fields; dropdowns from enums; submit POST /job/create; on success redirect or show created job.
   - **Update job:** Form pre-filled with current job data; only send changed fields in PATCH /job/{id}; support at least updating status, jobType, and other main fields.
5. **Error handling:** Parse `ApiResponse.success` and `message`/`errorCode`; show user-friendly messages on failure.
6. **Dates/times:** Use ISO-8601 for deadline and display in user’s locale.

Use the endpoints and request/response structures above as the single source of truth for the Job Service API when building the UI.
