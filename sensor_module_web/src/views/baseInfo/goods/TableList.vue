<template>
  <lay-card>
    <lay-table
        :columns="columns"
        :dataSource="dataSource"
        :loading="loading"
        :default-toolbar="defaultToolbar"
        v-model:selectedKeys="selectedKeys"
        @row="rowClick"
        @change="change"
        style="height: 660px;"
    >
      <template v-slot:toolbar>
        <lay-button size="sm" type="primary">新增</lay-button>
        <lay-button size="sm">删除</lay-button>
      </template>
      <template v-slot:goodsUnitValue="{ data }">
        {{ (data.goodsUnitValue/100).toFixed(2) }}
      </template>
      <template v-slot:operator="{}">
        <lay-button size="xs" type="primary">修改</lay-button>
        <lay-button size="xs">删除</lay-button>
      </template>
      <!-- <template v-slot:footer>
        {{ selectedKeys }}
      </template> -->
    </lay-table>
    <br/>
    <lay-page v-model="page.current"  :layout="page.layout" v-model:limit="page.limit" :pages="page.pages" :total="page.total" @change="change"></lay-page>
  </lay-card>
</template>

<script setup>
import {ref} from "vue";

const loading = ref(false)
const selectedKeys = ref([])
const checkbox = ref(true)
const defaultToolbar = ref(true)

const page = ref({
  // 当前页
  current: 6,
  // 每页数量
  limit: 10,
  // 总条数
  total: 100,
  // 显示切页按钮数量
  pages: 3,
  // 不同部分的展示（count -> 总条数, page -> 页码选择器, prev -> 上一页, next -> 下一页, limits -> 每页的数量选择, refresh -> 刷新,  skip -> 跳页,）。
  layout: ref(['count', 'prev', 'page', 'next', 'limits',  'refresh', 'skip'])
})
const columns = [
  {title:"复选",width:"50px",type:"checkbox",fixed:"left",align:"center"},
  {title:"编码",key:"goodsCode",width:"200px",align:"center"},
  {title:"名称",key:"goodsName",width:"200px",align:"center"},
  {title:"单位价值（单位：元）",key:"goodsUnitValue",customSlot:"goodsUnitValue",width:"180px",align:"center"},
  {title:"体积（单位：cm³）",key:"goodsUnitVolume",width:"180px",align:"center"},
  {title:"重量（单位：克）",key:"goodsUnitWeight",width:"180px",align:"center"},
  {title:"描述",key:"goodsDescription",ellipsisTooltip:true,align:"center"},
  {title:"操作",width:"180px",customSlot:"operator",key:"operator",fixed:"right",align:"center"}
]
let dataSource = ref([])
</script>