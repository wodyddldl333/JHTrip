      // reset 버튼으로 모든 것을 잊기.
      document.getElementById("btn-reset").addEventListener("click", () => {
        if (positions.length == 0) {
          alert("input your data");
          return 0;
        }

        alert("여행경로를 재설정합니다.");

        function setMarkers(map) {
          for (var i = 0; i < markers.length; i++) {
            markers[i].setMap(map);
          }
        }

        function hideMarkers() {
          setMarkers(null);
        }
        hideMarkers();
        document.getElementById("trip-list").innerHTML = "";
        positions = [];
        
        
      });
