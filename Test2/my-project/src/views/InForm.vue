<template>
  <div class="club-info">
    <el-form label-width="100px" :model="club">
      <el-form-item label="社团名称">
        <span>{{ club.clubName }}</span>
      </el-form-item>
      <el-form-item label="成立时间">
        <span>{{ club.applyTime }}</span>
      </el-form-item>
      <el-form-item label="社团描述">
        <span>{{ club.description }}</span>
      </el-form-item>
      <el-form-item label="社团类型">
        <span>{{ club.category }}</span>
      </el-form-item>
      <el-form-item label="社团负责人">
        <span>{{ club.publisher }}</span>
      </el-form-item>
      <el-form-item label="社团头像">
        <img :src="club.imageUrl" alt="Club Avatar" class="avatar-img" />
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup>
import { defineProps } from 'vue';

const props = defineProps({
  club: {
    type: Object,
    required: true,
  },
});
</script>

<style scoped>
.avatar-img {
  width: 150px;
  height: auto;
}
</style>
