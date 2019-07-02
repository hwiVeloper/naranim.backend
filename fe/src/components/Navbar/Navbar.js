import React, { Component } from "react";
import { NavLink } from "react-router-dom";
import PropTypes from "prop-types";
import {
  withStyles,
  AppBar,
  Toolbar,
  Typography,
  Button
} from "@material-ui/core/";

const styles = {
  root: {
    flexGrow: 1
  },
  grow: {
    flexGrow: 1
  },
  menuButton: {
    marginLeft: -12,
    marginRight: 20
  }
};

class Navbar extends Component {
  render() {
    const { classes } = this.props;

    const NavHome = props => <NavLink to="/" {...props} />;
    const NavMembers = props => <NavLink to="/member" {...props} />;
    const NavDiscussion = props => <NavLink to="/discussion" {...props} />;

    return (
      <div className={classes.root}>
        <AppBar position="fixed" color="default">
          <Toolbar>
            <Typography variant="h6" color="inherit" className={classes.grow}>
              우리동네 민주주의
            </Typography>
            <Button color="inherit" component={NavHome}>
              홈
            </Button>
            <Button color="inherit" component={NavMembers}>
              의원정보
            </Button>
            <Button color="inherit" component={NavDiscussion}>
              의사정보
            </Button>
          </Toolbar>
        </AppBar>
      </div>
    );
  }
}

Navbar.propTypes = {
  classes: PropTypes.object.isRequired
};

export default withStyles(styles)(Navbar);
