package com.example.golden.heart.bot.service;

import com.example.golden.heart.bot.model.Volunteer;
import com.example.golden.heart.bot.repository.VolunteerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VolunteerService {
    @Autowired
    private VolunteerRepository volunteerRepository;

    public Volunteer saveVolunteer(Volunteer volunteer) {
        return volunteerRepository.save(volunteer);
    }

    public Volunteer editeVolunteer(Volunteer volunteer) {
        return volunteerRepository.save(volunteer);
    }

    public Volunteer getVolunteer(Long id) {
        return volunteerRepository.findById(id).get();
    }

    public void removeVolunteer(Long id) {
        volunteerRepository.deleteById(id);
    }
}
