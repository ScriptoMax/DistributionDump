import React, { Component } from "react";
import { Route, withRouter, Link } from "react-router-dom";
import "../../App.css";
import axios from "axios";
import Navbar4 from "../Navbar/navbar5";
import cookie from "react-cookies";
import Footer from "../Footer/footer";
import {url} from "../../BaseUrl";

class GradeHackathon extends Component {
  constructor(props) {
    super(props);
    this.state = {
      properties: [{ Name: "Hackathon 1" }, { Name: "Hackathon 2" }],
      hackathons: [],
      authFlag: false,
      imageView: [],
      displayprop: ""
    };
    this.propertyChangeHandler = this.propertyChangeHandler.bind(this);
  }

  componentWillMount() {
    this.setState({
      authFlag: false
    });
  }
  propertyChangeHandler = e => {
    this.setState({
      displayprop: e.target.dataset.value
    });
    console.log("Successful test - ", this.state.displayprop);
  };
  componentDidMount() {
    const data = {
      screenName: window.localStorage.getItem("screenName")
    };
    axios
      .get(`${url}/hackathon/grade/${data.screenName}`)
      .then(response => {
        console.log(response);
        //update the state with the response data
        this.setState({
          authFlag: true,
          hackathons: response.data.body
        });
        console.log("Search :", this.state.properties);
        console.log("No of results :", this.state.properties.length);
      });
  }
  render() {
    let foot = <Footer data={this.props.data} />;
    console.log(this.props.location);
    let navbar = <Navbar4 data={this.props.data} />;
    let details = this.state.hackathons.map(hack => {
      return (
        <div class="hack-ctrl-list">
        <div class="displaypropinfo container-fluid">
          <div class="col-sm-8">
            <div class="headline">
              <h3 class="hit-headline">
                <div>
                  {hack.id}:{hack.name}
                </div>
              </h3>
              <div class="btn btn-warning" on>
                <span
                  onClick={this.propertyChangeHandler}
                  name="displayprop"
                  data-value={hack.name}
                  style={{ color: "white" }}
                >
                  Grade
                </span>
              </div>
            </div>
          </div>
          </div>
        </div>
      );
    });

    let redirectVar = null;
    if (this.state.displayprop !== "") {
      this.props.history.push({
        pathname: "/evaluate",
        state: {
          displayprop: this.state.displayprop
        }
      });
    }

    if (this.state.properties !== "") {
      return (
        <div>
          {redirectVar}
          <div class="main-div1" style={{ backgroundColor: "#F7F7F8" }}>
            {navbar}
            <div>
              <h2 style={{ marginLeft: "42%" }}>Grade Hackathons</h2>
              <h4 style={{ marginLeft: "35%" }}>
                Here you can grade hackathons you are a judge for
              </h4>
            </div>
            {details}
          </div>
          {foot}
        </div>
      );
    } else {
      return (
        <div>
          <div class="main-div1">
            <h2>No results for this query</h2>
          </div>
        </div>
      );
    }
  }
}
export default GradeHackathon;