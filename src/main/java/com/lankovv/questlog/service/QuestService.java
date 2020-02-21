package com.lankovv.questlog.service;

import com.lankovv.questlog.model.Quest;
import com.lankovv.questlog.model.User;
import com.lankovv.questlog.repository.QuestRepository;
import com.lankovv.questlog.repository.UserRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;


@Service
public class QuestService {
    private QuestRepository questRepository;

    @Autowired
    public QuestService(QuestRepository questRepository) {
        this.questRepository = questRepository;
    }

    public void saveQuest(Quest quest) {
        questRepository.save(quest);
    }

    public Quest findQuestById(Long id) {
        Optional<Quest> questOptional = Optional.ofNullable(questRepository.findById(id));
        return questOptional.get();
    }

    public void deleteQuest(Quest quest) {
        questRepository.delete(quest);
    }


    public JSONObject questsToJSON(Set<Quest> questSet) {
        JSONObject featureCollection = new JSONObject();
        featureCollection.put("type", "FeatureCollection");
        JSONArray questListJson = new JSONArray();
        for (Quest quest : questSet) {
            JSONObject questJson = quest.questToJson();
            questListJson.add(questJson);
        }
        featureCollection.put("features", questListJson);
        return featureCollection;
    }

    public void rewardExp(Quest quest) {
        User user = quest.getUser();
        user.getPlayerStatus().setExp(user.getPlayerStatus().getExp() + quest.getQuestType().getExpReward());
        if (user.getPlayerStatus().getExp() >= user.getPlayerStatus().getExpCap()) {
            user.getPlayerStatus().setLvl(user.getPlayerStatus().getLvl() + (user.getPlayerStatus().getExp() / user.getPlayerStatus().getExpCap()));
            user.getPlayerStatus().setExp(user.getPlayerStatus().getExp() % user.getPlayerStatus().getExpCap());
            user.getPlayerStatus().setExpCap(user.getPlayerStatus().getExpCap() * 2);
        }

    }
}
