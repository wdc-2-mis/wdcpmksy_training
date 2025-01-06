package com.springboot.training.controller;

import com.springboot.training.entity.IwmpBlock;
import com.springboot.training.entity.IwmpDistrict;
import com.springboot.training.repository.IwmpBlockRepository;
import com.springboot.training.repository.IwmpDistrictRepository;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import java.util.List;

@RestController
@RequestMapping("/registration")
public class RegistrationController {

	@Autowired
	private  IwmpDistrictRepository distrepo;
	
	@Autowired
	private IwmpBlockRepository blockrepo;

    @GetMapping("/districts/{stateCode}")
    public List<IwmpDistrict> getDistricts(@PathVariable Integer stateCode) {
        return distrepo.findByStateCode(stateCode);
    }

    
    @GetMapping("/blocks/{districtCode}")
    public List<IwmpBlock> getBlocks(@PathVariable Integer districtCode) {
        return blockrepo.findByDistrictCode(districtCode);
    }
    
}