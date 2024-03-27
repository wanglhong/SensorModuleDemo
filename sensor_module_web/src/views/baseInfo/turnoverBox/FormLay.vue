<template>
  <lay-layer v-model="displayFrom" :title="title" :success="layerLoad" :end="layerClose" :closeBtn="false" :area="['500px', '550px']">
    <div style="padding: 20px">
      <lay-form :model="modelDto" ref="layFormRef11" required>
        <lay-form-item label="RFID编码" prop="rfidCode">
          <lay-input placeholder="请输入RFID编码" v-model="modelDto.rfidCode"/>
        </lay-form-item>
        <lay-form-item label="名称" prop="turnoverBoxName">
          <lay-input placeholder="请输入名称" v-model="modelDto.turnoverBoxName"/>
        </lay-form-item>
        <lay-form-item label="体积" prop="turnoverBoxVolume">
          <lay-input placeholder="请输入体积（单位：cm³）" v-model="modelDto.turnoverBoxVolume"/>
        </lay-form-item>
        <lay-form-item label="容积" prop="turnoverBoxContainer">
          <lay-input placeholder="请输入容积（单位：cm³）" v-model="modelDto.turnoverBoxContainer"/>
        </lay-form-item>
        <lay-form-item label="重量" prop="turnoverBoxWeight">
          <lay-input placeholder="请输入重量（单位：克）	" v-model="modelDto.turnoverBoxWeight"/>
        </lay-form-item>
        <lay-form-item label="状态" prop="turnoverBoxState">
          <lay-select placeholder="请选择使用状态" v-model="modelDto.turnoverBoxState">
            <lay-select-option value="闲置中">闲置中</lay-select-option>
            <lay-select-option value="使用中">使用中</lay-select-option>
            <lay-select-option value="废弃">废弃</lay-select-option>
          </lay-select>
        </lay-form-item>
        <lay-form-item label="描述" prop="turnoverBoxRemark">
          <lay-textarea placeholder="请输入备注" v-model="modelDto.turnoverBoxRemark"/>
        </lay-form-item>
      </lay-form>
      <div style="width: 100%; text-align: center">
        <lay-button size="sm" type="primary" @click="toSubmit">提交</lay-button>
        <lay-button size="sm" @click="toCancel">取消</lay-button>
      </div>
    </div>
  </lay-layer>
</template>

<script setup>
  import {toRefs} from 'vue';
  import {layer} from '@layui/layui-vue';
  import {turnoverBoxDto} from "@/model/ModelDto.js";
  import { add, update } from '@/api/module/TurnoverBoxController.js';

  const props = defineProps({
    displayFrom: Boolean,
    title: String,
    modelData: {
      type: Object,
      default: () => {
        return turnoverBoxDto();
      }
    }
  });
  const { displayFrom, title, modelData } = toRefs(props);
  const emits = defineEmits(["toCancel", "loadDataSource"]);

  let modelDto = turnoverBoxDto();

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
    Object.assign(modelDto, turnoverBoxDto());
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