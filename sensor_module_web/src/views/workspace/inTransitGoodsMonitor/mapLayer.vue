<template>
  <lay-layer
    v-model="layerVisible"
    :title="title"
    :success="layerLoad"
    :end="layerClose"
    :closeBtn="false"
    :area="['1000px', 'auto']"
  >
    <div style="padding: 20px">
      <div class="layer-content" id="map"></div>
      <br />
      <div class="layer-footer">
        <lay-button size="sm" @click="toCancel">关闭</lay-button>
      </div>
    </div>
  </lay-layer>
</template>

<script setup>
import { ref, toRefs, reactive } from 'vue'
import carImg from '@/assets/car.png'
import io from 'socket.io-client'

const props = defineProps({
  layerVisible: Boolean,
  title: String,
  mapData: Object,
})
const { layerVisible, title, mapData } = toRefs(props)
const emits = defineEmits(['toCancel'])
let socket = reactive(null)

let map = reactive(null)

let latlngs = ref([])

let speedList = ref([])

// 轨迹线
let routeLine = reactive(null)
// 实时轨迹线
let realRouteLine = reactive(null)
// 轨迹方向箭头
let decorator = reactive(null)
let carIcon = L.icon({
  iconSize: [37, 26],
  iconAnchor: [19, 13],
  iconUrl: carImg,
})
// 动态marker
let animatedMarker = reactive(null)
let newLatlngs = ref([])
let center = ref([39.924317, 116.390619])

function initMap() {
  // 初始化地图
  map = L.map('map', {
    center: center.value,
    zoom: 13,
    preferCanvas: true, // 使用canvas模式渲染矢量图形
  })
  // 添加底图
  let tiles = L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png').addTo(map)
}

function darwLine() {
  routeLine = L.polyline(latlngs.value, {
    weight: 8,
  }).addTo(map)
  realRouteLine = L.polyline([], {
    weight: 8,
    color: '#FF9900',
  }).addTo(map)
  decorator = L.polylineDecorator(routeLine, {
    patterns: [
      {
        repeat: 50,
        symbol: L.Symbol.arrowHead({
          pixelSize: 5,
          headAngle: 75,
          polygon: false,
          pathOptions: {
            stroke: true,
            weight: 2,
            color: '#FFFFFF',
          },
        }),
      },
    ],
  }).addTo(map)
  animatedMarker = L.animatedMarker(routeLine.getLatLngs(), {
    speedList: speedList.value,
    interval: 200, // 默认为100mm
    icon: carIcon,
    playCall: updateRealLine,
  }).addTo(map)
  newLatlngs.value = [routeLine.getLatLngs()[0]]
  startClick()
}

// 绘制已行走轨迹线（橙色那条）
function updateRealLine(latlng) {
  newLatlngs.value.push(latlng)
  realRouteLine.setLatLngs(newLatlngs.value)
}

// 销毁地图
function destroyMap() {
  removeMarker()
  map.off()
  map.remove()
}

// 移除标记
function removeMarker() {
  stopClick()
  animatedMarker.removeFrom(map)
}

let speetX = 1 // 默认速度倍数
// 加速
function speetUp() {
  speetX = speetX * 2
  animatedMarker.setSpeetX(speetX)
}

// 减速
function speetDown() {
  speetX = speetX / 2
  animatedMarker.setSpeetX(speetX)
}

// 开始
function startClick() {
  animatedMarker.start()
}

// 暂停
function pauseClick() {
  animatedMarker.pause()
}

// 停止
function stopClick() {
  newLatlngs.value = []
  animatedMarker.stop()
}

//
function getMarker(data) {
  if (animatedMarker) {
    removeMarker()
  }
  latlngs.value = []
  speedList.value = []
  map.panTo(data.center[0])
  latlngs.value = data.latlngs
  const speedNum = parseInt(data.latlngs.length / 5)
  let count = speedNum
  let maxSpeed = 5
  data.latlngs.forEach((item, index) => {
    speedList.value.push(maxSpeed)
    count--
    if (count < 1) {
      count = speedNum
      if (maxSpeed > 1) {
        maxSpeed--
      }
    }
  })
  darwLine()
}

/**
 * 弹窗加载后执行
 */
function layerLoad() {
  initMap()
  // darwLine()
  const transportInfoId = mapData.value.id

  socket = io('ws://wlih.cn:8081', {
    transports: ['websocket'],
  })

  socket.emit('GET_TRANSPORT_ROUTE', transportInfoId)

  // 接收事件
  socket.on('GET_TRANSPORT_ROUTE', (e) => {
    if (JSON.parse(e).success) {
      const { data } = JSON.parse(e)
      getMarker(data)
    }
  })
}

/**
 * 弹窗关闭后执行
 */
function layerClose() {
  destroyMap()
  socket.disconnect()
}

/**
 * 取消
 */
function toCancel() {
  emits('toCancel', false)
}
</script>
<style lang="less" scoped>
.layer-content {
  width: 100%;
  height: 600px;
  background-color: #ddd;
  border-radius: 5px;
  overflow: hidden;
}
.layer-footer {
  padding: 0 20px;
  text-align: right;
}
</style>
