package com.nxt.ttcs.service.Impl;

import com.nxt.ttcs.entity.*;
import com.nxt.ttcs.repository.*;
import com.nxt.ttcs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class UserServiceImpl {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MonHocRepository monHocRepository;
    @Autowired
    FeedbackRepository feedbackRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    TimetableRepository timetableRepository;
    @Autowired
    DiemThanhPhanRepository diemThanhPhanRepository;

    public String findName(Authentication auth){
        return userRepository.findByUsername(auth.getName()).getHoten();
    }
    public void save(CustomUserDetail customUserDetail){
        customUserDetail.setPassword(passwordEncoder.encode(customUserDetail.getPassword()));
        List<Role> roleList = roleRepository.findByName("ROLE_USER");
        if(roleList.isEmpty()){
            roleList=Arrays.asList(new Role("ROLE_USER"));
        }
        customUserDetail.setRole(roleList);
        userRepository.save(customUserDetail);
    }
    public CustomUserDetail findUser(Authentication auth){
        return userRepository.findByUsername(auth.getName());
    }
    public void update(CustomUserDetail customUserDetail,Authentication auth){
        CustomUserDetail currentUserDetail1=userRepository.findByUsername(auth.getName());
        currentUserDetail1.setNgaysinh(customUserDetail.getNgaysinh());
        currentUserDetail1.setGioitinh(customUserDetail.isGioitinh());
        currentUserDetail1.setEmail(customUserDetail.getEmail());
        currentUserDetail1.setSodienthoai(customUserDetail.getSodienthoai());
        userRepository.save(currentUserDetail1);
    }
    public void handleTimeTable(String start, String end, Model model, Authentication auth){
        CustomUserDetail customUserDetail = findUser(auth);
        model.addAttribute("name", customUserDetail.getHoten());
        LocalDate startL,endL;
        if(start == null || end == null){
            startL=LocalDate.now().plusDays(1-LocalDate.now().getDayOfWeek().getValue());
            endL=LocalDate.now().plusDays(7-LocalDate.now().getDayOfWeek().getValue());
        } else {
            startL=LocalDate.parse(start);
            endL=LocalDate.parse(end);
        }
        model.addAttribute("start", startL);
        model.addAttribute("end", endL);
        List<TimeTable> timeTables = timetableRepository.findTabletimes(startL.toString(),endL.plusDays(1).toString(),customUserDetail.getId());
        TimeTable[][] timeTables1=new TimeTable[7][6];
        String[][] tengiangvien= new String[7][6];
//        String[][] tenmonhoc=new String[7][6];
        for(int i=0;i<7;i++){
            for(TimeTable t : timeTables){
                LocalDateTime x = LocalDateTime.parse(t.getNgay(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                if(x.getDayOfWeek().getValue()==i+1){
                    switch (x.getHour()){
                        case 7: {
                            timeTables1[i][0]=t;
                            tengiangvien[i][0]=userRepository.findById(t.getId_giangvien()).get().getHoten();
//                            tenmonhoc[i][0]=monHocRepository.findByMamonhoc(t.getMamonhoc()).getTenmonhoc();
                            break;
                        }
                        case 9: {
                            timeTables1[i][1] = t;
                            tengiangvien[i][1]=userRepository.findById(t.getId_giangvien()).get().getHoten();
//                            tenmonhoc[i][1]=monHocRepository.findByMamonhoc(t.getMamonhoc()).getTenmonhoc();
                            break;
                        }
                        case 12: {
                            timeTables1[i][2] = t;
                            tengiangvien[i][2]=userRepository.findById(t.getId_giangvien()).get().getHoten();
//                            tenmonhoc[i][2]=monHocRepository.findByMamonhoc(t.getMamonhoc()).getTenmonhoc();
                            break;
                        }
                        case 14: {
                            timeTables1[i][3] = t;
                            tengiangvien[i][3]=userRepository.findById(t.getId_giangvien()).get().getHoten();
//                            tenmonhoc[i][3]=monHocRepository.findByMamonhoc(t.getMamonhoc()).getTenmonhoc();
                            break;
                        }
                        case 16: {
                            timeTables1[i][4] = t;
                            tengiangvien[i][4]=userRepository.findById(t.getId_giangvien()).get().getHoten();
//                            tenmonhoc[i][4]=monHocRepository.findByMamonhoc(t.getMamonhoc()).getTenmonhoc();
                            break;
                        }
                        case 18: {
                            timeTables1[i][5] = t;
                            tengiangvien[i][5]=userRepository.findById(t.getId_giangvien()).get().getHoten();
//                            tenmonhoc[i][5]=monHocRepository.findByMamonhoc(t.getMamonhoc()).getTenmonhoc();
                            break;
                        }
                        default: break;
                    }
                }
            }
        }
        model.addAttribute("timetable", timeTables1);
        model.addAttribute("tengiangvien", tengiangvien);
//        model.addAttribute("tenmonhoc", tenmonhoc);
    }
    public void handleLophanhchinh(Model model, Authentication auth){
        CustomUserDetail current=userRepository.findByUsername(auth.getName());
        model.addAttribute("user", current);
        List<CustomUserDetail> lophanhchinh=userRepository.findByLophanhchinh(current.getLophanhchinh());
        model.addAttribute("lophanhchinh",lophanhchinh);
    }
    public void handleLoptinchi(String kihoc, Model model, Authentication auth){
        if(kihoc == null) kihoc="1";
        model.addAttribute("kihoc", Integer.parseInt(kihoc));

        CustomUserDetail current = userRepository.findByUsername(auth.getName());
        model.addAttribute("user", current);

        List<DiemThanhPhan> loptinchi=diemThanhPhanRepository.findByKihoc(Integer.parseInt(kihoc),current.getId());
        model.addAttribute("loptinchi",loptinchi);

//        HashMap<String,String> tenmonhoc = new HashMap<>();
//        for(DiemThanhPhan t:loptinchi){
//            tenmonhoc.put(t.getMonhoc().getMamonhoc(),t.getMonhoc().getTenmonhoc());
//        }
//        model.addAttribute("tenmonhoc",tenmonhoc);
    }
    public void handleLoptinchiInfo(String nhommonhoc, Model model, Authentication auth){
        CustomUserDetail current = userRepository.findByUsername(auth.getName());
        model.addAttribute("user", current);

        List<DiemThanhPhan> loptinchiInfo=diemThanhPhanRepository.findByNhommonhoc(nhommonhoc);
        model.addAttribute("loptinchiInfo", loptinchiInfo);

        model.addAttribute("nhommonhoc", nhommonhoc);
    }
    public void handleGochoctap(Model model, Authentication auth){
        CustomUserDetail current = userRepository.findByUsername(auth.getName());
        model.addAttribute("user", current);
        List<DiemThanhPhan> gochoctap=diemThanhPhanRepository.findByUserid(current.getId());
        model.addAttribute("gochoctap", gochoctap);

    }
    public void handlePhanhoiForm(Model model, Authentication auth){
        CustomUserDetail current = userRepository.findByUsername(auth.getName());
        model.addAttribute("user", current);
        model.addAttribute("feedbacks",current.getFeedbacks());
    }
    public void handlePhanhoi(Feedback feedback, Model model, Authentication auth){
        feedback.setNgaytao(LocalDateTime.now());
        CustomUserDetail current = userRepository.findByUsername(auth.getName());
        feedback.setCustomUserDetail(current);
        feedbackRepository.save(feedback);
        //current.getFeedbacks().add(feedback);
        model.addAttribute("user", current);
        model.addAttribute("feedbacks",current.getFeedbacks());
    }
    /*private UserDTOUnuse convertUser(CustomUserDetail customUserDetail){
        UserDTOUnuse userDTO=new UserDTOUnuse();
        userDTO.setName(customUserDetail.getName());
        userDTO.setUsername(customUserDetail.getUsername());
        return userDTO;
    }
    private CustomUserDetail convertUserDTO(UserDTOUnuse userDTO){
        CustomUserDetail customUserDetail=new CustomUserDetail();
        customUserDetail.setName(userDTO.getName());
        customUserDetail.setUsername(userDTO.getUsername());
        return customUserDetail;
    }*/
}
