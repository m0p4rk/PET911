document.addEventListener("DOMContentLoaded", () => {
    const mapContainer = document.getElementById('map');
    let map;

    // 지도 초기화 함수
    function initMap(centerCoords) {
        const mapOption = {
            center: centerCoords,
            level: 3
        };
        map = new kakao.maps.Map(mapContainer, mapOption);
    }

    const locationInput = document.getElementById('location');
    const searchButton = document.getElementById('searchButton');
    const currentLocationButton = document.getElementById('currentLocationButton');

    locationInput.addEventListener('input', () => {
        searchButton.disabled = locationInput.value.trim() === '';
    });

    searchButton.addEventListener('click', () => {
        const address = locationInput.value.trim();
        if (address) {
            searchHospitalsByAddress(address);
        }
    });

    currentLocationButton.addEventListener('click', () => {
        getCurrentLocation();
    });

    function getCurrentLocation() {
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(position => {
                const coords = new kakao.maps.LatLng(position.coords.latitude, position.coords.longitude);
                markPosition(coords, false);
                initMap(coords);
                searchNearbyHospitals(coords);
            }, error => {
                console.error('Geolocation error: ', error);
                fallbackToDefaultLocation();
            });
        } else {
            alert('Geolocation is not supported by this browser.');
            fallbackToDefaultLocation();
        }
    }

    function searchHospitalsByAddress(address) {
        const geocoder = new kakao.maps.services.Geocoder();
        geocoder.addressSearch(address, function(result, status) {
            if (status === kakao.maps.services.Status.OK) {
                const coords = new kakao.maps.LatLng(result[0].y, result[0].x);
                markPosition(coords, true);
                initMap(coords);
                searchNearbyHospitals(coords);
            } else {
                alert('Invalid address.');
            }
        });
    }

    function searchNearbyHospitals(coords) {
        const places = new kakao.maps.services.Places(map);
        const keyword = '24시 동물병원';
        places.keywordSearch(keyword, function(data, status) {
            if (status === kakao.maps.services.Status.OK) {
                displayPlaces(data, coords);
            } else {
                alert('No hospitals found.');
            }
        }, {
            location: coords,
            radius: 5000
        });
    }

    // 검색된 병원의 정보를 표시하는 함수
    function displayPlaces(places, centerCoords) {
        const bounds = new kakao.maps.LatLngBounds();
        const infoArea = document.getElementById('info-area');
        infoArea.innerHTML = ''; // Clear previous info

        places.forEach((place, index) => {
            const markerPosition = new kakao.maps.LatLng(place.y, place.x);
            const marker = new kakao.maps.Marker({
                position: markerPosition,
                map: map,
                title: place.place_name
            });

            // 병원 정보를 표시하는 커스텀 오버레이
            const content = `<div style="cursor:pointer; background:white; padding:2px 4px; border:1px solid black; font-size:12px;" onclick="selectPlace('${place.place_name}', '${place.address_name}', '${place.phone}', '${place.id}')">${place.place_name}</div>`;
            const customOverlay = new kakao.maps.CustomOverlay({
                map: map,
                position: markerPosition,
                content: content,
                yAnchor: 1.6
            });

            bounds.extend(markerPosition);
        });

        map.setBounds(bounds);
    }

// 사용자가 오버레이를 클릭했을 때 선택된 병원의 상세 정보를 표시합니다.
    window.selectPlace = function (name, address, phone, id) {
        const infoArea = document.getElementById('info-area');
        if (infoArea) {
            infoArea.innerHTML = `
            <div class="card mt-2">
                <div class="card-body">
                    <h5 class="card-title">${name}</h5>
                    <p class="card-text">Address: ${address}</p>
                    <p class="card-text">Phone: ${phone}</p>
                    <a href="https://map.kakao.com/link/map/${id}" target="_blank" class="btn btn-primary">View on Map</a>
                </div>
            </div>
        `;
        }
    };


    function markPosition(coords, isSearchResult) {
        const imageUrl = isSearchResult ? 'http://t1.daumcdn.net/localimg/localimages/07/mapapidoc/red_b_marker.png' : 'http://t1.daumcdn.net/localimg/localimages/07/mapapidoc/blue_b_marker.png';
        const markerImage = new kakao.maps.MarkerImage(imageUrl, new kakao.maps.Size(24, 35));
        const marker = new kakao.maps.Marker({
            map: map,
            position: coords,
            image: markerImage
        });
        marker.setMap(map);
    }

    function fallbackToDefaultLocation() {
        const seoulCenter = new kakao.maps.LatLng(37.566535, 126.9779692);
        initMap(seoulCenter);
        searchNearbyHospitals(seoulCenter);
    }

    getCurrentLocation();
});
