import React, { Component } from "react";
import { connect } from "react-redux";
import { bindActionCreators } from "redux";
import * as electionActions from "../reducers/election";
import { withStyles } from "@material-ui/core/";

const drawerWidth = 240;

const styles = theme => ({
  root: {
    display: "flex"
  },
  appBar: {
    // zIndex: theme.zIndex.drawer + 1
  },
  drawer: {
    width: drawerWidth,
    flexShrink: 0,
    zIndex: 0
  },
  drawerPaper: {
    width: drawerWidth
  },
  content: {
    flexGrow: 1,
    padding: theme.spacing(3)
  },
  toolbar: theme.mixins.toolbar
});

class Election extends Component {
  constructor(props) {
    super(props);
  }

  state = {};

  render() {
    const { classes } = this.props;
    return (
      <div className={classes.root}>
        {/* 선거일 리스트 (Chip UI) */}
        {/* <Chip
          color="primary"
          label="2019년 7월 21일"
          href="#"
          component="a"
          clickable
        /> */}

        {/* 선거정보 (Tab UI) */}
      </div>
    );
  }
}

const mapStateToProps = ({ election }) => ({
  // electionDates: election.
});

const mapDispatchToProps = dispatch => ({
  electionActions: bindActionCreators(electionActions, dispatch)
});

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(withStyles(styles)(Election));
