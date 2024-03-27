<template>
  <lay-card>
    <!-- 查询搜索框 -->
    <lay-form style="margin-top: 20px">
      <lay-row>
        <lay-col :md="6">
          <lay-form-item label="编号：" label-width="50">
            <lay-input v-model="localModelDto.goodsCode" style="width: 90%"/>
          </lay-form-item>
        </lay-col>
        <lay-col :md="6">
          <lay-form-item label="名称：" label-width="50">
            <lay-input v-model="localModelDto.goodsName" style="width: 90%"/>
          </lay-form-item>
        </lay-col>
        <lay-col :md="6">
          <lay-form-item label="备注：" label-width="50">
            <lay-input v-model="localModelDto.goodsDescription" style="width: 90%"/>
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
  import {watchEffect} from "vue";
  import {goodsDto} from "@/model/ModelDto.js";

  // 创建本地的响应式变量 localModelDto，并将其初始化为父组件传入的 modelDto 的值
  let localModelDto = goodsDto();
  const emits = defineEmits(["toSearch", "renewalModelDto"])

  /**
   * 查询
   */
  function toSearch() {
    emits("toSearch");
  }

  /**
   * 重置
   */
  function toReset() {
    // 通过Object.assign方法或通过遍历keys来重置属性值
    Object.assign(localModelDto, goodsDto());
    renewalModelDto();
  }

  function renewalModelDto() {
    emits("renewalModelDto", localModelDto);
  }

  /**
   * 监听 localModelDto 的变化，当其变化时更新父组件的变量 modelDto 的值
   */
  watchEffect(() => {
    renewalModelDto();
  })

</script>