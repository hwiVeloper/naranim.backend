import React, { Component } from "react";
import { connect } from "react-redux";
import { withStyles, Grid } from "@material-ui/core/";
import ElectionDates from "../components/ElectionDates";
import ElectionTab from "../components/ElectionTab";

import { getElectionDates, handleChangeTabpage } from "../actions";

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
  componentDidMount() {
    const { getElectionDates } = this.props;
    getElectionDates();
  }

  render() {
    const { classes, electionDates, tabpage } = this.props;
    const { handleChangeTabpage, handleClickChip } = this.props;

    return (
      <div className={classes.root}>
        <Grid container spacing={2}>
          {/* 선거일 리스트 (Chip UI) */}
          <Grid item xs={12}>
            <ElectionDates
              dates={electionDates}
              handleClick={handleClickChip}
            />
          </Grid>
          {/* 해당 선거일 선거종류정보 */}
          <Grid item xs={12} />
          {/* 선거정보 (Tab UI) */}
          <Grid item xs={12}>
            <ElectionTab
              changeTabpage={handleChangeTabpage}
              tabpage={tabpage}
            />
          </Grid>
        </Grid>
      </div>
    );
  }
}

const mapStateToProps = state => ({
  electionDates: state.election.get("dates"),
  tabpage: state.election.get("tabpage")
});

const mapDispatchToProps = {
  getElectionDates,
  handleChangeTabpage,
  handleClickChip
};

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(withStyles(styles)(Election));
