import React, { Component } from "react";
import { Route, withRouter, Link } from "react-router-dom";
import "../../App.css";
import axios from "axios";
import Navbar4 from "../Navbar/navbar5";
import cookie from "react-cookies";
import Footer from "../Footer/footer";
import {url} from "../../BaseUrl";

class ListAllHackathon extends Component {
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
      .get(`${url}/hackathon/viewall/${data.screenName}`)
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
          <div className="hack-ctrl-list">
        <div class="displaypropinfo container-fluid">
          <div class="col-sm-8">
            <div class="headline">
              <h3>
                <div style={{ paddingTop: "20px" }}>
                  {hack.id}:{hack.name}
                </div>
              </h3>
              <div class="btn btn-primary" on>
                <span
                  onClick={this.propertyChangeHandler}
                  name="displayprop"
                  data-value={hack.id}
                  style={{ color: "white" }}
                >
                  Register
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
        pathname: "/registerhackathon",
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
              <h2 style={{ marginLeft: "42%" }}>View Hackathons</h2>
              <h4 style={{ marginLeft: "26%" }}>
                Here you can register for joining hackathons where you are not judge or administrator
              </h4>
              <p style={{ fontSize: "18px" }}>{this.state.message}</p>
            </div>
            {/*Display the Table row based on data received*/}
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
export default ListAllHackathon;