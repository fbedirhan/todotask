import React from 'react'
import BaseScreen from './base';
import MaterialTableDemo from '../components/materialTable';
import Grid from '@material-ui/core/Grid';
import bgImage from '../assets/images/login-bg.jpg';

class Dependency extends BaseScreen {

    constructor(props) {
        super(props);
        this.state = {
            dependencyList: [],
            notDependencyList: [],
            userId: this.getUserId(),
            tableColumns: [
                { title: 'Name', field: 'dependentId.name' },
                { title: 'Description', field: 'dependentId.description' },
                {
                    field: 'dependentId.state', title: 'State', render: rowData => rowData.dependentId.state === 0 ? "Not Completed" : "Completed"
                }
            ],
            nonDependencyTableColumns: [
                { title: 'Name', field: 'name' },
                { title: 'Description', field: 'description' },
                {
                    field: 'state', title: 'State', render: rowData => rowData.state === 0 ? "Not Completed" : "Completed", lookup: { 0: 'Not Completed', 1: 'Completed' }
                }
            ]
        };

    }

    componentDidMount() {
        var itemId = this.props.match.params.id;
        var listId = this.props.match.params.listId;
        this.checkSession();
        this.setState({ itemId: itemId, listId: listId });
        this.fillDependecyList(itemId);
        this.fillNonDependecyList(itemId, listId);
    }

    fillDependecyList(itemId) {
        this.services.getDependenyItems(itemId).then(resp => {
            if (resp.data.success) {
                this.setState({ dependencyList: resp.data.data });
            } else {
                alert(resp.data.message);
            }
            console.log(resp.data);
        })
    }

    fillNonDependecyList(itemId, listId) {
        this.services.getNotDependTodoItems(itemId, listId).then(resp => {
            if (resp.data.success) {
                this.setState({ notDependencyList: resp.data.data });
            } else {
                alert(resp.data.message);
            }
            console.log(resp.data);
        })
    }

    deleteDependencyItem(id) {
        this.services.deleteDependency(id, this.state.itemId).then(resp => {
            if (resp.data.success) {
                this.fillDependecyList(this.state.itemId);
                this.fillNonDependecyList(this.state.itemId, this.state.listId);
            } else {
                alert(resp.data.message);
            }
        });
    }

    onAddDependencyClick(rowData) {
        this.services.createDependency(this.state.itemId, rowData.id).then(resp => {
            if (resp.data.success) {
                this.fillDependecyList(this.state.itemId);
                this.fillNonDependecyList(this.state.itemId, this.state.listId);
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
                        style={{ marginTop: 30,  marginBottom: 30 }}
                        title="Depenceny List"
                        dataSet={this.state.dependencyList}
                        columns={this.state.tableColumns}
                        editable={{
                            onRowDelete: oldData =>
                                new Promise(resolve => {
                                    setTimeout(() => {
                                        resolve();
                                        this.deleteDependencyItem(oldData.id);
                                    }, 600);
                                }),
                        }}
                    >
                    </MaterialTableDemo>

                    <MaterialTableDemo
                        style={{ marginBottom: 30  }}
                        title="Add Dependency"
                        dataSet={this.state.notDependencyList}
                        columns={this.state.nonDependencyTableColumns}
                        onRowClick={(event, rowData) => this.onAddDependencyClick(rowData)}
                    >
                    </MaterialTableDemo>
                </Grid>
            </div>
        );
    }
}
export default Dependency;