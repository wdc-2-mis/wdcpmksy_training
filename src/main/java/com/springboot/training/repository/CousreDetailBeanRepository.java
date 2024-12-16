package com.springboot.training.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.springboot.training.dto.CourseDetails;
import com.springboot.training.dto.ViewCourseDetails;

@Repository
public class CousreDetailBeanRepository {
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	public List<ViewCourseDetails> getCousrseDetails(){
		String sql = "select distinct training_id as trainingId, cast(count(distinct user_reg_id)as integer) as userIdCount from lms_user_question_answer group by training_id";
		return jdbcTemplate.query(sql, (resultSet, rowNum) -> {
			return new ViewCourseDetails(resultSet.getInt("trainingId"),
					resultSet.getInt("userIdCount"));
		});
	}
	

}
