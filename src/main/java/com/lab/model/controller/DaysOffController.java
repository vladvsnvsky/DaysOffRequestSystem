package com.lab.model.controller;

import com.lab.model.model.DaysOff;
import com.lab.model.repository.DaysOffRepository;
import com.lab.model.util.Icon;
import com.lab.model.util.MenuItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/daysOffRequests")
public class DaysOffController {
    Logger logger = LoggerFactory.getLogger(DaysOffController.class);
    @Autowired
    private DaysOffRepository daysOffRepository;

    @Autowired
    public DaysOffController(){
    }

    @GetMapping()
    public String open(Model model){
        List<MenuItem> menu = new ArrayList<>();

        MenuItem home = new MenuItem();
        home.setName("Home");
        Icon homeIcon = Icon.HOME;
        homeIcon.setColor(Icon.IconColor.INDIGO);

        home.setIcon(homeIcon);
        home.setUrl("/daysOffRequests");
        menu.add(home);

        MenuItem calendar = new MenuItem();
        calendar.setName("Calendar");
        calendar.setUrl("/daysOffRequests/calendar");
        Icon calendarIcon = Icon.CALENDAR;
        calendarIcon.setColor(Icon.IconColor.INDIGO);
        calendar.setIcon(calendarIcon);
        menu.add(calendar);

        MenuItem approvedRequests = new MenuItem();
        approvedRequests.setName("Approved Requests");
        approvedRequests.setUrl("/daysOffRequests/approvedRequests");
        Icon checkedIcon = Icon.CHECKED;
        checkedIcon.setColor(Icon.IconColor.INDIGO);
        approvedRequests.setIcon(checkedIcon);
        menu.add(approvedRequests);

        MenuItem notApprovedRequests = new MenuItem();
        notApprovedRequests.setName("Not Approved Requests");
        notApprovedRequests.setUrl("/daysOffRequests/notApprovedRequests");
        Icon notCheckedIcon = Icon.NOT_CHECKED;
        notCheckedIcon.setColor(Icon.IconColor.INDIGO);
        notApprovedRequests.setIcon(notCheckedIcon);
        menu.add(notApprovedRequests);

        List<DaysOff> requests = daysOffRepository.findAll();

        List<DaysOff> pendingRequestsList = requests.stream()
                .filter(request -> request.getApproved() == null)
                .collect(Collectors.toList());

        model.addAttribute("menuItems", menu);
        model.addAttribute("pending_requests", pendingRequestsList);

        return "approve_days/dashboard";
    }

    @GetMapping("/approvedRequests")
    public String openApprovedRequests(Model model){
        List<MenuItem> menu = new ArrayList<>();

        MenuItem home = new MenuItem();
        home.setName("Home");
        Icon homeIcon = Icon.HOME;
        homeIcon.setColor(Icon.IconColor.INDIGO);

        home.setIcon(homeIcon);
        home.setUrl("/daysOffRequests");
        menu.add(home);

        MenuItem calendar = new MenuItem();
        calendar.setName("Calendar");
        calendar.setUrl("/daysOffRequests/calendar");
        Icon calendarIcon = Icon.CALENDAR;
        calendarIcon.setColor(Icon.IconColor.INDIGO);
        calendar.setIcon(calendarIcon);
        menu.add(calendar);

        MenuItem approvedRequests = new MenuItem();
        approvedRequests.setName("Approved Requests");
        approvedRequests.setUrl("/daysOffRequests/approvedRequests");
        Icon checkedIcon = Icon.CHECKED;
        checkedIcon.setColor(Icon.IconColor.INDIGO);
        approvedRequests.setIcon(checkedIcon);
        menu.add(approvedRequests);

        MenuItem notApprovedRequests = new MenuItem();
        notApprovedRequests.setName("Not Approved Requests");
        notApprovedRequests.setUrl("/daysOffRequests/notApprovedRequests");
        Icon notCheckedIcon = Icon.NOT_CHECKED;
        notCheckedIcon.setColor(Icon.IconColor.INDIGO);
        notApprovedRequests.setIcon(notCheckedIcon);
        menu.add(notApprovedRequests);

        List<DaysOff> requests = daysOffRepository.findAll();

        List<DaysOff> approvedRequestsList = requests.stream()
                .filter(request -> request.getApproved() != null && request.getApproved())
                .collect(Collectors.toList());


        model.addAttribute("menuItems", menu);
        model.addAttribute("approved_requests", approvedRequestsList);
        return "approve_days/approved";
    }

    @GetMapping("/notApprovedRequests")
    public String openNotApprovedRequests(Model model){
        List<MenuItem> menu = new ArrayList<>();

        MenuItem home = new MenuItem();
        home.setName("Home");
        Icon homeIcon = Icon.HOME;
        homeIcon.setColor(Icon.IconColor.INDIGO);

        home.setIcon(homeIcon);
        home.setUrl("/daysOffRequests");
        menu.add(home);

        MenuItem calendar = new MenuItem();
        calendar.setName("Calendar");
        calendar.setUrl("/daysOffRequests/calendar");
        Icon calendarIcon = Icon.CALENDAR;
        calendarIcon.setColor(Icon.IconColor.INDIGO);
        calendar.setIcon(calendarIcon);
        menu.add(calendar);

        MenuItem approvedRequests = new MenuItem();
        approvedRequests.setName("Approved Requests");
        approvedRequests.setUrl("/daysOffRequests/approvedRequests");
        Icon checkedIcon = Icon.CHECKED;
        checkedIcon.setColor(Icon.IconColor.INDIGO);
        approvedRequests.setIcon(checkedIcon);
        menu.add(approvedRequests);

        MenuItem notApprovedRequests = new MenuItem();
        notApprovedRequests.setName("Not Approved Requests");
        notApprovedRequests.setUrl("/daysOffRequests/notApprovedRequests");
        Icon notCheckedIcon = Icon.NOT_CHECKED;
        notCheckedIcon.setColor(Icon.IconColor.INDIGO);
        notApprovedRequests.setIcon(notCheckedIcon);
        menu.add(notApprovedRequests);

        List<DaysOff> requests = daysOffRepository.findAll();

        List<DaysOff> notApprovedRequestsList = requests.stream()
                .filter(request -> request.getApproved() != null && !request.getApproved())
                .collect(Collectors.toList());


        model.addAttribute("menuItems", menu);
        model.addAttribute("not_approved_requests", notApprovedRequestsList);
        return "approve_days/not_approved";
    }

    @PostMapping("/approveRequest")
    public String approveRequest(@RequestParam Long requestId) {
        // Logic to approve the request
        Optional<DaysOff> req = daysOffRepository.findById(requestId);

        if(req.isPresent()){
            req.get().setApproved(true);
            daysOffRepository.save(req.get());
        }
        return "redirect:/daysOffRequests"; // Redirect after processing
    }

    @PostMapping("/denyRequest")
    public String denyRequest(@RequestParam Long requestId) {
        // Logic to approve the request
        Optional<DaysOff> req = daysOffRepository.findById(requestId);

        if(req.isPresent()){
            req.get().setApproved(false);
            daysOffRepository.save(req.get());
        }
        return "redirect:/daysOffRequests"; // Redirect after processing
    }


}
