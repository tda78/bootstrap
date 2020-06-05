let req = getRequest();

let authority;
getAuthority();
use(authority);

let users = {};
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

function drawTable(users) {
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
            + ' id="del' + i + '" onclick="deleteUser(' + i + ')"></td>'
            + '</tr>'
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
                for (let user of users) {
                    user.roles = user.roles.map(item => item.rolename);
                    console.dir(user);
                }
                console.log("get users: " + users);
            }
        }
    };
    req.open("get", "/admin/all", false);
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
        contentType: "application/json",
        complete: function (data) {

            user.user_id = data.responseText;
            users.push(user);
            drawTable(users)
        }

    })
    $('#newUserName')[0].value = "";
    $('#newUserPassword')[0].value = "";
    $('#newUserFirstName')[0].value = "";
    $('#newUserLastName')[0].value = "";
    $('#newUserAge')[0].value = "";

    drawTable(users);
    $('#adminUsers')[0].classList.add('active');
    $('#adminUsers')[0].classList.add('in');
    $('#navUsers')[0].classList.add('active');
    $('#adminUsers')[0].show;

    $('#newUser')[0].classList.remove('active');
    $('#navNewUser')[0].classList.remove('active');
}

function editUser(i) {

    $('#editUserName')[0].value = (users[i].username);
    $('#editUserPassword')[0].value = (users[i].password);
    $('#editUserFirstName')[0].value = (users[i].firstName);
    $('#editUserLastName')[0].value = (users[i].lastName);
    $('#editUserAge')[0].value = (users[i].age);

    $('#editUserRole')[0].selected = users[i].roles.includes("USER");
    $('#editAdminRole')[0].selected = users[i].roles.includes("ADMIN");

    /*$("#saveChanges").click(function(){saveChanges(i);});*/
    $('#buttonSubmitChangesContainer')[0].innerHTML =
        '<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>'
        + '<button type="submit" class="btn btn-primary" onclick="saveChanges('
        + i
        + ')" id="saveChanges" data-toggle="modal">Save changes</button>'

    $('#editModal').modal('show');

}

function saveChanges(i) {
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

    $.ajax({
        type: "POST",
        url: "/admin/update",
        async: false,
        data: editedUserJsonString,
        contentType: "application/json",
        complete: function () {
            users.splice(i, 1, user);
            drawTable(users);
        }
    });
    $('#editModal').modal('hide');
}
function deleteUser(i) {
    $('#delUserName')[0].value = (users[i].username);
    $('#delUserPassword')[0].value = (users[i].password);
    $('#delFirstName')[0].value = (users[i].firstName);
    $('#delUserLastName')[0].value = (users[i].lastName);
    $('#delUserAge')[0].value = (users[i].age);

    $('#deleteUserRole')[0].selected = users[i].roles.includes("USER");
    $('#deleteAdminRole')[0].selected = users[i].roles.includes("ADMIN");

    /*$("#SubmitDeleteUser").click(function(){submitDelete(i);});*/
    $("#buttonSubmitDeleteContainer")[0].innerHTML =
        '<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>'
        + '<button type="submit" id="SubmitDeleteUser" class="btn btn-danger" onclick="submitDelete('
        + i +
        ')">Delete user</button>';

    $('#deleteModal').modal('show');
}

function submitDelete(i) {
    console.log('submit delete' + i)
    let deletingUser = JSON.stringify(users[i]);
    $.ajax({
        type: "POST",
        url: "/admin/delete",
        async: false,
        data: deletingUser,
        contentType: "application/json",
        complete: function () {
            users.splice(i, 1);
            drawTable(users);
        }
    });
    $('#deleteModal').modal('hide');

}