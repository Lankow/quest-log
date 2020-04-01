package com.lankovv.questlog.controller;

import com.lankovv.questlog.model.Quest;
import com.lankovv.questlog.model.QuestStatus;
import com.lankovv.questlog.model.User;
import com.lankovv.questlog.service.QuestService;
import com.lankovv.questlog.service.UserService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.Objects;
import java.util.Set;


@Controller
public class QuestController {


    private UserService userService;
    private QuestService questService;

    @Autowired
    public QuestController(UserService userService, QuestService questService) {
        this.userService = Objects.requireNonNull(userService, "User service cannot be a null");
        this.questService = Objects.requireNonNull(questService, "Quest service cannot be a null");
    }


    @RequestMapping(value = {"/quests/add"}, method = RequestMethod.GET)
    public ModelAndView addQuestPage() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user/addQuest");
        modelAndView.addObject("quest", new Quest());
        modelAndView.addObject("userName", user.getName());
        modelAndView.addObject("playerStatus", user.getPlayerStatus());
        return modelAndView;
    }

    @RequestMapping(value = {"/quests/add"}, method = RequestMethod.POST)
    public ModelAndView addQuest(@Valid Quest quest, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("user/addQuest");
        } else {
            quest.setQuestStatus(QuestStatus.INCOMPLETE);
            user.addQuest(quest);
            userService.updateUser(user);
            modelAndView.addObject("successMessage", "Quest has been created");
            modelAndView.addObject("quest", new Quest());
            modelAndView.setViewName("user/addQuest");
        }
        modelAndView.addObject("userName", user.getName());
        modelAndView.addObject("playerStatus", user.getPlayerStatus());
        return modelAndView;
    }

    @RequestMapping(value = {"/quests"}, method = RequestMethod.GET)
    public ModelAndView quests() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try {
            User user = userService.findUserByEmail(auth.getName());
            Set<Quest> questSet = user.getQuests();
            JSONObject quests = questService.questsToJSON(questSet);
            modelAndView.addObject("userName", user.getName());
            modelAndView.addObject("playerStatus", user.getPlayerStatus());
            modelAndView.addObject("quests", quests);
        } catch (Exception e) {
            e.printStackTrace();
        }

        modelAndView.setViewName("user/quests");
        return modelAndView;
    }

    @RequestMapping(value = {"/quests/{action}/{id}"}, method = RequestMethod.GET)
    public RedirectView modifyQuest(@PathVariable String action, @PathVariable Long id) {
        RedirectView redirectView = new RedirectView("/quests");
        if (id != null && action != null) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.findUserByEmail(auth.getName());
            Quest quest = questService.findQuestById(id);
            if (user.getQuests().contains(quest)) {
                switch (action) {
                    case "complete":
                        if (quest.getQuestStatus() != QuestStatus.COMPLETE && quest.getQuestStatus() != QuestStatus.FAILED) {
                            quest.setQuestStatus(QuestStatus.COMPLETE);
                            questService.rewardExp(quest);
                            questService.saveQuest(quest);
                            userService.updateUser(user);
                        }
                        break;
                    case "failed":
                        if (quest.getQuestStatus() != QuestStatus.COMPLETE && quest.getQuestStatus() != QuestStatus.FAILED) {
                            quest.setQuestStatus(QuestStatus.FAILED);
                            questService.saveQuest(quest);
                        }
                        break;
                    case "delete":
                        user.removeQuest(quest);
                        userService.updateUser(user);
                        questService.deleteQuest(quest);
                        break;
                    case "edit":
                        redirectView.setUrl("/quests/edit/" + quest.getId());
                        return redirectView;
                }
            }
        }
        return redirectView;
    }

    @RequestMapping(value = {"/quests/edit/{id}"}, method = RequestMethod.GET)
    public ModelAndView editQuestPage(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView("user/editQuest");
        Quest quest = questService.findQuestById(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("quest",quest);
        modelAndView.addObject("playerStatus", user.getPlayerStatus());
        modelAndView.addObject("userName", user.getName());
        return modelAndView;
    }

    @RequestMapping(value = {"/quests/edit"}, method = RequestMethod.POST)
    public ModelAndView editQuest(@Valid Quest quest, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("user/editQuest");
        } else {
           questService.saveQuest(quest);
            modelAndView.addObject("successMessage", "Quest has been edited");
            modelAndView.addObject("quest", new Quest());
            modelAndView.setViewName("user/addQuest");
        }
        modelAndView.addObject("userName", user.getName());
        modelAndView.addObject("playerStatus", user.getPlayerStatus());
        return modelAndView;
    }
}