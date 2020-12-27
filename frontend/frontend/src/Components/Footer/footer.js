import React, { Component } from "react";
import "../../App.css";

class Footer extends Component {
  render() {
    return (
      <React.Fragment>
        <div
          class="footermain"
          style={{
            backgroundColor: "#323f4d",
            color: "white"
          }}
        >
          <div
            class="container"
            style={{ color: "#a0a9b2" }}
          >
            <div style={{ textAlign: "center" }} class="row">
              <div class="col-sm-12">
                <p
                    className="endfootertext"
                    style={{
                      marginTop: "20px",
                      fontSize: "16px",
                      lineHeight: "1.5em"
                    }}
                >
                    <span style={{ fontSize: "18px" }}>©</span>opyright? If you're willing to copy the image above, make shot and get it freely
                  <span style={{color: "white"}}>
                    &nbsp;(Don't believe you'd need something else since having experience with this app)
                  </span>
                </p>
                <p/>
                <p
                    className="endfootertext"
                    style={{fontSize: "16px", lineHeight: "50px"}}
                >
                  ©2020 <span style={{color: "white"}}> Polytech </span> & <span style={{color: "white"}}>
                  Distributed system course </span> & <span style={{color: "white"}}> a lively H`ackCent guy </span>
                </p>
              </div>
              <div class="col-sm-4" />
            </div>
          </div>
        </div>
      </React.Fragment>
    );
  }
}

export default Footer;