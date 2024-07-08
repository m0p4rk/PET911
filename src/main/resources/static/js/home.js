$(document).ready(function() {
    $('#questionForm').on('submit', function(event) {
        event.preventDefault();
        const question = $('#question').val();

        $.ajax({
            url: '/api/query',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({ questionText: question }),
            success: function(response) {
                displayResponse(response);
            },
            error: function(xhr, status, error) {
                $('#response').html('<div class="alert alert-danger">Error: ' + error + '</div>');
            }
        });
    });

    function displayResponse(response) {
        let responseHtml = '<div class="card"><div class="card-body">';

        if (response.category === 'HEALTH_GUIDE') {
            responseHtml += '<h5 class="card-title">Health Guide</h5>';
            responseHtml += '<div class="alert alert-info">' + response.responseText + '</div>';
        } else if (response.category === 'EMERGENCY') {
            responseHtml += '<h5 class="card-title">Emergency</h5>';
            responseHtml += '<div class="alert alert-danger">' + response.responseText + '</div>';
        } else {
            responseHtml += '<h5 class="card-title">General Information</h5>';
            responseHtml += '<div class="alert alert-secondary">' + response.responseText + '</div>';
        }

        responseHtml += '</div></div>';
        $('#response').html(responseHtml);
    }
});