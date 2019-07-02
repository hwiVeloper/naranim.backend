import React, { Component } from "react";
import { Route } from "react-router-dom";
import MemberList from "../components/MemberList";
import MemberDetail from "../components/MemberDetail";

const Members = ({ match }) => {
  return (
    <>
      <h2>Page : Members</h2>
      <Route
        exact
        path={match.url}
        render={() => <MemberList match={match} />}
      />
      <Route
        path={`${match.url}/:deptCd`}
        render={({ match }) => <MemberDetail match={match} />}
      />
    </>
  );
};

export default Members;
