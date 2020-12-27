package com.hackcent.service.utils;

import java.util.ArrayList;
import java.util.List;

import com.hackcent.model.Address;
import com.hackcent.model.Hackathon;
import com.hackcent.model.Organisation;
import com.hackcent.model.Team;
import com.hackcent.model.TeamMember;
import com.hackcent.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
class TeamHackResponse{
	
    private Long id;
    private String name;    
}

@Data
@AllArgsConstructor
class TeamMemberResponse{
    private Long id;    
}

@Data
public class TeamResponse {
	
    private Long id;
    private TeamHackResponse hackathon;
    private String submissionUrl;
    private List<TeamMemberResponse> teamMembers = new ArrayList<>();   

    public TeamResponse(Team team) {
        this.id = team.getTid();
        Hackathon hackathon = team.getHackathon();
        this.submissionUrl = team.getCodeUrl();
        this.hackathon = new TeamHackResponse(hackathon.getHid(),hackathon.getName());
        List<TeamMember> teamMemberList = team.getTeamMembers();

        for (TeamMember t : teamMemberList) {
            teamMembers.add(new TeamMemberResponse(t.getTmid()));
        }
    }
}