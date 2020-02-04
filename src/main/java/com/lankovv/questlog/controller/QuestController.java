package com.lankovv.questlog.controller;

import com.lankovv.questlog.model.Quest;
import com.lankovv.questlog.model.User;
import com.lankovv.questlog.service.QuestService;
import com.lankovv.questlog.service.UserService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.Set;


@Controller
public class QuestController {

    @Autowired
    private UserService userService;
    private QuestService questService;

    @RequestMapping(value = {"/quests/add"}, method = RequestMethod.GET)
    public ModelAndView addQuestPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user/addQuest");
        modelAndView.addObject("quest", new Quest());
        return modelAndView;
    }

    @RequestMapping(value = {"/quests/add"}, method = RequestMethod.POST)
    public ModelAndView addQuest(@Valid Quest quest, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("user/addQuest");
        } else {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.findUserByEmail(auth.getName());
            user.addQuest(quest);
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "Quest has been created successfully");
            modelAndView.addObject("quest", new Quest());
            modelAndView.setViewName("user/addQuest");
        }
        return modelAndView;
    }

    @RequestMapping(value = {"/quests"}, method = RequestMethod.GET)
    public ModelAndView quests() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try {
            User user = userService.findUserByEmail(auth.getName());
            Set<Quest> questSet = user.getQuests();
            JSONObject featureCollection = new JSONObject();
            featureCollection.put("type", "FeatureCollection");
            JSONArray questListJson = new JSONArray();
            for (Quest quest : questSet) {
                JSONObject questJson = quest.questToJson();
                questListJson.add(questJson);
            }
            featureCollection.put("features", questListJson);
            modelAndView.addObject("quests", featureCollection);
        } catch (Exception e) {
            e.printStackTrace();
        }

        modelAndView.setViewName("user/quests");
        return modelAndView;
    }


}
