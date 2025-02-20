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
public interface SubscriptionRepository extends JpaRepository<Subscription,Integer> {

    @Query("SELECT u FROM User u LEFT JOIN  FETCH u.subscriptionList WHERE u.id = :userId")
    Optional<User> findByIdWithSubscriptions(@Param("userId") int id);
}
