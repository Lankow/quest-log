package com.lankovv.questlog.repository;

import com.lankovv.questlog.model.Quest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestRepository extends JpaRepository<Quest, Integer> {
    Quest findByName(String name);
    Quest findById(Long id);
}


