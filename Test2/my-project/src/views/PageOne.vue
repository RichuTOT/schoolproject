<template>
  <div class="personal-center">
    <div class="user-info">
      <div class="avatar">
        <img src="https://via.placeholder.com/100" alt="用户头像" />
      </div>
      <div class="account-info">
        <p>账号：xxxxxxxxxxx</p>
        <el-button type="primary">编辑资料</el-button>
      </div>
    </div>
    <div class="club-list">
      <h3>社团清单</h3>
      <el-table :data="clubs" style="width: 100%">
        <el-table-column prop="date" label="时间" width="180"></el-table-column>
        <el-table-column prop="name" label="社团名称" width="180"></el-table-column>
        <el-table-column prop="status" label="审核状态" width="180"></el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
export default {
  name: 'PageOne',
  data() {
    return {
      clubs: [
        { date: '2024-07-05', name: '社团A', status: '已通过' },
        { date: '2024-07-05', name: '社团B', status: '审核中' },
      ],
    };
  },
};
</script>

<style scoped>
.personal-center {
  padding: 20px;
}

.user-info {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}

.avatar {
  width: 100px;
  height: 100px;
  border: 2px solid #ccc;
  border-radius: 50%;
  overflow: hidden;
  margin-right: 20px;
}

.avatar img {
  width: 100%;
  height: 100%;
}

.account-info {
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.account-info p {
  margin: 0;
  font-size: 16px;
}

.club-list {
  margin-top: 20px;
}

.el-table th, .el-table td {
  text-align: center;
}
</style>
