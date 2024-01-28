package com.lab.model.controller;

import com.lab.model.model.DaysOff;
import com.lab.model.repository.DaysOffRepository;
import com.lab.model.repository.UserRepository;
import com.lab.model.util.Icon;
import com.lab.model.util.MenuItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/myRequests")
public class MyRequestsController {

    @Autowired
    private DaysOffRepository daysOffRepository;

    @Autowired
    UserRepository userRepository;
    @GetMapping()
    public String open(Model model, Authentication authentication){
        List<MenuItem> menu = new ArrayList<>();

        MenuItem home = new MenuItem();
        home.setName("Home");
        Icon homeIcon = Icon.HOME;
        homeIcon.setColor(Icon.IconColor.INDIGO);

        home.setIcon(homeIcon);
        home.setUrl("/home");
        menu.add(home);

        MenuItem calendar = new MenuItem();
        calendar.setName("Calendar");
        calendar.setUrl("/calendar");
        Icon rolesIcon = Icon.CALENDAR;
        rolesIcon.setColor(Icon.IconColor.INDIGO);

        calendar.setIcon(rolesIcon);
        menu.add(calendar);

        MenuItem myRequests = new MenuItem();
        myRequests.setName("My Requests");
        myRequests.setUrl("/myRequests");
        Icon i = Icon.ARROW_RIGHT;
        i.setColor(Icon.IconColor.INDIGO);
        myRequests.setIcon(i);
        menu.add(myRequests);

        model.addAttribute("menuItems", menu);

        List<DaysOff> requests = daysOffRepository.findAll();

        String userEmail = authentication.getName();

        long userId = userRepository.findByEmail(userEmail).get().getId();

        List<DaysOff> myrequestsList = requests.stream()
                .filter(request -> request.getUser().getId() == userId)
                .collect(Collectors.toList());

        model.addAttribute("daysoffrequests", myrequestsList);

        return "approve_days/requests";
    }
}
