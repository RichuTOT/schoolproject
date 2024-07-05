<template>
    <div>
      <h1>Page Three</h1>
      <el-table :data="tableData">
        <el-table-column prop="date" label="Date" width="180"></el-table-column>
        <el-table-column prop="name" label="Name" width="180"></el-table-column>
        <el-table-column prop="address" label="Address"></el-table-column>
      </el-table>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        tableData: [
          { date: '2016-05-02', name: 'Tom', address: 'No. 189, Grove St, Los Angeles' },
          // 添加更多数据
        ]
      };
    }
  };
  </script>
  