import UserServices from '../services/userServices';
import React from 'react';

class BaseScreen extends React.Component {

    services = new UserServices();

    checkSession() {
        var userJSON = localStorage.getItem("user");
        if (userJSON !== null) {
            var user = JSON.parse(userJSON);
            console.log(user.id);
        } else {
            this.props.history.push('/login' );
        }
    }

    getUserId() {
        var userJSON = localStorage.getItem("user");
        if (userJSON !== null) {
            var user = JSON.parse(userJSON);
            return user.id;
        } else {
            return 0;
        }
        
    }

} export default BaseScreen;