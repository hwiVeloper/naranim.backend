import React, { Component } from "react";
import { connect } from "react-redux";
import { bindActionCreators } from "redux";
import { withStyles, Grid } from "@material-ui/core/";
import ElectionDates from "../components/ElectionDates";
import { getElectionDates } from "../api";
import ElectionTab from "../components/ElectionTab";

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

  componentDidMount() {
    // 선거일 정보 Call
    this.props.getElectionDates();
  }

  render() {
    const { classes, electionDates } = this.props;

    return (
      <div className={classes.root}>
        <Grid container spacing={2}>
          {/* 선거일 리스트 (Chip UI) */}
          <Grid item xs={12}>
            <ElectionDates dates={electionDates} />
          </Grid>
          {/* 선거정보 (Tab UI) */}
          <Grid item xs={12}>
            <ElectionTab />
          </Grid>
        </Grid>
      </div>
    );
  }
}

const mapStateToProps = state => ({
  electionDates: state.election.get("dates")
});

const mapDispatchToProps = {
  getElectionDates
};

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(withStyles(styles)(Election));
