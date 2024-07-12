<template>
    <div class="activity-approval">
      <el-card v-for="(activity, index) in activities" :key="index" class="activity-card">
        <div slot="header" class="clearfix">
          <span>活动申请</span>
          <el-button 
            type="success" 
            icon="el-icon-check" 
            size="mini" 
            @click="approveActivity(index)"
            style="float: right; margin-left: 10px;">       
          </el-button>
          <el-button 
            type="danger" 
            icon="el-icon-close" 
            size="mini" 
            @click="rejectActivity(index)"
            style="float: right;">
          </el-button>
        </div>
        <div>
          <p><strong>活动状态:</strong> {{ activity.status }}</p>
          <p><strong>申请社团:</strong> {{ activity.club }}</p>
          <p><strong>活动名称:</strong> {{ activity.name }}</p>
          <p><strong>活动时间:</strong> {{ activity.date }}</p>
          <p><strong>活动地点:</strong> {{ activity.location }}</p>
          <p><strong>活动经费:</strong> {{ activity.budget }}</p>
          <p><strong>活动介绍:</strong> {{ activity.description }}</p>
        </div>
      </el-card>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue';
  
  const activities = ref([]);
  
  const approveActivity = (index) => {
    activities.value[index].status = '已通过';
  };
  
  const rejectActivity = (index) => {
    activities.value[index].status = '已拒绝';
  };
  
  onMounted(async () => {
    // 从后端导入数据的逻辑
    try {
      const response = await fetch('/api/activities'); // 修改为实际的后端接口地址
      const data = await response.json();
      activities.value = data;
    } catch (error) {
      console.error('获取数据失败', error);
    }
  });
  </script>
  
  <style scoped>
  .activity-approval {
    padding: 20px;
    display: flex;
    flex-direction: column;
    align-items: center;
  }
  
  .activity-card {
    width: 80%;
    margin: 10px 0;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  }
  
  .el-card__header {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  
  .el-button {
    margin-left: 10px;
  }
  </style>
  