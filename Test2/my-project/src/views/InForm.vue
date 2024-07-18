<template>
  <div class="club-info">
    <el-form label-width="100px" :model="club">
      <el-form-item label="社团名称">
        <span>{{ club.clubName }}</span>
      </el-form-item>
      <el-form-item label="成立时间">
        <span>{{ formatApplyTime(club.applyTime) }}</span>
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
    </el-form>
  </div>
</template>

<script setup>
import { defineProps } from 'vue';
import { format } from 'date-fns';

const props = defineProps({
  club: {
    type: Object,
    required: true,
  },
});

const formatApplyTime = (applyTime) => {
  return format(new Date(applyTime), 'yyyy-MM-dd');
};
</script>

<style scoped>
.avatar-img {
  width: 150px;
  height: auto;
}
</style>
