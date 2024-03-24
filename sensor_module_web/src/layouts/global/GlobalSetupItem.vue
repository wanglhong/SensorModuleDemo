<template>
  <div class="global-setup-item">
    <div class="global-setup-item-label">
      {{ label }}
    </div>
    <div class="global-setup-item-extra">
      <slot></slot>
    </div>
  </div>
</template>

<script>
export default {
  name: "GlobalSetupItem",
};
</script>

<script setup>
// const props = withDefaults(defineProps(), {
//   label: "标题"
// })

const props = defineProps({
  label: {
    type: String,
    default: '标题'
  }
})

</script>

<style scoped>
.global-setup-item {
    height: 50px;
    line-height: 50px;
    padding: 0px 15px;
}
.global-setup-item-label {
    float: left;
    font-size: 14px;
}
.global-setup-item-extra {
    float: right;
}
</style>
