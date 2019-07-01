import React, { Component } from "react";
import { withStyles } from "@material-ui/core/styles";
import { Card, CardContent, CardMedia, Typography } from "@material-ui/core";
// import { SkipPrevious, PlayArrow, SkipNext } from "@material-ui/icons";
import "./MemberItem.css";

const styles = theme => ({
  card: {
    display: "flex",
    height: 150
  },
  details: {
    display: "flex",
    flexDirection: "column"
  },
  content: {
    flex: "1 0 auto"
  },
  cover: {
    width: 151
  },
  controls: {
    display: "flex",
    alignItems: "center",
    paddingLeft: theme.spacing(1),
    paddingBottom: theme.spacing(1)
  },
  playIcon: {
    height: 38,
    width: 38
  }
});

class MemberItem extends Component {
  render() {
    const { key, data, classes } = this.props;
    // const theme = useTheme();
    return (
      <Card className={classes.card}>
        <CardMedia
          className={classes.cover}
          image={data.jpgLink}
          title={data.empNm}
        />
        <div className={classes.detail}>
          <CardContent className={classes.content}>
            <Typography component="h5" variant="h5">
              {data.empNm}
            </Typography>
            <Typography variant="subtitle1" color="textSecondary">
              {data.engNm}
            </Typography>
          </CardContent>
        </div>
      </Card>
    );
  }
}

export default withStyles(styles)(MemberItem);
