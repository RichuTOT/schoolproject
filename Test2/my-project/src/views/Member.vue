<template>
    <div class="members">
      <div class="student-application">
        <h3>学生申请表</h3>
        <el-scrollbar style="height: 300px;">
            <el-table :data="studentApplications" style="width: 100%">
          <!-- 学生申请表格的列定义 -->
          <el-table-column prop="name" label="姓名" width="180"></el-table-column>
          <el-table-column prop="id" label="学号" width="180"></el-table-column>
          <el-table-column prop="date" label="申请时间" width="180"></el-table-column>
          <el-table-column prop="reason" label="申请理由"></el-table-column>
          <el-table-column label="操作" width="180">
            <template #default="scope">
              <el-button @click="approveApplication(scope.row)" type="primary" size="small">通过</el-button>
              <el-button @click="rejectApplication(scope.row)" type="danger" size="small">拒绝</el-button>
            </template>
          </el-table-column>
        </el-table>
        </el-scrollbar>  
      </div>
      <div class="member-management">
        <h3>社员管理表</h3>
        <el-table :data="members" style="width: 100%">
          <!-- 社员管理表格的列定义 -->
          <el-table-column prop="name" label="姓名" width="180"></el-table-column>
          <el-table-column prop="id" label="学号" width="180"></el-table-column>
          <el-table-column prop="gender" label="性别" width="100"></el-table-column>
          <el-table-column label="操作" width="180">
            <template #default="scope">
              <el-button @click="removeMember(scope.row)" type="danger" size="small">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref } from 'vue';
  
  const studentApplications = ref([
    // 学生申请表数据
    { name: '小明', id: '2019000001', date:'xx/xx/xxxx', reason: '从小热爱音乐，想加入社团结交跟自己一样喜欢音乐的好朋友，一起参加音乐活动，享受音乐带来的快乐。' },
    { name: '蓝蓝', id: '2019000080', date:'xx/xx/xxxx', reason: '从小热爱音乐，想加入社团结交跟自己一样喜欢音乐的好朋友，一起参加音乐活动，享受音乐带来的快乐。' },
    { name: '小红', id: '2019123456', date:'xx/xx/xxxx', reason: '从小热爱音乐，想加入社团结交跟自己一样喜欢音乐的好朋友，一起参加音乐活动，享受音乐带来的快乐。' },

  ]);
  
  const members = ref([
    // 社员管理表数据
    { name: '小明', id: '2019000001', gender: '男' },
    { name: '丽丽', id: '2019000012', gender: '女' },
    { name: '莉莉', id: '2019000036', gender: '女' },
    { name: '力力', id: '2019000101', gender: '男' },
    { name: '黎黎', id: '2019000009', gender: '女' },

  ]);
  
  const approveApplication = (application) => {
    // 批准申请的逻辑
    console.log('批准申请:', application);
  };
  
  const rejectApplication = (application) => {
    // 拒绝申请的逻辑
    console.log('拒绝申请:', application);
  };
  
  const removeMember = (member) => {
    // 删除社员的逻辑
    console.log('删除社员:', member);
  };
  </script>
  
  <style scoped>
  .members {
    padding: 20px;
  }
  
  .student-application,
  .member-management {
    margin-bottom: 20px;
  }
  
  h3 {
    margin-bottom: 10px;
  }
  
  .el-table {
    width: 100%;
    margin-top: 40px;
  }
  </style>