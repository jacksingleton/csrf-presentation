;(function() {
    $(document).ready(function(){
        $("#entry-form").submit(function(){
            $.ajax("/service/entries/", {
                method: "POST",
                data: {
                    content: $("#entry-form-text").val()
                },
                success: function(){
                    refresh();
                }
            });
            return false;
        });

        refresh();
    });

    function refresh() {
        $.ajax("/service/entries", {
            contentType: "json",
            success: function(response, result, xhr){
                console.log(response);
            }
        });
    }
}());