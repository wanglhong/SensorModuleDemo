<template>
  <lay-card>
    <!-- 查询搜索框 -->
    <lay-form style="margin-top: 20px">
      <lay-row>
        <lay-col :md="6">
          <lay-form-item label="运输信息" prop="transportInfoId">
            <lay-select
              placeholder="请选择运输信息"
              v-model="localModelDto.transportInfoId"
              style="width: 100%"
            >
              <template v-for="box in transportInfoList">
                <lay-select-option :value="box.id">
                  {{ box.name }}
                </lay-select-option>
              </template>
            </lay-select>
          </lay-form-item>
        </lay-col>
        <lay-col :md="6">
          <lay-form-item label="清关名称" prop="customsClearanceName">
            <lay-input
              placeholder="请输入清关名称"
              v-model="localModelDto.customsClearanceName"
            />
          </lay-form-item>
        </lay-col>
        <lay-col :md="6">
          <lay-form-item label="清关单号" prop="customsClearanceNumber">
            <lay-input
              placeholder="请输入清关单号"
              v-model="localModelDto.customsClearanceNumber"
            />
          </lay-form-item>
        </lay-col>
        <lay-col :md="6">
          <lay-form-item label="报关信息" prop="customsDeclarationInfoId">
            <lay-select
              placeholder="请选择报关信息"
              v-model="localModelDto.customsDeclarationInfoId"
              style="width: 100%"
            >
              <template v-for="box in declarationInfoList">
                <lay-select-option :value="box.id">
                  {{ box.name }}
                </lay-select-option>
              </template>
            </lay-select>
          </lay-form-item>
        </lay-col>
        <lay-col :md="6">
          <lay-form-item label="检验检疫机构" prop="inspectionOrganizationId">
            <lay-select
              placeholder="请选择检验检疫机构"
              v-model="localModelDto.inspectionOrganizationId"
              style="width: 100%"
            >
              <template v-for="box in inspectionOrganizationList">
                <lay-select-option :value="box.id">
                  {{ box.name }}
                </lay-select-option>
              </template>
            </lay-select>
          </lay-form-item>
        </lay-col>
        <lay-col :md="6">
          <lay-form-item label="检验检疫结果" prop="inspectionResult">
            <lay-input
              placeholder="请输入检验检疫结果"
              v-model="localModelDto.inspectionResult"
            />
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
import { customsClearanceInfoDto } from '@/model/ModelDto.js'
import { list as transportInfo } from '@/api/module/TransportInfoApi.js'
import { list as customsDeclarationInfo } from '@/api/module/customsDeclarationInfo.js'
import { list as organization } from '@/api/module/organization.js'

// 创建本地的响应式变量 localModelDto，并将其初始化为父组件传入的 modelDto 的值
let localModelDto = customsClearanceInfoDto()
const emits = defineEmits(['toSearch', 'renewalModelDto'])
const listPage = reactive({
  // 当前页
  pageNum: 1,
  // 每页数量
  pageSize: 100,
})

// TODO 运输信息
const transportInfoList = ref([])

// TODO 报关信息
const declarationInfoList = ref([])

// TODO 发货公司数据集
const inspectionOrganizationList = ref([])

transportInfo({ listPage }).then(({ success, code, msg, data }) => {
  if (success) {
    transportInfoList.value = data.data.map((item) => {
      return {
        ...item,
        name: item.transportInfoName,
      }
    })
  } else {
    layer.msg(msg, { icon: 2, time: 2000 })
  }
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

organization(listPage).then(({ success, code, msg, data }) => {
  if (success) {
    layer.msg(msg, { icon: 1 })
    inspectionOrganizationList.value = data.data.map((item) => {
      return {
        ...item,
        name: item.organizationName,
      }
    })
  } else {
    layer.msg(msg, { icon: 2, time: 2000 })
  }
})

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
  Object.assign(localModelDto, customsClearanceInfoDto())
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
