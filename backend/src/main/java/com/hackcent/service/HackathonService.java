package com.hackcent.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hackcent.model.*;
import com.hackcent.repository.*;
import com.hackcent.service.utils.HackathonResponse;
import com.hackcent.service.utils.TeamResponse;

import javassist.NotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class HackathonService {

    @Autowired
    private HackathonRepository hackathonRepository;

    @Autowired
    private OrganisationRepository organisationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TeamMemberRepository teamMemberRepository;
    
    public ResponseEntity<?> createHackathon(String name,
                                             String startDate,
                                             String finishDate,
                                             String postedOn,
                                             String description,
                                             String ownerScreenName,
                                             String judgeScreenName,
                                             Integer minTeamSize,
                                             Integer maxTeamSize,
                                             Float fee,
                                             Integer discount,
                                             String status,
                                             String orgNames) {

        if(name == null || startDate == null || finishDate == null || description == null || ownerScreenName == null || judgeScreenName == null || fee == null){
            return ResponseEntity.badRequest().body("Please fill up all the fields marked *");
        }

        if(name.equals("") || startDate.equals("") || finishDate.equals("") || description.equals("") || ownerScreenName.equals("") || judgeScreenName.equals("")){
            return ResponseEntity.badRequest().body("Please fill up all the fields marked *");
        }

        Hackathon tmpHack = hackathonRepository.findHackathonByName(name);
        if(tmpHack != null)
            return ResponseEntity.badRequest().body("Hackathon name already in use");


        Hackathon hackathon = new Hackathon();
        hackathon.setName(name);
        hackathon.setStartDate(startDate);
        hackathon.setFinishDate(finishDate);
        hackathon.setPostedOn(postedOn);
        hackathon.setDescription(description);

        User owner = userRepository.findUserByScreenName(ownerScreenName);
        if(owner.getUserType().equals("admin"))
            hackathon.setOwner(owner);
        else
            return ResponseEntity.badRequest().body("Not authorised to create a hackathon");

        hackathon.setJudgeScreenName(judgeScreenName);
        hackathon.setMaxTeamSize(Optional.ofNullable(maxTeamSize).orElse(-1));
        hackathon.setMinTeamSize(Optional.ofNullable(minTeamSize).orElse(-1));
        hackathon.setFee(Optional.ofNullable(fee).orElse(-1.0F));
        hackathon.setDiscount(Optional.ofNullable(discount).orElse(-1));
        hackathon.setStatus(status);

        System.out.println(orgNames);

        if( orgNames != null){
            hackathon.setSponsors(orgNames);
        }

        hackathonRepository.save(hackathon);
        HackathonResponse hackathonResponse = new HackathonResponse(hackathon);
        return ResponseEntity.ok().body(hackathonResponse);
    }
   
    public ResponseEntity<?> readHackathonList(){

        List<HackathonResponse> hackathonResponses = new ArrayList<HackathonResponse>();

        List<Hackathon> hacks = hackathonRepository.findAll();
        for (Object obj : hacks) {
            Hackathon tmp = (Hackathon) obj;
            hackathonResponses.add(new HackathonResponse(tmp));
        }

        return ResponseEntity.ok().body(hackathonResponses);
    }
    
    public ResponseEntity<?> readHackathonPublic(String screenName){

        List<HackathonResponse> hackathonResponses = new ArrayList<>();
        List<Hackathon> hacks = hackathonRepository.findAll()
        										   .stream()
        										   .filter(h -> h.getStatus().equals("created"))
        										   .collect(Collectors.toList());    
        
        for (Object obj : hacks) {
            Hackathon tmp = (Hackathon) obj;
            String [] judges = tmp.getJudgeScreenName().split("\\$");

            if(!tmp.getOwner().getScreenName().equals(screenName) && !Arrays.asList(judges).contains(screenName))
                hackathonResponses.add(new HackathonResponse(tmp));
        }

        return ResponseEntity.ok().body(hackathonResponses);
    }
    
    public ResponseEntity<?> readHackathonByOwner(String screenName){

        List<HackathonResponse> hackathonResponses = new ArrayList<>();

        List<Hackathon> hacks = hackathonRepository.findAll();
        for (Object obj : hacks) {
            Hackathon tmp = (Hackathon) obj;
            if(tmp.getOwner().getScreenName().equals(screenName))
                hackathonResponses.add(new HackathonResponse(tmp));
        }

        return ResponseEntity.ok().body(hackathonResponses);
    }
    
    public ResponseEntity<?> readHackathonByJudge(String screenName){

        List<HackathonResponse> hackathonResponses = new ArrayList<>();
        List<Hackathon> hacks = hackathonRepository.findAll();
        for (Object obj : hacks) {
            Hackathon tmp = (Hackathon) obj;
            String [] judges = tmp.getJudgeScreenName().split("\\$");
            if(Arrays.asList(judges).contains(screenName))
                hackathonResponses.add(new HackathonResponse(tmp));
        }

        return ResponseEntity.ok().body(hackathonResponses);
    }
    
    public ResponseEntity<?> readHackathon(Long id) {
        Optional<Hackathon> hackathon = hackathonRepository.findById(Optional.ofNullable(id).orElse(-1L));
        HackathonResponse hackathonResponse;
        
        if (hackathon.isPresent())        
            hackathonResponse = new HackathonResponse(hackathon.get());
        else 
			throw new NoSuchElementException("No match found by hackathon id\nProgram forced to exit as a next data source is empty");

        return ResponseEntity.ok().body(hackathonResponse);
    }
    
    public ResponseEntity<?> updateHackathonStatus(Long id, String status){
        Optional<Hackathon> hackathon = hackathonRepository.findById(Optional.ofNullable(id).orElse(-1L));
        
        if (hackathon.isPresent()) {        
	        if(!hackathon.get().getStatus().equals(status))
	            hackathon.get().setStatus(status);
	        else
	            return ResponseEntity.badRequest().body("Hackathon is already "+status);
        } else
        	throw new NoSuchElementException("No match found by hackathon id\nProgram forced to exit as a next data source is empty");       
        
        HackathonResponse hackathonResponse = new HackathonResponse(hackathon.get());

        return ResponseEntity.ok().body("Hackathon " + hackathon.get().getName() + " is " + status);
    }
    
    public ResponseEntity<?> getCurrentUserHackathons(String screenName) {
    	System.out.println("Nickname: " + screenName);
        User user = userRepository.findUserByScreenName(screenName);
        List<TeamMember> teamMembers = teamMemberRepository.findAll()
       		 											   .stream()       		 											  
       		 											   .filter(h -> h.getMemberId() == user.getUid())
       		 											   .collect(Collectors.toList());       		
        List<Hackathon> hackathons = hackathonRepository.findAll();
        List<HackathonResponse> hackathonResponses = new ArrayList<>();
        
        if (!teamMembers.isEmpty()) {
        	TeamMember currentUserMember = teamMembers.get(teamMembers.size()-1);
	        for (Hackathon hackathon : hackathons) {
	            if(hackathon.getHid() == currentUserMember.getTeam().getHackathon().getHid()){
	                HackathonResponse hackathonResponse = new HackathonResponse(hackathon);
	                hackathonResponses.add(hackathonResponse);
	            }
	        }
        }
        
        return ResponseEntity.ok().body(hackathonResponses);
    }
    
    public ResponseEntity<?> submitProjectLink(Long hid, String screenName, String codeUrl) {
        User user = userRepository.findUserByScreenName(screenName);
        Optional<Hackathon> hackathon = hackathonRepository.findById(hid);
        List<Team> teams = teamRepository.findAll()
						        		 .stream()
						        		 .filter(h -> h.getHackathon().getHid() == hid)
						        		 .collect(Collectors.toList());

        if(teams == null){
            return ResponseEntity.badRequest().body("No teams found for this hackathon");
        }

        Team hackteam = null;

        for(Team team: teams){
            List<TeamMember> teamMembers = team.getTeamMembers();
            for(TeamMember teamMember : teamMembers) {                
            	if(teamMember.getMemberId() == user.getUid()) {
                    hackteam = team;
                }
            }
        }
        
        if(hackteam == null){
            return ResponseEntity.badRequest().body("User is not part of any team to submit for this hackathon");
        }

        List<TeamMember> teamMembers = hackteam.getTeamMembers();
        if(teamMembers == null)
            return ResponseEntity.badRequest().body("No Team Members for this team id");

        for (TeamMember teamMember:
             teamMembers) {
            if(teamMember.getPaymentStatus().equals("None"))
                return ResponseEntity.badRequest().body("All team members must pay the fee before code submission. Check email for payment link");
        }


        if(hackathon.get().getStatus().equals("opened"))
            hackteam.setCodeUrl(codeUrl);
        else
            return ResponseEntity.badRequest().body("Can't submit. Hackathon is " + hackathon.get().getStatus());

        TeamResponse teamResponse = new TeamResponse(hackteam);
        return ResponseEntity.ok().body("Code submitted. Just note : you can update the link anytime untill hackathon is closed");
    }
}