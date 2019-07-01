import React, { Component } from "react";
import { withStyles, AppBar, Typography } from "@material-ui/core";

const styles = theme => ({
  footer: {
    marginTop: 10
  }
});

class Footer extends Component {
  render() {
    const { classes } = this.props;

    return (
      <footer className={classes.footer}>
        <AppBar color="inherit" position="static">
          <Typography variant="h5" component="h3">
            Woomin
          </Typography>
          <Typography component="p">@2019 All right reserved</Typography>
        </AppBar>
      </footer>
    );
  }
}

export default withStyles(styles)(Footer);
