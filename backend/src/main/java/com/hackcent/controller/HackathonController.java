package com.hackcent.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hackcent.model.Hackathon;
import com.hackcent.service.HackathonService;
import com.hackcent.service.OrganisationService;
import com.hackcent.service.TeamService;
import com.hackcent.service.UserService;
import com.hackcent.websession.utils.ResponseHeader;

@RestController
public class HackathonController {

    @Autowired
    private HackathonService hackathonService;

    @Autowired
    private TeamService teamService;

    private ResponseHeader responseHeader = new ResponseHeader();

    @RequestMapping(value = "/hackathon", method = {RequestMethod.POST})
    public ResponseEntity<?> postHackathon(@RequestParam(value = "name",required = true) String name,
                                           @RequestParam(value = "startDate",required = false) String startDate,
                                           @RequestParam(value = "finishDate",required = false) String finishDate,
                                           @RequestParam(value = "postedOn",required = false) String postedOn,
                                           @RequestParam(value = "description", required = false) String description,
                                           @RequestParam(value = "ownerScreenName",required = false) String ownerScreenName,
                                           @RequestParam(value = "judgeScreenNames",required = false) String judgeScreenNames,
                                           @RequestParam(value = "minTeamSize",required = false) Integer minTeamSize,
                                           @RequestParam(value = "maxTeamSize",required = false) Integer maxTeamSize,
                                           @RequestParam(value = "fee",required = false) Float fee,
                                           @RequestParam(value = "discount",required = false) Integer discount,
                                           @RequestParam(value = "organisationNames",required = false) String organisationNames) {

        ResponseEntity<?> responseEntity = hackathonService.createHackathon(name, startDate, finishDate, postedOn, description,
        																    ownerScreenName, judgeScreenNames, minTeamSize,
        																    maxTeamSize, fee, discount, "created", organisationNames);
        
        return new ResponseEntity<>(responseEntity, responseHeader.getHeader(), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/hackathon", method = {RequestMethod.GET})
    public ResponseEntity<?> getHackathonList(){
        ResponseEntity<?> responseEntity = hackathonService.readHackathonList();
        
        return new ResponseEntity<>(responseEntity, responseHeader.getHeader(), HttpStatus.OK);
    }

    @RequestMapping(value = "/hackathon/names/{screenName}", method = {RequestMethod.GET})
    public ResponseEntity<?> getHackathonList(@PathVariable String screenName){
        ResponseEntity<?> responseEntity = hackathonService.readHackathonByOwner(screenName);
        
        return new ResponseEntity<>(responseEntity, responseHeader.getHeader(), HttpStatus.OK);
    }

    @RequestMapping(value = "/hackathon/viewall/{screenName}", method = {RequestMethod.GET})
    public ResponseEntity<?> getValidHackathons(@PathVariable String screenName){
        ResponseEntity<?> responseEntity = hackathonService.readHackathonPublic(screenName);
        
        return new ResponseEntity<>(responseEntity, responseHeader.getHeader(), HttpStatus.OK);
    }

    @RequestMapping(value = "/hackathon/registered/{screenName}", method = {RequestMethod.GET})
    public ResponseEntity<?> getCurrentUserHackathons(@PathVariable String screenName) {    	
        ResponseEntity<?> responseEntity = hackathonService.getCurrentUserHackathons(screenName);
        
        return new ResponseEntity<>(responseEntity, responseHeader.getHeader(), HttpStatus.OK);
    }

    @RequestMapping(value = "/hackathon/grade/{screenName}", method = {RequestMethod.GET})
    public ResponseEntity<?> getGradableHackathons(@PathVariable String screenName){
        ResponseEntity<?> responseEntity = hackathonService.readHackathonByJudge(screenName);
        
        return new ResponseEntity<>(responseEntity, responseHeader.getHeader(), HttpStatus.OK);
    }

    @RequestMapping(value = "/hackathon/{hid}", method = {RequestMethod.GET})
    public ResponseEntity<?> getHackathon(@PathVariable Long hid){
        ResponseEntity responseEntity = hackathonService.readHackathon(hid);
        
        return new ResponseEntity<>(responseEntity, responseHeader.getHeader(), HttpStatus.OK);
    }

    @RequestMapping(value = "/hackathon/{hid}/register", method = {RequestMethod.POST})
    public ResponseEntity<?> registerTeam(@PathVariable Long hid,
                                          @RequestParam(value = "teamName",required = false) String teamName,
                                          @RequestParam(value = "leaderScreenName", required = true) String leaderScreenName,
                                          @RequestParam(value = "leaderRole", required = true) String email,
                                          @RequestParam(value = "member2ScreenName", required = false) String member2ScreenName,
                                          @RequestParam(value = "member2Role", required = false) String member2Role,
                                          @RequestParam(value = "member3ScreenName", required = false) String member3ScreenName,
                                          @RequestParam(value = "member3Role", required = false) String member3Role,
                                          @RequestParam(value = "member4ScreenName", required = false) String member4ScreenName,
                                          @RequestParam(value = "member4Role", required = false) String member4Role){

        ResponseEntity<?> responseEntity = teamService.registerTeam(hid, teamName, leaderScreenName, email, member2ScreenName, 
        		                                                    member2Role, member3ScreenName, member3Role, member4ScreenName, member4Role);

        return new ResponseEntity<>(responseEntity, responseHeader.getHeader(), HttpStatus.CREATED);
    }

    @RequestMapping(value = "hackathon/{hid}/opened", method = {RequestMethod.POST})
    public ResponseEntity<?> openHackathon(@PathVariable Long hid){
        ResponseEntity<?> responseEntity = hackathonService.updateHackathonStatus(hid, "opened");
        
        return new ResponseEntity<>(responseEntity, responseHeader.getHeader(), HttpStatus.OK);
    }

    @RequestMapping(value = "hackathon/{hid}/closed", method = {RequestMethod.POST})
    public ResponseEntity<?> closeHackathon(@PathVariable Long hid) {
        ResponseEntity<?> responseEntity = hackathonService.updateHackathonStatus(hid, "closed");
        
        return new ResponseEntity<>(responseEntity, responseHeader.getHeader(), HttpStatus.OK);
    }

    @RequestMapping(value = "hackathon/{hid}/finalized", method = {RequestMethod.POST})
    public ResponseEntity<?> finalizeHackathon(@PathVariable Long hid) {
        ResponseEntity<?> responseEntity = hackathonService.updateHackathonStatus(hid, "final");

        if(responseEntity.getStatusCode() == HttpStatus.OK){
            ResponseEntity<?> responseEntity1 = teamService.emailTeamMembers(hid);
        }

        return new ResponseEntity<>(responseEntity, responseHeader.getHeader(), HttpStatus.OK);
    }

    @RequestMapping(value="hackathon/{hid}/codesubmission/{screenName}",method= {RequestMethod.POST})
    public ResponseEntity<?> submitProjectLink(@PathVariable long hid,
                                            @PathVariable String screenName,
                                            @RequestParam(value="codeUrl",required=true) String codeUrl){
    	
        ResponseEntity<?> responseEntity = hackathonService.submitProjectLink(hid, screenName, codeUrl);
        return new ResponseEntity<>(responseEntity, responseHeader.getHeader(), HttpStatus.OK);
    }

    @RequestMapping(value = "hackathon/{tid}/score",method = {RequestMethod.POST})
    public ResponseEntity<?> updateScore(@PathVariable long tid,
                                         @RequestParam(value="score",required=true) float score){

        ResponseEntity<?> responseEntity = teamService.updateTeamScore(tid,score);
        return new ResponseEntity<>(responseEntity, responseHeader.getHeader(), HttpStatus.OK);
    }
}