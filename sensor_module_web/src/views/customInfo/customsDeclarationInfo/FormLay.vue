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
        <lay-form-item label="报关名称" prop="customsDeclarationName">
          <lay-input
            placeholder="请输入报关名称"
            v-model="modelDto.customsDeclarationName"
          />
        </lay-form-item>
        <lay-form-item label="报关单号" prop="customsDeclarationNumber">
          <lay-input
            placeholder="请输入报关单号"
            v-model="modelDto.customsDeclarationNumber"
          />
        </lay-form-item>
        <lay-form-item label="HS编码" prop="hsCode">
          <lay-input placeholder="请输入HS编码" v-model="modelDto.hsCode" />
        </lay-form-item>
        <lay-form-item label="货物价值" prop="goodsValue">
          <lay-input-number
            v-model="modelDto.goodsValue"
            position="right"
            min="1"
          ></lay-input-number>
        </lay-form-item>
        <lay-form-item label="预计过境时间" prop="estimatedTransitDate">
          <lay-date-picker
            placeholder="请输入预计过境时间"
            v-model="modelDto.estimatedTransitDate"
            :format="'YYYY-MM-DD HH:mm:ss'"
            type="datetime"
            style="width: 100%"
          />
        </lay-form-item>
        <lay-form-item label="实际过境时间" prop="actualTransitDate">
          <lay-date-picker
            placeholder="请输入实际过境时间"
            v-model="modelDto.actualTransitDate"
            :format="'YYYY-MM-DD HH:mm:ss'"
            type="datetime"
            style="width: 100%"
          />
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
import { customsDeclarationInfoDto } from '@/model/ModelDto.js'
import { add, update } from '@/api/module/customsDeclarationInfo.js'
import { list as transportInfo } from '@/api/module/TransportInfoApi.js'

const props = defineProps({
  displayFrom: Boolean,
  title: String,
  modelData: {
    type: Object,
    default: () => {
      return customsDeclarationInfoDto()
    },
  },
})
const { displayFrom, title, modelData } = toRefs(props)
const emits = defineEmits(['toCancel', 'loadDataSource'])

let modelDto = customsDeclarationInfoDto()

const listPage = reactive({
  // 当前页
  pageNum: 1,
  // 每页数量
  pageSize: 100,
})

// TODO 运输信息
const transportInfoList = ref([])

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
  Object.assign(modelDto, customsDeclarationInfoDto())
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
