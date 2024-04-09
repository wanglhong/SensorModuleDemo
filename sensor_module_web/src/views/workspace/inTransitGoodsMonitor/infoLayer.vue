<template>
  <lay-layer
    v-model="layerVisible"
    :title="title"
    :success="layerLoad"
    :end="layerClose"
    :closeBtn="false"
    :area="['500px', 'auto']"
  >
    <div style="padding: 20px">
      <div class="layer-content">
        <div v-if="errMsg" style="text-align: center">{{ errMsg }}</div>
        <div v-else>
          <div class="layer-content-item">
            <div class="layer-content-item-title">
              {{ title === '报关信息' ? '报关名称' : '清关名称' }}
            </div>
            <div class="layer-content-item-content">
              {{ infoVal.name }}
            </div>
          </div>
          <div class="layer-content-item">
            <div class="layer-content-item-title">
              {{ title === '报关信息' ? '报关单号' : '清关单号' }}
            </div>
            <div class="layer-content-item-content">
              {{ infoVal.number }}
            </div>
          </div>
          <div class="layer-content-item" v-if="title === '报关信息'">
            <div class="layer-content-item-title">货物价值</div>
            <div class="layer-content-item-content">
              {{ infoVal.goodsValue }}
            </div>
          </div>
          <div class="layer-content-item" v-if="title === '报关信息'">
            <div class="layer-content-item-title">HS编码</div>
            <div class="layer-content-item-content">
              {{ infoVal.hsCode }}
            </div>
          </div>
          <div class="layer-content-item" v-if="title === '报关信息'">
            <div class="layer-content-item-title">预计过境时间</div>
            <div class="layer-content-item-content">
              {{
                dayjs(infoVal.estimatedTransitDate).format('YYYY-MM-DD HH:mm')
              }}
            </div>
          </div>
          <div class="layer-content-item" v-if="title === '报关信息'">
            <div class="layer-content-item-title">实际过境时间</div>
            <div class="layer-content-item-content">
              {{ dayjs(infoVal.actualTransitDate).format('YYYY-MM-DD HH:mm') }}
            </div>
          </div>
          <div class="layer-content-item" v-if="title === '清关信息'">
            <div class="layer-content-item-title">报关信息</div>
            <div class="layer-content-item-content">
              {{ infoVal.customsDeclarationInfo?.customsDeclarationName }}
            </div>
          </div>
          <div class="layer-content-item" v-if="title === '清关信息'">
            <div class="layer-content-item-title">检验检疫机构</div>
            <div class="layer-content-item-content">
              {{ infoVal.inspectionOrganization?.organizationName }}
            </div>
          </div>
          <div class="layer-content-item" v-if="title === '清关信息'">
            <div class="layer-content-item-title">检验检疫结果</div>
            <div class="layer-content-item-content">
              {{ infoVal.inspectionResult }}
            </div>
          </div>
          <div class="layer-content-item" v-if="title === '清关信息'">
            <div class="layer-content-item-title">关税和税费</div>
            <div class="layer-content-item-content">
              {{ infoVal.taxAndFees }}
            </div>
          </div>
          <div class="layer-content-item" v-if="title === '清关信息'">
            <div class="layer-content-item-title">放行日期</div>
            <div class="layer-content-item-content">
              {{ dayjs(infoVal.clearanceDate).format('YYYY-MM-DD HH:mm') }}
            </div>
          </div>
          <div class="layer-content-item" v-if="title === '清关信息'">
            <div class="layer-content-item-title">备注</div>
            <div class="layer-content-item-content">
              {{ infoVal.remark }}
            </div>
          </div>
          <div class="layer-content-item">
            <div class="layer-content-item-title">附件</div>
            <div class="layer-content-item-content">
              <lay-button size="sm" type="primary" @click="viewDoc"
                >查看</lay-button
              >
            </div>
          </div>
        </div>
      </div>
      <br />
      <div class="layer-footer">
        <lay-button size="sm" @click="toCancel">关闭</lay-button>
      </div>
    </div>
  </lay-layer>
  <lay-layer
    v-model="filesVisible"
    :title="'附件信息'"
    :area="['500px', 'auto']"
  >
    <div style="padding: 20px">
      <div class="file-list" v-for="file in filesList">
        <div class="file-name">
          {{ file.fileName }}
        </div>
        <div class="file-btn">
          <lay-button
            size="xs"
            type="primary"
            @click="downloadFiles(file)"
            style="margin-right: 5px"
            >查看</lay-button
          >
        </div>
      </div>
    </div>
    <div
      style="
        width: 100%;
        text-align: right;
        margin-bottom: 20px;
        padding: 0 20px;
        box-sizing: border-box;
      "
    >
      <lay-button size="sm" @click="toClose">关 闭</lay-button>
    </div>
  </lay-layer>
</template>

<script setup>
import { ref, toRefs, reactive } from 'vue'
import dayjs from 'dayjs'
import { info as clearInfo } from '@/api/module/customsClearanceInfo'
import { info as declarationInfo } from '@/api/module/customsDeclarationInfo'
import { downloadFile } from '@/api/module/businessFile.js'

const props = defineProps({
  layerVisible: Boolean,
  title: String,
  infoData: Object,
})
const { layerVisible, title, infoData } = toRefs(props)
const emits = defineEmits(['toCancel'])

let infoVal = ref({})
let errMsg = ref('')

// 附件查看弹窗
let filesVisible = ref(false)
// 附件列表
let filesList = ref([])

/**
 * 弹窗加载后执行
 */
function layerLoad() {
  const params = {
    transportInfoId: infoData.value.id,
  }
  infoVal.value = {}
  errMsg.value = ''
  if (title.value === '报关信息') {
    declarationInfo(params)
      .then((res) => {
        console.log('res1', res)
        if (res.success) {
          infoVal.value = res.data
          infoVal.value.name = res.data.customsDeclarationName
          infoVal.value.number = res.data.customsDeclarationNumber
        } else {
          errMsg.value = res.msg
        }
      })
      .catch((err) => {
        errMsg.value = err.message
      })
  } else {
    clearInfo(params)
      .then((res) => {
        console.log('res2', res)
        if (res.success) {
          infoVal.value = res.data
          infoVal.value.name = res.data.customsClearanceName
          infoVal.value.number = res.data.customsClearanceNumber
        } else {
          errMsg.value = res.msg
        }
      })
      .catch((err) => {
        errMsg.value = err.message
      })
  }
}

/**
 * 查看附件
 * @param {*} data
 */
function viewDoc() {
  filesVisible.value = true
  if (infoVal.value.businessFileList && infoVal.value.businessFileList.length) {
    filesList.value = infoVal.value.businessFileList
  }
}

/**
 * 下载
 * @param {*} file
 */
function downloadFiles(file) {
  const fileName = file.fileName
  const param = {
    id: file.id,
  }
  downloadFile(param, fileName)
}

function toClose() {
  filesVisible.value = false
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
<style lang="less" scoped>
.flex-box {
  display: flex;
  align-items: center;
}
.layer-content {
  width: 100%;
  max-height: 600px;
  border-radius: 5px;
  background-color: #fff;
  overflow: hidden;
  display: block;

  .layer-content-item:extend(.flex-box) {
    width: calc(100% - 20px);
    margin: 5px 10px;
    justify-content: center;
    .layer-content-item-title:extend(.flex-box) {
      width: 120px;
    }
    .layer-content-item-content:extend(.flex-box) {
      flex: 1;
      font-weight: 600;
      justify-content: end;
    }
  }
}
.layer-footer {
  padding: 0 20px;
  text-align: right;
}
.file-list {
  width: 100%;
  display: flex;
  align-items: center;
  margin: 5px 0;
  .file-name {
    flex: 1 0 auto;
    height: 100%;
    margin-right: 10px;
    cursor: pointer;
  }
  .file-btn {
    flex: 0 1 auto;
    height: 100%;
    margin-right: 10px;
  }
}
</style>
