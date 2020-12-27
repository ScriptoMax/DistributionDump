import React, { Component } from "react";
import "../../App.css";
import Navbar from "../Navbar/navbar";
import Footer from "../Footer/footerhome";
import axios from "axios";
import cookie from "react-cookies";
import { Redirect } from "react-router";
import { Link } from "react-router-dom";

class HomePage extends Component {
  constructor(props) {
    //Call the constrictor of Super class i.e The Component
    super(props);
    //maintain the state required for this component
    this.state = {
      authFlag: false,
      error: false,
      displayprop: ""
    };
  }

  //Call the Will Mount to set the auth Flag to false
  componentWillMount() {}

  componentDidMount() {}

  render() {
    if (this.state.authFlag) {
      this.props.history.push({
        pathname: "/profile"
      });
    }
    let redirectVar = null;
    let errorMessage = null;
    if (this.state.error) {
      errorMessage = (
        <div
          style={{
            fontSize: "14px",
            backgroundColor: "#ed605a",
            lineHeight: "20px",
            color: "white",
            textAlign: "center",
            padding: "10px"
          }}
        >
          <p>No results found!.</p>
        </div>
      );
    }
    console.log(this.props.location.state);
    let foot = <Footer data={this.props.data} />;
    let navbar = <Navbar data={this.props.data} />;

    return (
      <React.Fragment>
        <header id="main" className="page-landing">
          {redirectVar}
          {navbar}
          <div className="MainContent">
              <div className="SubContent">
                <ul className="Font_List">
                  <li className="Font_Item">
                    <strong className="Font_Title">
                      Your hackathon experience starts here
                    </strong>
                    <br/>
                    <span className="Font_subTitle">
                  Choose a hackathon from world's leading companies
                </span>
                  </li>
                  <li className="Font_Item">
                    <strong className="Font_Title">
                      Register and join with confidence
                    </strong>
                    <br/>
                    <span className="Font_subTitle">
                  Secure payments, peace of mind
                </span>
                  </li>
                  <li className="Font_Item">
                    <strong className="Font_Title">Your code is your best feature</strong>
                    <br/>
                    <span className="Font_subTitle">
                  More clarity, more challenges, no compromises
                </span>
                  </li>
                </ul>
              </div>
          </div>
          <div class="container">
            <div class="row" style={{ font: "white" }} />
          </div>
        </header>
        <div>{foot}</div>
      </React.Fragment>
    );
  }
}

export default HomePage;