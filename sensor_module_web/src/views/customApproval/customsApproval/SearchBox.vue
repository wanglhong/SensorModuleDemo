<template>
  <lay-card>
    <!-- 查询搜索框 -->
    <lay-form style="margin-top: 20px">
      <lay-row>
        <lay-col :md="6">
          <lay-form-item label="审核名称" prop="auditName">
            <lay-input
              placeholder="请输入审核名称"
              v-model="localModelDto.auditName"
            />
          </lay-form-item>
        </lay-col>
        <lay-col :md="6">
          <lay-form-item label="审批类型" prop="auditType">
            <lay-select
              placeholder="请选择审批类型"
              v-model="localModelDto.auditType"
              style="width: 100%"
            >
              <template v-for="box in auditTypeList">
                <lay-select-option :value="box.id">
                  {{ box.name }}
                </lay-select-option>
              </template>
            </lay-select>
          </lay-form-item>
        </lay-col>
        <lay-col :md="6">
          <lay-form-item label="审批状态" prop="auditState">
            <lay-select
              placeholder="审批状态"
              v-model="localModelDto.auditState"
              style="width: 100%"
            >
              <template v-for="box in auditStateList">
                <lay-select-option :value="box.id">
                  {{ box.name }}
                </lay-select-option>
              </template>
            </lay-select>
          </lay-form-item>
        </lay-col>
        <lay-col :md="6">
          <lay-form-item label="审批人" prop="userIdOfAudit">
            <lay-tree-select
              placeholder="请选择审批人"
              :data="treeDataOfuserIdOfAudit"
              v-model="localModelDto.userIdOfAudit"
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
        </lay-col>
        <lay-col :md="6">
          <lay-form-item label="备注" prop="remark">
            <lay-input
              placeholder="备注"
              v-model="localModelDto.remark"
              style="width: 90%"
            />
          </lay-form-item>
        </lay-col>
        <lay-col :md="6">
          <lay-form-item label-width="0">
            <lay-button type="primary" @click="toSearch">查询</lay-button>
            <lay-button @click="toReset">重置</lay-button>
          </lay-form-item>
        </lay-col>
      </lay-row>
    </lay-form>
  </lay-card>
</template>

<script setup>
import { watchEffect, ref, reactive } from 'vue'
import { customsAuditDto } from '@/model/ModelDto.js'
import { getUserTree } from '@/api/module/organization.js'

// 创建本地的响应式变量 localModelDto，并将其初始化为父组件传入的 modelDto 的值
let localModelDto = customsAuditDto()
const emits = defineEmits(['toSearch', 'renewalModelDto'])

const listPage = reactive({
  // 当前页
  pageNum: 1,
  // 每页数量
  pageSize: 100,
})

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
    id: '待审核',
    name: '待审核',
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

/**
 * 查询
 */
function toSearch() {
  emits('toSearch')
}

/**
 * 重置
 */
function toReset() {
  // 通过Object.assign方法或通过遍历keys来重置属性值
  Object.assign(localModelDto, customsAuditDto())
  renewalModelDto()
}

function renewalModelDto() {
  emits('renewalModelDto', localModelDto)
}

/**
 * 监听 localModelDto 的变化，当其变化时更新父组件的变量 modelDto 的值
 */
watchEffect(() => {
  renewalModelDto()
})
</script>
