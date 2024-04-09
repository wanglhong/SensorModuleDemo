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
        <lay-form-item
          v-if="title === '新增'"
          label="审核名称"
          prop="auditName"
        >
          <lay-input
            placeholder="请输入审核名称"
            v-model="modelDto.auditName"
          />
        </lay-form-item>
        <lay-form-item
          v-if="title === '新增'"
          label="审批类型"
          prop="auditType"
        >
          <lay-select
            placeholder="请选择审批类型"
            v-model="modelDto.auditType"
            @change="changeAuditType"
            style="width: 100%"
          >
            <template v-for="box in auditTypeList">
              <lay-select-option :value="box.id">
                {{ box.name }}
              </lay-select-option>
            </template>
          </lay-select>
        </lay-form-item>
        <lay-form-item
          v-if="title === '新增'"
          label="审批信息"
          prop="auditInfoId"
        >
          <lay-select
            placeholder="请选择审批信息"
            v-model="modelDto.auditInfoId"
            style="width: 100%"
          >
            <template v-for="box in auditInfoIdList">
              <lay-select-option :value="box.id">
                {{ box.name }}
              </lay-select-option>
            </template>
          </lay-select>
        </lay-form-item>
        <lay-form-item label="审批状态" prop="auditState">
          <lay-select
            placeholder="审批状态"
            v-model="modelDto.auditState"
            style="width: 100%"
          >
            <template v-for="box in auditStateList">
              <lay-select-option :value="box.id">
                {{ box.name }}
              </lay-select-option>
            </template>
          </lay-select>
        </lay-form-item>
        <lay-form-item
          v-if="title === '新增'"
          label="审批人"
          prop="userIdOfAudit"
        >
          <lay-tree-select
            placeholder="请选择审批人"
            :data="treeDataOfuserIdOfAudit"
            v-model="modelDto.userIdOfAudit"
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
import { customsAuditDto } from '@/model/ModelDto.js'
import { add, update } from '@/api/module/customsApproval.js'
import { getUserTree } from '@/api/module/organization.js'
import { list as customsDeclarationInfo } from '@/api/module/customsDeclarationInfo.js'
import { list as customsClearanceInfo } from '@/api/module/customsClearanceInfo'

const props = defineProps({
  displayFrom: Boolean,
  title: String,
  modelData: {
    type: Object,
    default: () => {
      return customsAuditDto()
    },
  },
})
const { displayFrom, title, modelData } = toRefs(props)
const emits = defineEmits(['toCancel', 'loadDataSource'])

let modelDto = customsAuditDto()

// 审批类型
const auditTypeList = ref([
  {
    id: '报关审批',
    name: '报关审批',
  },
  {
    id: '清关审批',
    name: '清关审批',
  },
])

// 审批状态
const auditStateList = ref([
  {
    id: '待提交审核',
    name: '待提交审核',
  },
  {
    id: '审核中',
    name: '审核中',
  },
  {
    id: '审核通过',
    name: '审核通过',
  },
  {
    id: '审核驳回',
    name: '审核驳回',
  },
])

// TODO 审核人选择树
const treeDataOfuserIdOfAudit = ref([])

// TODO 报关信息
const declarationInfoList = ref([])

// TODO 清关信息
const clearanceInfoList = ref([])

// TODO 审批信息
const auditInfoIdList = ref([])

const listPage = reactive({
  // 当前页
  pageNum: 1,
  // 每页数量
  pageSize: 100,
})

customsDeclarationInfo({ listPage }).then(({ success, code, msg, data }) => {
  if (success) {
    declarationInfoList.value = data.data.map((item) => {
      return {
        ...item,
        name: item.customsDeclarationName,
      }
    })
  } else {
    layer.msg(msg, { icon: 2, time: 2000 })
  }
})

customsClearanceInfo({ listPage }).then(({ success, code, msg, data }) => {
  if (success) {
    clearanceInfoList.value = data.data.map((item) => {
      return {
        ...item,
        name: item.customsClearanceName,
      }
    })
  } else {
    layer.msg(msg, { icon: 2, time: 2000 })
  }
})

getUserTree().then(({ success, code, msg, data }) => {
  if (success) {
    layer.msg(msg, { icon: 1 })
    treeDataOfuserIdOfAudit.value = getTitle(data)
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

function changeAuditType(e) {
  modelDto.auditInfoId = null
  if (e === '报关审批') {
    auditInfoIdList.value = declarationInfoList.value
  } else {
    auditInfoIdList.value = clearanceInfoList.value
  }
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
  auditInfoIdList.value = []
  // 清空数据
  Object.assign(modelDto, customsAuditDto())
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
  if (modelDto.auditType === '报关审批') {
    modelDto.customsDeclarationInfoId = modelDto.auditInfoId
  } else {
    modelDto.customsClearanceInfoId = modelDto.auditInfoId
  }
  console.log('modelDto', modelDto);
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
