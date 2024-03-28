<template>
  <lay-layer v-model="displayFrom" :title="title" :success="layerLoad" :end="layerClose" :closeBtn="false" :area="['500px', 'auto']">
    <div style="padding: 20px">
      <lay-form :model="modelDto" ref="layFormRef11" required>
        <lay-form-item label="编码" prop="goodsCode">
          <lay-input placeholder="请输入编码" v-model="modelDto.goodsCode"/>
        </lay-form-item>
        <lay-form-item label="名称" prop="goodsName">
          <lay-input placeholder="请输入名称" v-model="modelDto.goodsName"/>
        </lay-form-item>
        <lay-form-item label="单位价值" prop="goodsUnitValue">
          <lay-input placeholder="请输入单位价值（单位：元）" v-model="modelDto.goodsUnitValue"/>
        </lay-form-item>
        <lay-form-item label="体积" prop="goodsUnitVolume">
          <lay-input placeholder="请输入体积（单位：cm³）" v-model="modelDto.goodsUnitVolume"/>
        </lay-form-item>
        <lay-form-item label="重量" prop="goodsUnitWeight">
          <lay-input placeholder="请输入重量（单位：克）	" v-model="modelDto.goodsUnitWeight"/>
        </lay-form-item>
        <lay-form-item label="备注" prop="goodsRemark">
          <lay-textarea placeholder="请输入备注" v-model="modelDto.goodsRemark"/>
        </lay-form-item>
      </lay-form>
      <div style="width: 100%; text-align: center">
        <lay-button size="sm" type="primary" @click="toSubmit">提交</lay-button>
        <lay-button size="sm" @click="toCancel">取消</lay-button>
      </div>
      <br/>
    </div>
  </lay-layer>
</template>

<script setup>
  import {toRefs} from 'vue';
  import {layer} from '@layui/layui-vue';
  import {goodsDto} from "@/model/ModelDto.js";
  import { add, update } from '@/api/module/GoodsApi.js';

  const props = defineProps({
    displayFrom: Boolean,
    title: String,
    modelData: {
      type: Object,
      default: () => {
        return goodsDto();
      }
    }
  });
  const { displayFrom, title, modelData } = toRefs(props);
  const emits = defineEmits(["toCancel", "loadDataSource"]);

  let modelDto = goodsDto();

  /**
   * 弹窗加载后执行
   */
  function layerLoad() {
    if (title.value === "修改") {
      // modelDto 赋值
      Object.keys(modelData.value).reduce((m, key) => {
        if (m.hasOwnProperty(key)) {
          m[key] = modelData.value[key];
        }
        return m;
      }, modelDto);
    }
  }

  /**
   * 弹窗关闭后执行
   */
  function layerClose() {
    // 清空数据
    Object.assign(modelDto, goodsDto());
  }

  /**
   * 提交
   */
  function toSubmit() {
    if (title.value === "修改") {
      submitUpdate();
    } else {
      submitAdd();
    }
  }

  /**
   * 添加
   */
  function submitAdd() {
    add({ modelDto : modelDto }).then(({ success, code, msg, data }) => {
      if (success) {
        layer.msg(msg, { icon: 1 });
        toCancel();
        emits("loadDataSource");
      } else {
        layer.msg(msg, { icon: 2, time: 2000 });
      }
    }).catch((err) => {
      layer.msg(err.message, { icon: 2, time: 2000 });
    });
  }

  /**
   * 修改
   */
  function submitUpdate() {
    update({ modelDto : modelDto }).then(({ success, code, msg, data }) => {
      if (success) {
        layer.msg(msg, { icon: 1 });
        toCancel();
        emits("loadDataSource");
      } else {
        layer.msg(msg, { icon: 2, time: 2000 });
      }
    }).catch((err) => {
      layer.msg(err.message, { icon: 2, time: 2000 });
    });
  }

  /**
   * 取消
   */
  function toCancel() {
    emits("toCancel", false);
  }

</script>