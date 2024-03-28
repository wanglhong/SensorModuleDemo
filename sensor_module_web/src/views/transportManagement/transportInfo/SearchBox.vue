<template>
  <lay-card>
    <!-- 查询搜索框 -->
    <lay-form style="margin-top: 20px">
      <lay-row>
        <lay-col :md="6">
          <lay-form-item label="编码：" label-width="50">
            <lay-input v-model="localModelDto.equipmentCode" style="width: 90%;"/>
          </lay-form-item>
        </lay-col>
        <lay-col :md="6">
          <lay-form-item label="名称：" label-width="50">
            <lay-input v-model="localModelDto.equipmentName" style="width: 90%;"/>
          </lay-form-item>
        </lay-col>
        <lay-col :md="6">
          <lay-form-item label="类型：" label-width="50">
            <lay-select placeholder="请选择设备类型" v-model="localModelDto.equipmentType" style="width: 90%;">
              <lay-select-option value="飞机">飞机</lay-select-option>
              <lay-select-option value="火车">火车</lay-select-option>
              <lay-select-option value="卡车">卡车</lay-select-option>
              <lay-select-option value="轮船">轮船</lay-select-option>
            </lay-select>
          </lay-form-item>
        </lay-col>
        <lay-col :md="6">
          <lay-form-item label="型号：" label-width="50">
            <lay-input v-model="localModelDto.equipmentModel" style="width: 90%;"/>
          </lay-form-item>
        </lay-col>
        <lay-col :md="6">
          <lay-form-item label="品牌：" label-width="50">
            <lay-input v-model="localModelDto.equipmentBrand" style="width: 90%;"/>
          </lay-form-item>
        </lay-col>
        <lay-col :md="6">
          <lay-form-item label="状态：" label-width="50">
            <lay-select placeholder="请选择设备状态" v-model="localModelDto.equipmentState" style="width: 90%;">
              <lay-select-option value="闲置中">闲置中</lay-select-option>
              <lay-select-option value="使用中">使用中</lay-select-option>
              <lay-select-option value="废弃">废弃</lay-select-option>
              <lay-select-option value="维修中">维修中</lay-select-option>
            </lay-select>
          </lay-form-item>
        </lay-col>
        <lay-col :md="6">
          <lay-form-item label="备注：" label-width="50">
            <lay-input v-model="localModelDto.equipmentRemark" style="width: 90%;"/>
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
  import {transportInfoDto} from "@/model/ModelDto.js";

  // 创建本地的响应式变量 localModelDto，并将其初始化为父组件传入的 modelDto 的值
  let localModelDto = transportInfoDto();
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
    Object.assign(localModelDto, transportInfoDto());
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