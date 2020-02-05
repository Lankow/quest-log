package com.lankovv.questlog.model;

import org.hibernate.validator.constraints.Length;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name = "QUEST")
public class Quest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NotEmpty(message = "*Please provide a name for a quest")
    @Length(max = 30, message = "*Quest name must be shorter than 30 characters")
    private String name;
    @Length(max = 100, message = "*Quest description must be shorter than 100 characters")
    private String description;
    @Column(name = "quest_type")
    private QuestType questType;
    @NotNull(message = "*Please pick a deadline date")
    @FutureOrPresent(message = "*Deadline date must be future")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date deadline;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "location_id")
    private Localization localization;
    @NotNull(message = "*Please pick time for a quest")
    private LocalTime time;
    @Column(name = "quest_status")
    private QuestStatus questStatus;

    public Quest(@NotEmpty(message = "*Please provide a name for a quest") @Length(max = 30, message = "*Quest name must be shorter than 30 characters") String name, @Length(max = 100, message = "*Quest description must be shorter than 100 characters") String description, QuestType questType, @NotNull(message = "*Please pick a deadline date") @FutureOrPresent(message = "*Deadline date must be future") Date deadline, User user, Localization localization, @NotNull(message = "*Please pick time for a quest") LocalTime time, QuestStatus questStatus) {
        this.name = name;
        this.description = description;
        this.questType = questType;
        this.deadline = deadline;
        this.user = user;
        this.localization = localization;
        this.time = time;
        this.questStatus = questStatus;
    }

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

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public QuestStatus getQuestStatus() {
        return questStatus;
    }

    public void setQuestStatus(QuestStatus questStatus) {
        this.questStatus = questStatus;
    }

    public JSONObject questToJson() {

        JSONObject geometryData = new JSONObject();
        JSONArray coordinates = new JSONArray();
        JSONObject propertiesData = new JSONObject();

        coordinates.add(localization.getLongitude());
        coordinates.add(localization.getLatitude());

        geometryData.put("type", "Point");
        geometryData.put("coordinates", coordinates);

        propertiesData.put("questId", id);
        propertiesData.put("name", name);
        propertiesData.put("type", questType.getName());
        propertiesData.put("status", questStatus.getName());
        propertiesData.put("deadline", deadline.toString());
        propertiesData.put("time", time.toString());
        propertiesData.put("address", localization.getAddress());
        propertiesData.put("description", description);

        JSONObject feature = new JSONObject();
        feature.put("type", "Feature");
        feature.put("geometry", geometryData);
        feature.put("properties", propertiesData);

        return feature;
    }
}
