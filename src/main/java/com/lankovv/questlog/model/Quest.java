package com.lankovv.questlog.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "QUEST")
public class Quest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private String name;
    private String description;
    @Column(name = "quest_type")
    private QuestType questType;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date deadline;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "location_id")
    private Localization localization;

    public Quest() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public QuestType getQuestType() {
        return questType;
    }

    public void setQuestType(QuestType questType) {
        this.questType = questType;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Localization getLocalization() {
        return localization;
    }

    public void setLocalization(Localization localization) {
        this.localization = localization;
    }

    //    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Quest quest= (Quest) o;
//        return id == quest.id &&
//                Objects.equals(name, quest.name) &&
//                Objects.equals(description, quest.description) &&
//                Objects.equals(deadline, quest.deadline) &&
//                Objects.equals(questType, quest.questType) &&
//                Objects.equals(user, quest.user);
//    }
//
//    @Override
//    public int hashCode() {
//
//        return Objects.hash(id, name, description, deadline, questType, user);
//    }
}
