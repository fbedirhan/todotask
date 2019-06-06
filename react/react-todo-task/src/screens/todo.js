
import React from 'react'
import BaseScreen from './base';
import Moment from 'moment'
import More from '@material-ui/icons/More';
import MaterialTableDemo from '../components/materialTable';
import Grid from '@material-ui/core/Grid';
import bgImage from '../assets/images/login-bg.jpg';

class TodoList extends BaseScreen {

    constructor(props) {
        Moment.locale('tr')
        super(props);
        this.state = {
            todoItemList: [],
            tableColumns: [
                { title: 'Todo List Name', field: 'name' },
                { title: 'Description', field: 'description' },
                {
                    field: 'state', title: 'State', render: rowData => rowData.state === 0 ? "Not Completed" : "Completed", lookup: { 0: 'Not Completed', 1: 'Completed' }
                },
                { title: 'Deadline', field: 'deadline', render: rowData => Moment(rowData.deadline).format('d MMM YYYY HH:mm'), type: 'datetime', customFilterAndSearch: (term, rowData) => Moment(term).isBefore(rowData.deadline) },
            ],
            listId: 0
        };

    }

    componentDidMount() {
        console.log(this.props.match.params.id);
        var listId = this.props.match.params.id;
        this.checkSession();
        this.setState({ listId: listId });
        this.fillTodoItems(listId);
    }

    fillTodoItems(listId) {
        this.services.getTodoItems(listId).then(resp => {
            if (resp.data.success) {
                this.setState({ todoItemList: resp.data.data });
            } else {
                alert(resp.data.message);
            }
            console.log(resp.data);
        })
    }

    createTodoItem(name, description, state, deadline, listId) {
        this.services.createTodoItem(name, description, state, deadline, listId, 1).then(resp => {
            if (resp.data.success) {
                this.setState({ todoItemList: resp.data.data });
            } else {
                alert(resp.data.message);
            }
        });
    }

    deleteTodoItem(id, listId) {
        this.services.deleteTodoItem(id, listId).then(resp => {
            if (resp.data.success) {
                this.setState({ todoItemList: resp.data.data });
            } else {
                alert(resp.data.message);
            }
        });
    }

    onRowClick(rowData,listId) {
        this.services.markTodoItem(rowData.id, listId,rowData.state).then(resp => {
            if (resp.data.success) {
                this.setState({ todoItemList: resp.data.data });
            } else {
                alert(resp.data.message);
            }
        });
    }

    render() {
        return (
            <div  >
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
                    dataSet={this.state.todoItemList}
                    columns={this.state.tableColumns}
                    options={{
                        filtering: true,
                        selection: false,
                        rowStyle: rowData => ({
                            backgroundColor: (rowData.state === 1) ? '#EEE' : '#FFF',
                        }),

                    }}
                    editable={{
                        onRowAdd: newData =>
                            new Promise(resolve => {
                                console.log("NEW DATA " + newData.name);
                                setTimeout(() => {
                                    resolve();
                                    this.createTodoItem(newData.name, newData.description, newData.state, newData.deadline, this.state.listId);
                                }, 600);
                            }),
                        onRowDelete: oldData =>
                            new Promise(resolve => {
                                setTimeout(() => {
                                    resolve();
                                    this.deleteTodoItem(oldData.id, this.state.listId);
                                }, 600);
                            }),
                    }}
                    actions={[
                        {
                            icon: More,
                            tooltip: 'Dependency Detail',
                            onClick: (event, rowData) =>  this.props.history.push('/dependency/'+rowData.id + "/" + this.state.listId)
                        }
                    ]}

                    onRowClick={(event, rowData, togglePanel) => this.onRowClick(rowData,this.state.listId)}

                >

                </MaterialTableDemo>
                </Grid>
            </div>
        );
    }
}
export default TodoList;