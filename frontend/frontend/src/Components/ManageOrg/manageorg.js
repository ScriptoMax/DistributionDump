import React, { Component } from "react";
import "../../App.css";
import axios from "axios";
import Navbar4 from "../Navbar/navbar5";
import cookie from "react-cookies";
import Footer from "../Footer/footer";
import {url} from "../../BaseUrl";

class ManageOrganisation extends Component {
  constructor(props) {
    super(props);
    this.state = {
      requestedMembers: [],
      organisation: "",
      members: [],
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

    console.log(this.props.location.state.organisation);
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
          members: response.data.body.members,
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
            authFlag: true
          });
          window.location.reload(1);
        } else {
          this.setState({
            authFlag: false,
            message: "Organisation Already Exists"
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
              <h3 class="hit-headline" style={{ paddingTop: "5px" }}>
                <div>
                  {member.screenName}
                </div>
              </h3>
              <button
                onClick={name => {
                  this.submitApprove(member.screenName);
                }}
                class="btn btn-success"
                >
                Accept
              </button>
              <button
                onClick={name => {
                  this.submitDelete(member.screenName);
                }}
                class="btn btn-secondary" style={{ marginLeft: "10px", color: "black" }}
              >
                Decline
              </button>
            </div>
          </div>
        </div>
      );
    });

    let details1 = this.state.members.map(member => {

      return (
        <div class="displaypropinfo container-fluid">
          <div class="col-sm-8">
            <div class="headline">
              <h3 class="hit-headline">
                <div>
                  {member.screenName}
                  <div><button
                      onClick={name => {
                        this.submitDelete(member.screenName);
                      }}
                      className="btn btn-danger" style={{ color: "white", marginTop: "10px" }}
                  >
                    Remove
                  </button>
                   </div>
                </div>
              </h3>
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
          <div class="main-div1">
            {navbar}
            <div>
              <h2 style={{ marginLeft: "36%" }}>Manage your Organisation</h2>
              <h4 style={{ marginLeft: "37%" }}>
                You can accept or decline invitations here
              </h4>
              <p style={{ fontSize: "18px" }}>{this.state.message}</p>
            </div>
            <div style={{ paddingBottom: "20px" }}>
            {/*Display the Table row based on data received*/}
            <h3 style={{ marginLeft: "2%" }}>Members Pending Approval</h3>
            {details}
            <h3 style={{ marginLeft: "2%", marginTop: "20px", marginBottom: 0, height: "20px", paddingBottom: "10px" }}>
              Approved members
            </h3>
            {details1}
            </div>
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

export default ManageOrganisation;