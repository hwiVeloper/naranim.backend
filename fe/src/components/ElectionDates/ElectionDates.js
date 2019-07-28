import React, { Component } from "react";
import { Chip, withStyles } from "@material-ui/core";
import moment from "moment";

const styles = theme => ({
  root: {
    flexGrow: 1,
    backgroundColor: theme.palette.background.paper
  }
});

class ElectionDates extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    const { dates, classes } = this.props;
    const comp = dates.map(date => (
      <Chip
        color="primary"
        label={moment(date, "YYYYMMDD").format("YYYY년 MM월 DD일")}
        onClick={() => console.log(date)}
        component="a"
        clickable
      />
    ));
    return <div className={classes.root}>{comp}</div>;
  }
}

export default withStyles(styles)(ElectionDates);
