import {
  Container,
  Divider,
  Grid,
  Paper,
  Typography,
  withStyles,
  List,
  ListItem,
  ListItemText,
  ListItemIcon
} from "@material-ui/core";
import {
  GroupRounded,
  LocationOnOutlined,
  PlusOne,
  MailOutline,
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

  makeHistoryText(strHistory) {
    if (typeof strHistory === "string") {
      strHistory = strHistory
        .replace(/,/g, "")
        .split("\r")
        .map(line => (
          <span>
            {line}
            <br />
          </span>
        ));
    }
    return strHistory;
  }

  render() {
    const { classes } = this.props;
    const { member } = this.state;

    const history = this.makeHistoryText(member.memTitle);

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
              <List dense>
                <ListItem>
                  <ListItemIcon>
                    <GroupRounded />
                  </ListItemIcon>
                  <ListItemText primary={member.polyNm} />
                </ListItem>
                <ListItem>
                  <ListItemIcon>
                    <LocationOnOutlined />
                  </ListItemIcon>
                  <ListItemText primary={member.origNm} />
                </ListItem>
                <ListItem>
                  <ListItemIcon>
                    <PlusOne />
                  </ListItemIcon>
                  <ListItemText primary={member.reeleGbnNm} />
                </ListItem>
                <ListItem>
                  <ListItemIcon>
                    <MailOutline />
                  </ListItemIcon>
                  <ListItemText primary={member.assemEmail || "-"} />
                </ListItem>
                <ListItem>
                  <ListItemIcon>
                    <HomeOutlined />
                  </ListItemIcon>
                  <ListItemText primary={member.assemHomep || "-"} />
                </ListItem>
                <ListItem>
                  <ListItemIcon>
                    <LocalPhone />
                  </ListItemIcon>
                  <ListItemText primary={member.assemTel || "-"} />
                </ListItem>
                <ListItem>
                  <ListItemIcon>
                    <Cake />
                  </ListItemIcon>
                  <ListItemText primary={member.bthDate || "-"} />
                </ListItem>
                <ListItem>
                  <ListItemIcon>
                    <ThumbUpAltOutlined />
                  </ListItemIcon>
                  <ListItemText primary={member.examCd || "-"} />
                </ListItem>
                <ListItem>
                  <ListItemIcon>
                    <FormatPaintOutlined />
                  </ListItemIcon>
                  <ListItemText primary={member.hbbyCd || "-"} />
                </ListItem>
                <ListItem>
                  <ListItemIcon>
                    <BusinessOutlined />
                  </ListItemIcon>
                  <ListItemText primary={member.shrtNm || "-"} />
                </ListItem>
                <ListItem alignItems="flex-start">
                  <ListItemIcon>
                    <CreateOutlined />
                  </ListItemIcon>
                  <ListItemText primary={history || "-"} />
                </ListItem>
              </List>
            </Grid>
          </Grid>
        </Container>
      </>
    );
  }
}

export default withStyles(styles)(MemberDetail);
