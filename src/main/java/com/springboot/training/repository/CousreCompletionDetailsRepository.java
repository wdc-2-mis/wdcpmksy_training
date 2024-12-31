package com.springboot.training.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.springboot.training.dto.CourseCompletionDetailsDto;

@Repository
public class CousreCompletionDetailsRepository {
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	public List<CourseCompletionDetailsDto> getCourseCompletionDetails(Integer id){
		
		String sql = "select u.user_reg_id, user_id, mobile_no, email, user_type, registration_id, luqd.status from lms_user u, (select user_reg_id, case when sum(case when status = 'P' then 1 else 0 end) >= 1 then 'P' else 'F' end  AS status from lms_user_quiz_details where training_id ="+id+" group by user_reg_id) as  luqd where u.user_reg_id = luqd.user_reg_id ";
		
		return jdbcTemplate.query(sql, (resultSet, rowNum) -> {
			return new CourseCompletionDetailsDto(resultSet.getInt("user_reg_id"),
					resultSet.getString("user_id"),
					resultSet.getString("mobile_no"),
					resultSet.getString("email"),
					resultSet.getString("status"),
					resultSet.getString("user_type"),
					resultSet.getString("registration_id"));
		});
		
	}

}
