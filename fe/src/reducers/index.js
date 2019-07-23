import { combineReducers } from "redux";
import { connectRouter } from "connected-react-router";
import election from "./election";

export default history =>
  combineReducers({
    router: connectRouter(history),
    election
  });
