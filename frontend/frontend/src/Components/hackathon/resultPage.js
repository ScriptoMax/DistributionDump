import React, { Component } from "react";
import "../../App.css";
import axios from "axios";
import Navbar4 from "../Navbar/navbar5";
import cookie from "react-cookies";
import Footer from "../Footer/footer";
import {url} from "../../BaseUrl";

class ResultPage extends Component {
  constructor(props) {
    super(props);
    this.state = {
      teams: [],
      authFlag: false,
      imageView: [],
      displayprop: []
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
      hackName: this.props.location.state.hackName
    };

    console.log("props: ", this.props.location.state.hackName);
    axios
      .get(`${url}/teamreport/${data.hackName}`)
      .then(response => {
        console.log(
          "The return code for teamreport get request is: ",
          response.statusCodeValue
        );
        this.setState({
          teams: response.data.body
        });
      });
  }

  render() {
    let foot = <Footer data={this.props.data} />;
    console.log(this.props.location);
    let navbar = <Navbar4 data={this.props.data} />;
    let members = null;
    let teamInfo = this.state.teams
      .map(team => {
        members = team.teamMembers.map(m => {
          return <div>{m}</div>;
        });
        return (
          <div class="displaypropinfo container-fluid" style={{ marginBottom: "20px" }}>
            <div class="col-sm-8">
              <div class="headline">
                <h3 class="hit-headline">
                  <div name="displayprop" style={{ marginRight: "5px" }}>
                    <span style={{ color: "black" }}>Team Name</span><br/>
                    {team.teamName}
                  </div>
                  <br />
                  <div name="displayprop" style={{ marginRight: "5px" }}>
                    <span style={{ color: "black" }}>Team Members</span><br/>
                    {members}{" "}
                  </div>
                  <br />
                  <div name="displayprop" style={{ marginRight: "5px" }}>
                    <span style={{ color: "black" }}>Score</span><br/>
                    {team.score}
                  </div>
                </h3>
              </div>
            </div>
          </div>
        );
      })
      .slice(0, 3);

    let teamInfo1 = this.state.teams
      .map(team => {
        let members = team.teamMembers.map(m => {
          return (
            <div>
              {m}
              <br />
            </div>
          );
        });
        return (
          <div class="displaypropinfo container-fluid">
            <div class="col-sm-8">
              <div class="headline">
                <h3 class="hit-headline">
                  <div name="displayprop" style={{ marginRight: "5px" }}>
                    {team.teamName}
                  </div>
                  <br />
                  <div name="displayprop" style={{ marginRight: "5px" }}>
                    {members}
                  </div>
                  <br />
                  <div
                    name="displayprop"
                    style={{ marginRight: "5px" }}
                    data-value={team.score}
                  >
                    {team.score}
                  </div>
                </h3>
              </div>
            </div>
          </div>
        );
      })
      .slice(3, 100);

    let redirectVar = null;
    if (this.state.displayprop != "") {
      this.props.history.push({
        pathname: "/property",
        state: {
          displayprop: this.state.displayprop
        }
      });
    }

    if (this.state.properties != "") {
      return (
        <div>
          {redirectVar}
          <div class="main-div1" style={{ backgroundColor: "#F7F7F8" }}>
            {navbar}
            <div style={{ textAlign: "center", fontSize: "30px" }}>
              Top 3 Teams
            </div>
            {/*Display the Table row based on data received*/}
            {teamInfo}
            <div style={{ textAlign: "center", fontSize: "30px" }}>
              Other Team Results
            </div>
            {teamInfo1}
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
export default ResultPage;