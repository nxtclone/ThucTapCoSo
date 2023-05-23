package com.nxt.ttcs.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "id_monhoc")
    private MonHoc monhoc;
    private String nhommonhoc;
    private String phonghoc;
    private Long id_giangvien;
    private String lop;
    @Column(columnDefinition = "DATETIME")
    private String ngay;
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private CustomUserDetail customUserDetail;

    public String getHTML() {
        return "Mã môn học: " + monhoc.getMamonhoc() +"<br>" +
                "Tên môn học: " + monhoc.getTenmonhoc() +"<br>" +
                "Số tín chỉ: " + monhoc.getSotinchi() +"<br>" +
                "Phòng học: " + phonghoc + "<br>" +
                "Lớp: " + lop + "<br>" +
                "Ngày: " + ngay;
    }
}
