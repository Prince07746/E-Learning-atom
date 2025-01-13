
---


### Final Database Schema for the Project
Here’s an updated schema with the necessary tables, relationships, and constraints:

---

#### **Users Table**
```sql
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('STUDENT', 'TEACHER', 'MANAGER') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

---

#### **Courses Table**
```sql
CREATE TABLE courses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) DEFAULT 0, -- Free if price = 0
    status ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE',
    teacher_id BIGINT, -- Teacher who owns the course
    FOREIGN KEY (teacher_id) REFERENCES users(id) ON DELETE SET NULL
);
```

---

#### **Lessons Table**
```sql
CREATE TABLE lessons (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    content_type ENUM('TEXT', 'VIDEO', 'IMAGE', 'SOUND') NOT NULL,
    content TEXT NOT NULL, -- URL or plain text
    course_id BIGINT NOT NULL,
    FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE
);
```

---

#### **Subscriptions Table**
```sql
CREATE TABLE subscriptions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    subscription_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE,
    UNIQUE(user_id, course_id) -- Prevent duplicate subscriptions
);
```

---

#### **Payments Table**
```sql
CREATE TABLE payments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    subscription_id BIGINT NOT NULL,
    payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    amount DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (subscription_id) REFERENCES subscriptions(id) ON DELETE CASCADE
);
```

---

#### **Feedback Table**
```sql
CREATE TABLE feedback (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    rating INT CHECK(rating BETWEEN 1 AND 5),
    comment TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE
);
```

---

#### **Discussions Table**
```sql
CREATE TABLE discussions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    course_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    created_by BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE,
    FOREIGN KEY (created_by) REFERENCES users(id) ON DELETE CASCADE
);
```

---

#### **Comments Table**
```sql
CREATE TABLE comments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    discussion_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (discussion_id) REFERENCES discussions(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
```

---

#### **Certificates Table**
```sql
CREATE TABLE certificates (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    issue_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE
);
```

---

### How to Integrate This in Spring Data JPA

1. **Entities:** Create a class for each table, mapping relationships using `@OneToMany`, `@ManyToMany`, etc.
2. **Repositories:** Use Spring Data JPA repositories for CRUD operations.
3. **Services:** Implement business logic to handle relationships.
4. **Controllers:** Expose APIs for front-end interaction.


---




To provide a comprehensive overview of all relationships in your database and document them properly, let's map each table and its relationships explicitly. Here's the full documentation, including **one-to-many**, **many-to-one**, and **many-to-many** relationships across all tables.

---

### **1. Users Table**
#### Purpose:
Stores information about system users, which could be students, teachers, or managers.

#### Relationships:
- **One-to-Many** with `subscriptions`: A user can subscribe to multiple courses.
- **One-to-Many** with `feedback`: A user can give feedback on multiple courses.
- **One-to-Many** with `comments`: A user can post multiple comments.
- **One-to-Many** with `certificates`: A user can earn multiple certificates.

---

### **2. Courses Table**
#### Purpose:
Stores information about courses, including their details, price, and status.

#### Relationships:
- **One-to-Many** with `lessons`: A course can have multiple lessons.
- **One-to-Many** with `subscriptions`: A course can have multiple subscribers.
- **One-to-Many** with `feedback`: A course can receive multiple pieces of feedback.
- **Many-to-Many** with `users`: Through `subscriptions`, linking students to the courses they are subscribed to.

---

### **3. Lessons Table**
#### Purpose:
Stores the content of a course, including text, videos, images, and sound.

#### Relationships:
- **Many-to-One** with `courses`: A lesson belongs to one course.

---

### **4. Subscriptions Table**
#### Purpose:
Acts as a junction table between `users` and `courses`, managing who is subscribed to which course.

#### Relationships:
- **Many-to-One** with `users`: A subscription is linked to one user.
- **Many-to-One** with `courses`: A subscription is linked to one course.

---

### **5. Feedback Table**
#### Purpose:
Stores feedback provided by users for courses.

#### Relationships:
- **Many-to-One** with `users`: Feedback is given by one user.
- **Many-to-One** with `courses`: Feedback is linked to one course.

---

### **6. Certificates Table**
#### Purpose:
Manages certificates awarded to users for completing courses.

#### Relationships:
- **Many-to-One** with `users`: A certificate belongs to one user.
- **Many-to-One** with `courses`: A certificate is linked to one course.

---

### **7. Discussions Table**
#### Purpose:
Manages discussions about courses, where users can comment.

#### Relationships:
- **One-to-Many** with `comments`: A discussion can have multiple comments.
- **Many-to-One** with `courses`: A discussion is linked to one course.

---

### **8. Comments Table**
#### Purpose:
Stores comments posted by users in discussions.

#### Relationships:
- **Many-to-One** with `discussions`: A comment belongs to one discussion.
- **Many-to-One** with `users`: A comment is posted by one user.

---

### **Database Schema with Relationships**

Here’s a summarized schema of the relationships:

| Table          | Related Table         | Relationship Type       | Description                                     |
|----------------|-----------------------|-------------------------|-------------------------------------------------|
| **Users**       | Subscriptions         | One-to-Many             | A user can subscribe to multiple courses.      |
| **Users**       | Feedback              | One-to-Many             | A user can provide multiple feedbacks.         |
| **Users**       | Comments              | One-to-Many             | A user can post multiple comments.             |
| **Users**       | Certificates          | One-to-Many             | A user can earn multiple certificates.         |
| **Courses**     | Lessons               | One-to-Many             | A course can have multiple lessons.            |
| **Courses**     | Subscriptions         | One-to-Many             | A course can have multiple subscribers.        |
| **Courses**     | Feedback              | One-to-Many             | A course can receive multiple feedbacks.       |
| **Courses**     | Discussions           | One-to-Many             | A course can have multiple discussions.        |
| **Courses**     | Certificates          | One-to-Many             | A course can issue multiple certificates.      |
| **Lessons**     | Courses               | Many-to-One             | A lesson belongs to one course.                |
| **Subscriptions** | Users              | Many-to-One             | A subscription links to one user.              |
| **Subscriptions** | Courses            | Many-to-One             | A subscription links to one course.            |
| **Feedback**    | Users                 | Many-to-One             | Feedback is linked to one user.                |
| **Feedback**    | Courses               | Many-to-One             | Feedback is linked to one course.              |
| **Certificates** | Users               | Many-to-One             | A certificate is awarded to one user.          |
| **Certificates** | Courses             | Many-to-One             | A certificate is linked to one course.         |
| **Discussions** | Comments              | One-to-Many             | A discussion can have multiple comments.       |
| **Discussions** | Courses               | Many-to-One             | A discussion is linked to one course.          |
| **Comments**    | Discussions           | Many-to-One             | A comment belongs to one discussion.           |
| **Comments**    | Users                 | Many-to-One             | A comment is posted by one user.               |

---

### **Notes for Documentation:**

1. **Foreign Keys with Descriptive Names**:
   - Use clear names like `user_id` and `course_id` for foreign keys to simplify understanding.

2. **Primary Keys**:
   - Use `AUTO_INCREMENT` for primary keys to ensure uniqueness.

3. **Join Tables**:
   - Use join tables (like `subscriptions`) to handle many-to-many relationships.

4. **JPA Integration**:
   - Annotate the entities with appropriate relationships, e.g., `@OneToMany`, `@ManyToOne`, `@JoinTable`.

---



