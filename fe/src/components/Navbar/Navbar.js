import React, { Component } from "react";
import { NavLink } from "react-router-dom";
import PropTypes from "prop-types";
import {
  withStyles,
  AppBar,
  Toolbar,
  Typography,
  Button
  // Menu,
  // MenuItem
} from "@material-ui/core/";

const styles = theme => ({
  root: {
    flexGrow: 1
  },
  navButton: {
    margin: theme.spacing(1)
  },
  grow: {
    flexGrow: 1
  },
  menuButton: {
    marginLeft: -12,
    marginRight: 20
  },
  appBarSpacer: theme.mixins.toolbar
});

class Navbar extends Component {
  state = {
    anchorEl: null
  };

  handleMenu = event => {
    this.setState({ anchorEl: event.currentTarget });
  };

  handleClose = () => {
    this.setState({ anchorEl: null });
  };

  render() {
    const { classes } = this.props;
    // const { anchorEl } = this.state;
    // const open = Boolean(anchorEl);

    // TopMenu 구현체 (forwardRef 적용)
    const LinkRef = React.forwardRef((props, ref) => (
      <div ref={ref}>
        <NavLink {...props} />
      </div>
    ));

    return (
      <div className={classes.root}>
        <AppBar position="fixed" color="default">
          <Toolbar>
            <Typography variant="h5" color="inherit" className={classes.grow}>
              WooMin
            </Typography>
            <Button component={LinkRef} className={classes.navButton} to="/">
              홈
            </Button>
            <Button
              component={LinkRef}
              className={classes.navButton}
              to="/member"
            >
              의원정보
            </Button>
            <Button
              component={LinkRef}
              className={classes.navButton}
              to="/discussion"
            >
              의사정보
            </Button>
            <Button
              component={LinkRef}
              className={classes.navButton}
              to="/election"
            >
              선거정보
            </Button>
            {/*
            <Button
              aria-controls="simple-menu"
              aria-haspopup="true"
              className={classes.navButton}
              onClick={this.handleMenu}
            >
              Dropwdown Test
            </Button>
            <Menu
              id="simple-menu"
              anchorEl={anchorEl}
              keepMounted
              open={open}
              onClose={this.handleClose}
            >
              <MenuItem onClick={this.handleClose}>메뉴1</MenuItem>
              <MenuItem onClick={this.handleClose}>메뉴2</MenuItem>
              <MenuItem onClick={this.handleClose}>메뉴3</MenuItem>
            </Menu>
            */}
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
