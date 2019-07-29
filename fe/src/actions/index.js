import axios from "axios";

// API URL
const apiUrl = "http://localhost";

const SET_DATES = "election/SET_DATES";
const CHANGE_TAB_PAGE = "election/CHANGE_TAB_PAGE";

const setDates = dates => ({ type: SET_DATES, dates });
const changeTabpage = (event, postValue) => ({
  type: CHANGE_TAB_PAGE,
  tabpage: postValue
});

export const getElectionDates = () => dispatch => {
  axios
    .get(`${apiUrl}/elections/getDates`)
    .then(res => {
      dispatch(setDates(res.data));
    })
    .catch(error => {
      console.error(error);
    });
};

export const handleChangeTabpage = (event, postValue) => dispatch => {
  dispatch(changeTabpage(event, postValue));
};
