package org.elearning.project.service;

import jakarta.transaction.Transactional;
import org.elearning.project.model.Certificate;
import org.elearning.project.model.Course;
import org.elearning.project.model.Subscription;
import org.elearning.project.model.User;
import org.elearning.project.repository.SubscriptionRepository;
import org.elearning.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


@Service
public class UserLearningService {

    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;

    @Autowired
    public UserLearningService(UserRepository userRepository, SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.userRepository = userRepository;
    }


    @Transactional
    public List<Course> getCourses(User user) {
     User user1 = subscriptionRepository.findByIdWithSubscriptions(user.getId()).orElse(null);
     if(user1 == null) return Collections.emptyList();
     return user1.getSubscriptionList().
                stream().map(Subscription::getCourse).toList();
    }

    @Transactional
    public List<Course> getCoursesSubscribed(User user) {
     User user1 = subscriptionRepository.findByIdWithSubscriptions(user.getId()).orElse(null);
     if(user1 == null) return Collections.emptyList();
     return user1.getSubscriptionList().
                stream().map(Subscription::getCourse).toList();
    }


    @Transactional
    public List<Certificate> getCertificateList(User user) {
     User user1 = subscriptionRepository.findByIdWithSubscriptions(user.getId()).orElse(null);
     if(user1 == null) return Collections.emptyList();
     return user1.getCertificateList();
    }


}
