package com.lankovv.questlog.controller;

import com.lankovv.questlog.model.Quest;
import com.lankovv.questlog.service.QuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class QuestController {

    @Autowired
    QuestService questService;

    @RequestMapping(value={"/quests"}, method = RequestMethod.GET)
    public ModelAndView quests(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user/quests");
        return modelAndView;
    }

    @RequestMapping(value={"/quests/add"}, method = RequestMethod.GET)
    public ModelAndView addQuestPage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user/addQuest");
        modelAndView.addObject("quest",new Quest());
        return modelAndView;
    }
    @RequestMapping(value={"/quests/add"}, method = RequestMethod.POST)
    public RedirectView addQuest(@ModelAttribute Quest quest){
        questService.saveQuest(quest);
        return new RedirectView("/quests");
    }
}
