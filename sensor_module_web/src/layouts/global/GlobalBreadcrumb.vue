<template>
    <lay-breadcrumb>
        <lay-breadcrumb-item v-for="(breadcrumb, index) in breadcrumbs" :key="index">
            {{ breadcrumb.title }}
        </lay-breadcrumb-item>
    </lay-breadcrumb>
</template>

<script>
export default {
    name: "GlobalBreadcrumb"
}
</script>


<script setup>
import { computed } from "vue";
import { useRoute } from "vue-router";
import { getParents } from "@/library/treeUtil.js";
import { useUserStore } from "@/store/user.js";

const userStore = useUserStore();
const route = useRoute();
const breadcrumbs = computed(() => getParents(userStore.menus, route.path)?.reverse());
</script>
