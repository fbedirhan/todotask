import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import { Route, Link, BrowserRouter as Router } from 'react-router-dom'
import Login from './screens/login'
import Register from './screens/register'
import Main from './screens/main'
import App from './App';
import * as serviceWorker from './serviceWorker';
import TodoList from './screens/todo';
import Dependency from './screens/dependency';

const routing = (
    <Router>
      <div>
        <Route exact path="/" component={Main} />
        <Route path="/login" component={Login} />
        <Route path="/register" component={Register} />
        <Route path="/main" component={Main} />
        <Route path="/todo/:id?" component={TodoList} />
        <Route path="/dependency/:id?/:listId?" component={Dependency} />
      </div>
    </Router>
  )

ReactDOM.render(routing, document.getElementById('root'));

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
