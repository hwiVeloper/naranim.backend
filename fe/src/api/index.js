import axios from "axios";

// API URL
const apiUrl = "http://localhost";

const SET_DATES = "election/SET_DATES";

const setDates = dates => ({ type: SET_DATES, dates });

export const getElectionDates = () => dispatch => {
  console.log("============== call api");
  axios
    .get(`${apiUrl}/elections/getDates`)
    .then(function(res) {
      dispatch(setDates(res.data));
    })
    .catch({});
};
