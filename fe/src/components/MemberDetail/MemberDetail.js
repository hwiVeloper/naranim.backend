import {
  Container,
  Divider,
  Grid,
  Paper,
  Typography,
  withStyles
} from "@material-ui/core";
import {
  GroupRounded,
  LocationOnOutlined,
  PlusOne,
  MailOutline,
  Home,
  HomeOutlined,
  LocalPhone,
  ThumbUpAltOutlined,
  FormatPaintOutlined,
  CreateOutlined,
  Cake,
  BusinessOutlined
} from "@material-ui/icons";
import axios from "axios";
import React, { Component } from "react";

const styles = theme => ({
  profileBox: {
    position: "relative",
    width: "100%",
    paddingBottom: "100%"
  },
  profileImage: {
    position: "absolute",
    width: "100%",
    height: "100%",
    borderRadius: "50%!important"
  },
  icon: {
    position: "relative",
    top: theme.spacing.unit,
    width: theme.typography.body1.width,
    height: theme.typography.body1.height,
    marginRight: theme.spacing(1)
  }
});

class MemberDetail extends Component {
  state = {
    member: {}
  };

  componentDidMount() {
    var comp = this; // axios 내부에서 this 참조를 하지 못함.
    const { match } = this.props;

    axios
      .get(`http://localhost/members/${match.params.deptCd}`)
      .then(function(res) {
        comp.setState({ member: res.data });
      })
      .catch(function(error) {
        comp.setState({ member: {} });
        console.log(error);
      });
  }

  render() {
    const { classes } = this.props;
    const { member } = this.state;

    return (
      <>
        <Container>
          <Grid container spacing={3}>
            <Grid item xs={12} lg={2}>
              <div className={classes.profileBox}>
                <Paper
                  component="img"
                  className={classes.profileImage}
                  src={member.jpgLink}
                />
              </div>
            </Grid>
            <Grid item xs={12} lg={10} alignContent="center">
              <Typography variant="h4" display="inline">
                {member.empNm}
              </Typography>
              <Typography variant="h5" display="inline" color="textSecondary">
                &nbsp;({member.engNm}&nbsp;|&nbsp;{member.hjNm})
              </Typography>
              <Divider />
              <Typography variant="body1">
                <GroupRounded className={classes.icon} />
                {member.polyNm}
              </Typography>
              <Typography>
                <LocationOnOutlined className={classes.icon} />
                {member.origNm}
              </Typography>
              <Typography>
                <PlusOne className={classes.icon} />
                {member.reeleGbnNm}
              </Typography>
              <Typography>
                <MailOutline className={classes.icon} />
                {member.assemEmail || "-"}
              </Typography>
              <Typography>
                <HomeOutlined className={classes.icon} />
                {member.assemHomep || "-"}
              </Typography>
              <Typography>
                <LocalPhone className={classes.icon} />
                {member.assemTel || "-"}
              </Typography>
              <Typography>
                <Cake className={classes.icon} />
                {member.bthDate || "-"}
              </Typography>
              <Typography>
                <ThumbUpAltOutlined className={classes.icon} />
                {member.examCd || "-"}
              </Typography>
              <Typography>
                <FormatPaintOutlined className={classes.icon} />
                {member.hbbyCd || "-"}
              </Typography>
              <Typography>
                <BusinessOutlined className={classes.icon} />
                {member.shrtNm || "-"}
              </Typography>
              <Typography>
                <CreateOutlined className={classes.icon} />
                {member.memTitle || "-"}
              </Typography>
            </Grid>
          </Grid>
        </Container>
      </>
    );
  }
}

export default withStyles(styles)(MemberDetail);
