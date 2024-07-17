<template>
  <el-container style="height: 100vh;">
    <el-aside width="200px" style="background-color: #f2f2f2;">
      <el-menu default-active="all" class="el-menu-vertical-demo" @select="handleCategorySelect">
        <el-menu-item index="all">全部</el-menu-item>
        <el-menu-item index="文学类">文学类</el-menu-item>
        <el-menu-item index="体育类">体育类</el-menu-item>
        <el-menu-item index="哲学类">哲学类</el-menu-item>
        <el-menu-item index="其他">其他</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header style="background-color: #fff; display: flex; align-items: center;">
        <el-input
          v-model="searchQuery"
          placeholder="输入搜索内容"
          prefix-icon="el-icon-search"
          @keyup.enter="handleSearch"
          style="margin-right: 10px; flex: 1;"
        />
        <el-button type="primary" @click="handleSearch">点击搜索</el-button>
      </el-header>
      <el-main>
        <el-table :data="paginatedClubs" class="club-table" style="margin-top: 20px;" empty-text="暂无合适社团">
          <el-table-column prop="name" label="社团名称" width="180"></el-table-column>
          <el-table-column prop="date" label="发布时间" width="180">
            <template v-slot="scope">
              <span>{{ formatDate(scope.row.date) }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="author" label="发布人" width="180"></el-table-column>
          <el-table-column prop="category" label="类别" width="180"></el-table-column>
          <el-table-column label="介绍">
            <template #default="scope">
              <el-button link @click="openDialog(scope.row)">详细</el-button>
            </template>
          </el-table-column>
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
          <el-table-column label="操作" width="150">
            <template v-slot="scope">
              <el-button
                :type="isApplied(scope.row) ? 'info' : 'primary'"
                @click="applyClub(scope.row)"
                :disabled="isApplied(scope.row)"
              >
                {{ isApplied(scope.row) ? '已申请' : '申请' }}
              </el-button>
            </template>
          </el-table-column>
        </el-table>
        
        <el-pagination
          v-if="filteredClubs.length"
          style="margin-top: 20px;"
          background
          layout="prev, pager, next"
          :total="filteredClubs.length"
          :page-size="pageSize"
          @current-change="handlePageChange"
        />
      </el-main>
    </el-container>
    <el-dialog v-model="dialogVisible" title="社团详情" width="50%">
      <InForm v-if="selectedClub" :club="selectedClub" />
    </el-dialog>
  </el-container>
</template>

<script>
import axios from 'axios';
import { ElMessage } from 'element-plus';
import { format } from 'date-fns';
import InForm from './InForm.vue';

export default {
  name: 'PageTwo',
  components: {
    InForm,
  },
  data() {
    return {
      searchQuery: '',
      clubs: [],
      currentPage: 1,
      pageSize: 10,
      selectedCategory: 'all',
      userId: localStorage.getItem('userId'),
      appliedClubs: [],
      favoriteClubs: [],
      dialogVisible: false,
      selectedClub: null,
    };
  },
  computed: {
    filteredClubs() {
      return this.clubs.filter(club => {
        const searchRegex = new RegExp(this.searchQuery, 'i');
        const matchesSearchQuery = (
          searchRegex.test(club.name) ||
          searchRegex.test(club.date) ||
          searchRegex.test(club.author) ||
          searchRegex.test(club.category)
        );
        const matchesCategory = (
          this.selectedCategory === 'all' || club.category === this.selectedCategory
        );
        return matchesSearchQuery && matchesCategory;
      });
    },
    paginatedClubs() {
      const start = (this.currentPage - 1) * this.pageSize;
      const end = start + this.pageSize;
      return this.filteredClubs.slice(start, end);
    },
  },
  methods: {
    async fetchClubs() {
      try {
        const response = await axios.get('/api/clubs', { withCredentials: true });
        this.clubs = response.data;
      } catch (error) {
        console.error('Error fetching clubs:', error);
      }
    },
    async fetchAppliedClubs() {
      try {
        const response = await axios.get(`/api/applications/user/${this.userId}`, { withCredentials: true });
        this.appliedClubs = response.data.map(application => application.name);
      } catch (error) {
        console.error('Error fetching applied clubs:', error);
      }
    },
    async fetchFavoriteClubs() {
      try {
        const response = await axios.get(`/api/favorites/user/${this.userId}`, { withCredentials: true });
        this.favoriteClubs = response.data.map(favorite => favorite.name);
      } catch (error) {
        console.error('Error fetching favorite clubs:', error);
      }
    },
    handleSearch() {
      this.currentPage = 1;
    },
    async applyClub(club) {
      try {
        await axios.post('/api/applications', {
          name: club.name,
          category: club.category,
          userId: this.userId,
          status: '审批中',
          date: new Date().toISOString()
        }, { withCredentials: true });
        this.appliedClubs.push(club.name);
        ElMessage.success('申请已提交');
      } catch (error) {
        console.error('Error applying for club:', error);
        ElMessage.error('申请提交失败');
      }
    },
    async toggleFavorite(club) {
      try {
        if (this.isFavorite(club)) {
          const response = await axios.delete(`/api/favorites`, {
            data: { userId: this.userId, name: club.name },
            withCredentials: true,
          });
          this.favoriteClubs = this.favoriteClubs.filter(name => name !== club.name);
          ElMessage.success('取消收藏成功');
        } else {
          await axios.post('/api/favorites', {
            name: club.name,
            category: club.category,
            userId: this.userId,
            date: new Date().toISOString(),
          }, { withCredentials: true });
          this.favoriteClubs.push(club.name);
          ElMessage.success('已收藏');
        }
      } catch (error) {
        console.error('Error toggling favorite:', error);
        ElMessage.error('操作失败');
      }
    },
    handlePageChange(page) {
      this.currentPage = page;
    },
    handleCategorySelect(key) {
      this.selectedCategory = key;
      this.currentPage = 1;
    },
    isFavorite(club) {
      return this.favoriteClubs.includes(club.name);
    },
    isApplied(club) {
      return this.appliedClubs.includes(club.name);
    },
    formatDate(dateString) {
      return format(new Date(dateString), 'yyyy-MM-dd');
    },
    async openDialog(club) {
      try {
        const response = await axios.get(`/api/club-applications/name/${club.name}`);
        this.selectedClub = response.data[0];
        this.dialogVisible = true;
      } catch (error) {
        console.error('Error fetching club application details:', error);
        ElMessage.error('获取社团详情失败');
      }
    },
  },
  async mounted() {
    await this.fetchClubs();
    await this.fetchAppliedClubs();
    await this.fetchFavoriteClubs();
  },
};
</script>

<style scoped>
.el-container {
  height: 100vh;
}

.el-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
}

.el-main {
  height: calc(100vh - 60px);
  overflow-y: auto;
}

.el-table th, .el-table td {
  text-align: center;
}

.no-data {
  text-align: center;
  margin-top: 20px;
  color: #909399;
}

.el-pagination {
  text-align: center;
}
</style>
