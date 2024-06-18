$(document).ready(function() {
    let selectedActors = [];

    $("#actor-input").autocomplete({
        source: function(request, response) {
            $.ajax({
                url: '/api/actors/search',
                dataType: 'json',
                data: {
                    name: request.term
                },
                success: function(data) {
                    response(data.map(function(actor) {
                        return {
                            label: actor.name,
                            value: actor.name,
                            id: actor.id
                        };
                    }));
                }
            });
        },
        minLength: 2,
        select: function(event, ui) {
            if (!selectedActors.includes(ui.item.id)) {
                selectedActors.push(ui.item.id);
                $("#selected-actors").append(
                    `<div class="selected-actor" data-id="${ui.item.id}">${ui.item.label} <span class="remove-actor" style="cursor:pointer;">&times;</span></div>`
                );
                updateHiddenInput();
            }
            $("#actor-input").val('');
            return false;
        }
    });

    function updateHiddenInput() {
        $("#actorIds").val(selectedActors.join(','));
    }

    $("#selected-actors").on("click", ".remove-actor", function() {
        let actorDiv = $(this).closest('.selected-actor');
        let actorId = actorDiv.data('id');
        selectedActors = selectedActors.filter(id => id !== actorId);
        actorDiv.remove();
        updateHiddenInput();
    });
});
