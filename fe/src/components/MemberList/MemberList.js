import React, { Component } from "react";
import { Link } from "react-router-dom";
import axios from "axios";
import {
  withStyles,
  Grid,
  Typography,
  Paper,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
  Input,
  TextField
} from "@material-ui/core";
import MemberItem from "../MemberItem";

const styles = theme => ({
  searchBox: {
    margin: theme.spacing(2, 0),
    padding: theme.spacing(2)
  },
  field: {
    marginRight: theme.spacing(2),
    minWidth: 120
  }
});

class MemberList extends Component {
  state = {
    members: [],
    polys: [],
    selPoly: "",
    txtName: ""
  };

  componentDidMount() {
    var comp = this; // axios 내부에서 this 참조를 하지 못함.
    axios
      .get("http://localhost/polys/")
      .then(function(res) {
        comp.setState({ polys: res.data });
      })
      .catch(function(error) {
        comp.setState({ polys: [] });
        console.log(error);
      });

    axios
      .get("http://localhost/members/")
      .then(function(res) {
        comp.setState({ members: res.data });
      })
      .catch(function(error) {
        comp.setState({ members: [] });
        console.log(error);
      });
  }

  handlePolyChange(e) {
    this.setState({ selPoly: e.target.value });
  }

  handleNameChange(e) {
    this.setState({ txtName: e.target.value });
  }

  applySearchFilter() {
    console.log("?");
  }

  render() {
    const { match, classes } = this.props;
    const { members, polys, selPoly, txtName } = this.state;
    const polyItems = polys.map(data => (
      <MenuItem key={data.polyCd} value={data.polyCd}>
        {data.polyNm}
      </MenuItem>
    ));
    const items = members
      .filter(function(item) {
        return (
          item.polyCd.indexOf(selPoly) >= 0 && item.empNm.indexOf(txtName) >= 0
        );
      })
      .map(data => (
        <Grid item xs={3} key={data.deptCd}>
          <Link
            to={`${match.url}/${data.deptCd}`}
            style={{ textDecoration: "none" }}
          >
            <MemberItem data={data} />
          </Link>
        </Grid>
      ));

    return (
      <>
        <div style={{ padding: 10 }}>
          <Typography variant="h3" paragraph>
            의원 정보
          </Typography>
          <Paper className={classes.searchBox} elevation1>
            <FormControl>
              <InputLabel shrink htmlFor="selPoly">
                소속당
              </InputLabel>
              <Select
                value={selPoly}
                onChange={event => this.handlePolyChange(event)}
                input={<Input name="poly" id="age-label-placeholder" />}
                displayEmpty
                name="poly"
                className={classes.field}
              >
                <MenuItem value="">
                  <em>All</em>
                </MenuItem>
                {polyItems}
              </Select>
            </FormControl>
            <TextField
              id="txtName"
              label="의원명"
              value={txtName}
              InputLabelProps={{
                shrink: true
              }}
              className={classes.field}
              onInput={event => this.handleNameChange(event)}
            />
          </Paper>
          <Grid container spacing={2}>
            {items}
          </Grid>
        </div>
      </>
    );
  }
}

export default withStyles(styles)(MemberList);
