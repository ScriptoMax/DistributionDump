import React, { Component } from "react";
import "../../App.css";
import { Route, withRouter, Link } from "react-router-dom";
import cookie from "react-cookies";
import axios from "axios";

//create the Navbar Component
class NavProfile extends Component {
  constructor(props) {
    super(props);
  }

  handleLogout = () => {
    localStorage.removeItem("email");
    localStorage.removeItem("screenName");
  };
  render() {
    var logDrop = (
      <li class="dropdown dropdown-custom">
        <a class="droptext dropdown-toggle" data-toggle="dropdown" href="#">
          <img
            src="images/hackerbadge.png"
            style={{ marginRight: "5px", marginTop: "10px", float: "left" }}
          />&nbsp;
          <div style={{ fontSize: "16px", marginTop: "5px", marginLeft: "10px", color: "#D2470A", fontWeight: "bolder" }}>
            {localStorage.getItem("screenName")}&nbsp;
            <span className="caret" style={{height: "10px", color: "#D2470A", fontWeight: "bolder"}} />
          </div>
        </a>
        <ul class="dropdown-menu">
          <li>
            <Link to="/profile" class="test">
              My Profile
            </Link>
          </li>
          <li>
            <Link to="/listhackathon" class="test">
              Create Hackathon
            </Link>
          </li>
          <li>
            <Link to="/organisations" class="test">
              Manage Organization
            </Link>
          </li>
          <li>
            <Link to="/list" class="test">
              Create Organization
            </Link>
          </li>
          <li>
            <Link to="/" class="test" onClick={this.handleLogout}>
              Logout
            </Link>
          </li>
        </ul>
      </li>
    );

    return (
      <nav class="navbar navbar-light navbar-light-custom">
        <div class="container-fluid">
          <div class="navbar-header">
            <a className="navbar-brand navbar-brand-custom" href="/profile">
              <img
                  src="images/hlogo.png"
                  style={{
                    marginLeft: "30px",
                    width: "320px",
                    height: "80px"
                  }}
              />
            </a>
          </div>
          <ul class="nav navbar-nav navbar-right">
            {logDrop}
            <li>
              <button class="btn navbar-btn1">
                <Link
                  to="/hackmain"
                  class="test"
                  style={{ textDecoration: "none", color: "#FBFCFC" }}
                >
                  Manage Hackathons
                </Link>
              </button>
              <button class="btn navbar-btn1">
                <Link
                  to="/allhackathons"
                  class="test"
                  style={{ textDecoration: "none", color: "#FBFCFC" }}
                >
                  View All Hackathons
                </Link>
              </button>
              <button class="btn navbar-btn1">
                <Link
                  to="/submithackathon"
                  class="test"
                  style={{ textDecoration: "none", color: "#FBFCFC" }}
                >
                  Submit Hackathons
                </Link>
              </button>
              <button class="btn navbar-btn1">
                <Link
                  to="/gradehackathon"
                  class="test"
                  style={{ textDecoration: "none", color: "#FBFCFC" }}
                >
                  Grade Hackathons
                </Link>
              </button>
            </li>
            <li>
            </li>
          </ul>
        </div>
      </nav>
    );
  }
}

export default NavProfile;