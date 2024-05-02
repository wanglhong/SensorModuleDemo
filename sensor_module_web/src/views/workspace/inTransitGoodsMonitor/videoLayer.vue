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
<!--        <video-->
<!--          :src="videoURL"-->
<!--          autoplay="autoplay"-->
<!--          loop-->
<!--          controls-->
<!--          style="width: 100%"-->
<!--        ></video>-->
        <video
            id="my-video"
            class="video-js vjs-default-skin"
            controls
            preload="auto"
            width="500px"
        >
          <source src="http://wlih.cn:8082/hls/1003.m3u8" type="application/x-mpegURL" />
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
import { ref, toRefs, reactive } from 'vue'
import { info } from '@/api/module/videoInfo.js'
import videojs from 'video.js'

const props = defineProps({
  layerVisible: Boolean,
  title: String,
  videoData: Object,
})
const { layerVisible, title, videoData } = toRefs(props)
const emits = defineEmits(['toCancel'])

const videoURL = ref('http://vjs.zencdn.net/v/oceans.mp4')

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
    }
  })
}

/**
 * 弹窗关闭后执行
 */
function layerClose() {}

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
