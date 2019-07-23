import { createAction, handleActions } from "redux-actions";
import { Map, List } from "immutable";

// 액션 타입 정의
const GET_DATES = "election/GET_DATES";

// 액션 생성
// handleActions에서 분기처리하여 실행된다.
export const getDates = createAction(GET_DATES);

// 초기 상태 정의
const initialState = Map({
  electionDates: []
});

// 리듀서 함수 정의
const election = handleActions(
  {
    [GET_DATES]: (state, action) => ({
      ...state
    })
  },
  initialState
);

export default election;
