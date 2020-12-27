import React, { Component } from "react";
import NavProfile from "../Navbar/navbar5";
import cookie from "react-cookies";
import { Redirect } from "react-router";
import "../../App.css";
import Footer from "../Footer/footer";
import axios from "axios";
import {url} from "../../BaseUrl";

class Profile extends Component {

  refreshPage() {
    if (window.localStorage) {
      if (!localStorage.getItem('initial')) {
        localStorage['initial'] = true;
        window.location.reload()
      } else {
        localStorage.removeItem('initial');
      }
    }
  }

  handleRefresh = () => {
    this.setState({});
  };

  constructor(props) {
    super(props);
    this.state = {
      UserData: {},
      email: "",
      name: "",
      organisation: "",
      organisations: "",
      screenName: "",
      address: "",
      city: "",
      states: "",
      street: "",
      zip: "",
      company: "",
      about: "",
      orgname: "",
      leaveorgname: "",
      businessTitle: "",
      authFlag: false,
      orgFlag: false,
      leaveFlag: false,
      MainName: "",
      organisationvalue: "",
      status: "",
      sponsors: []
    };
    //Bind the handlers to this class
    this.emailChangeHandler = this.emailChangeHandler.bind(this);
    this.firstnameChangeHandler = this.firstnameChangeHandler.bind(this);
    this.lastnameChangeHandler = this.lastnameChangeHandler.bind(this);
    this.aboutChangeHandler = this.aboutChangeHandler.bind(this);
    this.addressChangeHandler = this.addressChangeHandler.bind(this);
    this.submitUpdate = this.submitUpdate.bind(this);
    this.submitJoin = this.submitJoin.bind(this);
    this.businessTitleChangeHandler = this.businessTitleChangeHandler.bind(
        this
    );
    this.first_name = React.createRef();
    this.last_name = React.createRef();
  }

  //Call the Will Mount to set the auth Flag to false
  componentWillMount() {
    //window.location.reload(1);
  }

  componentDidMount() {
    // window.location.reload(1);
    this.refreshPage()

    const data = {
      screenName: window.localStorage.getItem("screenName")
    };
    this.state.screenName = window.localStorage.getItem("screenName")
    console.log("name = " + this.state.screenName)
    console.log("User Info:", localStorage.getItem("screenName"));
    axios
      .get(`${url}/user/${data.screenName}`)
      .then(response => {
        console.log("Status Code : ", response);
        console.log("Status", response.data.body);
        if (response.data.statusCodeValue === 200) {
          var user = {};
          this.setState({
            loginFlag: true,
            UserData: user
          });

          this.setState({
            name: response.data.body.name,
            email: response.data.body.email
          });
          if (response.data.body.address != null) {
            this.setState({
              street: response.data.body.address.street,
              city: response.data.body.address.city,
              states: response.data.body.address.state,
              zip: response.data.body.address.zip
            });
          }
          if (response.data.body.title != null) {
            this.setState({
              businessTitle: response.data.body.title
            });
            console.log("well, title = " + this.state.businessTitle)
          } else {
            console.log("pity, title = " + this.state.businessTitle)
          }

          if (response.data.body.aboutMe != null) {
            this.setState({
              about: response.data.body.aboutMe
            });
          }

          if (response.data.body.organisation != null) {
            this.setState({
              organisation: response.data.body.organisation.organisationName,
              status: response.data.body.organisation.status
            });
          }
        } else {
          this.setState({
            loginFlag: false
          });
        }
      });

    axios.get(`${url}/organisation/names/`).then(response => {
      console.log(response);
      //update the state with the response data
      this.setState({
        sponsors: response.data.body
      });
      console.log("Search :", this.state.sponsors);
    });
  }

  orgnameChangeHandler = e => {
    this.setState({
      orgname: e.target.value
    });
  };
  addressChangeHandler = e => {
    this.setState({
      address: e.target.value
    });
  };

  cityChangeHandler = e => {
    this.setState({
      city: e.target.value
    });
  };
  streetChangeHandler = e => {
    this.setState({
      street: e.target.value
    });
  };
  statesChangeHandler = e => {
    this.setState({
      states: e.target.value
    });
  };
  zipChangeHandler = e => {
    this.setState({
      zip: e.target.value
    });
  };
  //username change handler to update state variable with the text entered by the user
  emailChangeHandler = e => {
    this.setState({
      email: e.target.value
    });
  };
  //username change handler to update state variable with the text entered by the user
  firstnameChangeHandler = e => {
    this.setState({
      firstname: e.target.value
    });
  };
  //username change handler to update state variable with the text entered by the user
  lastnameChangeHandler = e => {
    this.setState({
      lastname: e.target.value
    });
  };
  //password change handler to update state variable with the text entered by the user
  cityChangeHandler = e => {
    this.setState({
      city: e.target.value
    });
  };
  aboutChangeHandler = e => {
    this.setState({
      about: e.target.value
    });
  };
  businessTitleChangeHandler = e => {
    this.setState({
      businessTitle: e.target.value
    });
  };
  handleSelect = e => {
    console.log(e);
    this.setState({ organisationvalue: e.target.value });
  };
  //submit Property handler to send a request to the node backend
  submitUpdate = e => {
    //prevent page from refresh
    e.preventDefault();
    const data = {
      screenName: localStorage.getItem("screenName"),
      street: this.state.street,
      city: this.state.city,
      states: this.state.states,
      zip: this.state.zip,
      businessTitle: this.state.businessTitle,
      about: this.state.about
    };
    console.log("data", data);
    //set the with credentials to true
    axios.defaults.withCredentials = true;
    //make a post request with the user data
    axios
      .post(
          `${url}/user/${data.screenName}/?title=${this.state.businessTitle}&street=${this.state.street}&city=${
              this.state.city
          }&state=${this.state.states}&zip=${this.state.zip}&aboutMe=${
              this.state.about
          }`
      )
      .then(response => {
        console.log("Status Code : ", response);
        if (response.data.statusCodeValue === 200) {
          this.setState({
            authFlag: true,
            message: "Congratulations! Successfully updated"
          });
          window.location.reload(1);
        } else {
          this.setState({
            authFlag: false,
            message: "Invalid Data "
          });
        }
      });
  };
  //submit Property handler to send a request to the node backend
  submitJoin = e => {
    //prevent page from refresh
    e.preventDefault();
    console.log(this.state.organisationvalue);
    const data = {
      orgname: this.state.organisationvalue,
      screenName: localStorage.getItem("screenName")
    };
    console.log("data", data);
    //set the with credentials to true
    axios.defaults.withCredentials = true;
    //make a post request with the user data
    axios
      .post(
        `${url}/organisation/${data.orgname}/join/${
          data.screenName
        }`
      )
      .then(response => {
        console.log("Status Code : ", response);
        if (response.data.statusCodeValue === 200) {
          this.setState({
            orgFlag: true,
            message: "Congratulations! Successfully updated"
          });
          window.location.reload(1);
        } else {
          this.setState({
            orgFlag: false,
            message: response.data.body
          });
        }
      });
  };
  submitLeave = e => {
    //prevent page from refresh
    e.preventDefault();
    const data = {
      orgname: this.state.organisation,
      screenName: localStorage.getItem("screenName")
    };
    console.log("data", data);
    //set the with credentials to true
    axios.defaults.withCredentials = true;
    //make a post request with the user data
    axios
      .post(
        `${url}/organisation/${data.orgname}/leave/${
          data.screenName
        }`
      )
      .then(response => {
        console.log("Status Code : ", response.data);
        if (response.data.statusCodeValue === 200) {
          this.setState({
            leaveFlag: true,
            message: "Congratulations! Successfully updated"
          });
          window.location.reload(1);
        } else {
          this.setState({
            leaveFlag: false,
            message: "Invalid Data "
          });
        }
      });
  };

  render() {

    var redirect = null;
    let details2 = this.state.sponsors.map(sponsor => {
      return (
        <option value={sponsor.organisationName}>
          {sponsor.organisationName}
        </option>
      );
    });
    let details = (
      <React.Fragment>
        <form>
          <div
            className="inputdiv"
            style={{ padding: "20px", background: "#DAEDF0" }}
          >
            <div class="row">
              <div id="profileheading" style={{ marginLeft: "15px" }}>
                <h3>Manage Organisation</h3>
              </div>
              {this.state.organisation === "" ? (
                <div class="row">
                  <div
                    class="form-group form-group-lg col-md-3"
                    style={{ marginLeft: "15px" }}
                  >
                    <label className="sr-only" for="firstname">
                      Search Organisation
                    </label>
                    <label style={{ fontSize: "15px" }}>
                      Select organisation to join<br />
                    </label>
                    <div style={{ fontSize: "15px", color: "red" }}>
                      {this.state.message}
                    </div>
                    <div style={{ fontSize: "15px" }}>
                      <select
                        value={this.state.organisationvalue}
                        onChange={this.handleSelect}
                      >
                        {" "}
                        <option value="Org">Select</option>
                        {details2}
                      </select>
                    </div>
                  </div>
                  <div class="col-md-3">
                    <button
                      onClick={this.submitJoin}
                      style={{
                        backgroundColor: "#0067db",
                        borderColor: "#0067db",
                        fontSize: "18px",
                        marginTop: "2px"
                      }}
                      class="btn btn-primary button-search"
                    >
                      Send Join Request
                    </button>
                  </div>
                </div>
              ) : (
                <div className="row">
                  <div
                    className="form-group form-group-lg col-md-6"
                    style={{ marginLeft: "15px" }}
                  >
                    <label className="sr-only" for="gender">
                      Organisation
                    </label>
                    <input
                      type="text"
                      className="form-control"
                      name="organisation"
                      placeholder="Organization"
                      defaultValue={this.state.organisation}
                        />
                  </div>
                  <div class="col-md-3">
                    <span
                      style={{
                        backgroundColor: "#0067db",
                        borderColor: "#0067db",
                        fontSize: "18px",
                        marginTop: "2px"
                      }}
                      class="btn btn-primary button-search"
                    >
                      {this.state.status}
                    </span>
                    <button
                      onClick={this.submitLeave}
                      style={{
                        backgroundColor: "#0067db",
                        borderColor: "#0067db",
                        fontSize: "18px",
                        marginTop: "2px"
                      }}
                      class="btn btn-primary button-search"
                    >
                      Leave Organisation
                    </button>
                  </div>
                </div>
              )}

              <div id="profileheading" style={{ marginLeft: "15px" }}>
                <h3>Profile Information</h3>
              </div>
              <div class="form-group form-group-lg col-md-6">
                <label className="sr-only" for="firstname">
                  First Name
                </label>
                <input
                  type="text"
                  className="form-control "
                  id="firstname"
                  name="firstname"
                  ref="First Name"
                  value={localStorage.getItem("screenName")}
                  placeholder="First Name"
                  onChange={this.firstnameChangeHandler}
                />
              </div>
            </div>

            <div className="row">
              <div className="form-group form-group-lg col-md-6">
                <label className="sr-only" for="gender">
                  Email
                </label>
                <input
                  type="text"
                  className="form-control"
                  name="email"
                  placeholder="Email"
                  value={localStorage.getItem("email")}
                  ref="Email"
                  onChange={this.emailChangeHandler}
                />
              </div>
            </div>

            <div className="row">
              <div className="form-group form-group-lg col-md-6">
                <label for="company" style={{ fontSize: "15px" }}>
                  Business Title
                </label>
                <input
                  type="text"
                  className="form-control"
                  name="BusinessTitle"
                  defaultValue={this.state.businessTitle}
                  placeholder="Business Title"
                  onChange={this.businessTitleChangeHandler}
                />
              </div>
            </div>
            <div class="row">
              <div className="form-group form-group-lg col-md-12">
                <label style={{ fontSize: "15px" }} for="About">
                  About
                </label>
                <input
                  type="text"
                  className="form-control"
                  name="About"
                  defaultValue={this.state.about}
                  placeholder="About me"
                  onChange={this.aboutChangeHandler}
                />
              </div>
            </div>
            <div className="row">
              <div className="form-group form-group-lg col-md-6">
                <label style={{ fontSize: "15px" }} for="city">
                  Street
                </label>
                <input
                  type="text"
                  className="form-control"
                  name="street"
                  placeholder="Street"
                  defaultValue={this.state.street}
                  onChange={this.streetChangeHandler}
                />
              </div>
            </div>
            <div className="row">
              <div className="form-group form-group-lg col-md-6">
                <label style={{ fontSize: "15px" }} for="city">
                  City
                </label>
                <input
                  type="text"
                  className="form-control"
                  name="city"
                  defaultValue={this.state.city}
                  placeholder="City"
                  onChange={this.cityChangeHandler}
                />
              </div>
            </div>
            <div className="row">
              <div className="form-group form-group-lg col-md-6">
                <label style={{ fontSize: "15px" }} for="city">
                  Country
                </label>
                <input
                  type="text"
                  className="form-control"
                  name="state"
                  placeholder="Country"
                  defaultValue={this.state.states}
                  onChange={this.statesChangeHandler}
                />
              </div>
            </div>
            <div className="row">
              <div className="form-group form-group-lg col-md-6">
                <label style={{ fontSize: "15px" }} for="city">
                  Zip
                </label>
                <input
                  type="text"
                  className="form-control"
                  name="zip"
                  defaultValue={this.state.zip}
                  placeholder="Zip Code"
                  onChange={this.zipChangeHandler}
                />
              </div>
            </div>
          </div>
        </form>
      </React.Fragment>
    );

    if (this.state.UserData !== "") {
      return (
        <div>
          {redirect}
          <NavProfile
            navdata={this.props.navdata}
            style={{ backgroundColor: "aliceblue" }}
          />
          <div class="main-div2">
            <div className="profilephoto" style={{ textAlign: "center", fontWeight: "bold" }}>
              <div style={{ fontSize: "32px" }}>
                {localStorage.getItem("screenName")}
              </div>
            </div>
            <div className="container profilemaindiv">
              <div>
                  {details}
              </div>
            </div>
            <div class="col-sm-3">
              <button
                onClick={this.submitUpdate}
                style={{
                  backgroundColor: "#0067db",
                  borderColor: "#0067db",
                  fontSize: "18px",
                  marginLeft: "143px",
                  marginTop: "10px"
                }}
                class="btn btn-primary button-search"
              >
                Save Changes
              </button>
            </div>
            <div style={{ marginTop: "100px" }}>
              <Footer footdata={this.props.footdata} />
            </div>
          </div>
        </div>
      );
    } else {
      return (
        <div>
          <NavProfile
            navdata={this.props.navdata}
            style={{ backgroundColor: "aliceblue" }}
          />
          <div class="main-div">
            <h2>No results for this query</h2>
          </div>
          <div style={{ marginTop: "100px" }}>
            <Footer footdata={this.props.footdata} />
          </div>
        </div>
      );
    }
  }
}
export default Profile;