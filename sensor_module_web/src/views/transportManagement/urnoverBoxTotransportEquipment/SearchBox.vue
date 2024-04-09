<template>
  <lay-card>
    <!-- 查询搜索框 -->
    <lay-form style="margin-top: 20px">
      <lay-row>
        <lay-col :md="6">
          <lay-form-item label="运输信息ID" prop="transportInfoId">
            <lay-select
              placeholder="请选择运输信息ID"
              v-model="localModelDto.transportInfoId"
              :search="true"
              style="width: 90%"
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
          <lay-form-item label="周转箱" prop="turnoverBoxId">
            <lay-select
              placeholder="请选择周转箱"
              v-model="localModelDto.turnoverBoxId"
              :search="true"
              style="width: 90%"
            >
              <template v-for="box in turnoverBoxList">
                <lay-select-option :value="box.id">
                  {{ box.name }}
                </lay-select-option>
              </template>
            </lay-select>
          </lay-form-item>
        </lay-col>
        <lay-col :md="6">
          <lay-form-item label="货物" prop="goodsId">
            <lay-select
              placeholder="货物"
              v-model="localModelDto.goodsId"
              style="width: 90%"
            >
              <template v-for="goods in goodsList">
                <lay-select-option :value="goods.id">
                  {{ goods.name }}
                </lay-select-option>
              </template>
            </lay-select>
          </lay-form-item>
        </lay-col>
        <lay-col :md="6">
          <lay-form-item label="货物数量" prop="goodsNum">
            <lay-input-number
              v-model="localModelDto.goodsNum"
              position="right"
            ></lay-input-number>
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
          <lay-form-item label-width="0" style="text-align: right">
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
import { goodsToTurnoverBoxDto } from '@/model/ModelDto.js'
import { list as turnoverBox } from '@/api/module/TurnoverBoxApi.js'
import { list as goods } from '@/api/module/GoodsApi.js'
import { list as transportInfo } from '@/api/module/TransportInfoApi.js'

// 创建本地的响应式变量 localModelDto，并将其初始化为父组件传入的 modelDto 的值
let localModelDto = goodsToTurnoverBoxDto()
const emits = defineEmits(['toSearch', 'renewalModelDto'])

// TODO 运输信息
const transportInfoList = ref([])
// TODO 周转箱
const turnoverBoxList = ref([])
// TODO 货物
const goodsList = ref([])
const page = reactive({
  // 当前页
  pageNum: 1,
  // 每页数量
  pageSize: 100,
})

transportInfo({ page }).then(({ success, code, msg, data }) => {
  if (success) {
    console.log('data', data.data)
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
  Object.assign(localModelDto, goodsToTurnoverBoxDto())
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
