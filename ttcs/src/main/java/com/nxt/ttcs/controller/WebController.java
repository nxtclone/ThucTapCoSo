package com.nxt.ttcs.controller;

import com.nxt.ttcs.dto.DiemThanhPhanDTO;
import com.nxt.ttcs.entity.CustomUserDetail;
import com.nxt.ttcs.entity.DiemThanhPhan;
import com.nxt.ttcs.entity.Feedback;
import com.nxt.ttcs.repository.TimetableRepository;
import com.nxt.ttcs.service.Impl.AdminServiceImpl;
import com.nxt.ttcs.service.Impl.UserServiceImpl;
import com.nxt.ttcs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.http.HttpResponse;
import java.util.List;

@Controller
public class WebController {
    @Autowired
    UserServiceImpl userService;
    @Autowired
    AdminServiceImpl adminService;
    @Autowired
    TimetableRepository timetableRepository;
    @GetMapping(value = {"/","/login"})
    String login(Authentication auth){
        if(auth == null){
            return "login";
        } else {
            for(GrantedAuthority grantedAuthority: auth.getAuthorities()){
                if(grantedAuthority.getAuthority().equals("ROLE_USER")) {
                    return "redirect:/dashboard";
                } else if(grantedAuthority.getAuthority().equals("ROLE_ADMIN")){
                    return "redirect:/danhsachnhomlop";
                }
            }
        }
        return "login";
    }
    @GetMapping("/register")
    String registerForm(){
        return "register";
    }
    @PostMapping("/register")
    String register(@ModelAttribute("user") CustomUserDetail customUserDetail){
        userService.save(customUserDetail);
        return "redirect:/register?success";
    }
    @GetMapping("/dashboard")
    String dashboard(@RequestParam(required = false) String start,@RequestParam(required = false) String end, Model model, Authentication auth){
        userService.handleTimeTable(start,end,model,auth);
        return "/user/dashboard";
    }
    @GetMapping("/tintuc")
    String tintuc(Model model, Authentication auth){
        model.addAttribute("name", userService.findName(auth));
        return "/user/tintuc";
    }
    @GetMapping("/hoctap/tientrinh")
    String tientrinh(Model model, Authentication auth){
        model.addAttribute("name", userService.findName(auth));
        return "/user/hoctap/tientrinh";
    }
    @GetMapping("/hoctap/loptinchi")
    String loptinchi(@RequestParam(required = false) String kihoc,Model model, Authentication auth){
        userService.handleLoptinchi(kihoc, model, auth);
        return "/user/hoctap/loptinchi";
    }
    @GetMapping("/hoctap/loptinchi/{nhommonhoc}")
    String loptinchiInfo(@PathVariable String nhommonhoc, Model model, Authentication auth){
        userService.handleLoptinchiInfo(nhommonhoc, model, auth);
        return "/user/hoctap/loptinchiInfo";
    }
    @GetMapping("/hoctap/lophanhchinh")
    String lophanhchinh(Model model, Authentication auth){
        userService.handleLophanhchinh(model, auth);
        return "/user/hoctap/lophanhchinh";
    }
    @GetMapping("/hoctap/gochoctap")
    String gochoctap(Model model, Authentication auth){
        userService.handleGochoctap(model, auth);
        return "/user/hoctap/gochoctap";
    }
    @GetMapping("/dichvumotcuasv")
    String dichvumotcua(Model model, Authentication auth){
        model.addAttribute("name", userService.findName(auth));
        return "/user/dichvumotcuasv";
    }
    @GetMapping("/congnosinhvien")
    String congnosinhvien(Model model, Authentication auth){
        model.addAttribute("name", userService.findName(auth));
        return "/user/congnosinhvien";
    }
    @GetMapping("/quanlythuviensinhvien/quanlyluanansinhvien")
    String quanlyluanansinhvien(Model model, Authentication auth){
        model.addAttribute("name", userService.findName(auth));
        return "/user/quanlythuviensinhvien/quanlyluanansinhvien";
    }
    @GetMapping("/quanlythuviensinhvien/quanlyluanvansinhvien")
    String quanlyluanvansinhvien(Model model, Authentication auth){
        model.addAttribute("name", userService.findName(auth));
        return "/user/quanlythuviensinhvien/quanlyluanvansinhvien";
    }
    @GetMapping("/quanlythuviensinhvien/quanlykhoaluansinhvien")
    String quanlykhoaluansinhvien(Model model, Authentication auth){
        model.addAttribute("name", userService.findName(auth));
        return "/user/quanlythuviensinhvien/quanlykhoaluansinhvien";
    }
    @GetMapping("/tienichkhac/phanhoi")
    String phanhoi(Model model, Authentication auth){
        userService.handlePhanhoiForm(model, auth);
        return "/user/tienichkhac/phanhoi";
    }
    @PostMapping("/tienichkhac/phanhoi")
    String PostPhanhoi(@ModelAttribute("feedback") Feedback feedback, Model model, Authentication auth){
        userService.handlePhanhoi(feedback, model, auth);
        return "/user/tienichkhac/phanhoi";
    }
    @GetMapping("/tienichkhac/vanbanhuongdan")
    String vanbanhuongdan(Model model, Authentication auth){
        model.addAttribute("name", userService.findName(auth));
        return "/user/tienichkhac/vanbanhuongdan";
    }
    @GetMapping("/tienichkhac/khaosat")
    String khaosat(Model model, Authentication auth){
        model.addAttribute("name", userService.findName(auth));
        return "/user/tienichkhac/khaosat";
    }
    @GetMapping("/tienichkhac/khaibaosuckhoe")
    String khaibaosuckhoe(Model model, Authentication auth){
        model.addAttribute("name", userService.findName(auth));
        return "/user/tienichkhac/khaibaosuckhoe";
    }
    @GetMapping("/tienichkhac/gioithieu")
    String gioithieu(Model model, Authentication auth){
        model.addAttribute("name", userService.findName(auth));
        return "/user/tienichkhac/gioithieu";
    }
    @GetMapping("/account/center")
    String accountcenter(Model model, Authentication auth){
        model.addAttribute("users", userService.findUser(auth));
        return "/user/accountcenter";
    }
    @GetMapping("/account/center/edit")
    String accountcentereditForm(Model model, Authentication auth){
        model.addAttribute("users", userService.findUser(auth));
        return "/user/accountcenter-edit";
    }
    @PostMapping("/account/center/edit")
    String accountcenteredit(@ModelAttribute("update-user") CustomUserDetail customUserDetail, Authentication auth){
        userService.update(customUserDetail,auth);
        return "redirect:/account/center";
    }
    @GetMapping("/danhsachnhomlop")
    String danhsachnhomlop(Model model, Authentication auth){
        adminService.handleDanhsachnhomlop(model, auth);
        return "/admin/danhsachnhomlop";
    }
    @GetMapping("/quanlysinhvien/{nhommonhoc}")
    String quanlysinhvien(@PathVariable String nhommonhoc, Model model, Authentication auth){
        adminService.handleQuanlysinhvien(nhommonhoc,model,auth);
        return "/admin/quanlysinhvien";
    }
    @PostMapping("/themsinhvien/{nhommonhoc}")
    String themsinhvienForm(@PathVariable String nhommonhoc, Model model, Authentication auth){
        adminService.handleThemsinhvienForm(nhommonhoc,model,auth);
        return "/admin/themsinhvien";
    }
    @GetMapping("/themsinhvien")
    String themsinhvien(@RequestParam String nhommonhoc, @RequestParam String msv, Model model){
        return adminService.handleThemsinhvien(nhommonhoc, msv,model);
    }
    @GetMapping("/quanlydiem/{nhommonhoc}/{msv}")
    String quanlydiemForm(@PathVariable String nhommonhoc,@PathVariable String msv, Model model, Authentication auth){
        adminService.handleQuanlydiemForm(nhommonhoc,msv,model,auth);
        return "/admin/quanlydiem";
    }
    @PostMapping("/quanlydiem")
    String quanlydiem(@ModelAttribute("CapNhatDiem") DiemThanhPhanDTO diemThanhPhan, Model model, Authentication auth){
        adminService.handleQuanlydiem(diemThanhPhan,model,auth);
        return "redirect:/quanlysinhvien/"+diemThanhPhan.getNhommonhoc();
    }
    @GetMapping("/xoasinhvien")
    String xoasinhvien(@RequestParam String nhommonhoc, @RequestParam String masv, Model model, Authentication auth){
        adminService.handleXoasinhvien(nhommonhoc,masv,model,auth);
        return "redirect:/quanlysinhvien/"+nhommonhoc;
    }
    @GetMapping("/xuatbangdiem/{nhommonhoc}")
    public ResponseEntity<Resource> exportUsersToExcel(@PathVariable String nhommonhoc) {
        return adminService.exportDiemThanhPhanToExcel(nhommonhoc);
    }
    @PostMapping("/uploadExcel")
    public String uploadExcel(@RequestParam("excelFile") MultipartFile file, @RequestParam("nhommonhoc") String nhommonhoc, Model model) {
        return adminService.uploadExcel(file, nhommonhoc, model);
    }
}
