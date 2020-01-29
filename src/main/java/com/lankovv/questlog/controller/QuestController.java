package com.lankovv.questlog.controller;

import com.lankovv.questlog.model.Quest;
import com.lankovv.questlog.model.User;
import com.lankovv.questlog.service.QuestService;
import com.lankovv.questlog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    UserService userService;

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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        user.addQuest(quest);
        userService.saveUser(user);
        return new RedirectView("/quests");
    }
}
