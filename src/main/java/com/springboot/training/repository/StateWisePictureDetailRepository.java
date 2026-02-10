package com.springboot.training.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.springboot.training.dto.StateWisePictureDetailDto;

@Repository
public class StateWisePictureDetailRepository {
	
	
	@Autowired
    private JdbcTemplate jdbcTemplate; 
	
	public List<StateWisePictureDetailDto> getStateWisePictureDetail(){
		
		String sql = "select st_code, (select st_name from iwmp_state where st_code=p.st_code) as st_name,  max(p.photo_path) as photo_path from state_photo p group by  p.st_code order by st_name";
		
		return jdbcTemplate.query(sql, (resultSet, rowNum) -> {
			return new StateWisePictureDetailDto(resultSet.getInt("st_code"),
					resultSet.getString("st_name"),
					resultSet.getString("photo_path"));
		});
		
	}

}
