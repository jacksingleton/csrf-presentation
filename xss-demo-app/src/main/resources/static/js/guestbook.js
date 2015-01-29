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
                    $("#entry-form-text").val("");
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

        $("#filter-form").submit(function(keyCode){
            return false;
        });

        $("#filter-text").keyup(function(){
            window.location.hash="#filter=" + $(this).val();
            refresh();
        });

        $("#error-message").click(function(){
            $(this).hide();
        });

        refresh();
        loadUserState();
    });

    function deleteAllEntries() {
        $.ajax("/service/deleteEntries/", {
            method: "POST"
        }).success(function () {
            refresh();
        }).fail(function (ajax, state, errorMessage) {
            alert("TODO: display error dialog.");
        });
    }

    function getFilterText() {
        var matches = /#filter=(.*)/.exec(window.location.hash);
        if (matches && matches.length > 1) {
            return matches[1];
        } else {
            return "";
        }
    }

    function refresh() {
        $.ajax("/service/entries", {
            contentType: "json",
            data: {filter: getFilterText()}
        }).success(function (entries, result, xhr) {
            $("#entries").html("");
            entries.found.map(function (entry) {
                $("#entries").append("<div class='entry'>" + entry.contents + "</div>");
            });

            if (entries.found.length == 0 && entries.filter != "") {
                showError("No results for filter " + entries.filter);
            } else {
                $("#error-message").hide();
            }
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
            showError(errorMessage);
        });
    }

    function showError(message){
        $("#error-message").html(message);
        $("#error-message").show();
    }

}());