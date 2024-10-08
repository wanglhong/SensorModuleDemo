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
                @click="displayFromLay('新增', transportInfoDto())"
              >
                <lay-icon class="layui-icon-addition" /> 新增
              </lay-button>
              <lay-button size="sm" @click="removeList">
                <lay-icon class="layui-icon-delete" /> 删除
              </lay-button>
            </template>
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
            <template v-slot:receiveOrganization="{ data }">
              {{ data.receiveOrganization.organizationName }}
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
  </lay-container>
</template>

<script setup>
import { onMounted, ref, reactive } from 'vue'
import dayjs from 'dayjs'
import { layer } from '@layui/layer-vue'
import FromLay from '@/views/transportManagement/transportInfo/FormLay.vue'
import SearchBox from '@/views/transportManagement/transportInfo/SearchBox.vue'
import { list, remove, removeByIdList } from '@/api/module/TransportInfoApi.js'
import { transportInfoDto } from '@/model/ModelDto.js'
import { list as organization } from '@/api/module/organization.js'

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
    key: 'transportInfoName',
    ellipsisTooltip: true,
    align: 'center',
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
  {
    title: '操作',
    ellipsisTooltip: true,
    customSlot: 'operator',
    key: 'operator',
    fixed: 'right',
    align: 'center',
  },
]
let dataSource = ref([])

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

const listPage = reactive({
  // 当前页
  pageNum: 1,
  // 每页数量
  pageSize: 100,
})

organization(listPage).then((res) => {
  console.log('res', res)
})

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
</style>
