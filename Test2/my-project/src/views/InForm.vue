<template>
    <div class="club-info">
      <h1>{{ club.name }}</h1>
      <el-table :data="clubInfo" class="club-table">
        <el-table-column prop="label" label="信息" width="200"></el-table-column>
        <el-table-column prop="value" label="内容"></el-table-column>
      </el-table>
    </div>
  </template>
  
  <script setup>
  import { ref } from 'vue';
  
  const club = ref({
    name: '音乐社',
    founded: '2022-6-1',
    description: '音乐社是为学生提供音乐交流的社团，致力于提供一个音乐喜爱者交流的平台，激发学生对音乐的热爱。',
    type: '艺术类',
    leader: '小明',
    avatar: '/path/to/avatar.png'
  });
  
  const clubInfo = ref([
    { label: '成立时间', value: club.value.founded },
    { label: '社团描述', value: club.value.description },
    { label: '社团类型', value: club.value.type },
    { label: '社团负责人', value: club.value.leader },
    { label: '社团头像', value: `<img src="${club.value.avatar}" alt="Club Avatar" class="avatar-img">` }
  ]);
  </script>
  
  <style scoped>
  .club-info {
    padding: 20px;
    max-width: 1000px; /* 这里设置了最大宽度，可以根据需要调整 */
    margin: 0 auto;
  }
  
  .club-table {
    font-size: 18px; /* 调整表格字体大小 */
  }
  
  .el-table th, .el-table td {
    padding: 20px; /* 调整单元格内边距 */
  }
  
  .el-table-column--label {
    width: 200px; /* 调整信息列的宽度 */
  }
  
  .avatar-img {
    width: 150px;
    height: auto;
  }
  </style>
  