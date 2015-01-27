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
            success: function(entries, result, xhr){
                $("#entries").html("");
                entries.map(function(entry){
                    $("#entries").append("<div class='entry'>" + entry.contents + "</div>");
                });
            }
        });
    }
}());