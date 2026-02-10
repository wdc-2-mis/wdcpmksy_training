package com.springboot.training.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.springboot.training.dto.CourseCompletionDetailsDto;
import com.springboot.training.dto.StateWiseNoofUserDetailDto;

@Repository
public class StateWiseNoofUserDetailRepository {
	
	
	@Autowired
    private JdbcTemplate jdbcTemplate; 
	
	public List<StateWiseNoofUserDetailDto> getStateWiseNoofUserDetail(){
		
		String sql = "select st_code, st_name, cast((select count(user_reg_id) from lms_user l where l.stcode=s.st_code) as integer) stuser, cast((select count(l.user_reg_id) from lms_user l, lms_user_quiz_details q where l.user_reg_id=q.user_reg_id and l.stcode=s.st_code) as integer) stparticipant, cast((select count(l.user_reg_id) from lms_user l, lms_user_quiz_details q where l.user_reg_id=q.user_reg_id and l.stcode=s.st_code and q.status='P' ) as integer) stpassparticipant, cast((select count(l.user_reg_id) from lms_user l, lms_user_quiz_details q where l.user_reg_id=q.user_reg_id and l.stcode=s.st_code and q.status='F' ) as integer) stfailparticipant from iwmp_state s where wdcpmksy=1 order by st_name";
		
		return jdbcTemplate.query(sql, (resultSet, rowNum) -> {
			return new StateWiseNoofUserDetailDto(resultSet.getInt("st_code"),
					resultSet.getString("st_name"),
					resultSet.getInt("stuser"),
					resultSet.getInt("stparticipant"),
					resultSet.getInt("stpassparticipant"),
					resultSet.getInt("stfailparticipant"));
		});
		
	}
    
	public List<StateWiseNoofUserDetailDto> getDistWiseNoofUserDetail(int state_cd){
		
		String sql = "select dcode as st_code, dist_name as st_name, cast((select count(user_reg_id) from lms_user l where l.dcode=s.dcode) as integer) stuser, cast((select count(l.user_reg_id) from lms_user l, lms_user_quiz_details q where l.user_reg_id=q.user_reg_id and l.dcode=s.dcode) as integer) stparticipant, cast((select count(l.user_reg_id) from lms_user l, lms_user_quiz_details q where l.user_reg_id=q.user_reg_id and l.dcode=s.dcode and q.status='P' ) as integer) stpassparticipant, cast((select count(l.user_reg_id) from lms_user l, lms_user_quiz_details q where l.user_reg_id=q.user_reg_id and l.dcode=s.dcode and q.status='F' ) as integer) stfailparticipant from iwmp_district s where st_code="+state_cd+" order by dist_name";
		
		return jdbcTemplate.query(sql, (resultSet, rowNum) -> {
			return new StateWiseNoofUserDetailDto(resultSet.getInt("st_code"),
					resultSet.getString("st_name"),
					resultSet.getInt("stuser"),
					resultSet.getInt("stparticipant"),
					resultSet.getInt("stpassparticipant"),
					resultSet.getInt("stfailparticipant"));
		});
		
	}

}
