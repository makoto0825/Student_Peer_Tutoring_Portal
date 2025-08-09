# Student Peer Tutoring Portal

A web application that connects students with peer tutors to facilitate collaborative learning and academic support.  
Supports **role-based access** (Student, Tutor, Admin), tutor **verification**, **department** management, and **session booking**.

## 🚀 Getting Started

### Prerequisites

- **Java 17+**
- **Maven 3.6+**
- **MySQL 8.0+**
- **IDE** (IntelliJ IDEA, Eclipse, VS Code)

### Setup

1. **Clone**

```bash
git clone https://github.com/makoto0825/Student_Peer_Tutoring_Portal.git
cd Student_Peer_Tutoring_Portal
```

2. **Database**
   Update `src/main/resources/application.properties` if needed:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/project?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=your_password_here

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

3. **Build**

```bash
mvn clean install
```

4. **Run**
   Run `ProjectApplication.java` from your IDE.

5. **Open**
   `http://localhost:8080`

## 📖 Page & Route Overview

### Public

- **Home** — `GET /home`  
  _Controller: `Home
Controller` → `home.html`_
  <img width="1215" height="587" alt="Screenshot 2025-08-09 at 14 41 27" src="https://github.com/user-attachments/assets/0ca67a43-92aa-41bf-9875-b8bc5a977035" />
  
- **Login** — `GET /login`  
  _Controller: `HomeController` → `login.html`_

  <img width="607" height="520" alt="Screenshot 2025-08-09 at 14 42 31" src="https://github.com/user-attachments/assets/f305b078-79ee-4b89-a811-ea125f95a71f" />

- **Register** — `GET /register`, `POST /register`  
  _Controller: `UserController` → `register.html`_
  <img width="702" height="726" alt="Screenshot 2025-08-09 at 14 43 16" src="https://github.com/user-attachments/assets/8de21a4b-9b9d-4ad8-a17c-9b472505e14f" />

- **Register Success** — `GET /register-success`  
  _Controller: `UserController` → `register_success.html`_
- **Root Redirect** — `GET /` → `redirect:/home`

### Student (role = 1)

- **Dashboard** — `GET /student` → `student.html`  
  Upcoming/past sessions with tutor names & departments
  <img width="1221" height="726" alt="Screenshot 2025-08-09 at 14 43 57" src="https://github.com/user-attachments/assets/c80cab3c-629d-41df-a289-e37c9acd4bff" />

- **Profile** — `GET /student/profile`, `POST /student/profil![Uploading Screenshot 2025-08-09 at 14.44.32.png…]()
e` → `student_profile.html`
  <img width="1221" height="726" alt="Screenshot 2025-08-09 at 14 44 50" src="https://github.com/user-attachments/assets/0ff4586d-7bdb-4a89-b9ae-84bc680a139a" />
  
- **Book Session** — `GET /student/book-session`, `POST /student/book-session` → `session-booking.html`  
  Department/tutor filters + unbooked sessions
  <img width="690" height="506" alt="Screenshot 2025-08-09 at 14 45 14" src="https://github.com/user-attachments/assets/87dad324-8945-4f1e-94bc-b4aaad036911" />

- **Cancel Session** — `POST /student/cancel-session`  
  (Only future sessions; server-validated)

### Tutor (role = 2)

- **Dashboard** — `GET /tutor` → `tutor.html`  
  Upcoming (today or later) + past (booked) sessions, student names
  <img width="1213" height="731" alt="Screenshot 2025-08-09 at 14 46 47" src="https://github.com/user-attachments/assets/fd981f97-7d7c-4631-a956-7c22798ad3cd" />

- **Create Session** — `POST /tutor/session`  
  (Add open time slots: date + timeSlot)

  <img width="329" height="259" alt="Screenshot 2025-08-09 at 14 47 12" src="https://github.com/user-attachments/assets/038a004e-1a0c-44b9-9ba7-3c16347d7b34" />

- **Delete Session** — `POST /tutor/session/delete`  
  (Only upcoming sessions)
- **Profile** — `GET /tutor/profile`, `POST /tutor/profile` → `tutor-profile.html`
  <img width="1004" height="620" alt="Screenshot 2025-08-09 at 14 48 50" src="https://github.com/user-attachments/assets/dd923496-ef1a-4cfb-bee2-089aa1fe8d14" />


### Admin

- **Admin Panel** — `GET /admin` → `admin.html`  
  Lists **pending tutors** (role=2, verified=false)
  <img width="1217" height="349" alt="Screenshot 2025-08-09 at 14 48 03" src="https://github.com/user-attachments/assets/d5dc9673-00fb-4970-8529-10001e0889d0" />

- **Verify Tutor** — `POST /admin/verify/{id}`
- **Deny Tutor** — `POST /admin/deny/{id}` (deletes unverified tutor)

> If a user hits a role-mismatched page, controllers redirect to `/access-denied` (add a template if you want a nice page).

## 🏗️ Project Structure

```
src/
├─ main/
│  ├─ java/com/example/project/
│  │  ├─ ProjectApplication.java
│  │  ├─ config/
│  │  │  ├─ SecurityConfig.java
│  │  │  └─ PasswordEncoderConfig.java
│  │  ├─ controller/
│  │  │  ├─ HomeController.java
│  │  │  ├─ UserController.java
│  │  │  ├─ StudentController.java
│  │  │  ├─ TutorController.java
│  │  │  └─ AdminController.java
│  │  ├─ entity/
│  │  │  ├─ User.java
│  │  │  ├─ Department.java
│  │  │  └─ Session.java
│  │  ├─ repository/
│  │  │  ├─ UserRepository.java
│  │  │  ├─ DepartmentRepository.java
│  │  │  └─ SessionRepository.java
│  │  ├─ seed/
│  │  │  └─ DepartmentSeeder.java
│  │  └─ service/
│  │     ├─ CustomUserDetailsService.java
│  │     ├─ UserService.java
│  │     ├─ DepartmentService.java
│  │     └─ SessionService.java
│  └─ resources/
│     ├─ application.properties
│     ├─ static/
│     └─ templates/
└─ test/
```

## 🔧 Tech Stack

- **Spring Boot 3.x**, **Thymeleaf**
- **Spring Security** (BCrypt)
- **MySQL** + **Spring Data JPA**
- **Maven**, **Java 17**

## 🔑 Core Features

- **Registration with Roles**  
  Role 1 = Student, Role 2 = Tutor (Admin supported); Tutors start **unverified**.
- **Tutor Verification (Admin)**  
  Approve or deny pending tutors.
- **Departments (Seeded)**  
  `DepartmentSeeder` loads initial departments at startup.
- **Sessions**  
  Tutors publish time slots; Students book/cancel with safeguards.
- **Profiles**  
  Students & Tutors can update their profile fields.

## 🚦 Endpoints (Quick Reference)

| Endpoint                  | Method   | Purpose                    | Access        |
| ------------------------- | -------- | -------------------------- | ------------- |
| `/`                       | GET      | Redirect to `/home`        | Public        |
| `/home`                   | GET      | Home page                  | Public        |
| `/login`                  | GET      | Login page                 | Public        |
| `/register`               | GET/POST | Create account             | Public        |
| `/register-success`       | GET      | Post-register confirmation | Public        |
| `/student`                | GET      | Student dashboard          | Student       |
| `/student/profile`        | GET/POST | View/update profile        | Student       |
| `/student/book-session`   | GET/POST | List & book sessions       | Student       |
| `/student/cancel-session` | POST     | Cancel future session      | Student       |
| `/tutor`                  | GET      | Tutor dashboard            | Tutor         |
| `/tutor/session`          | POST     | Create session slot        | Tutor         |
| `/tutor/session/delete`   | POST     | Delete upcoming session    | Tutor         |
| `/tutor/profile`          | GET/POST | View/update profile        | Tutor         |
| `/admin`                  | GET      | Pending tutors list        | Admin         |
| `/admin/verify/{id}`      | POST     | Verify tutor               | Admin         |
| `/admin/deny/{id}`        | POST     | Deny (delete) tutor        | Admin         |
| `/logout`                 | POST     | Logout                     | Authenticated |

## 🔄 Database Schema (Essentials)

### `users`

| Column        | Type                       | Notes                                   |
| ------------- | -------------------------- | --------------------------------------- |
| id            | BIGINT PK                  | auto                                    |
| username      | VARCHAR(255)               | unique, not null                        |
| password      | VARCHAR(255)               | BCrypt                                  |
| email         | VARCHAR(255)               | not null                                |
| first_name    | VARCHAR(255)               |                                         |
| last_name     | VARCHAR(255)               |                                         |
| description   | TEXT                       | profile bio                             |
| department_id | BIGINT FK → department(id) | required for tutors                     |
| role          | INT                        | 1=Student, 2=Tutor, (3=Admin supported) |
| verified      | BOOLEAN                    | tutors must be verified by admin        |
| enabled       | BOOLEAN                    | default true                            |

### `department`

| Column | Type         | Notes            |
| ------ | ------------ | ---------------- |
| id     | BIGINT PK    | auto             |
| name   | VARCHAR(255) | unique, not null |

### `session`

| Column     | Type                  | Notes                                 |
| ---------- | --------------------- | ------------------------------------- |
| id         | BIGINT PK             | auto                                  |
| tutor_id   | BIGINT FK → users(id) | required                              |
| student_id | BIGINT FK → users(id) | null until booked                     |
| date       | DATE                  | required                              |
| time_slot  | VARCHAR(50)           | e.g., `"09:00-09:30"`                 |
| status     | VARCHAR(50)           | optional (e.g., `"OPEN"`, `"BOOKED"`) |

## 🧭 Typical Flows & Role Features

### All Users

**Features**

- Register (`/register`) → success screen (`/register-success`)
- Log in (`/login`), log out (`/logout`)
- View Home (`/home`)
- Update profile (role-specific pages)

**Flow**

1. Visit `/register` → submit form
2. See `/register-success` → go to `/login`
3. Log in → redirected to role dashboard (`/student`, `/tutor`, or `/admin`)
4. Update profile from role-specific profile page

---

### Student (role = 1)

**Key Pages & Endpoints**

- Dashboard: `GET /student`
- Profile: `GET /student/profile`, `POST /student/profile`
- Book Session: `GET /student/book-session`, `POST /student/book-session`
- Cancel Session: `POST /student/cancel-session`

**Features**

- See **upcoming** and **past** sessions (with tutor names + department names)
- Filter/browse **available (unbooked)** sessions by department/tutor
- Book a session (assigns `student_id`)
- Cancel **future** sessions only (server-validated)
- Edit profile (name, email, description, department if applicable)

**Typical Flow**

1. Open **Student Dashboard** (`/student`) to review sessions
2. Go to **Book Session** (`/student/book-session`) → filter by department/tutor
3. **POST book-session** with `sessionId` → success/error flash
4. If needed, **cancel a future session** via `POST /student/cancel-session`
5. **Update profile** at `/student/profile` → success flash

**Guards**

- Cancels only allowed for **dates after today**
- Role check enforced; non-students are redirected

---

### Tutor (role = 2)

**Key Pages & Endpoints**

- Dashboard: `GET /tutor`
- Create Session Slot: `POST /tutor/session`
- Delete Session Slot: `POST /tutor/session/delete`
- Profile: `GET /tutor/profile`, `POST /tutor/profile`

**Features**

- View upcoming sessions (today or later) and past **booked** sessions
- See student names for booked sessions
- Publish **open time slots** (date + `timeSlot`)
- Delete **upcoming** sessions only
- Edit profile (name, email, description, department)

**Typical Flow**

1. (After admin verification) open **Tutor Dashboard** (`/tutor`)
2. **Create session slots** with date + timeSlot → success flash
3. Manage slots: **delete upcoming** sessions if needed
4. Track booked sessions and prep using student names
5. **Update profile** at `/tutor/profile` → success flash

**Guards**

- Only **upcoming** sessions can be deleted
- Only verified tutors should be actively scheduling (enforced by admin flow)
- Role check enforced; non-tutors are redirected

---

### Admin

**Key Pages & Endpoints**

- Admin Panel: `GET /admin`
- Verify Tutor: `POST /admin/verify/{id}`
- Deny Tutor: `POST /admin/deny/{id}`

**Features**

- View **pending tutors** (role=2, `verified=false`)
- **Verify** tutor (sets `verified=true`)
- **Deny** tutor (remove unverified tutor account)

**Typical Flow**

1. Open **Admin Panel** (`/admin`) → see **Pending Tutors** list
2. For each candidate:
   - **Verify** → tutor becomes active and can create sessions
   - **Deny** → tutor record deleted
3. Return to `/admin` to review remaining pending tutors

**Guards**

- Admin-only access to `/admin` and verify/deny endpoints
- Verify action only applies to users with role=2

---

## 🐛 Troubleshooting

- **DB connection**: ensure MySQL is running; verify credentials in `application.properties`.
- **Port conflict**: set `server.port=8081`.
- **Tables not present**: confirm `spring.jpa.hibernate.ddl-auto=update`.
- **Seeds not loading**: check `DepartmentSeeder` is annotated and runs at startup.

## 📝 Development Notes

- Thymeleaf templates live in `src/main/resources/templates`.
- Controllers rely on `SessionService`/`UserService` for business logic and validation.
- Security paths & post-login redirects are configured in `SecurityConfig`.

## 📄 License

For educational use as part of Web Application Development coursework.
