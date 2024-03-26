<template>
  <lay-container fluid="true" style="padding: 10px">
    <lay-row :space="10">
      <lay-col :md="24">
        <search-box @toSearch="toSearch" @renewalModelDto="renewalModelDto"></search-box>
      </lay-col>
      <lay-col :md="24">
        <lay-card>
          <lay-table
            :columns="columns"
            :dataSource="dataSource"
            :loading="loading"
            :default-toolbar="defaultToolbar"
            v-model:selectedKeys="selectedIdList "
            @change="change"
            style="height: 700px;"
          >
            <template v-slot:toolbar>
              <lay-button size="sm" type="primary" @click="displayFromLay('新增')">
                <lay-icon class="layui-icon-addition"/> 新增
              </lay-button>
              <lay-button size="sm" @click="removeList">
                <lay-icon class="layui-icon-delete"/> 删除
              </lay-button>
            </template>
            <template v-slot:goodsUnitValue="{ data }">
              {{ (data.goodsUnitValue/100).toFixed(2) }}
            </template>
            <template v-slot:operator="{ data }">
              <lay-button size="xs" type="primary" @click="displayFromLay('修改')">修改</lay-button>
              <lay-popconfirm
                  content="确定要删除此数据吗?"
                  @confirm="removeOne(data.id)"
                  @cancel="cancel"
              >
                <lay-button size="xs" border="red" border-style="dashed">删除</lay-button>
              </lay-popconfirm>
            </template>
            <template v-slot:footer>
              <lay-page v-model="page.pageNum"  :layout="page.layout" v-model:limit="page.pageSize" :pages="page.pages" :total="page.total" @change="change"></lay-page>
            </template>
          </lay-table>
        </lay-card>
      </lay-col>
    </lay-row>
    <from-lay :displayFrom="displayFrom" :title="fromTitle" :model="modelDto" @toCancel="toCancel"/>
  </lay-container>
</template>

<script setup>
  import FromLay from '@/views/baseInfo/goods/formLay.vue';
  import SearchBox from '@/views/baseInfo/goods/SearchBox.vue';
  import { onMounted, ref, reactive } from 'vue';
  import { layer } from '@layui/layer-vue';
  import { add, list, update, remove, removeByIdList } from '@/api/module/goodsController.js';

  let modelDto = reactive({});
  const loading = ref(false);
  const selectedIdList  = ref([]);
  const checkbox = ref(true);
  const defaultToolbar = ref(true);
  const displayFrom = ref(false);
  const fromTitle = ref('新增');
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
  });
  const columns = [
    {title: "复选", width: "50px", type: "checkbox", fixed: "left", align: "center"},
    {title: "编码", key: "goodsCode", ellipsisTooltip: true, align: "center"},
    {title: "名称", key: "goodsName", ellipsisTooltip: true, align: "center"},
    {title: "单位价值（单位：元）", key: "goodsUnitValue", customSlot: "goodsUnitValue", width: "180px", align: "center"},
    {title: "体积（单位：cm³）", key: "goodsUnitVolume", ellipsisTooltip: true, align: "center"},
    {title: "重量（单位：克）", key: "goodsUnitWeight", ellipsisTooltip: true, align: "center"},
    {title: "备注", key: "goodsDescription", ellipsisTooltip: true, align: "center"},
    {title: "操作", ellipsisTooltip: true, customSlot: "operator", key: "operator", fixed: "right", align: "center"}
  ];
  let dataSource = ref([]);

  function loadDataSource() {
    loading.value = true;
    list({ modelDto: modelDto, page: page }).then(({ success, code, msg, data }) => {
      loading.value = false;
      if (success) {
        dataSource.value = data.data;
        page.total = data.total;
      } else {
        layer.msg(msg, { icon: 2 });
      }
    }).catch((err) => {
      loading.value = false;
      layer.msg(err, { icon: 2, time: 2000 });
    });
  }

  // 改变事件
  const change = function() {
    loadDataSource();
  }

  // 查询
  function toSearch() {
    loadDataSource();
  }

  function renewalModelDto(newModelDto) {
    modelDto = newModelDto;
  }

  // 删除选中数据
  function removeOne(id) {
    let load = layer.load;
    remove({ id: id}).then(({ success, code, msg, data }) => {
      layer.close(load);
      if (success) {
        layer.msg(msg);
        loadDataSource();
      } else {
        layer.msg(msg, { icon: 2 });
      }
    }).catch((err) => {
      layer.msg(err, { icon: 2, time: 2000 });
    });
  }

  function cancel() {
    layer.msg('您已取消操作')
  }

  // 删除选中数据
  function removeList() {
    if (selectedIdList.value.length < 1) {
      layer.msg('请选择要删除的数据', { icon: 2 });
    } else {
      removeByIdList({ idList: selectedIdList.value }).then(({ success, code, msg, data }) => {
        if (success) {
          layer.msg("删除成功！", { icon: 1 });
          selectedIdList.value = [];
          loadDataSource();
        } else {
          layer.msg(msg, { icon: 2 });
        }
      }).catch((err) => {
        layer.msg(err, { icon: 2, time: 2000 });
      });
    }
  }

  /**
   * displayFromLay
   */
  function displayFromLay(title) {
    fromTitle.value = title;
    displayFrom.value = true;
  }
  const toCancel = (key) => {
    fromTitle.value = '标题';
    displayFrom.value = key;
  }

  // 使用onMounted生命周期钩子在组件挂载完成后加载数据
  onMounted(() => {
    loadDataSource();
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