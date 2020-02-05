package com.lankovv.questlog.model;

import javax.persistence.*;

@Entity(name = "PLAYER_STATUS")
public class PlayerStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long lvl;
    private Long exp;
    private Long expCap;
    @OneToOne(mappedBy = "playerStatus")
    private User user;
    @Transient
    private Long lvlCompletion;

    public PlayerStatus() {
    }

    public PlayerStatus(Long lvl, Long exp, Long expCap) {
        this.lvl = lvl;
        this.exp = exp;
        this.expCap = expCap;
    }

    @PostLoad
    public void calculateLvlCompletion() {
        lvlCompletion = exp / expCap;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLvl() {
        return lvl;
    }

    public void setLvl(Long lvl) {
        this.lvl = lvl;
    }

    public Long getExp() {
        return exp;
    }

    public void setExp(Long exp) {
        this.exp = exp;
    }

    public Long getExpCap() {
        return expCap;
    }

    public void setExpCap(Long expCap) {
        this.expCap = expCap;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getLvlCompletion() {
        return lvlCompletion;
    }

    public void setLvlCompletion(Long lvlCompletion) {
        this.lvlCompletion = lvlCompletion;
    }
}
