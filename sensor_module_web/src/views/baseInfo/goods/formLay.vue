<template>
  <lay-layer v-model="displayFrom" :title="title" :closeBtn="false" :area="['500px', '550px']">
    <div style="padding: 20px">
      <lay-form :model="model" ref="layFormRef11" required>
        <lay-form-item label="编码" prop="goodsCode">
          <lay-input placeholder="请输入重量" v-model="model.goodsCode"/>
        </lay-form-item>
        <lay-form-item label="名称" prop="goodsName">
          <lay-input placeholder="请输入名称" v-model="model.goodsName"/>
        </lay-form-item>
        <lay-form-item label="单位价值" prop="goodsUnitValue">
          <lay-input placeholder="请输入单位价值（单位：元）" v-model="model.goodsUnitValue"/>
        </lay-form-item>
        <lay-form-item label="体积" prop="goodsUnitVolume">
          <lay-input placeholder="请输入体积（单位：cm³）" v-model="model.goodsUnitVolume"/>
        </lay-form-item>
        <lay-form-item label="重量" prop="goodsUnitWeight">
          <lay-input placeholder="请输入重量（单位：克）	" v-model="model.goodsUnitWeight"/>
        </lay-form-item>
        <lay-form-item label="描述" prop="goodsDescription">
          <lay-textarea placeholder="请输入备注" v-model="model.goodsDescription"/>
        </lay-form-item>
      </lay-form>
      <div style="width: 100%; text-align: center">
        <lay-button size="sm" type="primary" @click="toSubmit"
        >保存</lay-button
        >
        <lay-button size="sm" @click="toCancel">取消</lay-button>
      </div>
    </div>
  </lay-layer>
</template>

<script setup>
  import {toRefs} from 'vue';
  import {layer} from '@layui/layui-vue';
  import {goodsDto} from "@/model/ModelDto.js";

  const props = defineProps({
    displayFrom: Boolean,
    title: String,
    model: {
      type: Object,
      default: () => {
        return goodsDto();
      }
    }
  });

  const { displayFrom, title, model } = toRefs(props)
  const emits = defineEmits(["toCancel"])

  /**
   * 提交
   */
  function toSubmit() {
    layer.msg('提交成功');
  }

  /**
   * 取消
   */
  function toCancel() {
    emits("toCancel", false);
  }

</script>