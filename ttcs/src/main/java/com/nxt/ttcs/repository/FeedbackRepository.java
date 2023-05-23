package com.nxt.ttcs.repository;

import com.nxt.ttcs.entity.DiemThanhPhan;
import com.nxt.ttcs.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback,Long> {
}
