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
		String sql = "select training_id, course_name, course_description, noof_question, duration_exam, min_pass_marks, course_start, course_end, status, COALESCE(useridcount,0) as useridcount from ((select training_id, course_name, course_description, noof_question, duration_exam, min_pass_marks, course_start, course_end, status from public.lms_training_details) as ltd left join (select distinct training_id as trainingId, cast(count(distinct user_reg_id)as integer) as userIdCount from lms_user_question_answer group by training_id) as luq on ltd.training_id = luq.trainingId)";
		return jdbcTemplate.query(sql, (resultSet, rowNum) -> {
			return new ViewCourseDetails(resultSet.getInt("training_id"),
					resultSet.getString("course_name"),
					resultSet.getString("course_description"),
					resultSet.getInt("noof_question"),
					resultSet.getInt("duration_exam"),
					resultSet.getInt("min_pass_marks"),
					resultSet.getDate("course_start"),
					resultSet.getDate("course_end"),
					resultSet.getString("status"),
					resultSet.getInt("useridcount"));
		});
	}
	

}
