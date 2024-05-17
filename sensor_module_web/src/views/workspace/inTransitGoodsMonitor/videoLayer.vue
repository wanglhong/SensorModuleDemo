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
      <div class="layer-content">
        <video
            ref="videoPlayer"
            preload="auto"
            autoplay="autoplay"
            style="width: 100%; height: 100%;"
        >
          <source :src="videoURL" type="application/x-mpegURL" />
        </video>
      </div>
      <br />
      <div class="layer-footer">
        <lay-button size="sm" @click="toCancel">关闭</lay-button>
      </div>
    </div>
  </lay-layer>
</template>

<script setup>
import { ref, toRefs } from 'vue'
import { info } from '@/api/module/videoInfo.js'
import videojs from 'video.js'
import 'video.js/dist/video-js.css';

const props = defineProps({
  layerVisible: Boolean,
  title: String,
  videoData: Object,
})
const { layerVisible, title, videoData } = toRefs(props)
const emits = defineEmits(['toCancel'])

const videoURL = ref('https://test-streams.mux.dev/x36xhzz/x36xhzz.m3u8')
const videoPlayer = ref(null);
let player = null;

function getVideo() {
  videojs(
      "my-video",
      {
        bigPlayButton: false,
        textTrackDisplay: false,
        posterImage: true,
        errorDisplay: false,
        controlBar: true
      },
      function() {
        this.play();
      }
  );
}

/**
 * 弹窗加载后执行
 */
function layerLoad() {
  console.log('videoData', videoData.value)
  const param = {
    transportInfoId: videoData.value.id,
  }
  info(param).then((res) => {
    console.log('res', res);
    if (res.success && res.data) {
      videoURL.value = res.data
      if (layerVisible.value) {
        player = videojs(videoPlayer.value, {
          autoplay: false,
          controls: true,
          sources: [{
            src: videoURL.value,
            type: 'application/x-mpegURL'
          }]
        });
      }
    }
  })
}

/**
 * 弹窗关闭后执行
 */
function layerClose() {
  if (player) {
    player.dispose();
  }
}

/**
 * 取消
 */
function toCancel() {
  emits('toCancel', false)
}
</script>
<style lang="less" scope>
.layer-content {
  width: 100%;
  max-height: 600px;
  background-color: #000;
  border-radius: 5px;
  overflow: hidden;
  display: flex;
}
.layer-footer {
  padding: 0 20px;
  text-align: right;
}
</style>
