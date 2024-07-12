<template>
    <div class="club-approval">
      <el-table :data="clubs" style="width: 100%">
        <el-table-column prop="name" label="社团名称" width="180"></el-table-column>
        <el-table-column prop="publisher" label="发布人" width="180"></el-table-column>
        <el-table-column label="详细信息" width="180">
          <template #default="scope">
            <el-button type="text" @click="viewDetails(scope.row)">查看</el-button>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="180">
          <template #default="scope">
            <el-button type="primary" @click="approveClub(scope.row)">同意</el-button>
            <el-button type="danger" @click="rejectClub(scope.row)">拒绝</el-button>
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="180">
          <template #default="scope">
            <span :style="{ color: scope.row.status === '发布成功' ? 'green' : 'red' }">{{ scope.row.status }}</span>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue';
  
  const clubs = ref([]);
  
  const viewDetails = (row) => {
    // 查看社团详细信息的逻辑
    console.log('查看详情', row);
  };
  
  const approveClub = (row) => {
    // 同意社团申请的逻辑
    row.status = '发布成功';
    console.log('同意社团', row);
  };
  
  const rejectClub = (row) => {
    // 拒绝社团申请的逻辑
    row.status = '已拒绝';
    console.log('拒绝社团', row);
  };
  
  onMounted(async () => {
    // 从后端导入数据的逻辑
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
  .club-approval {
    padding: 20px;
  }
  
  .el-table {
    width: 100%;
    margin-left: 40px;
  }
  </style>
  