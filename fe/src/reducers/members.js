import { createAction, handleActions } from "redux-actions";
import { Map, List } from "immutable";

const READ = "members/READ";

export const read = createAction(READ);

// 초기 상태 정의
const initialState = Map({
  counters: List([
    Map({
      color: "black",
      number: 0
    })
  ])
});

const members = handleActions(
  {
    [READ]: (state, action) => {
      console.log(state);
      console.log(action);
      console.log("members/READ");
      return 1;
    }
  },
  initialState
);

export default members;
