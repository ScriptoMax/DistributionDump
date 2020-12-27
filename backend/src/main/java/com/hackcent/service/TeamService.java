package com.hackcent.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hackcent.model.Hackathon;
import com.hackcent.model.Team;
import com.hackcent.model.TeamMember;
import com.hackcent.model.User;
import com.hackcent.repository.HackathonRepository;
import com.hackcent.repository.TeamRepository;
import com.hackcent.repository.TeamMemberRepository;
import com.hackcent.repository.UserRepository;
import com.hackcent.service.utils.HackathonReportResponse;
import com.hackcent.service.utils.TeamResponse;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class TeamService {
	
    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private HackathonRepository hackathonRepository;

    @Autowired
    private TeamMemberRepository teamMemberRepository;

    @Autowired
    private UserRepository userRepository;    
   
    public ResponseEntity<?> createTeam(Long hid) {
        Optional<Hackathon> hackathon = hackathonRepository.findById(Optional.ofNullable(hid).orElse(-1L));
        Team team = new Team();
        team.setHackathon(hackathon.get());
        teamRepository.save(team);
        TeamResponse teamResponse = new TeamResponse(team);
        return ResponseEntity.ok().body(teamResponse);
    }    
    
    public ResponseEntity<?> registerTeam(Long hid,
                                          String teamName,
                                          String leaderScreenName,
                                          String leaderRold,
                                          String member2ScreenName,
                                          String member2Role,
                                          String member3ScreenName,
                                          String member3Role,
                                          String member4ScreenName,
                                          String member4Email) {

        long hackid = Optional.ofNullable(hid).orElse(-1L);
        Optional<Hackathon> hackathon = hackathonRepository.findById(hackid);

        if(!hackathon.get().getStatus().equals("created"))
            return ResponseEntity.badRequest().body("This hackathon is in progress/completed.");

        String [] screenNames = new String[]{leaderScreenName, member2ScreenName, member3ScreenName, member4ScreenName};
        Team checkTeam = teamRepository.findTeamByName(teamName);
        if(checkTeam != null){
            return ResponseEntity.badRequest().body("This team is not available for use");
        }

        List<String> listScreenNames = Arrays.asList(screenNames);
        List<Team> teams = teamRepository.findAll();
        if(teams != null){
            for (Team team : teams) {
                if(team.getHackathon().getHid() == hid) {
                    List<TeamMember> teamMembers = team.getTeamMembers();
                    for (TeamMember teamMember : teamMembers) {
                        Optional<User> user = userRepository.findById(teamMember.getMemberId());
                        if (listScreenNames.contains(user.get().getScreenName())) {
                            return ResponseEntity.badRequest().body("User " + user.get().getName() + " has registered for hackathon " + team.getHackathon().getName() + " already");
                        }
                    }
                }
            }
        }

        User user = userRepository.findUserByScreenName(leaderScreenName);
        User user2 = null;
        User user3 = null;
        User user4 = null;

        Date date1 = null;
        Date date2 = null;

        try {
            date1 = new SimpleDateFormat("yyyy-MM-dd").parse(hackathon.get().getStartDate());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String s2 = simpleDateFormat.format(new Date());
            date2 = new SimpleDateFormat("yyy-MM-dd").parse(s2);
            if (date2.compareTo(date1) > 0) {
                return ResponseEntity.badRequest().body("Hackathon already started");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Cannot parse date exception");
        }

        Team team = new Team();
        team.setHackathon(hackathon.get());
        team.setName(teamName);

        String owner = hackathon.get().getOwner().getName();
        String [] judges = hackathon.get().getJudgeScreenName().split("\\$");

        long tid = team.getTid();
        String id = "" + Long.toString(tid) + "_" + Long.toString(hackid);
        TeamMember teamMember1 = new TeamMember();
        teamMember1.setTeam(team);
        teamMember1.setMemberId(user.getUid());
        teamMember1.setPaymentStatus("None");
        TeamMember teamMember2 = null;
        if(member2ScreenName != null && !member2ScreenName.equals("undefined")) {
            if(member2ScreenName.equals(owner))
                return ResponseEntity.badRequest().body(owner + " is the owner. Owner cannot register for his hackathon");

            if(Arrays.asList(judges).contains(member2ScreenName))
                return ResponseEntity.badRequest().body(member2ScreenName+" is a judge. A judge cannot be registered fot the same hackathon");
            user2 = userRepository.findUserByScreenName(member2ScreenName);
            teamMember2 = new TeamMember();
            if(user2 != null ) {
                teamMember2.setMemberId(user2.getUid());
                teamMember2.setTeam(team);
                teamMember2.setPaymentStatus("None");
            } else {
                return ResponseEntity.badRequest().body(member2ScreenName + " is not a valid member in the system.");
            }
        }

        TeamMember teamMember3 = null;
        if(member3ScreenName != null && !member3ScreenName.equals("undefined")) {
            if(member3ScreenName.equals(owner))
                return ResponseEntity.badRequest().body(owner + " is the owner. Owner cannot register for his hackathon");
            if(Arrays.asList(judges).contains(member3ScreenName))
                return ResponseEntity.badRequest().body(member3ScreenName+" is a judge. A judge cannot be registerd fot the same hackathon");

            user3 = userRepository.findUserByScreenName(member3ScreenName);
            teamMember3 = new TeamMember();
            if(user3 != null) {
                teamMember3.setMemberId(user3.getUid());
                teamMember3.setTeam(team);
                teamMember3.setPaymentStatus("None");
            } else {
                return ResponseEntity.badRequest().body(member3ScreenName + " is not a valid member in the system.");
            }
        }

        TeamMember teamMember4 = null;
        if(member4ScreenName != null && !member4ScreenName.equals("undefined")) {
            if(member4ScreenName.equals(owner))
                return ResponseEntity.badRequest().body(owner + " is the owner. Owner cannot register for his hackathon");
            if(Arrays.asList(judges).contains(member4ScreenName))
                return ResponseEntity.badRequest().body(member4ScreenName + " is a judge. A judge cannot be registerd fot the same hackathon");

            user4 = userRepository.findUserByScreenName(member4ScreenName);
            teamMember4 = new TeamMember();
            if(user4 != null) {
                teamMember4.setMemberId(user4.getUid());
                teamMember4.setTeam(team);
                teamMember4.setPaymentDate("None");
            } else{
                return ResponseEntity.badRequest().body(member4ScreenName + " is not a valid member in the system.");
            }
        }

        teamRepository.save(team);
        teamMemberRepository.save(teamMember1);
        new Thread(() -> {
            System.out.println("Sending email to " + user.getEmail());
            }).start();

        if(teamMember2 != null){
            teamMemberRepository.save(teamMember2);
            String screenname = user2.getScreenName();
            String email = user2.getEmail();
            new Thread(() -> {
                System.out.println("Sending email to " + user.getEmail());
                }).start();
        }
        
        if(teamMember3 != null){
            teamMemberRepository.save(teamMember3);
            String screenname = user3.getScreenName();
            String email = user3.getEmail();
            new Thread(() -> {
                System.out.println("Sending email to " + user.getEmail());
               }).start();
        }
        
        if(teamMember4 != null){
            teamMemberRepository.save(teamMember4);
            String screenname = user4.getScreenName();
            String email = user3.getEmail();
            new Thread(() -> {
                System.out.println("Sending email to "+user.getEmail());
               }).start();
        }

        Optional<Team> updatedTeam = teamRepository.findById(team.getTid());
        TeamResponse teamResponse = new TeamResponse(updatedTeam.get());

        return ResponseEntity.ok().body(teamResponse);
    }
   
    public ResponseEntity<?> updateTeamScore(long tid, float score) {
        Optional<Team> team = teamRepository.findById(tid);
        if(team != null){
            Hackathon hackathon = team.get().getHackathon();
            if(hackathon.getStatus().equals("opened"))
                return ResponseEntity.badRequest().body("Admin has not opened hackthon for grading yet.");
            else if(hackathon.getStatus().equals("final"))
                return ResponseEntity.badRequest().body("This hackathon is closed for grading.");
            team.get().setScore(score);
        }

        return ResponseEntity.ok().body("Score submitted");
    }
    
    public ResponseEntity<?> getTeams(String hackName){
        Hackathon hackathon = hackathonRepository.findHackathonByName(hackName);
        List<Team> allTeams = teamRepository.findAll();
        List<TeamResponse> hackTeams = new ArrayList<>();
        if(allTeams != null) {
            for (Team team : allTeams) {
                if (team.getHackathon().getHid() == hackathon.getHid()) {
                    TeamResponse teamResponse = new TeamResponse(team);
                    hackTeams.add(teamResponse);
                }
            }
        }
        
        return ResponseEntity.ok().body(hackTeams);
    }
    
    public ResponseEntity<?> getFinalTeams(String hackName){
        Hackathon hackathon = hackathonRepository.findHackathonByName(hackName);
        List<Team> allTeams = teamRepository.findAll();
        List<HackathonReportResponse> hackathonReportResponses = new ArrayList<>();
        if(allTeams != null){
            for (Team team : allTeams) {
                if(team.getHackathon().getHid() == hackathon.getHid()){
                    List<String> memberNames = new ArrayList<>();
                    List<TeamMember> teamMembers = team.getTeamMembers();
                    for(TeamMember teamMember : teamMembers){
                        Optional<User> user = userRepository.findById(teamMember.getMemberId());
                        memberNames.add(user.get().getScreenName());
                    }

                    HackathonReportResponse hackathonReportResponse = new HackathonReportResponse(team.getName(), memberNames, team.getScore());
                    hackathonReportResponses.add(hackathonReportResponse);
                }
            }
        }

        List<HackathonReportResponse> sortedTeam = hackathonReportResponses.stream().sorted(Comparator.comparingDouble(HackathonReportResponse::getScore)).collect(Collectors.toList());

        return ResponseEntity.ok().body(sortedTeam);
    }
   
    public ResponseEntity<?> emailTeamMembers(long hid){
        Optional<Hackathon> hackathon = hackathonRepository.findById(hid);
        List<Team> allTeams = teamRepository.findAll();
        if(allTeams != null){
            for (Team team : allTeams) {
                List<TeamMember> teamMembers = team.getTeamMembers();
                for(TeamMember teamMember : teamMembers){
                    Optional<User> user = userRepository.findById(teamMember.getMemberId());
                    new Thread(() -> {
                        System.out.println("Sending email to " + user.get().getEmail());
                        }).start();
                }
            }
        }

        return ResponseEntity.ok().body("Email sent");
    }
}