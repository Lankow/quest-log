package com.lankovv.questlog.model;

public enum QuestType {

    MAIN("Main Quest",100L),
    SIDE("Side Quest", 50L),
    DAILY("Daily Quest", 20L);

    String name;
    Long expReward;

    QuestType(String name, Long expReward) {
        this.name = name;
        this.expReward = expReward;
    }

    public String getName() {
        return name;
    }

    public Long getExpReward() {
        return expReward;
    }
}
