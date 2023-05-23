package com.nxt.ttcs.repository;

import com.nxt.ttcs.entity.MonHoc;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonHocRepository extends JpaRepository<MonHoc,Long> {
    MonHoc findByMamonhoc(String mamonhoc);
}
