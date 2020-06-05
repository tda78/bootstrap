
let req = getRequest();

let authority;
getAuthority();
use(authority);

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
    let headerString = "<b>" + user.username + "</b> with roles " + user.roles.join(', ');
    document.getElementById("headerString").innerHTML = headerString;


    let mainMenu = ' <ul class="nav nav-stacked nav-left nav-pills" >' +
        '<li class="active">' +
        '<a href="#userPanel" id="userTab" data-toggle="tab" role="tab">User</a>' +
        '</li>' +
        '</ul>'
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

