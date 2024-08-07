<template>
  <div class="personal-center">
    <div class="user-info">
      <div class="avatar">
        <img src="../assets/touxiang3.jpg" alt="用户头像" />
      </div>
      <div class="account-info">
        <p>账号：{{ username }}</p>
        <p>角色：{{ role }}</p>
        <el-button type="primary" @click="openEditDialog">编辑资料</el-button>
      </div>
    </div>
    <div class="club-list">
      <h3>社团审批流程</h3>
      <el-table :data="sortedApplicationRequests" style="width: 100%" empty-text="暂无申请">
        <el-table-column prop="date" label="申请时间" :min-width="180">
          <template v-slot="scope">
            <span>{{ formatDate(scope.row.date) }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="name" label="社团名称" :min-width="180"></el-table-column>
        <el-table-column prop="category" label="类别" :min-width="180"></el-table-column>
        <el-table-column prop="status" label="审批状态" :min-width="180">
          <template v-slot="scope">
            <span :style="{ color: getStatusColor(scope.row.status) }">
              {{ formatStatus(scope.row.status) }}
            </span>
          </template>
        </el-table-column>
      </el-table>

      <h3>收藏的社团</h3>
      <el-table :data="sortedFavoriteClubs" style="width: 100%" empty-text="暂无收藏的社团">
        <el-table-column prop="date" label="收藏时间" :min-width="180">
          <template v-slot="scope">
            <span>{{ formatDate(scope.row.date) }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="name" label="社团名称" :min-width="180"></el-table-column>
        <el-table-column prop="category" label="类别" :min-width="180"></el-table-column>
        <el-table-column label="操作" :min-width="180">
          <template v-slot="scope">
            <el-button type="danger" @click="removeFavorite(scope.row)">取消收藏</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <my-dialog ref="myDialog" @submit="handleFormSubmit" />
  </div>
</template>

<script>
import { computed, ref, onMounted } from 'vue';
import axios from 'axios';
import { ElMessage } from 'element-plus';
import { format } from 'date-fns';
import MyDialog from './MyForm.vue';

export default {
  name: 'PageOne',
  components: {
    MyDialog,
  },
  setup() {
    const userId = localStorage.getItem('userId');
    const username = localStorage.getItem('username');
    const role = localStorage.getItem('role');
    const applicationRequests = ref([]);
    const favoriteClubs = ref([]);
    const avatarFilename = ref('default-avatar.png'); // 默认头像文件名
    const myDialog = ref(null);

    const sortedApplicationRequests = computed(() => {
      return applicationRequests.value.sort((a, b) => new Date(b.date) - new Date(a.date));
    });

    const sortedFavoriteClubs = computed(() => {
      return favoriteClubs.value.sort((a, b) => new Date(b.date) - new Date(a.date));
    });

    const fetchApplications = async () => {
      try {
        const response = await axios.get(`/api/applications/user/${userId}`);
        applicationRequests.value = response.data;
      } catch (error) {
        console.error('Error fetching application requests:', error);
        ElMessage.error('获取社团申请数据失败');
      }
    };

    const fetchFavorites = async () => {
      try {
        const response = await axios.get(`/api/favorites/user/${userId}`);
        favoriteClubs.value = response.data;
      } catch (error) {
        console.error('Error fetching favorites:', error);
        ElMessage.error('获取收藏社团数据失败');
      }
    };

    const getStatusColor = (status) => {
      switch (status) {
        case '审核中':
          return 'orange';
        case '已通过':
          return 'green';
        case '已拒绝':
          return 'red';
        default:
          return 'black';
      }
    };

    const formatStatus = (status) => {
      switch (status) {
        case '审核中':
          return '审核中';
        case '已通过':
          return '已通过';
        case '已拒绝':
          return '已拒绝';
        default:
          return status;
      }
    };

    const removeFavorite = async (club) => {
      try {
        await axios.delete(`/api/favorites`, {
          data: { userId: userId, name: club.name },
          withCredentials: true,
        });
        favoriteClubs.value = favoriteClubs.value.filter(fav => fav.name !== club.name);
        ElMessage.success('取消收藏成功');
      } catch (error) {
        console.error('Error removing favorite:', error);
        ElMessage.error('取消收藏失败');
      }
    };

    const formatDate = (dateString) => {
      return format(new Date(dateString), 'yyyy-MM-dd');
    };

    const openEditDialog = async () => {
      try {
        const response = await axios.get(`/api/users/${userId}`);
        myDialog.value.showDialog();
        myDialog.value.formData = response.data;
      } catch (error) {
        console.error('Error fetching user info:', error);
      }
    };

    const handleFormSubmit = async (formData) => {
      try {
        await axios.put(`/api/users/${userId}`, formData);
        ElMessage.success('个人资料更新成功');
      } catch (error) {
        console.error('Error updating user info:', error);
        ElMessage.error('个人资料更新失败');
      }
    };

    onMounted(async () => {
      await fetchApplications();
      await fetchFavorites();
    });

    return {
      username,
      role,
      sortedApplicationRequests,
      sortedFavoriteClubs,
      getStatusColor,
      formatStatus,
      removeFavorite,
      formatDate,
      openEditDialog,
      handleFormSubmit,
      myDialog,
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

.no-favorites, .no-requests {
  text-align: center;
  color: #999;
  margin-top: 20px;
}

.activity-image {
  width: 100px;
  height: 100px;
  object-fit: cover;
}
</style>
