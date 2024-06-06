var contentType = document.getElementById("s-content").value;




var markerLatElements = document.querySelectorAll('.marker-lat');
var markerLongElements = document.querySelectorAll('.marker-long');
var markerTitleElements = document.getElementsByClassName("marker-title");



document.getElementById("btn-search").addEventListener("click", function() {
    // 선택된 옵션의 값을 가져옴
    var selectedValue = document.getElementById("search-content-id").value;

    // 가져온 값을 출력 또는 다른 작업 수행
    console.log("Selected value:", selectedValue);
});


markerLatElements = Array.from(markerLatElements);
markerLongElements = Array.from(markerLongElements);



var latitudes = [];
var longitudes = [];


markerLatElements.forEach(function(element) {
    var latitude = parseFloat(element.textContent.trim());
    latitudes.push(latitude);
});

// 경도(longitude) 값을 가져와서 배열에 저장
markerLongElements.forEach(function(element) {
    var longitude = parseFloat(element.textContent.trim());
    longitudes.push(longitude);
});



var positions = [];

for (var i = 0; i < latitudes.length && i < longitudes.length; i++) {

    let markerInfo = {

        title: markerTitleElements[i].textContent,
        contenttypeid: contentType,
        latlng: new kakao.maps.LatLng(latitudes[i], longitudes[i]),

    };

    positions.push(markerInfo);
}

var markers = [];
displayMarker();


function displayMarker() {
    // 마커 이미지의 이미지 주소입니다
    
    
    var imageSrc;

    for (var i = 0; i < positions.length; i++) {
        // 마커 이미지의 이미지 크기 입니다
    
        var imageSize = new kakao.maps.Size(25, 25);

        var id = positions[i].contenttypeid;
        
        if(id!=0){
    
            //타입마다 마커 이미지 다르게 설정
            if (id == "12") {
                imageSrc = "./img/mark/gwangang.png";
            } else if (id == "14") {
                imageSrc = "./img/mark/munwha.png";
            } else if (id == "15") {
                imageSrc = "./img/mark/festival.png";
            } else if (id == "25") {
                imageSrc = "./img/mark/trip.png";
            } else if (id == "28") {
                imageSrc = "./img/mark/sports.png";
            } else if (id == "32") {
                imageSrc = "./img/mark/sleep.png";
            } else if (id == "38") {
                imageSrc = "./img/mark/shopping.png";
            } else {
                imageSrc = "./img/mark/eat.png";
            }
        }
        else{
            imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png"; 
        }
        

        // 마커 이미지를 생성합니다
        var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);

        // 마커를 생성합니다
        var marker = new kakao.maps.Marker({
            map: map, // 마커를 표시할 지도
            position: positions[i].latlng, // 마커를 표시할 위치
            title: positions[i].title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
            image: markerImage, // 마커 이미지
            clickable: true, // 클릭할 수 있는가.
        });
        

        // 마커에 표시할 인포윈도우를 생성합니다
            
        var infowindow = new kakao.maps.InfoWindow({
            content: positions[i].title, // 인포윈도우에 표시할 내용
        });

        kakao.maps.event.addListener(
            marker,
            "mouseover",
            makeOverListener(map, marker, infowindow)
        );

        kakao.maps.event.addListener(
            marker,
            "mouseout",
            makeOutListener(infowindow)
        );

        markers.push(marker);

    }

    // 첫번째 검색 정보를 이용하여 지도 중심을 이동 시킵니다
    map.setCenter(positions[0].latlng);
}

// 인포윈도우를 표시하는 클로저를 만드는 함수입니다
function makeOverListener(map, marker, infowindow) {
    return function() {
        infowindow.open(map, marker);
    };
}

// 인포윈도우를 닫는 클로저를 만드는 함수입니다
function makeOutListener(infowindow) {
    return function() {
        infowindow.close();
    };
}