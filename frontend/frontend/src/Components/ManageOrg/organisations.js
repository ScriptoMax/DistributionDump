import React, { Component } from "react";
import "../../App.css";
import axios from "axios";
import Navbar4 from "../Navbar/navbar5";
import cookie from "react-cookies";
import Footer from "../Footer/footer";
import {url} from "../../BaseUrl";

class Organisations extends Component {
  constructor(props) {
    super(props);
    this.state = {
      organisation: "",
      organisations: [],
      authFlag: false,
      imageView: [],
      displayprop: ""
    };
    this.organisationChangeHandler = this.organisationChangeHandler.bind(this);
  }

  componentWillMount() {
    this.setState({
      authFlag: false
    });
  }

  organisationChangeHandler = e => {
    console.log(e.target.dataset.value);
    this.setState({
      organisation: e.target.dataset.value
    });
  };

  componentDidMount() {
    const data = {
      screenName: localStorage.getItem("screenName")
    };
    axios
      .get(`${url}/organisation/owner/${data.screenName}`)
      .then(response => {
        console.log(response);
        //update the state with the response data
        this.setState({
          authFlag: true,
          organisations: response.data.body
        });
        console.log("Search :", this.state.organisations);
        });
  }

  submitOrganisation = e => {
    e.preventDefault();
    const data = {
      name: this.state.name,
      owner: this.state.owner,
      description: this.state.description,
      address: this.state.address
    };

    console.log(data);
    //set the with credentials to true
    axios.defaults.withCredentials = true;
    //make a post request with the user data
    axios
      .post(
        `${url}/organisation?name=${
          this.state.name
        }&ownerName=${this.state.owner}&description=${this.state.description}`
      )
      .then(response => {
        console.log("Status Code : ", response);
        if (response.data.statusCodeValue === 200) {
          this.setState({
            authFlag: true,
            message:
              "Congratulations! You have successfully listed your organisation"
          });
        } else {
          this.setState({
            authFlag: false,
            message: "Organisation Already Exists"
          });
        }
      });
  };

  render() {
    let foot = <Footer data={this.props.data} />;
    let navbar = <Navbar4 data={this.props.data} />;
    let details = this.state.organisations.map(org => {

      return (
        <div class="displaypropinfo container-fluid">
          <div class="col-sm-8">
            <div class="headline">
              <h3 class="hit-headline" style={{ marginBottom: "5px" }}>
                <div
                  onClick={this.organisationChangeHandler}
                  name="organisation"
                  data-value={org.organisationName}
                >
                  {org.organisationName}
                </div>
              </h3>
              <p>Requests Pending: {org.requestedMembers.length}</p>
              <p>Members: {org.members.length}</p>
            </div>
          </div>
        </div>
      );
    });
    let redirectVar = null;
    if (this.state.organisation !== "") {
      this.props.history.push({
        pathname: "/memberlist",
        state: {
          organisation: this.state.organisation
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
              <h2 style={{ marginLeft: "36%" }}>Manage your Organisations</h2>
              <h4 style={{ marginLeft: "30%" }}>
                Here you can view all organisations you've created as administrator
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

export default Organisations;