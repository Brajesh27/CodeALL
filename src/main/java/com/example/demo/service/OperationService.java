package com.example.demo.service;

import com.example.demo.model.Profile;
import com.example.demo.model.User;
import com.example.demo.repository.ProfileRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
 public class OperationService {
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    public Profile saveOrUpdateProfile(User user, String leetcode, String codechef, String codeforces) {
        // Find existing profile or create new one
        Profile profile = profileRepository.findByUser(user)
                .orElse(new Profile());

        profile.setUser(user);
        profile.setLeetcodeUsername(leetcode);
        profile.setCodechefUsername(codechef);
        profile.setCodeforcesUsername(codeforces);
       user.set_onboarded(true);
        userRepository.save(user);

        // Save profile in DB
        return profileRepository.save(profile);
    }
}
