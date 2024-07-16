<template>
  <div class="members">
    <div class="student-application">
      <h3>学生申请表</h3>
      <el-scrollbar style="height: 300px;">
        <el-table :data="studentApplications" style="width: 100%">
          <el-table-column prop="name" label="姓名" width="180"></el-table-column>
          <el-table-column prop="studentId" label="学号" width="180"></el-table-column>
          <el-table-column prop="category" label="性别" width="100"></el-table-column>
          <el-table-column prop="formattedDate" label="申请时间" width="180"></el-table-column>
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
        <el-table-column prop="name" label="姓名" width="180"></el-table-column>
        <el-table-column prop="studentId" label="学号" width="180"></el-table-column>
        <el-table-column prop="gender" label="性别" width="100"></el-table-column>
        <el-table-column prop="formattedDate" label="申请时间" width="180"></el-table-column>
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
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { format } from 'date-fns';

const studentApplications = ref([]);
const members = ref([]);

const fetchApplications = async (userId) => {
  try {
    const response = await axios.get(`http://localhost:8088/api/applications/with-user-info?userId=${userId}`);
    studentApplications.value = response.data.map(application => ({
      ...application,
      formattedDate: format(new Date(application.date), 'yyyy-MM-dd')
    }));
  } catch (error) {
    console.error('Error fetching applications:', error);
  }
};

const fetchMembers = async () => {
  try {
    const response = await axios.get('http://localhost:8088/api/members');
    members.value = response.data.map(member => ({
      ...member,
      formattedDate: format(new Date(member.date), 'yyyy-MM-dd')
    }));
  } catch (error) {
    console.error('Error fetching members:', error);
  }
};

const approveApplication = async (application) => {
  try {
    await axios.post(`http://localhost:8088/api/applications/approve/${application.id}`);
    application.status = 'approved';
    console.log('批准申请:', application);
  } catch (error) {
    console.error('Error approving application:', error);
  }
};

const rejectApplication = async (application) => {
  try {
    await axios.post(`http://localhost:8088/api/applications/reject/${application.id}`);
    application.status = 'rejected';
    console.log('拒绝申请:', application);
  } catch (error) {
    console.error('Error rejecting application:', error);
  }
};

const removeMember = async (member) => {
  try {
    await axios.delete(`http://localhost:8088/api/members/${member.id}`);
    members.value = members.value.filter(m => m.id !== member.id);
    console.log('删除社员:', member);
  } catch (error) {
    console.error('Error removing member:', error);
  }
};

onMounted(() => {
  const userId = localStorage.getItem('userId');
  fetchApplications(userId);
  fetchMembers();
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
