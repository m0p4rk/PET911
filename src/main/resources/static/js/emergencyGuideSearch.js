$(document).ready(function() {
    // Load dashboard data on page load
    loadDashboardData();

    // Handle form submission
    $('#symptomForm').submit(function(e) {
        e.preventDefault();
        var species = $('#species').val();
        var symptom = $('#symptom').val();
        searchEmergencyGuide(species, symptom);
    });

    function loadDashboardData() {
        $.ajax({
            url: '/api/emergency-guide/dashboard',
            method: 'GET',
            success: function(data) {
                $('#totalGuides').text(data.totalGuides);
                $('#speciesCoverage').text(data.speciesCoverage);
                $('#commonSymptom').text(data.commonSymptom);
            },
            error: function(xhr, status, error) {
                console.error('Error loading dashboard data:', error);
            }
        });
    }

    function searchEmergencyGuide(species, symptom) {
        $.ajax({
            url: '/api/emergency-guide/search',
            method: 'GET',
            data: { species: species, symptom: symptom },
            success: function(data) {
                console.log('Received data:', JSON.stringify(data, null, 2));  // 데이터 구조 확인
                displayResults(data);
            },
            error: function(xhr, status, error) {
                console.error('Error:', error);
                $('#response').html('<div class="alert alert-danger">Error searching for emergency guide. Please try again.</div>');
            }
        });
    }

    function displayResults(data) {
        if (data.length === 0) {
            $('#response').html('<div class="alert alert-info">No emergency guides found for the given criteria.</div>');
            return;
        }

        var resultsHtml = '<div class="card"><div class="card-body"><h5 class="card-title">Emergency Guide Results</h5><ul class="list-group">';

        data.forEach(function(guide) {
            resultsHtml += '<li class="list-group-item">';
            resultsHtml += '<h6 class="mb-1">Step ' + (guide.guide_step || guide.guideStep) + '</h6>';  // 두 가지 가능한 이름 체크
            resultsHtml += '<p class="mb-1">' + (guide.guide_text || guide.guideText) + '</p>';  // 두 가지 가능한 이름 체크
            resultsHtml += '</li>';
        });

        resultsHtml += '</ul></div></div>';
        $('#response').html(resultsHtml);
    }
});