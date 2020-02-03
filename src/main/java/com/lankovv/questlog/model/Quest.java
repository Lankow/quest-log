package com.lankovv.questlog.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalTime;
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
    private LocalTime time;

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

    public JSONObject questToJson() {

        JSONObject geometryData = new JSONObject();
        JSONArray coordinates = new JSONArray();
        JSONObject propertiesData = new JSONObject();

        coordinates.add(localization.getLongitude());
        coordinates.add(localization.getLatitude());

        geometryData.put("type", "Point");
        geometryData.put("coordinates", coordinates);

        propertiesData.put("name", name);
        propertiesData.put("type", questType.getName());
        propertiesData.put("deadline", deadline.toString());
        propertiesData.put("time", time.toString());
        propertiesData.put("address", localization.getAddress());
        propertiesData.put("description", description);

        JSONObject feature = new JSONObject();
        feature.put("type","Feature");
        feature.put("geometry",geometryData);
        feature.put("properties",propertiesData);

        return feature;
    }
}
