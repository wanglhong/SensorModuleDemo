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
        <lay-form-item label="运输信息" prop="transportInfoId">
          <lay-select
            placeholder="请选择运输信息"
            v-model="modelDto.transportInfoId"
            style="width: 100%"
          >
            <template v-for="box in transportInfoList">
              <lay-select-option :value="box.id">
                {{ box.name }}
              </lay-select-option>
            </template>
          </lay-select>
        </lay-form-item>
        <lay-form-item label="清关名称" prop="customsClearanceName">
          <lay-input
            placeholder="请输入清关名称"
            v-model="modelDto.customsClearanceName"
          />
        </lay-form-item>
        <lay-form-item label="清关单号" prop="customsClearanceNumber">
          <lay-input
            placeholder="请输入清关单号"
            v-model="modelDto.customsClearanceNumber"
          />
        </lay-form-item>
        <lay-form-item label="报关信息" prop="customsDeclarationInfoId">
          <lay-select
            placeholder="请选择报关信息"
            v-model="modelDto.customsDeclarationInfoId"
            style="width: 100%"
          >
            <template v-for="box in declarationInfoList">
              <lay-select-option :value="box.id">
                {{ box.name }}
              </lay-select-option>
            </template>
          </lay-select>
        </lay-form-item>
        <lay-form-item label="检验检疫机构" prop="inspectionOrganizationId">
          <lay-select
            placeholder="请选择检验检疫机构"
            v-model="modelDto.inspectionOrganizationId"
            style="width: 100%"
          >
            <template v-for="box in inspectionOrganizationList">
              <lay-select-option :value="box.id">
                {{ box.name }}
              </lay-select-option>
            </template>
          </lay-select>
        </lay-form-item>
        <lay-form-item label="检验检疫结果" prop="inspectionResult">
          <lay-input
            placeholder="请输入清关名称"
            v-model="modelDto.inspectionResult"
          />
        </lay-form-item>
        <lay-form-item label="关税和税费" prop="taxAndFees">
          <lay-input-number
            v-model="modelDto.taxAndFees"
            position="right"
            min="1"
          ></lay-input-number>
        </lay-form-item>
        <lay-form-item label="放行日期" prop="clearanceDate">
          <lay-date-picker
            placeholder="放行日期"
            v-model="modelDto.clearanceDate"
            :format="'YYYY-MM-DD HH:mm:ss'"
            type="datetime"
            style="width: 100%"
          />
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
import { customsClearanceInfoDto } from '@/model/ModelDto.js'
import { add, update } from '@/api/module/customsClearanceInfo'
import { list as transportInfo } from '@/api/module/TransportInfoApi.js'
import { list as customsDeclarationInfo } from '@/api/module/customsDeclarationInfo.js'
import { list as organization } from '@/api/module/organization.js'

const props = defineProps({
  displayFrom: Boolean,
  title: String,
  modelData: {
    type: Object,
    default: () => {
      return customsClearanceInfoDto()
    },
  },
})
const { displayFrom, title, modelData } = toRefs(props)
const emits = defineEmits(['toCancel', 'loadDataSource'])

let modelDto = customsClearanceInfoDto()

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
  Object.assign(modelDto, customsClearanceInfoDto())
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
