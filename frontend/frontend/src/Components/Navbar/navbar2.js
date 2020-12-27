import React, { Component } from "react";
import "../../App.css";

//create the Navbar Component
class Navbar2 extends Component {
  render() {
    return (
      <nav class="navbar navbar-light navbar-light-custom">
        <div class="container-fluid">
          <div class="navbar-header">
            <a class="navbar-brand navbar-brand-custom" href="/login">
                <img
                    src="images/hlogo.png"
                    style={{
                        marginLeft: "80px",
                        width: "320px",
                        height: "80px"
                    }}
                />
            </a>
          </div>
          <ul class="nav navbar-nav navbar-right">
            <li>
              <a class="logoimage" href="">
                <img src="images/logalert.png" />
              </a>
            </li>
          </ul>
        </div>
      </nav>
    );
  }
}

export default Navbar2;