//TODO: refactor AJAX code
(function () {
    $(document).ready(function () {
        $("#entry-form").submit(function () {
            $.ajax("/service/entries/", {
                method: "POST",
                data: {
                    content: $("#entry-form-text").val()
                },
                success: function () {
                    loadUserState();
                    refresh();
                }
            });
            return false;
        });

        $("#delete-all-button").click(function () {
            deleteAllEntries();
            return false;
        });

        $("#login-form").submit(function () {
            login();
            return false;
        });

        refresh();
        loadUserState();
    });

    function deleteAllEntries() {
        $.ajax("/service/entries/", {
            method: "DELETE"
        }).success(function () {
            refresh();
        }).fail(function (ajax, state, errorMessage) {
            alert("TODO: display error dialog.");
        });
    }

    function refresh() {
        $.ajax("/service/entries", {
            contentType: "json"
        }).success(function (entries, result, xhr) {
            $("#entries").html("");
            entries.map(function (entry) {
                $("#entries").append("<div class='entry'>" + entry.contents + "</div>");
            });
        });
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
        $("#delete-all-form").css("display", loggedIn ? "block" : "none");
        $("#login-form").css("display", loggedIn ? "none" : "block");
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
        }).fail(function (ajax, state, errorMessage) {
            alert("TODO: display error dialog.");
        });
    }

}());