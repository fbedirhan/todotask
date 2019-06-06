import React from 'react'
import BaseScreen from './base';
import MaterialTableDemo from '../components/materialTable';
import Grid from '@material-ui/core/Grid';
import bgImage from '../assets/images/login-bg.jpg';

class Main extends BaseScreen {

    constructor(props) {
        super(props);
        this.state = {
            todoList: [],
            userId: this.getUserId(),
            tableColumns: [
                { title: 'Todo List Name', field: 'name' }
            ],
        };

    }

    componentDidMount() {
        this.checkSession();
        this.fillTodoList();
    }

    fillTodoList() {
        this.services.getUserList(this.state.userId).then(resp => {
            if (resp.data.success) {
                this.setState({ todoList: resp.data.data });
            } else {
                alert(resp.data.message);
            }
            console.log(resp.data);
        })
    }

    createTodoList(name) {
        this.services.createTodoList(name, this.state.userId).then(resp => {
            if (resp.data.success) {
                this.setState({ todoList: resp.data.data });
            } else {
                alert(resp.data.message);
            }
        });
    }

    deleteTodoList(id) {
        this.services.deleteTodoList(id, this.state.userId).then(resp => {
            if (resp.data.success) {
                this.setState({ todoList: resp.data.data });
            } else {
                alert(resp.data.message);
            }
        });
    }

    onRowClick(rowData) {
        this.props.history.push('/todo/' + rowData.id);
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
                    <MaterialTableDemo
                        style={{ marginLeft: 50, marginRight: 50, marginTop: 50 }}
                        title="Todo Lists"
                        dataSet={this.state.todoList}
                        columns={this.state.tableColumns}
                        editable={{
                            onRowAdd: newData =>
                                new Promise(resolve => {
                                    console.log("NEW DATA " + newData.name);
                                    setTimeout(() => {
                                        resolve();
                                        this.createTodoList(newData.name);
                                    }, 600);
                                }),
                            onRowDelete: oldData =>
                                new Promise(resolve => {
                                    setTimeout(() => {
                                        resolve();
                                        this.deleteTodoList(oldData.id);
                                    }, 600);
                                }),
                        }}
                        onRowClick={(event, rowData) => this.onRowClick(rowData)}

                    >

                    </MaterialTableDemo>
                </Grid>
            </div>
        );
    }
}
export default Main;