<template>
  <lay-card>
    <!-- 查询搜索框 -->
    <lay-form style="margin-top: 20px">
      <lay-row>
        <lay-col :md="6">
          <lay-form-item label="运输人" prop="userId">
            <lay-tree-select placeholder="运输人" :data="treeDataOfTransportUser" v-model="localModelDto.userId" :search="true" style="width: 90%">
              <template #title="{data}">
                <div>
                  <lay-icon type="layui-icon-addition" v-if="!data.disabled"/>
                  {{ data.title }}
                </div>
              </template>
            </lay-tree-select>
          </lay-form-item>
        </lay-col>
        <lay-col :md="6">
          <lay-form-item label="运输工具" prop="transportEquipmentId">
            <lay-tree-select placeholder="运输工具" :data="treeDataOfTransportEquipment" v-model="localModelDto.transportEquipmentId" :search="true" style="width: 90%">
              <template #title="{data}">
                <div>
                  <lay-icon type="layui-icon-addition" v-if="!data.disabled"/>
                  {{ data.title }}
                </div>
              </template>
            </lay-tree-select>
          </lay-form-item>
        </lay-col>
        <lay-col :md="6">
          <lay-form-item label="发货公司" prop="sendOrganizationId">
            <lay-select placeholder="发货公司" v-model="localModelDto.sendOrganizationId" style="width: 90%;">
              <template v-for="sendOrganization in sendOrganizationList">
                <lay-select-option :value="sendOrganization.id">
                  {{ sendOrganization.name }}
                </lay-select-option>
              </template>
            </lay-select>
          </lay-form-item>
        </lay-col>
        <lay-col :md="6">
          <lay-form-item label="收货公司" prop="receiveOrganizationId">
            <lay-select placeholder="收货公司" v-model="localModelDto.receiveOrganizationId" style="width: 90%;">
              <template v-for="receiveOrganization in receiveOrganizationList">
                <lay-select-option :value="receiveOrganization.id">
                  {{ receiveOrganization.name }}
                </lay-select-option>
              </template>
            </lay-select>
          </lay-form-item>
        </lay-col>
        <lay-col :md="6">
          <lay-form-item label="起运国" prop="sendCountry">
            <lay-input placeholder="起运国" v-model="localModelDto.sendCountry" style="width: 90%;"/>
          </lay-form-item>
        </lay-col>
        <lay-col :md="6">
          <lay-form-item label="目的地国" prop="receiveCountry">
            <lay-input placeholder="目的地国" v-model="localModelDto.receiveCountry" style="width: 90%;"/>
          </lay-form-item>
        </lay-col>
<!--        <lay-col :md="6">-->
<!--          <lay-form-item label="起运时间" prop="sendDate">-->
<!--            <lay-date-picker placeholder="请输入起运时间" v-model="localModelDto.sendDate" :format="'YYYY-MM-DD HH:mm:ss'" type="datetime" style="width: 90%;"/>-->
<!--          </lay-form-item>-->
<!--        </lay-col>-->
<!--        <lay-col :md="6">-->
<!--          <lay-form-item label="预计过境时间" prop="estimateDate">-->
<!--            <lay-date-picker placeholder="请输入预计过境时间" v-model="localModelDto.estimateDate" :format="'YYYY-MM-DD HH:mm:ss'" type="datetime" style="width: 90%;"/>-->
<!--          </lay-form-item>-->
<!--        </lay-col>-->
<!--        <lay-col :md="6">-->
<!--          <lay-form-item label="实际过境时间" prop="actualDate">-->
<!--            <lay-date-picker placeholder="请输入实际过境时间" v-model="localModelDto.actualDate" :format="'YYYY-MM-DD HH:mm:ss'" type="datetime" style="width: 90%;"/>-->
<!--          </lay-form-item>-->
<!--        </lay-col>-->
        <lay-col :md="6">
          <lay-form-item label="备注" prop="remark">
            <lay-input placeholder="备注" v-model="localModelDto.remark" style="width: 90%;"/>
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
  import {watchEffect, ref} from "vue";
  import {transportInfoDto} from "@/model/ModelDto.js";

  // 创建本地的响应式变量 localModelDto，并将其初始化为父组件传入的 modelDto 的值
  let localModelDto = transportInfoDto();
  const emits = defineEmits(["toSearch", "renewalModelDto"])

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
              title: '运输工具01',
              id: 10001,
            },
            {
              title: '运输工具02',
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