import { Map, List } from "immutable";

const prefix = "election/";

const initialState = Map({
  dates: List([])
});

const election = (state = initialState, action) => {
  const { dates } = state.get("dates");
  console.log(dates);

  switch (action.type) {
    case "election/SET_DATES":
      return state.set("dates", action.dates);
    // case "SETTIMELINE":
    //   return [...state, ...action.timeline];
    // case "APPENDFEED":
    //   return Object.values([action.feed, ...state]);
    // case "UPDATEFEED":
    //   return state.map(feed => {
    //     if (feed.id === action.feed.id) {
    //       return mergeObject(feed, action.feed);
    //     }
    //     return feed;
    //   });
    // case "APPENDCOMMENT":
    //   return state.map(feed => {
    //     if (feed.id === action.comment.rootDocument.id) {
    //       return Object.values([action.comment, ...feed.comments]);
    //     }
    //     return feed;
    //   });
    default:
      return state;
  }
};

export default election;
