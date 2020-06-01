let req = getRequest();

let authority;
getAuthority();
use(authority);

let users;
getUsers();
drawTable(users);

function getRequest() {
    if (window.XMLHttpRequest) { // Mozilla, Safari, ...
        newRequest = new XMLHttpRequest();
        if (newRequest.overrideMimeType) {
            newRequest.overrideMimeType('text/xml');
        }
    } else if (window.ActiveXObject) { // IE
        try {
            newRequest = new ActiveXObject("Msxml2.XMLHTTP");
        } catch (e) {
            try {
                newRequest = new ActiveXObject("Microsoft.XMLHTTP");
            } catch (e) {
            }
        }
    }
    return (newRequest);
}

function use(user) {
    user.roles = user.roles.map(item => item.rolename);
    let footerString = "<b>" + user.username + "</b> with roles " + user.roles.join(', ');
    document.getElementById(id = "headerString").innerHTML = footerString;


    let mainMenu = `
            <ul class="nav nav-stacked nav-left nav-pills" >
                <br>
                <li class="active"><a href="#adminPanel" data-toggle="tab" role="tab">Admin</a></li>`;
    if (user.roleNames.includes('USER')) {
        mainMenu += `<li><a href="#userPanel" id="userTab" data-toggle="tab" role="tab">User</a></li>`
    }
    mainMenu += '</ul>'
    document.getElementById("mainTabs").innerHTML = mainMenu;


    let table = `<table class="table table-condensed table-sm my-n1">
    <caption>
        <th>id</th>
        <th>first name</th>
        <th>last name</th>
        <th>age</th>
        <th>email</th>
        <th>password</th>
        <th>roles</th>
    </caption>
    <tbody><tr>`
    user.roles = user.roles.map(item => item.rolename);
    table += ('<td>'
        + user.user_id + '</td><td>'
        + user.firstName + '</td><td>'
        + user.lastName + '</td><td>'
        + user.age + '</td><td>'
        + user.username + '</td><td>'
        + user["password"] + '</td><td>'
        + user.roleNames.join(', ') + '</td>'
        + '</tr></table>');
    document.getElementById("userPanel").innerHTML = table;

}

function drawTable(users) {
    //  console.log(users);
    let table = `<table class="table table-condensed table-sm my-n1">
        <caption>
        <th>id</th>
        <th>first name</th>
        <th>last name</th>
        <th>age</th>
        <th>email</th>
        <th>password</th>
        <th>roles</th>
        <th>Edit</th>
        <th>Delete</th>
        </caption>
        <tbody>`
    for (let i = 0; i < users.length; i++) {
        /* console.log(rowUser);*/
        users[i].roles = users[i].roles.map(item => item.rolename);
        table += ('<tr><td>'
            + users[i].user_id + '</td><td>'
            + users[i].firstName + '</td><td>'
            + users[i].lastName + '</td><td>'
            + users[i].age + '</td><td>'
            + users[i].username + '</td><td>'
            + users[i]["password"] + '</td><td>'
            + users[i].roles.join(', ') + '</td><td>'
            + ' <input type="button" class="btn btn-primary btn-mini py-1 my-1" data-toggle="modal" value="edit"'
            + ' id="edit' + i + '" onclick="editUser(' + i + ')"></td><td>'
            + ' <input type="button" class="btn btn-danger btn-mini py-1 my-1" data-toggle="modal" value="delete"'
            + ' id="del"' + i + '></td>' +
            '</tr>'
        )
    }
    table += '</tbody></table>';
    document.getElementById("adminTable").innerHTML = table;

}

function getUsers() {
    req.onreadystatechange = function () {
        if (req.readyState == 4) {
            if (req.status == 200) {
                let res = req.responseText;
                users = JSON.parse(res);
            }
        }
    };
    req.open("get", "/admin/all", false);
    req.send(null);
}

function getAuthority() {
    req.onreadystatechange = function () {
        if (req.readyState == 4) {
            if (req.status == 200) {
                let res = req.responseText;
                let user = JSON.parse(res);
                authority = user;
            }
        }
    };
    req.open("get", "/authority", false);
    req.send(null);
}

function createUser() {

    let user = {
        username: $('#newUserName').val(),
        password: $('#newUserPassword').val(),
        firstName: $('#newUserFirstName').val(),
        lastName: $('#newUserLastName').val(),
        age: $('#newUserAge').val(),
        roles: $('#newUserRoles').val()
    }
    let newUserJsonString = JSON.stringify(user);

        $.ajax({
            type: 'post',
            url: "/admin/newUser",
            async: false,
            data: newUserJsonString,
            contentType: "application/json"
        })



/*    req.onreadystatechange = function () {
        if (req.readyState == 4) {
            if (req.status == 200) {
                let res = req.responseText;
                let user = JSON.parse(res);
                users.push(user);
                drawTable(users);
            }
        }
    }
    ;
    req.open("post", "/admin/newUser", true);
    req.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    req.send("json=" + newUserJsonString);*/
}

function editUser(i) {

    $('#editUserName')[0].value = (users[i].username);
    $('#editUserPassword')[0].value = (users[i].password);
    $('#editUserFirstName')[0].value = (users[i].firstName);
    $('#editUserLastName')[0].value = (users[i].lastName);
    $('#editUserAge')[0].value = parseInt(users[i].age);

    $('#editUserRole')[0].selected = users[i].roles.includes("USER");
    $('#editAdminRole')[0].selected = users[i].roles.includes("ADMIN");

    $("#saveChanges").click(function(){saveChanges(i);});

    $('#editModal').modal('show');

}

function saveChanges(i) {
    console.log(i);
    console.log(users[i]);
    console.log(users[i].user_id);
    let user = {
        user_id: users[i].user_id,
        username: $('#editUserName').val(),
        password: $('#editUserPassword').val(),
        firstName: $('#editUserFirstName').val(),
        lastName: $('#editUserLastName').val(),
        age: $('#editUserAge').val(),
        roles: $('#editUserRoles').val()
    }
    let editedUserJsonString = JSON.stringify(user);
   /* alert(editedUserJsonString);*/
        $.ajax({
            type: "POST",
            url: "/admin/newUser",
            async: true,
            data: newUserJsonString,
            contentType: "application/json",
            success: function(msg){
                alert( "Прибыли данные: " + msg );
            }
        });
 /*   req.onreadystatechange = function () {
        if (req.readyState == 4) {
            if (req.status == 200) {
                let res = req.responseText;
                users[i] = JSON.parse(res);
                drawTable(users);
            }
        }
    }
    ;
    req.open("post", "/admin/update", true);
    req.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    req.send("json=" + editedUserJsonString);*/
}