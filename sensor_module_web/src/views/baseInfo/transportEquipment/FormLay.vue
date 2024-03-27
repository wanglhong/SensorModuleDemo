<template>
  <lay-layer v-model="displayFrom" :title="title" :success="layerLoad" :end="layerClose" :closeBtn="false" :area="['500px', 'auto']">
    <div style="padding: 20px; max-height: 600px">
      <lay-form :model="modelDto" ref="layFormRef11" required>
        <lay-form-item label="设备编码" prop="equipmentCode">
          <lay-input placeholder="请输入设备编码" v-model="modelDto.equipmentCode"/>
        </lay-form-item>
        <lay-form-item label="设备名称" prop="equipmentName">
          <lay-input placeholder="请输入设备名称" v-model="modelDto.equipmentName"/>
        </lay-form-item>
        <lay-form-item label="设备类型" prop="equipmentType">
          <lay-select placeholder="请选择设备类型" v-model="modelDto.equipmentType" style="width: 100%;">
            <lay-select-option value="飞机">飞机</lay-select-option>
            <lay-select-option value="火车">火车</lay-select-option>
            <lay-select-option value="卡车">卡车</lay-select-option>
            <lay-select-option value="轮船">轮船</lay-select-option>
          </lay-select>
        </lay-form-item>
        <lay-form-item label="设备型号" prop="equipmentModel">
          <lay-input placeholder="请输入设备型号" v-model="modelDto.equipmentModel"/>
        </lay-form-item>
        <lay-form-item label="设备品牌" prop="equipmentBrand">
          <lay-input placeholder="请输入设备品牌" v-model="modelDto.equipmentBrand"/>
        </lay-form-item>
        <lay-form-item label="状态" prop="equipmentState">
          <lay-radio v-model="modelDto.equipmentState" name="equipmentState" :value="'闲置中'" label="闲置中"/>
          <lay-radio v-model="modelDto.equipmentState" name="equipmentState" :value="'使用中'" label="使用中"/>
          <lay-radio v-model="modelDto.equipmentState" name="equipmentState" :value="'废弃'" label="废弃"/>
          <lay-radio v-model="modelDto.equipmentState" name="equipmentState" :value="'维修中'" label="维修中"/>
        </lay-form-item>
        <lay-form-item label="设备备注" prop="equipmentRemark">
          <lay-textarea placeholder="请输入设备备注" v-model="modelDto.equipmentRemark"/>
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
  import {transportEquipmentDto} from "@/model/ModelDto.js";
  import { add, update } from '@/api/module/TransportEquipmentController.js';

  const props = defineProps({
    displayFrom: Boolean,
    title: String,
    modelData: {
      type: Object,
      default: () => {
        return transportEquipmentDto();
      }
    }
  });
  const { displayFrom, title, modelData } = toRefs(props);
  const emits = defineEmits(["toCancel", "loadDataSource"]);

  let modelDto = transportEquipmentDto();

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
    Object.assign(modelDto, transportEquipmentDto());
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