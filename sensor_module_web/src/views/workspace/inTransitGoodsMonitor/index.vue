<template>
  <lay-container :fluid="true" style="padding: 10px">
    <lay-row space="10">
      <lay-col :md="24">
        <search-box
          @toSearch="toSearch"
          @renewalModelDto="renewalModelDto"
        ></search-box>
      </lay-col>
      <lay-col :md="24">
        <lay-card>
          <lay-table
            :columns="columns"
            :data-source="dataSource"
            v-model:expandKeys="expandKeys"
          >
            <template v-slot:userName="{ data }">
              {{ data.transportUser.userName }}
            </template>
            <template v-slot:equipmentCode="{ data }">
              {{ data.transportEquipment.equipmentCode }}
            </template>
            <template v-slot:equipmentType="{ data }">
              {{ data.transportEquipment.equipmentType }}
            </template>
            <template v-slot:sendOrganization="{ data }">
              {{ data.sendOrganization.organizationName }}
            </template>
            <template v-slot:sendDate="{ data }">
              {{ dayjs(data.sendDate).format('YYYY-MM-DD HH:mm') }}
            </template>
            <template v-slot:estimateDate="{ data }">
              {{ dayjs(data.estimateDate).format('YYYY-MM-DD HH:mm') }}
            </template>
            <template v-slot:actualDate="{ data }">
              {{ dayjs(data.actualDate).format('YYYY-MM-DD HH:mm') }}
            </template>
            <template v-slot:receiveOrganization="{ data }">
              {{ data.receiveOrganization.organizationName }}
            </template>
            <template v-slot:expand="{ data }">
              <div class="expand-content">
                <div class="expand-content-btn">
                  <lay-button
                    border="blue"
                    border-style="dashed"
                    @click="viewInfo('报关信息', data)"
                    >报关信息</lay-button
                  >
                </div>
                <div class="expand-content-btn">
                  <lay-button
                    border="green"
                    border-style="dashed"
                    @click="viewInfo('清关信息', data)"
                    >清关信息</lay-button
                  >
                </div>
                <div class="expand-content-btn">
                  <lay-button
                    border="orange"
                    border-style="dashed"
                    @click="viewMap(data)"
                    >运输路线地图</lay-button
                  >
                </div>
                <div class="expand-content-btn">
                  <lay-button
                    border="red"
                    border-style="dashed"
                    @click="viewVideo(data)"
                    >在线监控</lay-button
                  >
                </div>
              </div>
            </template>
            <template v-slot:footer>
              <lay-page
                v-model="page.pageNum"
                :layout="page.layout"
                v-model:limit="page.pageSize"
                :pages="page.pages"
                :total="page.total"
                @change="change"
              ></lay-page>
            </template>
          </lay-table> </lay-card
      ></lay-col>
    </lay-row>

    <map-layer
      :layerVisible="mapVisible"
      :title="'运输路线地图'"
      :mapData="transportInfo"
      @toCancel="toCancelMap"
    ></map-layer>

    <video-layer
      :layerVisible="videoVisible"
      :title="'在线监控'"
      :videoData="transportInfo"
      @toCancel="toCancelVideo"
    ></video-layer>

    <info-layer
      :layerVisible="infoVisible"
      :title="infoTitle"
      :infoData="transportInfo"
      @toCancel="toCancelInfo"
    ></info-layer>
  </lay-container>
</template>
<script setup>
import mapLayer from './mapLayer.vue'
import videoLayer from './videoLayer.vue'
import infoLayer from './infoLayer.vue'
import { ref, onMounted, reactive } from 'vue'
import { list } from '@/api/module/TransportInfoApi.js'
import SearchBox from './SearchBox.vue'
import dayjs from 'dayjs'

let modelDto = reactive({})
const loading = ref(false)
const page = reactive({
  // 当前页
  pageNum: 1,
  // 每页数量
  pageSize: 10,
  // 总条数
  total: 0,
  // 显示切页按钮数量
  pages: 3,
  // 不同部分的展示（count -> 总条数, page -> 页码选择器, prev -> 上一页, next -> 下一页, limits -> 每页的数量选择, refresh -> 刷新,  skip -> 跳页,）。
  layout: ref(['count', 'prev', 'page', 'next', 'limits', 'refresh', 'skip']),
})

const columns = [
  {
    title: '运输名称',
    key: 'transportInfoName',
    ellipsisTooltip: true,
    align: 'center',
    sort: 'desc',
  },
  {
    title: '运输人',
    key: 'userId',
    customSlot: 'userName',
    ellipsisTooltip: true,
    align: 'center',
  },
  {
    title: '运输工具',
    key: 'transportEquipmentId',
    customSlot: 'equipmentCode',
    ellipsisTooltip: true,
    align: 'center',
  },
  {
    title: '运输方式',
    key: 'transportMode',
    customSlot: 'equipmentType',
    ellipsisTooltip: true,
    align: 'center',
  },
  {
    title: '发货公司',
    key: 'sendOrganizationId',
    customSlot: 'sendOrganization',
    ellipsisTooltip: true,
    align: 'center',
  },
  {
    title: '收货公司',
    key: 'receiveOrganizationId',
    customSlot: 'receiveOrganization',
    ellipsisTooltip: true,
    align: 'center',
  },
  {
    title: '起运国',
    key: 'sendCountry',
    ellipsisTooltip: true,
    align: 'center',
  },
  {
    title: '目的地国',
    key: 'receiveCountry',
    ellipsisTooltip: true,
    align: 'center',
  },
  {
    title: '起运时间',
    key: 'sendDate',
    customSlot: 'sendDate',
    ellipsisTooltip: true,
    align: 'center',
  },
  {
    title: '预计过境时间',
    key: 'estimateDate',
    customSlot: 'estimateDate',
    ellipsisTooltip: true,
    align: 'center',
  },
  {
    title: '实际过境时间',
    key: 'actualDate',
    customSlot: 'actualDate',
    ellipsisTooltip: true,
    align: 'center',
  },
  {
    title: '备注',
    key: 'transportState',
    ellipsisTooltip: true,
    align: 'center',
  },
  { title: '备注', key: 'remark', ellipsisTooltip: true, align: 'center' },
]

let dataSource = ref([])
const expandKeys = ref([])

function loadDataSource() {
  loading.value = true
  modelDto.transportState = '运输中'
  list({ modelDto: modelDto, page: page })
    .then(({ success, code, msg, data }) => {
      console.log('data', data)
      loading.value = false
      if (success) {
        dataSource.value = data.data
        page.total = data.total
      } else {
        layer.msg(msg, { icon: 2, time: 2000 })
      }
    })
    .catch((err) => {
      loading.value = false
      layer.msg(err, { icon: 2, time: 2000 })
    })
}

/**
 * 查询
 */
function toSearch() {
  loadDataSource()
}

/**
 * 更新modelDto
 * @param newModelDto 新的modelDto
 */
function renewalModelDto(newModelDto) {
  modelDto = newModelDto
}

let transportInfo = reactive({})

// 地图
let mapVisible = ref(false)

// 视频
let videoVisible = ref(false)

// 信息弹窗标题
let infoTitle = ref('')
let infoVisible = ref(false)

// 打开地图
function viewMap(data) {
  transportInfo = data
  mapVisible.value = true
}

// 关闭地图
function toCancelMap() {
  mapVisible.value = false
}

// 打开视频
function viewVideo(data) {
  transportInfo = data
  videoVisible.value = true
}

// 关闭视频
function toCancelVideo() {
  videoVisible.value = false
}

// 打开信息
function viewInfo(title, data) {
  infoTitle.value = title
  transportInfo = data
  infoVisible.value = true
}

// 关闭信息
function toCancelInfo() {
  infoVisible.value = false
}
/**
 * 使用 onMounted 生命周期钩子在组件挂载完成后加载数据
 */
onMounted(() => {
  loadDataSource()
})
</script>

<style lang="less" scoped>
.expand-content {
  display: flex;
  align-items: center;
  justify-content: center;
  .expand-content-btn {
    flex: 1;
    text-align: center;
  }
}
</style>
