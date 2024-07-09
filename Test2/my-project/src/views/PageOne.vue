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
      <h3>社团审批流程</h3>
      <el-table :data="sortedApplicationRequests" style="width: 100%">
        <el-table-column prop="date" label="申请时间" width="180"></el-table-column>
        <el-table-column prop="name" label="社团名称" width="180"></el-table-column>
        <el-table-column prop="category" label="类别" width="180"></el-table-column>
        <el-table-column prop="status" label="审批状态" width="180">
          <template v-slot="scope">
            <span :style="{ color: getStatusColor(scope.row.status) }">
              {{ scope.row.status }}
            </span>
          </template>
        </el-table-column>
      </el-table>
      <div v-if="sortedApplicationRequests.length === 0" class="no-requests">暂无申请</div>

      <h3>收藏的社团</h3>
      <el-table :data="sortedFavoriteClubs" style="width: 100%">
        <el-table-column prop="date" label="收藏时间" width="180"></el-table-column>
        <el-table-column prop="name" label="社团名称" width="180"></el-table-column>
        <el-table-column prop="category" label="类别" width="180"></el-table-column>
        <el-table-column label="收藏" width="150">
          <template v-slot="scope">
            <el-button
              :type="isFavorite(scope.row) ? 'danger' : 'success'"
              @click="toggleFavorite(scope.row)"
            >
              {{ isFavorite(scope.row) ? '取消收藏' : '收藏' }}
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <div v-if="sortedFavoriteClubs.length === 0" class="no-favorites">暂无收藏的社团</div>
    </div>
  </div>
</template>

<script>
import { computed } from 'vue';
import { store } from '../store';
import { ElMessage } from 'element-plus';

export default {
  name: 'PageOne',
  setup() {
    const toggleFavorite = (club) => {
      if (store.isFavorite(club)) {
        store.removeFavorite(club);
        ElMessage({
          message: '取消收藏',
          type: 'info',
        });
      } else {
        store.addFavorite(club);
        ElMessage({
          message: '已收藏',
          type: 'success',
        });
      }
    };

    const isFavorite = (club) => {
      return store.isFavorite(club);
    };

    const sortedFavoriteClubs = computed(() => {
      return store.favoriteClubs.sort((a, b) => new Date(b.date) - new Date(a.date));
    });

    const sortedApplicationRequests = computed(() => {
      return store.applicationRequests.sort((a, b) => new Date(b.date) - new Date(a.date));
    });

    const getStatusColor = (status) => {
      switch (status) {
        case '审批中':
          return 'orange';
        case '申请通过':
          return 'green';
        case '申请失败':
          return 'red';
        default:
          return 'black';
      }
    };

    return {
      toggleFavorite,
      isFavorite,
      sortedFavoriteClubs,
      sortedApplicationRequests,
      getStatusColor,
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
</style>
