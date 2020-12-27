import React, { Component } from "react";
import "../../App.css";
import axios from "axios";
import Navbar4 from "../Navbar/navbar5";
import cookie from "react-cookies";
import Footer from "../Footer/footer";
import {url} from "../../BaseUrl";

class HackReport extends Component {
  constructor(props) {
    super(props);
    this.state = {
      teams: {},
      authFlag: false,
      imageView: [],
      profit: "",
      displayprop: "",
      hackName: ""
    };
  }

  componentWillMount() {
    this.setState({
      authFlag: false
    });
  }
  componentDidMount() {
    const data = {
      hackName: this.props.location.state.hackName3
    };

    console.log("props: ", this.props.location.state.hackName3);
    axios
      .get(`${url}/payment/expensereport/${data.hackName}`)
      .then(response => {
        //update the state with the response data
        this.setState({
          authFlag: true,
          teams: response.data.body
        });
        console.log("Search :", this.state.teams);
      });
  }

  render() {
    let foot = <Footer data={this.props.data} />;
    console.log(this.props.location);
    let navbar = <Navbar4 data={this.props.data} />;
    let details = (
      <div class="displaypropinfo container-fluid">
        <div class="headline col-sm-3" style={{ textAlign: "center" }}>
          Total Paid Registration Fees
          <div class="headline">
            <h3 class="hit-headline">
              <div name="displayprop" style={{ marginRight: "5px" }}>
                {this.state.teams.paidTotal}
              </div>
            </h3>
          </div>
        </div>
        <div class="headline col-sm-3" style={{ textAlign: "center" }}>
          Total Unpaid Registration Fees
          <div class="headline">
            <h3 class="hit-headline">
              <div name="displayprop" style={{ marginRight: "5px" }}>
                {this.state.teams.notPaidTotal}
              </div>
            </h3>
          </div>
        </div>
        <div class="headline col-sm-3" style={{ textAlign: "center" }}>
          Sponsor Revenue
          <div class="headline">
            <h3 class="hit-headline">
              <div name="displayprop" style={{ marginRight: "5px" }}>
                {this.state.teams.numberOfSponsors * 1000}
              </div>
            </h3>
          </div>
        </div>
        <div class="headline col-sm-3" style={{ textAlign: "center" }}>
          PROFIT
          <div class="headline">
            <h3 class="hit-headline">
              <div name="displayprop" style={{ marginRight: "5px" }}>
                {this.state.teams.paidTotal +
                  this.state.teams.numberOfSponsors * 1000}
              </div>
            </h3>
          </div>
        </div>
      </div>
    );

    let redirectVar = null;
    if (this.state.properties !== "") {
      return (
        <div>
          {redirectVar}
          <div class="main-div1" style={{ backgroundColor: "#F7F7F8" }}>
            {navbar}
            <div style={{ textAlign: "center", fontSize: "30px" }}>
              Earning Report
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
export default HackReport;