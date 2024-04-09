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
        <lay-form-item label="周转箱" prop="turnoverBoxId">
          <lay-select
            placeholder="请选择周转箱"
            v-model="modelDto.turnoverBoxId"
            style="width: 100%"
          >
            <template v-for="box in turnoverBoxList">
              <lay-select-option :value="box.id">
                {{ box.name }}
              </lay-select-option>
            </template>
          </lay-select>
        </lay-form-item>
        <lay-form-item label="货物" prop="goodsId">
          <lay-select
            placeholder="请选择货物"
            v-model="modelDto.goodsId"
            style="width: 100%"
          >
            <template v-for="goods in goodsList">
              <lay-select-option :value="goods.id">
                {{ goods.name }}
              </lay-select-option>
            </template>
          </lay-select>
        </lay-form-item>
        <lay-form-item label="货物数量" prop="goodsNum">
          <lay-input-number
            v-model="modelDto.goodsNum"
            position="right"
            min="1"
          ></lay-input-number>
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
import { goodsToTurnoverBoxDto } from '@/model/ModelDto.js'
import { add, update } from '@/api/module/goodsToTurnoverBox.js'
import { list as turnoverBox } from '@/api/module/TurnoverBoxApi.js'
import { list as goods } from '@/api/module/GoodsApi.js'

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
// TODO 周转箱
const turnoverBoxList = ref([])
// TODO 货物
const goodsList = ref([])

turnoverBox({ page }).then(({ success, code, msg, data }) => {
  if (success) {
    turnoverBoxList.value = data.data.map((item) => {
      return {
        ...item,
        name: item.turnoverBoxName,
      }
    })
  } else {
    layer.msg(msg, { icon: 2, time: 2000 })
  }
})

goods({ page }).then(({ success, code, msg, data }) => {
  if (success) {
    goodsList.value = data.data.map((item) => {
      return {
        ...item,
        name: item.goodsName,
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
