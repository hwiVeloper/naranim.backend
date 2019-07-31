import { Map, List } from "immutable";
import axios from "axios";

// API URL
const apiUrl = "http://localhost";

const SET_DATES = "election/SET_DATES";

const setDates = dates => ({ type: SET_DATES, dates });

export const getElectionDates = () => dispatch => {
  console.log("api call");
  axios
    .get(`${apiUrl}/elections/getDates`)
    .then(res => {
      console.log("them");
    })
    .then(res => {
      dispatch(setDates(res.data));
    })
    .catch(error => {
      console.log("catch");
    });
};

const initialState = Map({
  dates: List([]),
  tabpage: 0
});

const election = (state = initialState, action) => {
  // const { dates } = state.get("dates");
  const { tabpage } = state.get("tabpage");

  switch (action.type) {
    case "election/SET_DATES":
      return state.set("dates", action.dates);
    case "election/CHANGE_TAB_PAGE":
      if (tabpage === action.tabpage) {
        return state;
      }
      return state.set("tabpage", action.tabpage);
    case "election/CLICK_CHIP":
      return state;
    default:
      return state;
  }
};

export default election;
