package com.hackcent.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hackcent.model.*;
import com.hackcent.repository.*;
import com.hackcent.service.utils.ExpenseReport;
import com.hackcent.service.utils.HackathonPaymentReportResponse;
import com.hackcent.service.utils.HackathonReportResponse;
import com.hackcent.service.utils.TeamMemberPaymentReport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PaymentService {
	
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private HackathonRepository hackathonRepository;

    @Autowired
    private TeamMemberRepository teamMemberRepository;

    @Autowired
    private OrganisationRepository organisationRepository;    
    
    public ResponseEntity<?> createPayment(String screenName, Long hid) {
        User user = userRepository.findUserByScreenName(screenName);
        Optional<Hackathon> hackathon = hackathonRepository.findById(Optional.ofNullable(hid).orElse(-1L));       
        TeamMember teamMember = null;        
        List<Team> teams = teamRepository.findAll()
								         .stream()
								         .filter(h -> h.getHackathon().getHid() == hid)
								         .collect(Collectors.toList());       
        
        for(Team team : teams){
            List<TeamMember> teamMembers = team.getTeamMembers();
            for (TeamMember tmp : teamMembers) {
                Optional<User> u = userRepository.findById(tmp.getMemberId());
                if(u.get().getScreenName().equals(screenName)){
                    teamMember = tmp;
                }
            }
        }

        System.out.println(teamMember == null);

        if(teamMember.getPaymentStatus().equals("Paid"))
            return ResponseEntity.badRequest().body("Payment already received for this hackathon");

        teamMember.setPaymentDate("Paid");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String s2 = simpleDateFormat.format(new Date());
        teamMember.setPaymentDate(s2);

        Payment payment = new Payment();
        payment.setHackathon(hackathon.get());
        payment.setUid(user.getUid());

        paymentRepository.save(payment);
        String email = user.getEmail();
        Long id = hid;
        String hname = hackathon.get().getName();
        
        new Thread(() -> {
            System.out.println("Sending payment invoice to " + email);           
        }).start();

        return ResponseEntity.ok().body("Payment Successfull");
    }

    public float getAmount(String screenName, Long hid) {
        User user = userRepository.findUserByScreenName(screenName);
        Optional<Hackathon> hackathon = hackathonRepository.findById(Optional.ofNullable(hid).orElse(-1L));

        float fee = hackathon.get().getFee();
        int discount = hackathon.get().getDiscount();
        float amount = fee;
        if(user != null) {

            String [] orgNames = hackathon.get().getSponsors().split("$");
            for (String orgName : orgNames) {
                if(user.getOrganisation() != null) {
                    if (user.getOrganisation().getName().equals(orgName) && user.getOrgStatus().equals("Approved") && discount != 0) {
                        amount = (fee * discount) / 100;
                    }
                }
            }
        }

        return amount;
    }
    
    public ResponseEntity<?> getPaymentAmount(String screenName, Long hid) {
        User user = userRepository.findUserByScreenName(screenName);
        Optional<Hackathon> hackathon = hackathonRepository.findById(Optional.ofNullable(hid).orElse(-1L));

        float fee = hackathon.get().getFee();
        int discount = hackathon.get().getDiscount();
        float amount = fee;
        if(user != null) {
            String [] orgNames = hackathon.get().getSponsors().split("$");
            for (String orgName : orgNames) {
                if(user.getOrganisation() != null) {
                    if (user.getOrganisation().getName().equals(orgName) && user.getOrgStatus().equals("Approved") && discount != 0) {
                        amount = (fee * discount) / 100;
                    }
                }
            }
        }
        
        TeamMember teamMember = teamMemberRepository.findTeamMemberByMemberId(user.getUid());
        System.out.println(teamMember);
        teamMember.setAmount(amount);
        return ResponseEntity.ok().body(Float.toString(amount));
    }
    
    public ResponseEntity<?> getTeamsPayment(String hackName) {
        Hackathon hackathon = hackathonRepository.findHackathonByName(hackName);
        List<Team> allTeams = teamRepository.findAll();
        List<HackathonPaymentReportResponse> hackathonPaymentReportResponses = new ArrayList<>();

        if(allTeams != null){
            for (Team team:
                    allTeams) {
                if(team.getHackathon().getHid() == hackathon.getHid()){
                    List<TeamMember> teamMembers = team.getTeamMembers();
                    List<TeamMemberPaymentReport> teamMemberPaymentReports = new ArrayList<>();

                    for(TeamMember teamMember : teamMembers){
                        Optional<User> user = userRepository.findById(teamMember.getMemberId());
                        TeamMemberPaymentReport teamMemberPaymentReport = new TeamMemberPaymentReport(user.get().getScreenName(),
                        		                                                                      teamMember.getAmount(),
                        		                                                                      teamMember.getPaymentStatus(),
                        		                                                                      teamMember.getPaymentDate());
                        teamMemberPaymentReports.add(teamMemberPaymentReport);
                    }

                    System.out.println(teamMemberPaymentReports.size());
                    HackathonPaymentReportResponse hackathonPaymentReportResponse = new HackathonPaymentReportResponse(team.getName(),teamMemberPaymentReports);
                    hackathonPaymentReportResponses.add(hackathonPaymentReportResponse);
                }
            }
        }

        return ResponseEntity.ok().body(hackathonPaymentReportResponses);
    }
    
    public ResponseEntity<?> getEarningReport(String hackname) {
    	Hackathon hackathon = hackathonRepository.findHackathonByName(hackname);
    	Long hid = hackathon.getHid(); 
        List<Team> teams = teamRepository.findAll()
       		 							 .stream()
       		 							 .filter(h -> h.getHackathon().getHid() == hid)
       		 							 .collect(Collectors.toList());
        String [] sponsors = hackathon.getSponsors().split("\\$");
        int numberOfSponsors = sponsors.length;
        int paymentCount = 0;
        int notPaidCount = 0;
        float paidTotal = 0;
        float notPaidTotal = 0;

        for (Team team : teams) {
            List<TeamMember> teamMembers = team.getTeamMembers();
            for (TeamMember teamMember : teamMembers) {
                Optional<User> user = userRepository.findById(teamMember.getMemberId());

                if(teamMember.getPaymentStatus().equals("None")){
                    notPaidCount++;
                    notPaidTotal += getAmount(user.get().getScreenName(), hackathon.getHid());
                } else {
                    paymentCount++;
                    paidTotal += teamMember.getAmount();
                }
            }
        }

        ExpenseReport expenseReport = new ExpenseReport(numberOfSponsors, paymentCount, notPaidCount, paidTotal, notPaidTotal);
        return ResponseEntity.ok().body(expenseReport);
    }
}