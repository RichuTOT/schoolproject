<template>
  <div class="club-approval">
    <el-table :data="sortedClubs" style="width: 100%">
      <el-table-column prop="clubName" label="社团名称" width="180"></el-table-column>
      <el-table-column prop="publisher" label="发布人" width="180"></el-table-column>
      <el-table-column prop="description" label="详细信息" width="300"></el-table-column>
      <el-table-column label="操作" width="180">
        <template #default="scope">
          <div v-if="scope.row.status === 'pending'">
            <el-button type="primary" @click="confirmApproval(scope.row)">同意</el-button>
            <el-button type="danger" @click="confirmRejection(scope.row)">拒绝</el-button>
          </div>
          <div v-else>
            <span :style="{ color: scope.row.status === 'approved' ? 'green' : 'red' }">
              {{ scope.row.status === 'approved' ? '已同意' : '已拒绝' }}
            </span>
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="status" label="状态" width="180">
        <template #default="scope">
          <span :style="{ color: scope.row.status === 'approved' ? 'green' : scope.row.status === 'rejected' ? 'red' : 'yellow' }">
            {{ scope.row.status === 'pending' ? '审核中' : scope.row.status === 'approved' ? '已同意' : '已拒绝' }}
          </span>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import { ElMessageBox, ElMessage } from 'element-plus';

const clubs = ref([]);

const confirmApproval = (row) => {
  ElMessageBox.confirm('你确定要同意该社团申请吗？', '确认', {
    confirmButtonText: '同意',
    cancelButtonText: '取消',
    type: 'warning',
  })
    .then(() => {
      approveClub(row);
    })
    .catch(() => {
      ElMessage({
        type: 'info',
        message: '已取消操作',
      });
    });
};

const confirmRejection = (row) => {
  ElMessageBox.confirm('你确定要拒绝该社团申请吗？', '确认', {
    confirmButtonText: '拒绝',
    cancelButtonText: '取消',
    type: 'warning',
  })
    .then(() => {
      rejectClub(row);
    })
    .catch(() => {
      ElMessage({
        type: 'info',
        message: '已取消操作',
      });
    });
};

const approveClub = async (row) => {
  try {
    const response = await fetch(`http://localhost:8088/api/club-applications/approve/${row.id}`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
    });
    if (response.ok) {
      row.status = 'approved';
      ElMessage({
        type: 'success',
        message: '社团已同意',
      });
      await fetch(`http://localhost:8088/api/clubs`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          name: row.clubName,
          author: row.publisher,
          date: row.applyTime,
          category: row.category,
          userId: row.userId, // 添加 userId
        }),
      });
      await updateUserRole(row.userId, 'clubleader');
    } else {
      throw new Error('批准失败');
    }
  } catch (error) {
    ElMessage({
      type: 'error',
      message: error.message,
    });
  }
};

const rejectClub = async (row) => {
  try {
    const response = await fetch(`http://localhost:8088/api/club-applications/reject/${row.id}`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
    });
    if (response.ok) {
      row.status = 'rejected';
      ElMessage({
        type: 'success',
        message: '社团已拒绝',
      });
    } else {
      throw new Error('拒绝失败');
    }
  } catch (error) {
    ElMessage({
      type: 'error',
      message: error.message,
    });
  }
};

const updateUserRole = async (userId, role) => {
  try {
    const response = await fetch(`http://localhost:8088/api/users/${userId}/role`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ role }),
    });
    if (!response.ok) {
      throw new Error('角色更新失败');
    }
  } catch (error) {
    ElMessage({
      type: 'error',
      message: error.message,
    });
  }
};

const fetchClubs = async () => {
  try {
    const response = await fetch('http://localhost:8088/api/club-applications');
    const data = await response.json();
    clubs.value = data;
  } catch (error) {
    console.error('获取数据失败', error);
  }
};

onMounted(() => {
  fetchClubs();
});

const sortedClubs = computed(() => {
  return clubs.value.slice().sort((a, b) => {
    if (a.status === 'pending' && b.status !== 'pending') return -1;
    if (a.status !== 'pending' && b.status === 'pending') return 1;
    return 0;
  });
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
