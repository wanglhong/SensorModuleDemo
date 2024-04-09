<template>
  <lay-layer
    v-model="displayFrom"
    :title="title"
    :success="layerLoad"
    :end="layerClose"
    :closeBtn="false"
    :area="['500px', 'auto']"
  >
    <div style="padding: 20px; max-height: 600px">
      <lay-form :model="modelDto" ref="layFormRef11" required>
        <lay-form-item label="运输名称" prop="transportInfoName">
          <lay-input
            placeholder="请输入运输名称"
            v-model="modelDto.transportInfoName"
          />
        </lay-form-item>
        <lay-form-item label="运输人" prop="userId">
          <lay-tree-select
            placeholder="请选择运输人"
            :data="treeDataOfTransportUser"
            v-model="modelDto.userId"
            :search="true"
            style="width: 100%"
          >
            <template #title="{ data }">
              <div>
                <lay-icon type="layui-icon-addition" v-if="!data.disabled" />
                {{ data.title }}
              </div>
            </template>
          </lay-tree-select>
        </lay-form-item>
        <lay-form-item label="运输工具" prop="transportEquipmentId">
          <lay-tree-select
            placeholder="请选择运输工具"
            :data="treeDataOfTransportEquipment"
            v-model="modelDto.transportEquipmentId"
            :search="true"
            style="width: 100%"
          >
            <template #title="{ data }">
              <div>
                <lay-icon type="layui-icon-addition" v-if="!data.disabled" />
                {{ data.title }}
              </div>
            </template>
          </lay-tree-select>
        </lay-form-item>
        <lay-form-item label="发货公司" prop="sendOrganizationId">
          <lay-select
            placeholder="请选择发货公司"
            v-model="modelDto.sendOrganizationId"
            style="width: 100%"
          >
            <template v-for="sendOrganization in organizationList">
              <lay-select-option :value="sendOrganization.id">
                {{ sendOrganization.name }}
              </lay-select-option>
            </template>
          </lay-select>
        </lay-form-item>
        <lay-form-item label="收货公司" prop="receiveOrganizationId">
          <lay-select
            placeholder="请选择收货公司"
            v-model="modelDto.receiveOrganizationId"
            style="width: 100%"
          >
            <template v-for="receiveOrganization in organizationList">
              <lay-select-option :value="receiveOrganization.id">
                {{ receiveOrganization.name }}
              </lay-select-option>
            </template>
          </lay-select>
        </lay-form-item>
        <lay-form-item label="起运国" prop="sendCountry">
          <lay-input
            placeholder="请输入起运国"
            v-model="modelDto.sendCountry"
          />
        </lay-form-item>
        <lay-form-item label="目的地国" prop="receiveCountry">
          <lay-input
            placeholder="请输入目的地国"
            v-model="modelDto.receiveCountry"
          />
        </lay-form-item>
        <lay-form-item label="起运时间" prop="sendDate">
          <lay-date-picker
            placeholder="请输入起运时间"
            v-model="modelDto.sendDate"
            :format="'YYYY-MM-DD HH:mm:ss'"
            type="datetime"
            style="width: 100%"
          />
        </lay-form-item>
        <lay-form-item label="预计过境时间" prop="estimateDate">
          <lay-date-picker
            placeholder="请输入预计过境时间"
            v-model="modelDto.estimateDate"
            :format="'YYYY-MM-DD HH:mm:ss'"
            type="datetime"
            style="width: 100%"
          />
        </lay-form-item>
        <lay-form-item label="实际过境时间" prop="actualDate">
          <lay-date-picker
            placeholder="请输入实际过境时间"
            v-model="modelDto.actualDate"
            :format="'YYYY-MM-DD HH:mm:ss'"
            type="datetime"
            style="width: 100%"
          />
        </lay-form-item>
        <lay-form-item label="运输状态" prop="remark">
          <lay-select
            placeholder="请选择运输状态"
            v-model="modelDto.transportState"
            style="width: 100%"
          >
            <template v-for="box in transportStateList">
              <lay-select-option :value="box.id">
                {{ box.name }}
              </lay-select-option>
            </template>
          </lay-select>
        </lay-form-item>
        <lay-form-item label="备注" prop="remark">
          <lay-textarea placeholder="请输入备注" v-model="modelDto.remark" />
        </lay-form-item>
      </lay-form>
      <div style="width: 100%; text-align: center">
        <lay-button size="sm" type="primary" @click="toSubmit">提交</lay-button>
        <lay-button size="sm" @click="toCancel">取消</lay-button>
      </div>
      <br />
    </div>
  </lay-layer>
</template>

<script setup>
import { ref, toRefs, reactive } from 'vue'
import { layer } from '@layui/layui-vue'
import { transportInfoDto } from '@/model/ModelDto.js'
import { add, update } from '@/api/module/TransportInfoApi.js'
import {
  getUserTree,
  getEquipmentTree,
  list as organization,
} from '@/api/module/organization.js'

const props = defineProps({
  displayFrom: Boolean,
  title: String,
  modelData: {
    type: Object,
    default: () => {
      return transportInfoDto()
    },
  },
})
const { displayFrom, title, modelData } = toRefs(props)
const emits = defineEmits(['toCancel', 'loadDataSource'])

let modelDto = transportInfoDto()
// TODO 运输人选择树
const treeDataOfTransportUser = ref([])
// TODO 运输工具选择树
const treeDataOfTransportEquipment = ref([])
// TODO 发货公司数据集
const organizationList = ref([])

const listPage = reactive({
  // 当前页
  pageNum: 1,
  // 每页数量
  pageSize: 100,
})

const transportStateList = ref([
  { id: '未开始', name: '未开始' },
  { id: '运输中', name: '运输中' },
  { id: '已结束', name: '已结束' },
])

organization(listPage).then(({ success, code, msg, data }) => {
  if (success) {
    layer.msg(msg, { icon: 1 })
    organizationList.value = data.data.map((item) => {
      return {
        ...item,
        name: item.organizationName,
      }
    })
  } else {
    layer.msg(msg, { icon: 2, time: 2000 })
  }
})

getUserTree().then(({ success, code, msg, data }) => {
  if (success) {
    layer.msg(msg, { icon: 1 })
    treeDataOfTransportUser.value = getTitle(data)
  } else {
    layer.msg(msg, { icon: 2, time: 2000 })
  }
})

getEquipmentTree().then(({ success, code, msg, data }) => {
  if (success) {
    layer.msg(msg, { icon: 1 })
    treeDataOfTransportEquipment.value = getTitle(data)
  } else {
    layer.msg(msg, { icon: 2, time: 2000 })
  }
})

function getTitle(obj) {
  return obj.map((item) => {
    let iObj = {
      ...item,
      title: item.name,
    }
    if (item.children?.length) {
      iObj.children = getTitle(item.children)
    }
    return iObj
  })
}

/**
 * 弹窗加载后执行
 */
function layerLoad() {
  if (title.value === '修改') {
    // modelDto 赋值
    Object.keys(modelData.value).reduce((m, key) => {
      if (m.hasOwnProperty(key)) {
        m[key] = modelData.value[key]
      }
      return m
    }, modelDto)
  }
}

/**
 * 弹窗关闭后执行
 */
function layerClose() {
  // 清空数据
  Object.assign(modelDto, transportInfoDto())
}

/**
 * 提交
 */
function toSubmit() {
  if (title.value === '修改') {
    submitUpdate()
  } else {
    submitAdd()
  }
}

/**
 * 添加
 */
function submitAdd() {
  add({ modelDto: modelDto })
    .then(({ success, code, msg, data }) => {
      if (success) {
        layer.msg(msg, { icon: 1 })
        toCancel()
        emits('loadDataSource')
      } else {
        layer.msg(msg, { icon: 2, time: 2000 })
      }
    })
    .catch((err) => {
      layer.msg(err.message, { icon: 2, time: 2000 })
    })
}

/**
 * 修改
 */
function submitUpdate() {
  update({ modelDto: modelDto })
    .then(({ success, code, msg, data }) => {
      if (success) {
        layer.msg(msg, { icon: 1 })
        toCancel()
        emits('loadDataSource')
      } else {
        layer.msg(msg, { icon: 2, time: 2000 })
      }
    })
    .catch((err) => {
      layer.msg(err.message, { icon: 2, time: 2000 })
    })
}

/**
 * 取消
 */
function toCancel() {
  emits('toCancel', false)
}
</script>
