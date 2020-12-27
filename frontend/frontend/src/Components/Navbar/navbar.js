import React, { Component } from "react";
import "../../App.css";
import { Link } from "react-router-dom";
import cookie from "react-cookies";
import { Redirect } from "react-router";
//create the Navbar Component
class Navbar extends Component {
  constructor(props) {
    super(props);
    this.state = {
      authFlag: false
    };
    this.handleLogout = this.handleLogout.bind(this);
    this.handleClick = this.handleClick.bind(this);
  }
  //handle logout to destroy the cookie

  handleLogout = () => {
    localStorage.removeItem("email");
    localStorage.removeItem("screenName");
  };

  handleClick = () => {
    this.setState({
      authFlag: true
    });
  };
  render() {
    console.log(this.props.email);
    let redirectVar = null;
    let dropDown = (
      <li class="dropdown">
        <a class="dropdown-toggle dropdown-toggle-custom" data-toggle="dropdown">
          <img
              src="images/authbtn.png"
              style={{
                marginLeft: "25px",
                width: "200px",
                height: "70px"
              }} />
        </a>
        <ul class="dropdown-menu" style={{ left: "auto", right: 0, marginRight: "20px" }}>
          <li>
            <Link to="/login" class="test">
              Login
            </Link>
          </li>
          <li>
            <Link to="/register" class="test">
              Register
            </Link>
          </li>
        </ul>
      </li>
    );
    if (localStorage.getItem("screenName")) {
      dropDown = (
        <li class="dropdown">
          <div class="dropdown-toggle" data-toggle="dropdown">
            <img
                src="images/hackerbadge.png"
                style={{ marginRight: "5px", marginTop: "10px", float: "left" }}
            />&nbsp;
            <div style={{ fontSize: "20px", color: "#D2470A", fontWeight: "bolder" }}>
              {localStorage.getItem("screenName")}&nbsp;
              <span className="caret" style={{height: "10px", color: "#D2470A", fontWeight: "bolder"}}/>
            </div>
          </div>
          <ul class="dropdown-menu" style={{ left: "auto", right: 0, marginRight: "20px" }}>
            <li>
              <Link to="/profile" class="test">
                My profile
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
    }

    return (
      <nav
        class="navbar navbar-light"
        style={{ backgroundColor: "transparent", marginBottom: 0, border: 0 }}
      >
        {redirectVar}
        <div class="container-fluid">
          <div class="navbar-header navbar-header-custom">
            <a class="navbar-brand navbar-brand-custom" href="/">
              <img
                src="images/hlogo.png"
                style={{
                  marginLeft: "80px",
                  width: "320px",
                  height: "75px"
                }}
              />
            </a>
          </div>
          <ul class="nav navbar-nav navbar-right">
            <div id="active-user-badge">
            {dropDown}
            </div>
          </ul>
        </div>
      </nav>
    );
  }
}

export default Navbar;