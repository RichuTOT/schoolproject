<template>
    <div class="club-management">
      <el-table :data="clubs" style="width: 1500px">
        <el-table-column prop="index" label="序号" width="100">
          <template #default="scope">
            <span>{{ scope.$index + 1 }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="name" label="社团名称" width="200"></el-table-column>
        <el-table-column prop="date" label="成立时间" width="180"></el-table-column>
        <el-table-column prop="president" label="社长" width="120"></el-table-column>
        <el-table-column label="操作" width="120">
          <template #default="scope">
            <el-button type="danger" size="mini" @click="deleteClub(scope.$index)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue';
  
  const clubs = ref([]);
  
  const deleteClub = (index) => {
    clubs.value.splice(index, 1);
  };
  
  onMounted(async () => {
    try {
      const response = await fetch('/api/clubs'); // 修改为实际的后端接口地址
      const data = await response.json();
      clubs.value = data;
    } catch (error) {
      console.error('获取数据失败', error);
    }
  });
  </script>
  
  <style scoped>
  .club-management {
    padding: 20px;
    display: flex;
    justify-content: center;
    align-items: center;
    margin-left: 200px;
  }
  
  .el-table {
    margin-top: 20px;
  }
  </style>
  