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
        <lay-form-item label="运输信息ID" prop="transportInfoId">
          <lay-select
            placeholder="请选择运输信息ID"
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
        <lay-form-item
          v-if="title === '新增'"
          label="装箱ID"
          prop="id"
        >
          <lay-select
            placeholder="请选择装箱ID"
            v-model="modelDto.id"
            style="width: 100%"
            @change="changeSelect"
          >
            <template v-for="box in goodsToTurnoverBoxList">
              <lay-select-option :value="box.id">
                {{ box.name }}
              </lay-select-option>
            </template>
          </lay-select>
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
import { goodsToTurnoverBoxDto } from '@/model/ModelDto.js'
import { add, update } from '@/api/module/goodsToTurnoverBox.js'
import { list as transportInfo } from '@/api/module/TransportInfoApi.js'
import { list as goodsToTurnoverBox } from '@/api/module/goodsToTurnoverBox.js'

const props = defineProps({
  displayFrom: Boolean,
  title: String,
  modelData: {
    type: Object,
    default: () => {
      return goodsToTurnoverBoxDto()
    },
  },
})
const { displayFrom, title, modelData } = toRefs(props)
const emits = defineEmits(['toCancel', 'loadDataSource'])

let modelDto = goodsToTurnoverBoxDto()

const page = reactive({
  // 当前页
  pageNum: 1,
  // 每页数量
  pageSize: 100,
})

// TODO 运输信息
const transportInfoList = ref([])
// TODO 货物装箱信息
const goodsToTurnoverBoxList = ref([])

transportInfo({ page }).then(({ success, code, msg, data }) => {
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

goodsToTurnoverBox({ page }).then(({ success, code, msg, data }) => {
  if (success) {
    goodsToTurnoverBoxList.value = data.data
      .filter((item) => !item.transportInfoId)
      .map((item) => {
        return {
          ...item,
          name: item.id,
        }
      })
  } else {
    layer.msg(msg, { icon: 2, time: 2000 })
  }
})

function changeSelect(e) {
  goodsToTurnoverBoxList.value.forEach((item) => {
    if (item.id === e) {
      for (const key in modelDto) {
        if (key !== 'transportInfoId' && key !== 'id') {
          modelDto[key] = item[key]
        }
      }
      return
    }
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
  Object.assign(modelDto, goodsToTurnoverBoxDto())
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
