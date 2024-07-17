<template>
  <div class="members">
    <div class="student-application">
      <h3>学生申请表</h3>
      <el-scrollbar style="height: 300px;">
        <el-table :data="studentApplications" style="width: 100%" empty-text="暂无申请">
          <el-table-column prop="name" label="姓名" width="180"></el-table-column>
          <el-table-column prop="studentId" label="学号" width="180"></el-table-column>
          <el-table-column prop="category" label="性别" width="100"></el-table-column>
          <el-table-column prop="formattedDate" label="申请时间" width="180"></el-table-column>
          <el-table-column label="操作" width="180">
            <template #default="scope">
              <el-button @click="confirmAction(scope.row, 'approve')" type="primary" size="small">通过</el-button>
              <el-button @click="confirmAction(scope.row, 'reject')" type="danger" size="small">拒绝</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-scrollbar>
    </div>
    <div class="member-management">
      <h3>社员管理表</h3>
      <el-table :data="members" style="width: 100%" empty-text="暂无成员">
        <el-table-column prop="name" label="姓名" width="180"></el-table-column>
        <el-table-column prop="studentId" label="学号" width="180"></el-table-column>
        <el-table-column prop="category" label="性别" width="100"></el-table-column>
        <el-table-column prop="formattedDate" label="申请时间" width="180"></el-table-column>
        <el-table-column label="操作" width="180">
          <template #default="scope">
            <el-button @click="confirmRemoveMember(scope.row)" type="danger" size="small">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { format } from 'date-fns';
import { ElMessageBox, ElMessage } from 'element-plus';

const studentApplications = ref([]);
const members = ref([]);

const fetchApplications = async (userId) => {
  try {
    const response = await axios.get(`http://localhost:8088/api/applications/with-user-info?userId=${userId}`);
    studentApplications.value = response.data
      .filter(application => application.status === 'pending')
      .map(application => ({
        ...application,
        formattedDate: format(new Date(application.date), 'yyyy-MM-dd')
      }));
  } catch (error) {
    console.error('Error fetching applications:', error);
  }
};

const fetchMembers = async (userId) => {
  try {
    const response = await axios.get(`http://localhost:8088/api/applications/with-user-info?userId=${userId}`);
    members.value = response.data
      .filter(member => member.status === '通过')
      .map(member => ({
        ...member,
        formattedDate: format(new Date(member.date), 'yyyy-MM-dd')
      }));
  } catch (error) {
    console.error('Error fetching members:', error);
  }
};

const confirmAction = (application, action) => {
  const actionText = action === 'approve' ? '通过' : '拒绝';
  ElMessageBox.confirm(`确定要${actionText}该申请吗?`, '确认操作', {
    confirmButtonText: '确认',
    cancelButtonText: '取消',
    type: 'warning',
  }).then(() => {
    if (action === 'approve') {
      approveApplication(application);
    } else {
      rejectApplication(application);
    }
  }).catch(() => {
    ElMessage.info('已取消操作');
  });
};

const approveApplication = async (application) => {
  try {
    await axios.post(`http://localhost:8088/api/applications/approve/${application.id}`);
    studentApplications.value = studentApplications.value.filter(app => app.id !== application.id);
    ElMessage.success('申请已通过');
  } catch (error) {
    console.error('Error approving application:', error);
  }
};

const rejectApplication = async (application) => {
  try {
    await axios.post(`http://localhost:8088/api/applications/reject/${application.id}`);
    studentApplications.value = studentApplications.value.filter(app => app.id !== application.id);
    ElMessage.success('申请已拒绝并删除');
  } catch (error) {
    console.error('Error rejecting application:', error);
  }
};

const confirmRemoveMember = (member) => {
  ElMessageBox.confirm('确定要移除该社员吗?', '确认操作', {
    confirmButtonText: '确认',
    cancelButtonText: '取消',
    type: 'warning',
  }).then(() => {
    removeMember(member);
  }).catch(() => {
    ElMessage.info('已取消操作');
  });
};

const removeMember = async (member) => {
  try {
    await axios.post(`http://localhost:8088/api/applications/reject/${member.id}`);
    members.value = members.value.filter(m => m.id !== member.id);
    ElMessage.success('社员已移除');
  } catch (error) {
    console.error('Error removing member:', error);
  }
};

onMounted(() => {
  const userId = localStorage.getItem('userId');
  fetchApplications(userId);
  fetchMembers(userId);
});
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
