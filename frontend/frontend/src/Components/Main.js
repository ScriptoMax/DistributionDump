import React, { Component } from "react";
import { Route } from "react-router-dom";
import SignUp from "./SignUp/signup";
import Login from "./Login/login";

import Navbar from "./Navbar/navbar";
import Footer from "./Footer/footer";
import Navbar2 from "./Navbar/navbar2";

import Profile from "./Profile/profile";
import listorganisations from "./CreateHackOrg/listorganisations";
import listHackathon from "./CreateHackOrg/listHackathon";
import manageorg from "./ManageOrg/manageorg";
import organisations from "./ManageOrg/organisations";
import homepage from "./homepage/homepage";
import mainpage from "./hackathon/mainPage";
import registerHackathon from "./hackathon/register";
import payHackathon from "./hackathon/payment";
import redirect from "./Profile/redirect";
import NavProfile from "./Navbar/navbar5";
import finalmembers from "./ManageOrg/finalmembers";
import listallhackathon from "./hackathon/allhackathons";
import submitHackathon from "./hackathon/submitHackathon";
import resultPage from "./hackathon/resultPage";
import Hackeval from "./hackathon/hackEval";
import paymentstatus from "./hackathon/paymentStatus";
import gradeHackathon from "./hackathon/gradehackathon";
import hackreport from "./hackathon/hackreport";
import home from "./homepage/home";

//Create a Main Component
class Main extends Component {
  render() {
    return (
      <div>
        {/*Render Different Component based on Route*/}

        <Route path="/list" component={listorganisations} />
        <Route path="/footer" component={Footer} />
        <Route path="/navbar" component={Navbar} />
        <Route path="/navbar2" component={Navbar2} />
        <Route path="/navbar5" component={NavProfile} />
        <Route path="/register" component={SignUp} />
        <Route exact path="/" component={homepage} />
        <Route path="/profile" component={Profile} />
        <Route path="/login" component={Login} />
        <Route path="/memberlist" component={manageorg} />
        <Route path="/listhackathon" component={listHackathon} />
        <Route path="/evaluate" component={Hackeval} />
        <Route path="/hackmain" component={mainpage} />
        <Route path="/registerHackathon" component={registerHackathon} />
        <Route path="/payHackathon" component={payHackathon} />
        <Route path="/organisations" component={organisations} />
        <Route path="/members" component={finalmembers} />
        <Route path="/redirect" component={redirect} />
        <Route path="/allhackathons" component={listallhackathon} />
        <Route path="/submitHackathon" component={submitHackathon} />
        <Route path="/paymentstatus" component={paymentstatus} />
        <Route path="/resultPage" component={resultPage} />
        <Route path="/gradehackathon" component={gradeHackathon} />
        <Route path="/paymentStatus" component={paymentstatus} />
        <Route path="/home" component={home} />
        <Route path="/hackreport" component={hackreport} />
      </div>
    );
  }
}

//Export The Main Component
export default Main;