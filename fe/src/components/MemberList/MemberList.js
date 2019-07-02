import React, { Component } from "react";
import { Link } from "react-router-dom";
import axios from "axios";
import { Grid } from "@material-ui/core";
import MemberItem from "../MemberItem";

class MemberList extends Component {
  state = {
    members: []
  };

  constructor(props) {
    super(props);
    this.memberItemOnClick = this.memberItemOnClick.bind(this);
  }

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

  memberItemOnClick(deptCd) {
    console.log(deptCd);
  }

  render() {
    const { match } = this.props;
    const { members } = this.state;
    const items = members.map(data => (
      <Grid item xs={3} key={data.deptCd}>
        <Link
          to={`${match.url}/${data.deptCd}`}
          style={{ textDecoration: "none" }}
        >
          <MemberItem data={data} />
        </Link>
      </Grid>
    ));

    return (
      <>
        <div style={{ padding: 10 }}>
          <h3>의원 정보</h3>
          <Grid container spacing={2}>
            {items}
          </Grid>
        </div>
      </>
    );
  }
}

export default MemberList;
