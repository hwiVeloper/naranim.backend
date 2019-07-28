import React, { Component } from "react";
import { BrowserRouter, Route, Switch } from "react-router-dom";
import { Home, Members, Discussion, Election } from "./container";
import { CssBaseline, Container } from "@material-ui/core";
import { withStyles } from "@material-ui/styles";
import Navbar from "./components/Navbar";
import Footer from "./components/Footer";

// redux
import { createStore, applyMiddleware } from "redux";
import { Provider } from "react-redux";
import { ConnectedRouter, routerMiddleware } from "connected-react-router";
import reducers from "./reducers";
import { createBrowserHistory } from "history";
import { composeWithDevTools } from "redux-devtools-extension/developmentOnly";
import ReduxThunk from "redux-thunk";

const history = createBrowserHistory();

const configureStore = preloadedState => {
  const store = createStore(
    reducers(history), // root reducer with router state,
    preloadedState,
    composeWithDevTools(
      applyMiddleware(
        routerMiddleware(history), // for dispatching history actions
        // ... other middlewares ...
        // 추후 thunk, saga 추가시 설정
        ReduxThunk
      )
    )
  );
  return store;
};

const initialState = {};

const store = configureStore(initialState);

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
      <Provider store={store}>
        <ConnectedRouter history={history}>
          <div className={classes.root}>
            <CssBaseline />
            <Container component="main" maxWidth="false">
              <BrowserRouter>
                <Navbar />
                <div className={classes.contents}>
                  <Route exact path="/" component={Home} />
                  <Route path="/member" component={Members} />
                  <Route exact path="/discussion" component={Discussion} />
                  <Route exact path="/election" component={Election} />
                </div>
              </BrowserRouter>
            </Container>
            {/* <Footer /> */}
          </div>
        </ConnectedRouter>
      </Provider>
    );
  }
}

export default withStyles(styles)(App);
