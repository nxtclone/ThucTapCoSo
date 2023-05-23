package com.nxt.ttcs.repository;

import com.nxt.ttcs.entity.CustomUserDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<CustomUserDetail, Long> {
    CustomUserDetail findByUsername(String username);

    CustomUserDetail findByMasv(String masv);
    List<CustomUserDetail> findByLophanhchinh(String lophanhchinh);
}
