import axios from "axios";

// API URL
const apiUrl = "http://localhost";

const SET_DATES = "election/SET_DATES";
const CHANGE_TAB_PAGE = "election/CHANGE_TAB_PAGE";
const CLICK_CHIP = "election/CLICK_CHIP";

const setDates = dates => ({ type: SET_DATES, dates });
const changeTabpage = (event, postValue) => ({
  type: CHANGE_TAB_PAGE,
  tabpage: postValue
});
const clickChip = event => ({ type: CLICK_CHIP });

// 선거일자 리스트
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

// 선거정보 탭 변경
export const handleChangeTabpage = (event, postValue) => dispatch => {
  dispatch(changeTabpage(event, postValue));
};

// 선거일자 클릭 이벤트
export const handleClickChip = event => dispatch => {
  dispatch(clickChip(event));
};
