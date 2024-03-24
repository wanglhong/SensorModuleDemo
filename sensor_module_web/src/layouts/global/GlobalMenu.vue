<template>
  <lay-menu
    :tree="true"
    :collapse="collapse"
    :level="appStore.level"
    :inverted="appStore.inverted"
    :theme="appStore.sideTheme"
    :openKeys="openKeys"
    :selectedKey="selectedKey"
    @changeOpenKeys="changeOpenKeys"
    @changeSelectedKey="changeSelectedKey"
  >
    <GlobalMenuItem :menus="menus"></GlobalMenuItem>
  </lay-menu>
</template>

<script>
export default {
  name: "GlobalMenu",
};
</script>

<script setup>
import { useAppStore } from "@/store/app.js"
import GlobalMenuItem from "@/layouts/global/GlobalMenuItem.vue";

const appStore = useAppStore()

const props = defineProps({
  collapse: {
    type: Boolean,
    default: false
  },
  menus: {
    type: Array,
    default: () => []
  },
  openKeys: {
    type: Array,
    default: () => []
  },
  selectedKey: {
    type: String,
    default: ''
  }
})

const emits = defineEmits(["changeOpenKeys", "changeSelectedKey"])

const changeOpenKeys = keys => {
  emits("changeOpenKeys", keys)
}

const changeSelectedKey = key => {
  emits("changeSelectedKey", key)
}

</script>

<style>
.layui-nav-tree * {
  font-size: 14px;
}

.layui-nav-tree .layui-nav-item > a,
.layui-nav-tree.inverted .layui-nav-item > a {
  padding: 3px 22px;
}

.layui-nav-tree.inverted .layui-this > a {
  padding: 3px 16px;
}

.layui-nav-tree .layui-nav-item > a > span {
  padding-left: 10px;
}

.layui-nav-tree .layui-nav-item > a .layui-nav-more {
  font-size: 12px!important;
  padding: 3px 0px;
}
</style>