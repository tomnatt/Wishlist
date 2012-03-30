$(document).ready(function() {
    // add the links
    $(".taglist li").wrapInner('<a href="#" class="tag" />');
    // onclick add the tag to the list
    $(".taglist li a.tag").click(function() {
        $("#tags").val($("#tags").val() + ", " + $(this).text());
    });
});
