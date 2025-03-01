package org.elearning.project.repository;

import org.elearning.project.model.Subscription;
import org.elearning.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    public User findByEmail(String email);
    public User findByName(String name);
    public User findByEmailAndPassword(String email,String password);
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.subscriptionList WHERE u.id = :id")
    Optional<User> findUserWithSubscriptions(@Param("id") int id);
}
