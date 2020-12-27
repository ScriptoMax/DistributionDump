package com.hackcent.service.utils;


import java.util.ArrayList;
import java.util.List;

import com.hackcent.model.Address;
import com.hackcent.model.Hackathon;
import com.hackcent.model.Organisation;
import com.hackcent.model.Team;
import com.hackcent.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
class HackOwnerResponse{
	
    private String name;
    private String screenName;
    private String email;   
}

@Data
@AllArgsConstructor
class HackSponserResponse{
	
    private long id;
    private String name;
    private String description;   
}

@Data
@AllArgsConstructor
class HackTeamResponse{
    private Long id;   
}

@Data
@AllArgsConstructor
public class HackathonResponse {
    private Long id;
    private String name;
    private String startDate;
    private String finishDate;
    private String postedOn;
    private int minTeamSize;
    private int maxTeamSize;
    private float fee;
    private int discount;
    private String status;
    private HackOwnerResponse owner;
    private String [] sponsors;
    private String [] judges;

    private List<HackTeamResponse> teams = new ArrayList<>();

    public HackathonResponse(Hackathon hackathon) {
        this.id = hackathon.getHid();
        this.name = hackathon.getName();
        this.startDate = hackathon.getStartDate();
        this.finishDate = hackathon.getFinishDate();
        this.postedOn = hackathon.getPostedOn();
        this.minTeamSize = hackathon.getMinTeamSize();
        this.maxTeamSize = hackathon.getMaxTeamSize();
        this.fee = hackathon.getFee();
        this.discount = hackathon.getDiscount();
        this.status = hackathon.getStatus();

        User user = hackathon.getOwner();

        if(user != null){
            this.owner = new HackOwnerResponse(user.getName(), user.getScreenName(), user.getEmail());
        }

        this.sponsors = hackathon.getSponsors().split("\\$");
        this.judges = hackathon.getJudgeScreenName().split("\\$");

        List<Team> team_list = hackathon.getTeams();
        for (Team t : team_list) {
            teams.add(new HackTeamResponse(t.getTid()));
        }
    }    
}