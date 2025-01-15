package org.elearning.project.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        // Initialize a User object before each test
        user = new User();
        user.setId(1);
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password123");
        user.setRole(Role.STUDENT);
    }

    @Test
    void prePersistDate() {
        // Assuming prePersistDate is used to automatically set created_at on entity persistence.
        user.prePersistDate();

        assertNotNull(user.getCreated_at(), "Created date should not be null after prePersist.");
    }

    @Test
    void getId() {
        assertEquals(1, user.getId(), "The ID should match.");
    }

    @Test
    void setId() {
        user.setId(2);
        assertEquals(2, user.getId(), "The ID should be updated to 2.");
    }

    @Test
    void getName() {
        assertEquals("John Doe", user.getName(), "The name should match.");
    }

    @Test
    void setName() {
        user.setName("Jane Doe");
        assertEquals("Jane Doe", user.getName(), "The name should be updated to Jane Doe.");
    }

    @Test
    void getEmail() {
        assertEquals("john.doe@example.com", user.getEmail(), "The email should match.");
    }

    @Test
    void setEmail() {
        user.setEmail("jane.doe@example.com");
        assertEquals("jane.doe@example.com", user.getEmail(), "The email should be updated to jane.doe@example.com.");
    }

    @Test
    void getPassword() {
        assertEquals("password123", user.getPassword(), "The password should match.");
    }

    @Test
    void setPassword() {
        user.setPassword("newpassword");
        assertEquals("newpassword", user.getPassword(), "The password should be updated.");
    }

    @Test
    void getRole() {
        assertEquals(Role.STUDENT, user.getRole(), "The role should match.");
    }

    @Test
    void setRole() {
        user.setRole(Role.MANAGER);
        assertEquals(Role.MANAGER, user.getRole(), "The role should be updated to ADMIN.");
    }

    @Test
    void getCreated_at() {
        assertNull(user.getCreated_at(), "Created_at should be null before persistence.");

        // Test after setting it (simulate prePersist)
        user.setCreated_at(LocalDateTime.now());
        assertNotNull(user.getCreated_at(), "Created_at should not be null after setting.");
    }

    @Test
    void setCreated_at() {
        LocalDateTime now = LocalDateTime.now();
        user.setCreated_at(now);
        assertEquals(now, user.getCreated_at(), "The created_at date should match.");
    }
}
