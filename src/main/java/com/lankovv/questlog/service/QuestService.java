package com.lankovv.questlog.service;

import com.lankovv.questlog.model.Quest;
import com.lankovv.questlog.model.User;
import com.lankovv.questlog.repository.QuestRepository;
import com.lankovv.questlog.repository.UserRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class QuestService {

    private UserRepository userRepository;
    private QuestRepository questRepository;

    @Autowired
    public QuestService(UserRepository userRepository, QuestRepository questRepository) {
        this.userRepository = userRepository;
        this.questRepository = questRepository;
    }

    public void saveQuest(Quest quest) {
        questRepository.save(quest);
    }

    public JSONObject userQuestsToJson(User user) {
        return questListToJson(user.getQuests());
    }

    private JSONObject questListToJson(Set<Quest> quests) {
        JSONObject featureCollection = new JSONObject();
        featureCollection.put("type", "FeatureCollection");
        JSONArray questList = new JSONArray();
        for (Quest quest : quests) {
            questList.add(quest.questToJson());
        }
        featureCollection.put("features", featureCollection);
        return featureCollection;
    }
}
