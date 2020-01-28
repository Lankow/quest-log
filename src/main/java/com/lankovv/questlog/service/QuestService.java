package com.lankovv.questlog.service;

import com.lankovv.questlog.model.Quest;
import com.lankovv.questlog.repository.QuestRepository;
import com.lankovv.questlog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestService {

    private UserRepository userRepository;
    private QuestRepository questRepository;

    @Autowired
    public QuestService(UserRepository userRepository, QuestRepository questRepository) {
        this.userRepository = userRepository;
        this.questRepository = questRepository;
    }

    public void saveQuest(Quest quest){
        questRepository.save(quest);
    }
}
