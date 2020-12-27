import React, { Component } from "react";
import "../../App.css";
import axios from "axios";
import Navbar4 from "../Navbar/navbar2";
import cookie from "react-cookies";
import Footer from "../Footer/footer";
import {url} from "../../BaseUrl";

class FinalMembers extends Component {
  constructor(props) {
    super(props);
    this.state = {
      requestedMembers: [],
      organisation: "",
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
      screenName: localStorage.getItem("screenName")
    };
    axios
      .get(
        `${url}/organisation/${
          this.props.location.state.organisation
        }?screenName=${data.screenName}`
      )
      .then(response => {
        console.log(response.data.body);
        //update the state with the response data
        this.setState({
          authFlag: true,
          requestedMembers: response.data.body.requestedMembers,
          organisation: response.data.body.organisationName
        });
        console.log("Search :", this.state.requestedMembers);
        console.log("No of results :", this.state.organisation);
      });
  }

  submitApprove(name) {
    const data = name;
    console.log(data);
    //set the with credentials to true
    axios.defaults.withCredentials = true;
    //make a post request with the user data
    axios
      .post(
        `${url}/organisation/${
          this.state.organisation
        }/approve/${data}`
      )
      .then(response => {
        console.log("Status Code : ", response);
        if (response.data.statusCodeValue === 200) {
          this.setState({
            authFlag: true,
            message:
              "Congratulations! You have successfully listed your organisation"
          });
          window.location.reload(1);
        } else {
          this.setState({
            authFlag: false,
            message: "Organisation Already Exist "
          });
        }
      });
  }

  submitDelete(name) {
    const data = name;

    console.log(data);
    //set the with credentials to true
    axios.defaults.withCredentials = true;
    //make a post request with the user data
    axios
      .post(
        `${url}/organisation/${
          this.state.organisation
        }/leave/${data}`
      )
      .then(response => {
        console.log("Status Code : ", response);
        if (response.data.statusCodeValue === 200) {
          this.setState({
            authFlag: true,
            message:
              "Congratulations! You have successfully listed your organisation"
          });
          window.location.reload(1);
        } else {
          this.setState({
            authFlag: false,
            message: "Organisation Already Exist "
          });
        }
      });
  }

  render() {
    let foot = <Footer data={this.props.data} />;
    let navbar = <Navbar4 data={this.props.data} />;
    let details = this.state.requestedMembers.map(member => {

      return (
        <div class="displaypropinfo container-fluid">
          <div class="col-sm-8">
            <div class="headline">
              <h3 class="hit-headline">
                <div>
                  {member.screenName}
                </div>
              </h3>
              <button
                onClick={name => {
                  this.submitApprove(member.screenName);
                }}
                class="btn btn-primary"
              >
                Accept
              </button>
            </div>
          </div>
        </div>
      );
    });
    let redirectVar = null;

    if (this.state.requestedMembers !== "") {
      return (
        <div>
          {redirectVar}
          <div class="main-div1" style={{ backgroundColor: "#F7F7F8" }}>
            {navbar}
            <div>
              <h2 style={{ marginLeft: "42%" }}>Manage your Organisation</h2>
              <h4 style={{ marginLeft: "38%" }}>
                You can accept or decline invitations here
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
            <h2>No pending requests currently</h2>
          </div>
        </div>
      );
    }
  }
}

export default FinalMembers;