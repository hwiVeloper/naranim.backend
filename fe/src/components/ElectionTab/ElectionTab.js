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
    flexGrow: 1,
    backgroundColor: theme.palette.background.paper
  }
});

class ElectionTab extends Component {
  a11yProps(index) {
    return {
      id: `election-tab-${index}`,
      "aria-controls": `election-tabpanel-${index}`
    };
  }

  render() {
    const { classes, tabpage, changeTabpage } = this.props;

    const TabPanel = props => {
      const { children, value, index, ...other } = props;

      return (
        <Typography
          component="div"
          role="tabpanel"
          hidden={value !== index}
          id={`election-tabpanel-${index}`}
          aria-labelledby={`election-tab-${index}`}
          {...other}
        >
          <Box p={3}>{children}</Box>
        </Typography>
      );
    };

    return (
      <div className={classes.root}>
        <AppBar position="static" color="default">
          <Tabs
            value={tabpage}
            onChange={changeTabpage}
            textColor="primary"
            indicatorColor="primary"
            aria-label="Election Tabs"
          >
            <Tab label="Item One" {...this.a11yProps(0)} />
            <Tab label="Item Two" {...this.a11yProps(1)} />
            <Tab label="Item Three" {...this.a11yProps(2)} />
          </Tabs>
          <TabPanel value={tabpage} index={0}>
            Item One
          </TabPanel>
          <TabPanel value={tabpage} index={1}>
            Item Two
          </TabPanel>
          <TabPanel value={tabpage} index={2}>
            Item Three
          </TabPanel>
        </AppBar>
      </div>
    );
  }
}

export default withStyles(styles)(ElectionTab);
