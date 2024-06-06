<template>
  <div id="map"></div>
</template>

<script setup>
import { ref, onMounted } from "vue";

var map;
const positions = ref([]);
const markers = ref([]);
const props = defineProps({ attractions: Array, selectAttraction: Object });

onMounted(() => {
  const script = document.createElement("script");
  script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${
    import.meta.env.VITE_KAKAO_MAP_SERVICE_KEY
  }&libraries=services,clusterer`;
  script.onload = () => kakao.maps.load(() => initMap());
  document.head.appendChild(script);
});

const initMap = () => {
  let linePath = [];
  props.attractions.forEach((attraction) => {
    let obj = {};
    obj.latlng = new kakao.maps.LatLng(
      attraction.latitude,
      attraction.longitude
    );
    obj.title = attraction.title;
    obj.firstImage = attraction.firstImage;
    obj.addr1 = attraction.addr1;

    positions.value.push(obj);
    linePath.push(obj.latlng);
  });

  const container = document.getElementById("map");
  const options = {
    center: new kakao.maps.LatLng(33.450701, 126.570667),
    level: 3,
  };
  map = new kakao.maps.Map(container, options);

  loadMarkers();

  var polyline = new kakao.maps.Polyline({
    path: linePath,
    strokeWeight: 3,
    strokeColor: "royalblue",
    strokeOpacity: 0.8,
    strokeStyle: "solid",
  });
  polyline.setMap(map);
};

const loadMarkers = () => {
  deleteMarkers();
  positions.value.forEach((position, index) => {
    const markerContent = document.createElement("div");
    markerContent.className = "custom-marker";
    markerContent.innerHTML = `<div class="custom-icon">${index + 1}</div>`;

    const customOverlay = new kakao.maps.CustomOverlay({
      position: position.latlng,
      content: markerContent,
      yAnchor: 1,
    });
    customOverlay.setMap(map);

    markers.value.push(customOverlay);

    const iwContent = `
      <div style="text-align: center;">
        <img
          src="${position.firstImage}"
          onerror="this.onerror=null; this.src='/img/noImage.jpg';"
          loading: lazy;
          style="
            border: 1px solid #ddd;
            border-radius: 4px;
            padding: 5px;
            margin-top: 10px;
            width: 200px;
            height: auto;
          "
        />
      </div>
      <div style="font-family: Arial, sans-serif; padding: 10px; max-width: 300px;">
        <h4 style="margin-top: 0; font-size: 18px; color: #333;">${position.title}</h4>
        <ul style="padding-left: 20px; margin: 10px 0;">
          <li style="font-size: 14px; color: #444;">${position.addr1}</li>
        </ul>
      </div>
    `;
    const infowindow = new kakao.maps.InfoWindow({
      content: iwContent,
      removable: true,
    });

    markerContent.addEventListener("click", () => {
      infowindow.open(map, customOverlay);
    });
  });

  const bounds = positions.value.reduce(
    (bounds, position) => bounds.extend(position.latlng),
    new kakao.maps.LatLngBounds()
  );

  map.setBounds(bounds);
};

const deleteMarkers = () => {
  if (markers.value.length > 0) {
    markers.value.forEach((marker) => marker.setMap(null));
  }
};
</script>

<style>
#map {
  margin: 0 auto;
  width: 1280px;
  height: 700px;
}

.custom-icon {
  display: inline-flex;
  justify-content: center;
  align-items: center;
  width: 40px;
  height: 40px;
  background-color: rgb(72, 51, 190);
  color: white;
  font-weight: bold;
  border-radius: 50%;
  font-size: 24px;
  cursor: pointer;
}
</style>
