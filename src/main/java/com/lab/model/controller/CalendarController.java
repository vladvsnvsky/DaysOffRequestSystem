package com.lab.model.controller;

import com.lab.model.dto.DaysOffDTO;
import com.lab.model.model.DaysOff;
import com.lab.model.repository.UserRepository;
import com.lab.model.service.DaysOffService;
import com.lab.model.util.Icon;
import com.lab.model.util.MenuItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/calendar")
public class CalendarController {

    DaysOffService daysOffService;
    @Autowired
    public CalendarController(DaysOffService daysOffService) {
        this.daysOffService = daysOffService;
    }
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

        return "calendar";
    }

    @GetMapping("/requestSuccess")
    public String requestSuccess() {
        return "requestsResults/requestSuccess"; // This should match the name of your HTML file for the success page
    }

    @GetMapping("/requestFailed")
    public String requestFailed() {
        return "requestsResults/requestFailed"; // This should match the name of your HTML file for the failure page
    }

    @PostMapping()
    public String sendDaysOffRequest(@ModelAttribute DaysOffDTO dto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        dto.setUserEmail(userEmail);
        boolean result = daysOffService.saveDaysOffRequestIfNoOverlap(dto);
        if(result == true)
            return "redirect:/calendar/requestSuccess";
        else {
            return "redirect:/calendar/requestFailed";
        }
    }

}
