import React, { Component } from "react";
import "../../App.css";
import axios from "axios";
import Navbar4 from "../Navbar/navbar5";
import cookie from "react-cookies";
import Footer from "../Footer/footer";
import {url} from "../../BaseUrl";

class Hackeval extends Component {
  constructor(props) {
    super(props);
    this.state = {
      properties: [
        { Name: "Sarang", requestsPending: 2 },
        { Name: "Darryl", requestsPending: 4 }
      ],
      hackathons: [],
      authFlag: false,
      messageFlag: false,
      imageView: [],
      grade: "",
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

  gradeChangeHandler = e => {
    this.setState({
      grade: e.target.value
    });
  };

  componentDidMount() {
    const data = {
      hackname: this.props.location.state.displayprop
    };
    console.log(data.hackname);
    axios.get(`${url}/team/${data.hackname}`).then(response => {
      console.log(response);
      //update the state with the response data
      this.setState({
        authFlag: true,
        hackathons: response.data.body
      });
      console.log("Search :", this.state.hackathons);
    });
  }

  onGradeSubmit(id) {
    const data = {
      grade: this.state.grade,
      id: id
    };
    console.log(data);
    axios
      .post(
        `${url}/hackathon/${data.id}/score?score=${data.grade}`
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
    let details = this.state.hackathons.map(org => {
      return (
        <div class="displaypropinfo container-fluid">
          <div class="col-sm-8">
            <div class="headline">
              <h3 class="hit-headline">
                <div>
                  Team id: {org.id}
                  <br />
                  Submitted URL {org.submissionUrl}
                  <button
                    class="btn btn-primary btn-md"
                    style={{ marginLeft: "20px" }}
                    data-toggle="modal"
                    data-target="#myModal"
                  >
                    Evaluate
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
                        <h4 class="modal-title" style={{ textAlign: "center" }}>
                          Evaluate this Submission
                        </h4>
                      </div>
                      <div class="modal-body">
                        <div style={{ overflowX: "auto" }}>
                          <div
                            style={{ fontSize: "18px", marginBottom: "10px" }}
                          >
                            Enter grade out of 10 {" "}
                          </div>
                          <div class="form-group">
                            <input
                              onChange={this.gradeChangeHandler}
                              type="text"
                              class="form-control"
                              name="type"
                              placeholder="Enter grade here"
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
                                this.onGradeSubmit(org.id);
                              }}
                            >
                              Submit Grade
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
          <div class="main-div1" style={{ background: "url('images/matrixspirit.png')", backgroundRepeat: "no-repeat", backgroundSize: "cover" }}>
            {navbar}
            <div>
              <h2 style={{ marginLeft: "40%" }}>Evaluate Hackathon</h2>
              <h4 style={{ marginLeft: "25%" }}>
                Here you can grade teams involved in contests you are a judge for
              </h4>
              <p style={{ fontSize: "18px", color: "red" }}>
                {this.state.message}
              </p>
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
export default Hackeval;