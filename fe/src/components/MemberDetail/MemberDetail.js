import React, { Component } from "react";

class MemberDetail extends Component {
  render() {
    const { match } = this.props;
    return (
      <>
        <h2>Page : MemberDetail :: {match.params.deptCd}</h2>
      </>
    );
  }
}

export default MemberDetail;
