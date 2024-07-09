<template>
  <el-container style="height: 100vh;">
    <el-aside width="200px" style="background-color: #f2f2f2;">
      <!-- 侧边栏内容 -->
      <el-menu default-active="all" class="el-menu-vertical-demo" @select="handleCategorySelect">
        <el-menu-item index="all">全部</el-menu-item>
        <el-menu-item index="学术类">学术类</el-menu-item>
        <el-menu-item index="文体类">文体类</el-menu-item>
        <el-menu-item index="志愿服务类">志愿服务类</el-menu-item>
        <el-menu-item index="休闲类">休闲类</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header style="background-color: #b3c0d1; display: flex; align-items: center;">
        <!-- 头部内容 -->
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
        <!-- 主内容 -->
        <el-table :data="paginatedClubs" class="club-table" style="margin-top: 20px;">
          <el-table-column prop="name" label="社团名称" width="180"></el-table-column>
          <el-table-column prop="date" label="发布时间" width="180"></el-table-column>
          <el-table-column prop="author" label="发布人" width="180"></el-table-column>
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
        <div v-if="!filteredClubs.length" class="no-data">
          无合适社团
        </div>
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
  </el-container>
</template>

<script>
import axios from 'axios';
import { store } from '../store';
import { ElMessage } from 'element-plus';

export default {
  name: 'PageTwo',
  data() {
    return {
      searchQuery: '',
      clubs: [],
      currentPage: 1,
      pageSize: 10, // 每页显示的条数
      selectedCategory: 'all',
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
        const response = await axios.get('/api/clubs');
        this.clubs = response.data;
      } catch (error) {
        console.error('Error fetching clubs:', error);
      }
    },
    handleSearch() {
      this.currentPage = 1; // 重置分页到第一页
    },
    applyClub(club) {
      if (!store.applicationRequests.some(req => req.name === club.name)) {
        store.addApplicationRequest(club);
        ElMessage.success('申请已提交');
      }
    },
    handlePageChange(page) {
      this.currentPage = page;
    },
    handleCategorySelect(key) {
      this.selectedCategory = key;
      this.currentPage = 1; // 重置分页到第一页
    },
    isFavorite(club) {
      return store.isFavorite(club);
    },
    toggleFavorite(club) {
      if (store.isFavorite(club)) {
        store.removeFavorite(club);
        ElMessage.success('取消收藏');
      } else {
        store.addFavorite(club);
        ElMessage.success('已收藏');
      }
    },
    isApplied(club) {
      return store.applicationRequests.some(req => req.name === club.name);
    },
  },
  mounted() {
    this.fetchClubs();
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
  height: calc(100vh - 60px); /* 减去header的高度 */
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
