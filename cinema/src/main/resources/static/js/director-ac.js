$(document).ready(function() {
    $("#director-input").autocomplete({
        source: function(request, response) {
            $.ajax({
                url: '/api/directors/search',
                dataType: 'json',
                data: {
                    name: request.term
                },
                success: function(data) {
                    response(data.map(function(director) {
                        return {
                            label: director.name,
                            value: director.name,
                            id: director.id
                        };
                    }));
                }
            });
        },
        minLength: 2,
        select: function(event, ui) {
            $("#directorId").val(ui.item.id);
        }
    });
});
