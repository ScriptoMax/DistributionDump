package com.hackcent.service.utils;

import java.util.List;

import com.hackcent.model.Address;
import com.hackcent.model.Hackathon;
import com.hackcent.model.Organisation;
import com.hackcent.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class HackathonReportResponse {
	
    private String teamName;
    private List<String> teamMembers;
    private float score;  
}