import React, { Component } from "react";
import "../../App.css";
import axios from "axios";
import Navbar4 from "../Navbar/navbar5";
import cookie from "react-cookies";
import Footer from "../Footer/footer";
import {url} from "../../BaseUrl";

class SubmitHackathon extends Component {
  constructor(props) {
    super(props);
    this.state = {
      properties: [
        { Name: "Sarang", requestsPending: 2 },
        { Name: "Darryl", requestsPending: 4 }
      ],
      hackathon: [
        { name: "Hack1", requestsPending: 2 },
        { name: "Hack2", requestsPending: 4 }
      ],
      hackathons: [],
      authFlag: false,
      messageFlag: false,
      imageView: [],
      url: "",
      leaderFlag: false,
      hackName: "",
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
  organisationChangeHandler = e => {
    this.setState({
      organisation: e.target.dataset.value
    });
    console.log("Successful test - ", this.state.displayprop);
  };

  URLChangeHandler = e => {
    this.setState({
      url: e.target.value
    });
  };

  submitLeaderboard = name => {
    this.setState({
      leaderFlag: true,
      hackName: name
    });
  };

  componentDidMount() {
    const data = {
      screenName: localStorage.getItem("screenName")
    };
    axios
      .get(`${url}/hackathon/registered/${data.screenName}`)
      .then(response => {
        console.log(response);
        //update the state with the response data
        this.setState({
          authFlag: true,
          hackathons: response.data.body
        });
        console.log("Search :", this.state.hackathons);
      });
  }

  onCodeSubmit(id) {
    const data = {
      id: id,
      url: this.state.url,
      screenName: localStorage.getItem("screenName")
    };
    console.log(data);
    axios
      .post(
        `${url}/hackathon/${data.id}/codesubmission/${
          data.screenName
        }?code_url=${data.url}`
      )
      .then(response => {
        console.log(response);
        //update the state with the response data
        this.setState({
          message: response.data.body
        });
      });
  }
  render() {
    let foot = <Footer data={this.props.data} />;
    let navbar = <Navbar4 data={this.props.data} />;

    if (this.state.leaderFlag) {
      this.props.history.push({
        pathname: "/resultPage",
        state: {
          hackName: this.state.hackName
        }
      });
    }
    let details = this.state.hackathons.map(org => {
      return (
        <div class="displaypropinfo container-fluid" style={{ minHeight: "115px" }}>
          <div class="col-sm-8">
            <div class="headline">
              <h3 class="hit-headline">
                <p style={{ fontSize: "18px", color: "red" }}>
                  {this.state.message}
                </p>
                <div>
                  {org.id}: {org.name}
                  <button
                    class="btn btn-warning btn-md"
                    style={{ marginLeft: "20px" }}
                    data-toggle="modal"
                    data-target="#myModal"
                  >
                    Submit Your Code
                  </button>
                  <button
                    class="btn btn-warning btn-md"
                    style={{ marginLeft: "20px" }}
                    onClick={id => {
                      this.submitLeaderboard(org.name);
                    }}
                  >
                    View Top Teams
                  </button>
                </div>
                <div class="modal fade" id="myModal" role="dialog">
                  <div class="modal-dialog">
                    <div class="modal-content">
                      <div class="modal-header">
                        <button
                          type="button"
                          class="close"
                          data-dismiss="modal"
                        >
                          &times;
                        </button>
                        <h4 class="modal-title">Submit your Code</h4>
                      </div>
                      <div class="modal-body">
                        <div style={{ overflowX: "auto" }}>
                          <div class="form-group">
                            <input
                              onChange={this.URLChangeHandler}
                              type="text"
                              class="form-control"
                              name="type"
                              placeholder="URL of your work"
                            />
                            <button
                              class="btn btn-primary btn-md"
                              style={{
                                textAlign: "center",
                                marginLeft: "200px",
                                marginTop: "10px"
                              }}
                              data-dismiss="modal"
                              onClick={id => {
                                this.onCodeSubmit(org.id);
                              }}
                            >
                              Submit Your Code
                            </button>
                          </div>
                        </div>
                      </div>
                      <div class="modal-footer">
                        <button
                          type="button"
                          class="btn btn-default"
                          data-dismiss="modal"
                        >
                          Close
                        </button>
                      </div>
                    </div>
                  </div>
                </div>
              </h3>
            </div>
          </div>
        </div>
      );
    });
    let redirectVar = null;
    if (this.state.displayprop !== "") {
      this.props.history.push({
        pathname: "/property",
        state: {
          displayprop: this.state.displayprop
        }
      });
    }
    if (this.state.properties !== "") {
      return (
        <div>
          {redirectVar}
          <div class="main-div1" style={{ backgroundColor: "#F7F7F8" }} >
            {navbar}
            <div>
              <h2 style={{ marginLeft: "40%" }}>Submit Your Hackathons</h2>
              <h4 style={{ marginLeft: "35%" }}>
                You can submit Hackathons you have registered for here
              </h4>
            </div>
            {details}
            {/*Display the Table row based on data received*/}
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
export default SubmitHackathon;