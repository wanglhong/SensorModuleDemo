<template>
  <lay-layer v-model="displayFrom" :title="title" :success="layerLoad" :end="layerClose" :closeBtn="false" :area="['500px', 'auto']">
    <div style="padding: 20px; max-height: 600px">
      <lay-form :model="modelDto" ref="layFormRef11" required>
        <!--
        // 发货公司ID
        sendOrganizationId: null,
        // 发货公司信息
        sendOrganization: null,
        // 收货公司ID
        receiveOrganizationId: null,
        // 收货公司信息
        receiveOrganization: null,
        // 起运国
        sendCountry: null,
        // 目的地国
        receiveCountry: null,
        // 起运时间
        sendDate: null,
        // 预计过境时间
        estimateDate: null,
        // 实际过境时间
        actualDate: null,
        // 备注
        remark: null
        -->
        <lay-form-item label="运输人" prop="userId">
          <lay-tree-select placeholder="请选择运输人" :data="treeDataOfTransportUser" v-model="modelDto.userId" :search="true" style="width: 100%">
            <template #title="{data}">
              <div>
                <lay-icon type="layui-icon-addition" v-if="!data.disabled"/>
                {{ data.title }}
              </div>
            </template>
          </lay-tree-select>
        </lay-form-item>
        <lay-form-item label="运输工具" prop="transportEquipmentId">
          <lay-tree-select placeholder="请选择运输工具" :data="treeDataOfTransportEquipment" v-model="modelDto.transportEquipmentId" :search="true" style="width: 100%">
            <template #title="{data}">
              <div>
                <lay-icon type="layui-icon-addition" v-if="!data.disabled"/>
                {{ data.title }}
              </div>
            </template>
          </lay-tree-select>
        </lay-form-item>
        <lay-form-item label="发货公司" prop="sendOrganizationId">
          <lay-select placeholder="请选择发货公司" v-model="modelDto.sendOrganizationId" style="width: 100%;">
            <template v-for="sendOrganization in sendOrganizationList">
              <lay-select-option value="{{ sendOrganization.id }}">
                {{ sendOrganization.name }}
              </lay-select-option>
            </template>
          </lay-select>
        </lay-form-item>
        <lay-form-item label="收货公司" prop="receiveOrganizationId">
          <lay-select placeholder="请选择收货公司" v-model="modelDto.receiveOrganizationId" style="width: 100%;">
            <template v-for="receiveOrganization in receiveOrganizationList">
              <lay-select-option value="{{ receiveOrganization.id }}">
                {{ receiveOrganization.name }}
              </lay-select-option>
            </template>
          </lay-select>
        </lay-form-item>
        <lay-form-item label="起运国" prop="sendCountry">
          <lay-input placeholder="请输入起运国" v-model="modelDto.sendCountry"/>
        </lay-form-item>
        <lay-form-item label="目的地国" prop="receiveCountry">
          <lay-input placeholder="请输入目的地国" v-model="modelDto.receiveCountry"/>
        </lay-form-item>
        <lay-form-item label="起运时间" prop="sendDate">
          <lay-date-picker placeholder="请输入起运时间" v-model="modelDto.sendDate" type="datetime" style="width: 100%;"/>
        </lay-form-item>
        <lay-form-item label="预计过境时间" prop="estimateDate">
          <lay-date-picker placeholder="请输入预计过境时间" v-model="modelDto.estimateDate" type="datetime" style="width: 100%;"/>
        </lay-form-item>
        <lay-form-item label="实际过境时间" prop="actualDate">
          <lay-date-picker placeholder="请输入实际过境时间" v-model="modelDto.actualDate" type="datetime" style="width: 100%;"/>
        </lay-form-item>
        <lay-form-item label="备注" prop="remark">
          <lay-textarea placeholder="请输入备注" v-model="modelDto.remark"/>
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
import {ref, toRefs} from 'vue';
  import {layer} from '@layui/layui-vue';
  import {transportInfoDto} from "@/model/ModelDto.js";
  import { add, update } from '@/api/module/TransportInfoApi.js';

  const props = defineProps({
    displayFrom: Boolean,
    title: String,
    modelData: {
      type: Object,
      default: () => {
        return transportInfoDto();
      }
    }
  });
  const { displayFrom, title, modelData } = toRefs(props);
  const emits = defineEmits(["toCancel", "loadDataSource"]);

  let modelDto = transportInfoDto();
  // TODO 运输人选择树
  const treeDataOfTransportUser = ref([
    {
      title: '测试公司01',
      disabled: true,
      children: [
        {
          title: '测试部门01',
          disabled: true
        },
        {
          title: '测试部门02',
          disabled: true,
          children: [
            {
              title: '测试员工01',
              id: 10001,
            },
            {
              title: '测试员工02',
              id: 10002
            },
          ],
        }
      ]
    }
  ]);
  // TODO 运输工具选择树
  const treeDataOfTransportEquipment = ref([
    {
      title: '测试公司01',
      disabled: true,
      children: [
        {
          title: '测试部门01',
          disabled: true
        },
        {
          title: '测试部门02',
          disabled: true,
          children: [
            {
              title: '测试员工01',
              id: 10001,
            },
            {
              title: '测试员工02',
              id: 10002
            },
          ],
        }
      ]
    }
  ]);
  // TODO 发货公司数据集
  const sendOrganizationList = ref([
    {
      id: 10001,
      name: '发货公司-01'
    },
    {
      id: 10002,
      name: '发货公司-02'
    }
  ]);
  // TODO 收货公司数据集
  const receiveOrganizationList = ref([
    {
      id: 10001,
      name: '收货公司-01'
    },
    {
      id: 10002,
      name: '收货公司-02'
    }
  ]);

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
    Object.assign(modelDto, transportInfoDto());
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