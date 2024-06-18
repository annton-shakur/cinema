$(document).ready(function() {
    var selectedCategories = [];

    function updateSelectedCategories() {
        $("#selected-categories").empty();
        selectedCategories.forEach(function(category) {
            var categoryTag = $("<span class='category-tag' data-category-id='" + category.id + "'>" + category.name + "<span class='remove-category'> x</span></span>");
            categoryTag.find(".remove-category").on("click", function() {
                removeCategory($(this).parent().data('categoryId'));
            });
            $("#selected-categories").append(categoryTag);
        });

        var selectedCategoryIds = selectedCategories.map(c => c.id).join(",");
        $("#categoryIds").val(selectedCategoryIds);
    }

    function removeCategory(categoryId) {
        selectedCategories = selectedCategories.filter(function(category) {
            return category.id !== categoryId;
        });
        updateSelectedCategories();
    }

    $("#category-input").autocomplete({
        source: function(request, response) {
            $.ajax({
                url: '/api/categories/search',
                dataType: 'json',
                data: {
                    name: request.term
                },
                success: function(data) {
                    var availableCategories = data.filter(function(category) {
                        return !selectedCategories.find(c => c.id === category.id);
                    });
                    response(availableCategories.map(function(category) {
                        return {
                            label: category.name,
                            value: category.name,
                            id: category.id
                        };
                    }));
                }
            });
        },
        minLength: 1,
        select: function(event, ui) {
            var selectedCategory = {
                id: ui.item.id,
                name: ui.item.value
            };
            if (!selectedCategories.find(c => c.id === selectedCategory.id)) {
                selectedCategories.push(selectedCategory);
                updateSelectedCategories();
            }
            $(this).val('');
            return false;
        }
    });
});
