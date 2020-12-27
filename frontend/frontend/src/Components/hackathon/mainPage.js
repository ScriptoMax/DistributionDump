import React, { Component } from "react";
import "../../App.css";
import axios from "axios";
import Navbar4 from "../Navbar/navbar5";
import cookie from "react-cookies";
import Footer from "../Footer/footer";
import {url} from "../../BaseUrl";

class MainPage extends Component {
  constructor(props) {
    super(props);
    this.state = {
      properties: [{ Name: "Hackathon 1" }, { Name: "Hackathon 2" }],
      hackathons: [],
      authFlag: false,
      imageView: [],
      displayprop: "",
      leaderFlag: false,
      hackName: "",
      hackName2: "",
      payFlag: false
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
      owner: localStorage.getItem("screenName")
    };
    axios
      .get(`${url}/hackathon/names/${data.owner}`)
      .then(response => {
        //update the state with the response data
        console.log(response);
        this.setState({
          authFlag: true,
          hackathons: response.data.body
        });
        console.log("Search :", this.state.properties);
        console.log("No of results :", this.state.properties.length);
      });
  }

  submitOpen(id) {
    const data = id;
    console.log(data);
    //set the with credentials to true
    axios.defaults.withCredentials = true;
    //make a post request with the user data
    axios
      .post(`${url}/hackathon/${data}/opened`)
      .then(response => {
        console.log("Status Code : ", response);
        this.setState({
          authFlag: true,
          message: response.data.body
        });
      });
  }

  submitFinalize(id) {
    const data = id;
    console.log(data);
    //set the with credentials to true
    axios.defaults.withCredentials = true;
    //make a post request with the user data
    axios
      .post(`${url}/hackathon/${data}/finalized`)
      .then(response => {
        console.log("Status Code : ", response);
        this.setState({
          authFlag: true,
          message: response.data.body
        });
      });
  }

  submitClose(id) {
    const data = id;
    console.log(data);
    //set the with credentials to true
    axios.defaults.withCredentials = true;
    //make a post request with the user data
    axios
      .post(`${url}/hackathon/${data}/closed`)
      .then(response => {
        console.log("Status Code : ", response);
        this.setState({
          authFlag: true,
          message: response.data.body
        });
      });
  }

  submitLeaderboard = name => {
    this.setState({
      leaderFlag: true,
      hackName: name
    });
  };

  submitPaymentReport = name => {
    this.setState({
      payFlag: true,
      hackName2: name
    });
  };

  submitEarningReport = name => {
    this.setState({
      earningFlag: true,
      hackName3: name
    });
  };

  render() {
    let foot = <Footer data={this.props.data} />;
    console.log(this.props.location);
    let navbar = <Navbar4 data={this.props.data} />;
    let details = this.state.hackathons.map(hackathon => {
      if (this.state.leaderFlag) {
        this.props.history.push({
          pathname: "/resultPage",
          state: {
            hackName: this.state.hackName
          }
        });
      }

      if (this.state.payFlag) {
        this.props.history.push({
          pathname: "/paymentStatus",
          state: {
            hackName2: this.state.hackName2
          }
        });
      }

      if (this.state.earningFlag) {
        this.props.history.push({
          pathname: "/hackreport",
          state: {
            hackName3: this.state.hackName3
          }
        });
      }

      return (
        <div class="displaypropinfo container-fluid">
          <div class="col-sm-8">
            <div class="headline">
              <h3 class="hit-headline">
                <div>
                  {hackathon.id}:{hackathon.name}
                </div>
              </h3>

              <button
                onClick={id => {
                  this.submitOpen(hackathon.id);
                }}
                style={{ marginLeft: "10px" }}
                class="btn btn-warning"
              >
                Open
              </button>
              <button
                onClick={id => {
                  this.submitClose(hackathon.id);
                }}
                style={{ marginLeft: "10px" }}
                class="btn btn-danger"
              >
                Close
              </button>
              <button
                onClick={id => {
                  this.submitFinalize(hackathon.id);
                }}
                style={{ marginLeft: "10px" }}
                class="btn btn-primary"
              >
                Finalize
              </button>
            </div>
          </div>
          <div class="col-sm-4 hack-card-btn">
            <button
              onClick={id => {
                this.submitLeaderboard(hackathon.name);
              }}
              style={{ float: "right", width: "180px" }}
              class="btn btn-primary"
            >
              Top Teams
            </button>
          </div>
          <div class="col-sm-4">
            <button
              onClick={id => {
                this.submitPaymentReport(hackathon.name);
              }}
              style={{ float: "right", width: "180px", marginTop: "5px" }}
              class="btn btn-warning"
            >
              Payment Report
            </button>
          </div>
          <div class="col-sm-4">
            <button
              onClick={id => {
                this.submitEarningReport(hackathon.name);
              }}
              style={{ float: "right", width: "180px", marginTop: "5px" }}
              class="btn btn-success"
            >
              Earning Report
            </button>
          </div>
        </div>
      );
    });
    let redirectVar = null;
    if (this.state.properties !== "") {
      return (
        <div>
          {redirectVar}
          <div class="main-div1" style={{ backgroundColor: "#F7F7F8" }}>
            {navbar}
            <div>
              <h2 style={{ marginLeft: "42%" }}>Manage Your Hackathons</h2>
              <h4 style={{ marginLeft: "30%" }}>
                Here you can open, close and finalize any hackathons you've created as administrator
              </h4>
            </div>
            <div
              style={{
                marginTop: "30px",
                marginLeft: "40px",
                fontSize: "15px",
                width: "200px",
                backgroundColor: "blue",
                color: "white"
              }}
            >
              {this.state.message}
            </div>
            <div id="hack-ctrl-btns">
            {/*Display the Table row based on data received*/}
            {details}
            </div>
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
export default MainPage;