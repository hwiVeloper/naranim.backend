import React, { Component } from "react";
import { withStyles, Typography, Paper } from "@material-ui/core";

const styles = theme => ({
  footer: {
    flexGrow: 1,
    marginTop: "auto",
    zIndex: 1
  },
  footerPaper: {
    padding: theme.spacing(3, 2)
  }
});

class Footer extends Component {
  render() {
    const { classes } = this.props;

    return (
      <footer className={classes.footer}>
        <Paper className={classes.footerPaper}>
          <Typography variant="h5" component="h3">
            Woomin
          </Typography>
          <Typography component="p">@2019 All right reserved</Typography>
        </Paper>
      </footer>
    );
  }
}

export default withStyles(styles)(Footer);
