(function () {
    $(document).ready(function () {

        $("#login-form").submit(function () {
            login();
            return false;
        });

        $('#missiles-form').submit(function() {
            launchTheMissiles();
            return false;
        });

        loadUserState();
    });

    function refreshMissileStatus() {
        $.ajax('/service/missileStatus').success(function(data) {
            $('#missile-status').text(data);
        });
    }

    function launchTheMissiles() {
        $.post("/service/launchTheMissiles").success(refreshMissileStatus);
    }

    function loadUserState() {
        $.ajax("/service/userState", {
            contentType: "json"
        }).success(function (user, result, xhr) {
            initUI(user)
        });
    }

    function initUI(userState) {
        var loggedIn = userState['loggedIn'];
        $("#missiles-form").css("display", loggedIn ? "block" : "none");
        $("#login-form").css("display", loggedIn ? "none" : "block");
        refreshMissileStatus();
    }

    function login() {
        $.ajax("/service/login", {
            method: "POST",
            data: {
                username: $("#username-text").val(),
                password: $("#password-text").val()
            }
        }).success(function (user, result, xhr) {
            initUI(user);
        });
    }
}());