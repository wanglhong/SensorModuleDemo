<template>
  <lay-container fluid="true" style="padding: 10px">
    <lay-row :space="10">
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
            :dataSource="dataSource"
            :loading="loading"
            :default-toolbar="true"
            v-model:selectedKeys="selectedIdList"
            @change="change"
            class="table-box"
          >
            <template v-slot:toolbar>
              <lay-button
                size="sm"
                type="primary"
                @click="displayFromLay('新增', customsDeclarationInfoDto())"
              >
                <lay-icon class="layui-icon-addition" /> 新增
              </lay-button>
              <lay-button size="sm" @click="removeList">
                <lay-icon class="layui-icon-delete" /> 删除
              </lay-button>
            </template>
            <template v-slot:transportInfo="{ data }">
              {{ data.transportInfo.transportInfoName }}
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
            <template v-slot:operator="{ data }">
              <lay-button size="xs" type="normal" @click="uploadDoc(data)"
                >附件管理</lay-button
              >
              <lay-button
                size="xs"
                type="primary"
                @click="displayFromLay('修改', data)"
                >修改</lay-button
              >
              <lay-popconfirm
                content="确定要删除此数据吗?"
                @confirm="removeOne(data.id)"
                @cancel="cancel"
              >
                <lay-button size="xs" border="red" border-style="dashed"
                  >删除</lay-button
                >
              </lay-popconfirm>
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
          </lay-table>
        </lay-card>
      </lay-col>
    </lay-row>
    <from-lay
      :displayFrom="displayFrom"
      :title="fromTitle"
      :modelData="modelData"
      @toCancel="closeFromLay"
      @loadDataSource="loadDataSource"
    />

    <lay-layer
      v-model="filesVisible"
      :title="'附件管理'"
      :area="['500px', 'auto']"
    >
      <div style="padding: 20px">
        <div class="file-list" v-for="file in filesList">
          <div class="file-name" @click="downloadFiles(file)">
            {{ file.fileName }}
          </div>
          <div class="file-btn">
            <lay-button
              size="xs"
              type="primary"
              @click="updateFile(file)"
              style="margin-right: 5px"
              >修改</lay-button
            >
            <lay-button
              size="xs"
              border="red"
              @click="delFile(file)"
              border-style="dashed"
              >删除</lay-button
            >
          </div>
        </div>
        <div class="file-add">
          <lay-button
            type="primary"
            @click="addFile()"
            style="margin-right: 5px"
            >新增附件</lay-button
          >
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
        <lay-button size="sm" @click="toFinish">关 闭</lay-button>
      </div>
    </lay-layer>

    <lay-layer
      v-model="uploadVisible"
      :title="fileTitle"
      :area="['500px', 'auto']"
    >
      <div style="padding: 20px">
        <lay-form :model="uploadForm" ref="uploadForm" required>
          <lay-form-item label="上传附件" prop="">
            <lay-upload
              ref="uploadRef"
              :url="`${baseUrl}/api/app/businessFile/upload`"
              v-model="uploadFiles"
              field="uploadFile"
              @done="doneUpload"
            >
              <template #preview>
                <div>{{ uploadFiles[uploadFiles.length - 1]?.fileName }}</div>
              </template>
            </lay-upload>
          </lay-form-item>
          <lay-form-item label="备注" prop="">
            <lay-textarea
              placeholder="请输入备注"
              v-model="uploadForm.remark"
            />
          </lay-form-item>
        </lay-form>
      </div>
      <div style="width: 100%; text-align: center; margin-bottom: 20px">
        <lay-button size="sm" type="primary" @click="toSubmit">提交</lay-button>
        <lay-button size="sm" @click="toCancel">取消</lay-button>
      </div>
    </lay-layer>
  </lay-container>
</template>

<script setup>
import { onMounted, ref, reactive } from 'vue'
import dayjs from 'dayjs'
import { layer } from '@layui/layer-vue'
import FromLay from '@/views/customInfo/customsDeclarationInfo/FormLay.vue'
import SearchBox from '@/views/customInfo/customsDeclarationInfo/SearchBox.vue'
import {
  list,
  remove,
  removeByIdList,
} from '@/api/module/customsDeclarationInfo.js'
import { customsDeclarationInfoDto } from '@/model/ModelDto.js'
import {
  add as fileAdd,
  delFile as fileRemove,
} from '@/api/module/declarationInfoBusinessFile.js'
import { downloadFile } from '@/api/module/businessFile.js'
import JSONbig from 'json-bigint'
const JSONbigString = new JSONbig({ storeAsString: true })

const baseUrl = 'http://localhost:8080'

let modelDto = reactive({})
let modelData = reactive({})
const loading = ref(false)
const selectedIdList = ref([])
const displayFrom = ref(false)
const fromTitle = ref('新增')
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
    title: '复选',
    width: '50px',
    type: 'checkbox',
    fixed: 'left',
    align: 'center',
  },
  {
    title: '运输名称',
    key: 'transportInfoId',
    customSlot: 'transportInfo',
    ellipsisTooltip: true,
    align: 'center',
  },
  {
    title: '报关名称',
    key: 'customsDeclarationName',
    ellipsisTooltip: true,
    align: 'center',
  },
  {
    title: '报关单号',
    key: 'customsDeclarationNumber',
    ellipsisTooltip: true,
    align: 'center',
  },
  {
    title: 'HS编码',
    key: 'hsCode',
    ellipsisTooltip: true,
    align: 'center',
  },
  {
    title: '货物价值',
    key: 'goodsValue',
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
    title: '操作',
    ellipsisTooltip: true,
    customSlot: 'operator',
    width: '200px',
    key: 'operator',
    fixed: 'right',
    align: 'center',
  },
]
let dataSource = ref([])

const listPage = reactive({
  // 当前页
  pageNum: 1,
  // 每页数量
  pageSize: 100,
})

// 当前id
const currenId = ref(null)
// 附件管理弹窗
let filesVisible = ref(false)
// 文件列表
let filesList = ref([])
let fileTitle = ref('上传附件')
let updateFiles = reactive({})
// 文件上传弹出层
let uploadVisible = ref(false)
// 上传的文件
let uploadFiles = ref([])
// 文件上传表单
let uploadForm = reactive({
  declarationInfoId: null,
  businessFileId: null,
  remark: null,
})

function loadDataSource() {
  loading.value = true
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
 * 改变事件
 */
const change = function () {
  loadDataSource()
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

/**
 * 删除选中数据
 * @param id 需要删除的数据的id
 */
function removeOne(id) {
  let load = layer.load
  remove({ id: id })
    .then(({ success, code, msg, data }) => {
      layer.close(load)
      if (success) {
        layer.msg(msg, { icon: 1 })
        loadDataSource()
      } else {
        layer.msg(msg, { icon: 2, time: 2000 })
      }
    })
    .catch((err) => {
      layer.close(load)
      layer.msg(err, { icon: 2, time: 2000 })
    })
}

/**
 * 取消
 */
function cancel() {
  layer.msg('您已取消操作')
}

/**
 * 删除选中数据
 */
function removeList() {
  if (selectedIdList.value.length < 1) {
    layer.msg('请选择要删除的数据', { icon: 7, time: 2000 })
  } else {
    let load = layer.load
    removeByIdList({ idList: selectedIdList.value })
      .then(({ success, code, msg, data }) => {
        layer.close(load)
        if (success) {
          layer.msg(msg, { icon: 1 })
          selectedIdList.value = []
          loadDataSource()
        } else {
          layer.msg(msg, { icon: 2, time: 2000 })
        }
      })
      .catch((err) => {
        layer.close(load)
        layer.msg(err, { icon: 2, time: 2000 })
      })
  }
}

/**
 * 上传附件按钮
 * @param {*} data
 */
function uploadDoc(data) {
  currenId.value = data.id
  if (data.businessFileList && data.businessFileList.length) {
    filesList.value = data.businessFileList
  }
  filesVisible.value = true
}

// 下载文件
function downloadFiles(file) {
  const fileName = file.fileName
  const param = {
    id: file.id,
  }
  downloadFile(param, fileName)
}

// 新增文件
function addFile() {
  uploadForm = {
    declarationInfoId: null,
    businessFileId: null,
    remark: null,
  }
  fileTitle.value = '新增附件'
  uploadFiles.value = []
  uploadVisible.value = true
}

// 修改文件
function updateFile(file) {
  addFile()
  updateFiles = file
  fileTitle.value = '修改附件'
}

// 删除文件
function delFile(file, isUpdate) {
  const param = {
    businessFileId: file.id,
    declarationInfoId: currenId.value,
  }
  fileRemove(param)
    .then(({ success, code, msg, data }) => {
      if (success) {
        layer.msg(msg, { icon: 1 })
        for (let i = filesList.value.length - 1; i >= 0; i--) {
          if (file.id === filesList.value[i].id) {
            if (isUpdate) {
              filesList.value.splice(i, 1, uploadFiles.value[0])
            } else {
              filesList.value.splice(i, 1)
            }
          }
        }
      } else {
        layer.msg(msg, { icon: 2, time: 2000 })
      }
    })
    .catch((err) => {
      layer.msg(err.message, { icon: 2, time: 2000 })
    })
}

function toFinish() {
  filesVisible.value = false
}

// 文件上传完成
function doneUpload(file) {
  let data = JSONbigString.parse(file.data)
  uploadFiles.value = [data.data]
  const id = data.data.id
  uploadForm['businessFileId'] = id
}

// 文件上传提交
function toSubmit() {
  uploadForm.declarationInfoId = currenId.value
  fileAdd(uploadForm)
    .then(({ success, code, msg, data }) => {
      if (success) {
        layer.msg(msg, { icon: 1 })
        // 修改成功后删除原附件
        if (fileTitle.value === '修改附件') {
          delFile(updateFiles, true)
        } else {
          filesList.value.push(uploadFiles.value[0])
        }
        loadDataSource()
        toCancel()
      } else {
        layer.msg(msg, { icon: 2, time: 2000 })
      }
    })
    .catch((err) => {
      layer.msg(err.message, { icon: 2, time: 2000 })
    })
}

function toCancel() {
  uploadVisible.value = false
  fileTitle.value === '上传附件'
}

/**
 * 显示 From Lay
 */
function displayFromLay(title, data) {
  fromTitle.value = title
  displayFrom.value = true
  modelData = data
}

/**
 * 关闭 From Lay
 */
const closeFromLay = () => {
  fromTitle.value = '标题'
  displayFrom.value = false
}

/**
 * 使用 onMounted 生命周期钩子在组件挂载完成后加载数据
 */
onMounted(() => {
  loadDataSource()
})
</script>

<style scoped>
.table-box {
  margin-top: 10px;
  padding: 10px;
  height: 700px;
  width: 100%;
  border-radius: 4px;
  box-sizing: border-box;
  background-color: #fff;
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
.file-add {
  margin: 10px;
}
</style>
