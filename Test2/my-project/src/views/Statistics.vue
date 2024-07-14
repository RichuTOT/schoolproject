<template>
  <div class="statistics">
    <div class="card">
      <h3>社团数量</h3>
      <p>{{ clubCount }}</p>
    </div>
    <div class="card">
      <h3>社员人数</h3>
      <p>{{ memberCount }}</p>
    </div>
    <div class="card">
      <h3>活动数量</h3>
      <p>{{ activityCount }}</p>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';

const clubCount = ref(0);
const memberCount = ref(0);
const activityCount = ref(0);

onMounted(async () => {
  try {
    const response = await fetch('http://localhost:8088/api/statistics'); // 修改为实际的后端接口地址
    const data = await response.json();
    clubCount.value = data.clubCount;
    memberCount.value = data.memberCount;
    activityCount.value = data.activityCount;
  } catch (error) {
    console.error('获取数据失败', error);
  }
});
</script>

<style scoped>
.statistics {
  display: flex;
  justify-content: space-between;
  padding: 20px;
}

.card {
  width: 30%;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 5px;
  text-align: center;
}

.card h3 {
  font-size: 18px;
  margin-bottom: 10px;
}

.card p {
  font-size: 20px;
  font-weight: bold;
}
</style>
