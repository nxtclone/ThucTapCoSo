package com.nxt.ttcs.service;

import com.nxt.ttcs.entity.CustomUserDetail;
import com.nxt.ttcs.entity.Feedback;
import com.nxt.ttcs.entity.TimeTable;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface UserService {
    String findName(Authentication auth);
    void save(CustomUserDetail customUserDetail);
    CustomUserDetail findUser(Authentication auth);
    void update(CustomUserDetail customUserDetail,Authentication auth);
    List<TimeTable> findTimetable(Authentication auth);

    void handleTimeTable(String start, String end, Model model, Authentication auth);
    void handleLophanhchinh(Model model, Authentication auth);
    void handleLoptinchi(String kihoc, Model model, Authentication auth);
    void handleLoptinchiInfo(String nhommonhoc, Model model, Authentication auth);
    void handleGochoctap(Model model, Authentication auth);
    void handlePhanhoiForm(Model model, Authentication auth);
    void handlePhanhoi(Feedback feedback, Model model, Authentication auth);
}
