import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import Grid from '@material-ui/core/Grid';
import CardHeader from '@material-ui/core/CardHeader';
import CardContent from '@material-ui/core/CardContent';
import TextField from '@material-ui/core/TextField';
import bgImage from '../assets/images/login-bg.jpg';  
import { Button } from '@material-ui/core';
import BaseScreen from './base';


class Login extends BaseScreen {

    classes = makeStyles(theme => ({
        card: {
            maxWidth: 500,
            alignContent: 'center',
        }
    }));

    constructor(props) {
        super(props);
        this.state = {
            username: '',
            password: ''
        };

        this.handleSubmit = this.handleSubmit.bind(this);
    }

    componentDidMount() {
      localStorage.clear();
    }
    handleSubmit(event) {
        this.services.login(this.state.username, this.state.password).then(response => {
            if (response.data.success) {
                //alert(response.data.data[0]);
                localStorage.setItem("user", JSON.stringify(response.data.data[0]));
                this.props.history.push('/main');
            } else {
                alert(response.data.message);
            }
            console.log(response);
        })
            .catch(error => {
                console.log(error);
                alert(error);
            });

    }

    render() {
        return (
            <div>
                <Grid

                    container
                    spacing={0}
                    direction="column"
                    alignItems="center"
                    justify="center"
                    style={{ minHeight: '100vh', backgroundImage: `url(${bgImage})` }}
                >
                    <Card className={this.classes.card}>
                        <CardHeader
                            title="TO-DO Task Login"
                        />

                        <CardContent>
                            <div>
                                <div>
                                    <TextField
                                        id="filled-name"
                                        label="Email"
                                        style={{ width: 300 }}
                                        margin="normal"
                                        variant="outlined"
                                        value={this.state.username}
                                        onChange={(event) => (this.setState({ username: event.target.value }))}
                                    />
                                </div>
                                <div>
                                    <TextField
                                        id="filled-name"
                                        type="password"
                                        label="Password"
                                        style={{ width: 300 }}
                                        margin="normal"
                                        variant="outlined"
                                        value={this.state.password}
                                        onChange={(event) => (this.setState({ password: event.target.value }))}
                                    />
                                </div>
                                <div>
                                    <Button
                                        variant="contained"
                                        style={{ width: 300, marginTop: 20, height: 50 }}
                                        color="primary"
                                        onClick={this.handleSubmit}
                                        className={this.classes.button}>
                                        Login
                            </Button>
                                </div>
                            </div>
                        </CardContent>



                    </Card></Grid ></div>
        );
    }
}

export default Login;