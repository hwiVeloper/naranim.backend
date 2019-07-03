import React, { Component } from "react";
import "typeface-nanum-gothic";
import "./App.css";
import { BrowserRouter, Route } from "react-router-dom";
import { Home, Members, Discussion } from "./pages";
import { CssBaseline, Container } from "@material-ui/core";
import { withStyles } from "@material-ui/styles";
import Navbar from "./components/Navbar";
import Footer from "./components/Footer";

const styles = theme => ({
  root: {
    display: "flex",
    flexDirection: "column",
    minHeight: "100vh"
  },
  contents: {
    marginTop: 80
  },
  footer: {
    marginTop: "auto",
    backgroundColor: "white"
  }
});

class App extends Component {
  render() {
    const { classes } = this.props;
    return (
      <div className={classes.root}>
        <CssBaseline />
        <Container component="main" maxWidth="false">
          <BrowserRouter>
            <Navbar />
            <div className={classes.contents}>
              <Route exact path="/" component={Home} />
              <Route path="/member" component={Members} />
              <Route exact path="/discussion" component={Discussion} />
            </div>
          </BrowserRouter>
        </Container>
        <Footer />
      </div>
    );
  }
}

export default withStyles(styles)(App);
