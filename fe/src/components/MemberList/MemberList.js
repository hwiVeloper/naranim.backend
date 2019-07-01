import React, { Component } from "react";
import axios from "axios";
import { Grid } from "@material-ui/core";
import MemberItem from "../MemberItem";

class MemberList extends Component {
  state = {
    members: []
  };

  componentDidMount() {
    var comp = this; // axios 내부에서 this 참조를 하지 못함.
    axios
      .get("http://localhost/members/")
      .then(function(res) {
        comp.setState({ members: res.data });
      })
      .catch(function(error) {
        comp.setState({ members: [] });
        console.log(error);
      });
  }
  render() {
    const { members } = this.state;

    const items = members.map(data => (
      <Grid item xs={3}>
        <MemberItem key={data.deptCd} data={data} />
      </Grid>
    ));
    return (
      <>
        <h3>의원 정보</h3>
        <Grid container spacing={3}>
          {items}
        </Grid>
      </>
    );
  }
}

export default MemberList;
