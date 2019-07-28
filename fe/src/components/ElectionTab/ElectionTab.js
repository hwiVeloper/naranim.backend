import React, { Component } from "react";
import {
  withStyles,
  AppBar,
  Tabs,
  Tab,
  Typography,
  Box
} from "@material-ui/core";

const styles = theme => ({
  root: {
    flexGrow: 1
    // backgroundColor: theme.palette.background.paper
  }
});

class ElectionTab extends Component {
  a11yProps(index) {
    return {
      id: `simple-tab-${index}`,
      "aria-controls": `simple-tabpanel-${index}`
    };
  }

  render() {
    const { classes } = this.props;

    const TabPanel = props => {
      const { children, value, index, ...other } = props;

      return (
        <Typography
          component="div"
          role="tabpanel"
          hidden={value !== index}
          id={`simple-tabpanel-${index}`}
          aria-labelledby={`simple-tab-${index}`}
          {...other}
        >
          <Box p={3}>{children}</Box>
        </Typography>
      );
    };

    return (
      <div className={classes.root}>
        <AppBar position="static">
          <Tabs
            value={0}
            onChange={() => console.log("changed tab")}
            aria-label="simple tabs example"
          >
            <Tab label="Item One" {...this.a11yProps(0)} />
            <Tab label="Item Two" {...this.a11yProps(1)} />
            <Tab label="Item Three" {...this.a11yProps(2)} />
          </Tabs>
          <TabPanel value={0} index={0}>
            Item One
          </TabPanel>
          <TabPanel value={1} index={1}>
            Item Two
          </TabPanel>
          <TabPanel value={2} index={2}>
            Item Three
          </TabPanel>
        </AppBar>
      </div>
    );
  }
}

export default withStyles(styles)(ElectionTab);
