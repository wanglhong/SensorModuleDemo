<template>
  <lay-container fluid="true" style="padding: 10px">
    <lay-row :space="10">
      <lay-col :md="24">
        <lay-card>
          <!-- 查询搜索框 -->
          <lay-form style="margin-top: 20px">
            <lay-row>
              <lay-col :md="6">
                <lay-form-item label="账号：" label-width="50">
                  <lay-input
                    v-model="searchAccount"
                    style="width: 90%"
                  ></lay-input>
                </lay-form-item>
              </lay-col>
              <lay-col :md="6">
                <lay-form-item label="邮箱：" label-width="50">
                  <lay-input
                    v-model="searchEmail"
                    style="width: 90%"
                  ></lay-input>
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
      </lay-col>
      <lay-col :md="24">
        <lay-card>
          <lay-table
            :columns="columns"
            :dataSource="dataSource"
            :loading="loading"
            :default-toolbar="defaultToolbar"
            v-model:selectedKeys="selectedKeys"
            @row="rowClick"
            @change="change"
            style="height: 700px;"
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
            <template v-slot:footer>
              <lay-page v-model="page.pageNum"  :layout="page.layout" v-model:limit="page.pageSize" :pages="page.pages" :total="page.total" @change="change"></lay-page>
            </template>
          </lay-table>
        </lay-card>
      </lay-col>
    </lay-row>
  </lay-container>
</template>

<script setup>
import { onMounted, ref, reactive } from 'vue'
import { layer } from '@layui/layer-vue'
import { list } from '@/api/module/goodsController.js'

const loading = ref(false)
const selectedKeys = ref([])
const checkbox = ref(true)
const defaultToolbar = ref(true)
const page = reactive({
  // 当前页
  pageNum: 1,
  // 每页数量
  pageSize: 10,
  // 总条数
  total: 0,
  // 显示切页按钮数量
  pages: 3,
  // 不同部分的展示（count -> 总条数, page -> 页码选择器, prev -> 上一页, next -> 下一页, limits -> 每页的数量选择, refresh -> 刷新,  skip -> 跳页,）。
  layout: ref(['count', 'prev', 'page', 'next', 'limits',  'refresh', 'skip'])
})

const columns = [
  {title: "复选", width: "50px", type: "checkbox", fixed: "left", align: "center"},
  {title: "编码", key: "goodsCode", width: "130px", align: "center"},
  {title: "名称", key: "goodsName", width: "130px", align: "center"},
  {title: "单位价值（单位：元）", key: "goodsUnitValue", customSlot: "goodsUnitValue", width: "180px", align: "center"},
  {title: "体积（单位：cm³）", key: "goodsUnitVolume", width: "180px", align: "center"},
  {title: "重量（单位：克）", key: "goodsUnitWeight", width: "180px", align: "center"},
  {title: "描述", key: "goodsDescription", ellipsisTooltip: true, align: "center"},
  {title: "操作", width: "180px", customSlot: "operator", key: "operator", fixed: "right", align: "center"}
]

let dataSource = ref([])

const loadDataSource = async (modelDto) => {
  loading.value = true
  list({ modelDto: modelDto, page: page }).then(({ success, code, msg, data }) => {
    loading.value = false
    if (success) {
      dataSource.value = data.data;
      page.total = data.total;
    } else {
      layer.msg(msg, { icon: 2 })
    }
  })
}

// 行单击事件
const rowClick = function(data) {}
// 行双击事件
const rowDoubleClick = function(data) {}
// 分页事件
const change = function() {
  loadDataSource(null)
}
function toSearch() {
  layer.load(2, { time: 2000 })
}
const searchAccount = ref("")
const searchEmail = ref("")
function toReset() {
  searchAccount.value = ""
  searchEmail.value = ""
}

// 使用onMounted生命周期钩子在组件挂载完成后加载数据
onMounted(() => {
  loadDataSource(null)
})

</script>

<style scoped>

.table-box {
  margin-top: 10px;
  padding: 10px;
  height: 700px;
  width: 100%;
  border-radius: 4px;
  box-sizing: border-box;
  background-color: #fff;
}

</style>