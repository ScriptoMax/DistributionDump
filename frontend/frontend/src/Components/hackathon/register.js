import React, { Component } from "react";
import "../../App.css";
import axios from "axios";
import cookie from "react-cookies";
import NavbarOwner from "../Navbar/navbar5";
import { Redirect } from "react-router";
import {url} from "../../BaseUrl";

class registerhackathon extends Component {
  //call the constructor method
  constructor(props) {
    //Call the constrictor of Super class i.e The Component
    super(props);
    //maintain the state required for this component
    this.state = {
      name: "",
      teamName: "",
      authFlag: false,
      rolevalue: "",
      rolevalue1: "",
      maxTeam: 0
    };
    //Bind the handlers to this class
    this.teamNameChangeHandler = this.teamNameChangeHandler.bind(this);
  }
  //Call the Will Mount to set the auth Flag to false
  componentWillMount() {}
  //username change handler to update state variable with the text entered by the user
  teamNameChangeHandler = e => {
    this.setState({
      teamName: e.target.value
    });
  };

  teamLeadRoleChangeHandler = e => {
    this.setState({
      leadRole: e.target.value
    });
  };

  ChangeHandler = e => {
    this.setState({
      [e.target.name]: e.target.value
    });
  };

  componentDidMount() {
    const data = {
      hackid: this.props.location.state.displayprop
    };

    this.setState({
      name: localStorage.getItem("screenName"),
      hackid: data.hackid
    });

    axios
        .get(`${url}/hackathon/${data.hackid}`)
        .then(response => {
          console.log(response);
          //update the state with the response data
          this.setState({
            maxTeam: response.data.body.maxTeamSize
          });

          console.log("No of results :", this.state.maxTeam);
        });
  }

  submitProperty = e => {
    const data = {
      hackid: this.props.location.state.displayprop,
      name: localStorage.getItem("screenName")
    };
    console.log(data.name);
    console.log(this.state);
    axios
        .post(
            `${url}/hackathon/${
                data.hackid
            }/register/?leaderScreenName=${data.name}&leaderRole=${
                this.state.leadRole
            }&teamName=${this.state.teamName}&member2ScreenName=${
                this.state.Member1name
            }&member2Role=${this.state.Member1role}&member3ScreenName=${
                this.state.Member2name
            }&member3Role=${this.state.Member2role}&member4ScreenName=${
                this.state.Member3name
            }&member4Role=${this.state.Member3role}`
        )
        .then(response => {
          console.log(response);
          //update the state with the response data
          if (response.data.statusCodeValue === 200) {
            this.setState({
              authFlag: true
            },()=>{
              this.props.history.push("/submitHackathon")
            });
          } else {
            this.setState({
              message: response.data.body
            });
          }
        });
  };

  handleSelect = e => {
    console.log(e);
    this.setState({ rolevalue: e.target.value });
  };

  handleSelect1 = e => {
    console.log(e);
    this.setState({ rolevalue1: e.target.value });
  };

  createTable = () => {
    let table = [];
    console.log(this.state.maxTeam);

    const userOptions = [
      { uservalue: 'duglas', label: 'duglas' },
      { uservalue: 'amdal', label: 'amdal' },
      { uservalue: 'faradey', label: 'faradey' },
      { uservalue: 'shiffrin', label: 'shiffrin' },
      { uservalue: 'parker', label: 'parker' },
      { uservalue: 'falcon', label: 'falcon' },
      { uservalue: 'adizes', label: 'adizes' },
      { uservalue: 'morris', label: 'morris' }
    ];

    // Outer loop to create parent
    for (let i = 1; i < this.state.maxTeam; i++) {
      table.push(
      <div className="form-group hack-register">
        <select defaultValue={"Team member #" + (i + 1)} name={"Member" + i + "name"} value={this.state.value}
                onChange={this.ChangeHandler} style={{marginTop: "30px"}}>
          <option hidden value="">{"Team member #" + (i + 1)}</option>
          <option className="hack-reg-options" value={userOptions[0].uservalue}>{userOptions[0].uservalue}</option>
          <option className="hack-reg-options" value={userOptions[1].uservalue}>{userOptions[1].uservalue}</option>
          <option className="hack-reg-options" value={userOptions[2].uservalue}>{userOptions[2].uservalue}</option>
          <option className="hack-reg-options" value={userOptions[3].uservalue}>{userOptions[3].uservalue}</option>
          <option className="hack-reg-options" value={userOptions[4].uservalue}>{userOptions[4].uservalue}</option>
          <option className="hack-reg-options" value={userOptions[5].uservalue}>{userOptions[5].uservalue}</option>
          <option className="hack-reg-options" value={userOptions[6].uservalue}>{userOptions[6].uservalue}</option>
          <option className="hack-reg-options" value={userOptions[7].uservalue}>{userOptions[7].uservalue}</option>
        </select>

            <label style={{ fontSize: "15px" }}>
              Assign Role: <br />
            </label>
            <div style={{ fontSize: "15px" }}>
              <select name={"Member" + i + "role"} onChange={this.ChangeHandler}>
                <option value="select">Select</option>
                <option value="productmanager">Product Manager</option>
                <option value="engineer">Engineer</option>
                <option value="fullstack">Full Stack</option>
                <option value="designer">Designer</option>
                <option value="other">Other</option>
              </select>
            </div>
          </div>
      );
    }
    return table;
  };
  render() {
    let navbar = <NavbarOwner data={this.props.data} />;
    const { description, selectedFile } = this.state;
    //redirect based on successful login
    let redirectVar = null;

    return (
        <div>
          {redirectVar}
          {navbar}
          <div class="container">
            <div class="panel login-form">
              <div class="main-div-login">
                <div class="panel">
                  <h2>Registration</h2>
                  <p>Please enter your team details</p>
                  <p style={{ fontSize: "18px", color: "red" }}>
                    {this.state.message}
                  </p>
                </div>

                <div class="form-group">
                  <input
                      onChange={this.teamNameChangeHandler}
                      type="text"
                      class="form-control"
                      name="descriptionprop"
                      placeholder="Team Name"
                  />
                </div>
                <div class="form-group">
                  <input
                      onChange={this.nameChangeHandler}
                      type="text"
                      class="form-control"
                      name="name"
                      placeholder="Leader Name"
                      value={this.state.name}
                  />

                  <label style={{ fontSize: "15px" }}>
                    Assign Role: <br />
                  </label>
                  <div style={{ fontSize: "15px" }}>
                    <select
                        value={this.state.rolevalue}
                        onChange={this.handleSelect}
                    >
                      <option value="select">Select</option>
                      <option value="productmanager">Product Manager</option>
                      <option value="engineer">Engineer</option>
                      <option value="fullstack">Full Stack</option>
                      <option value="designer">Designer</option>
                      <option value="other">Other</option>
                    </select>
                  </div>

                </div>
                {this.createTable()}
                <button
                    onClick={this.submitProperty}
                    class="btn btn-warning btn-lg"
                    style={{ marginLeft: "30px" }}
                >
                  Register
                </button>
              </div>
            </div>
          </div>
        </div>
    );
  }
}
//export Login Component
export default registerhackathon;