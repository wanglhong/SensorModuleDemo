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
          <lay-form-item label="报关名称" prop="customsDeclarationName">
            <lay-input
              placeholder="请输入报关名称"
              v-model="localModelDto.customsDeclarationName"
            />
          </lay-form-item>
        </lay-col>
        <lay-col :md="6">
          <lay-form-item label="报关单号" prop="customsDeclarationNumber">
            <lay-input
              placeholder="请输入报关单号"
              v-model="localModelDto.customsDeclarationNumber"
            />
          </lay-form-item>
        </lay-col>
        <lay-col :md="6">
          <lay-form-item label="HS编码" prop="hsCode">
            <lay-input
              placeholder="请输入HS编码"
              v-model="localModelDto.hsCode"
            />
          </lay-form-item>
        </lay-col>
        <lay-col :md="6">
          <lay-form-item label="货物价值" prop="goodsValue">
            <lay-input-number
              v-model="localModelDto.goodsValue"
              position="right"
              min="1"
            ></lay-input-number>
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
import { customsDeclarationInfoDto } from '@/model/ModelDto.js'
import { list as transportInfo } from '@/api/module/TransportInfoApi.js'

// 创建本地的响应式变量 localModelDto，并将其初始化为父组件传入的 modelDto 的值
let localModelDto = customsDeclarationInfoDto()
const emits = defineEmits(['toSearch', 'renewalModelDto'])

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
  Object.assign(localModelDto, transportInfoDto())
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
