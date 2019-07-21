import { combineReducers } from "redux";
import { connectRouter } from "connected-react-router";
import members from "./members";

export default history =>
  combineReducers({
    router: connectRouter(history),
    members
  });
