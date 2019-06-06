
import axios from 'axios';
import { Constants } from '../config';

const jsonConfig = {
    headers: { 'content-type': 'application/json;charset=UTF-8' }
}

class UserServices {

    login(email, password) {
        let formData = new FormData();
        formData.set('email', email);
        formData.set('password', password);

        const config = {
            headers: { 'content-type': 'multipart/form-data' }
        }
        return axios.post(Constants.apiBaseURL + "user/login", formData, config);
    }

    register(email, password, name, surname) {
        var dataItem = {
            "email": email,
            "password": password,
            "name": name,
            "surname": surname
        };
        return axios.post(Constants.apiBaseURL + "user/register", dataItem, jsonConfig);
    }

    getUserList(id) {
        return axios.get(Constants.apiBaseURL + "todoList/user/" + id, jsonConfig);
    }

    createTodoList(name, userId) {
        var dataItem = {
            "user": {
                "id": userId,
            },
            "name": name
        };
        return axios.post(Constants.apiBaseURL + "todoList/create", dataItem, jsonConfig);
    }

    deleteTodoList(id, userId) {
        return axios.delete(Constants.apiBaseURL + "todoList/delete/" + id + "/" + userId, jsonConfig);
    }

    getTodoItems(listId) {
        return axios.get(Constants.apiBaseURL + "todoItem/list/" + listId, jsonConfig);
    }

    createTodoItem(name,description,state,deadline, listId,userId) {
        var dataItem = {
            "todoList": {
                "id": listId,
            },
            "name": name,
            "description": description,
            "state":state,
            "deadline":deadline
        };
        return axios.post(Constants.apiBaseURL + "todoItem/create", dataItem, jsonConfig);
    }

    deleteTodoItem(itemId, listId) {
        return axios.delete(Constants.apiBaseURL + "todoItem/delete/" + itemId + "/" + listId, jsonConfig);
    }

    getNotDependTodoItems(itemId,listId) {
        return axios.get(Constants.apiBaseURL + "todoItem/notDependent/" + itemId + "/" + listId, jsonConfig);
    }

    getDependenyItems(itemId) {
        return axios.get(Constants.apiBaseURL + "todoItem/dependency/" + itemId, jsonConfig);
    }

    createDependency(todoItemId,dependentItemId) {
        var dataItem = {
            "todoItemId": {
                "id": todoItemId,
            },
            "dependentId": {
                "id": dependentItemId,
            },
        };
        return axios.post(Constants.apiBaseURL + "todoItem/dependency/create", dataItem, jsonConfig);
    }

    deleteDependency(dependencyId, itemId) {
        return axios.delete(Constants.apiBaseURL + "todoItem/delete/dependency/" + dependencyId + "/" + itemId, jsonConfig);
    }

    markTodoItem(itemId,listId,state) {
        let formData = new FormData();
        formData.set('listId', listId);
        formData.set('itemId', itemId);
        formData.set('state', state);
        const config = {
            headers: { 'content-type': 'multipart/form-data' }
        }
        return axios.post(Constants.apiBaseURL + "todoItem/mark", formData, config);
    }

} export default UserServices;