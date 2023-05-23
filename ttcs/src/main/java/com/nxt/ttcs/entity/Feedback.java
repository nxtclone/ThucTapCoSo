package com.nxt.ttcs.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(columnDefinition = "TEXT")
    private String phongban;
    @Column(columnDefinition = "TEXT")
    private String noidung;
    @Column(columnDefinition = "TEXT")
    private String noidungphanhoi;
    @Column(columnDefinition = "DATE")
    private LocalDateTime ngaytao;
    @ManyToOne()
    @JoinColumn(name="USER_ID")
    private CustomUserDetail customUserDetail;
}
