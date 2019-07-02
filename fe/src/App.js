import React, { Component } from "react";
import "./App.css";
import { BrowserRouter, Route } from "react-router-dom";
import { Home, Members, MemberDetail, Discussion } from "./pages";
import Navbar from "./components/Navbar";
import Footer from "./components/Footer";

class App extends Component {
  render() {
    return (
      <>
        <BrowserRouter>
          <Navbar />
          <div>
            <Route exact path="/" component={Home} />
            <Route path="/member" component={Members} />
            <Route exact path="/discussion" component={Discussion} />
          </div>
          <Footer />
        </BrowserRouter>
      </>
    );
  }
}

export default App;
