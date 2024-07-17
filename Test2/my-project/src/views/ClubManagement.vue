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
          style="margin-right: 10px; flex: 1; background-color: #fff;"
        />
        <el-button type="primary" @click="handleSearch">点击搜索</el-button>
      </el-header>
      <el-main>
        <el-table v-if="filteredClubs.length" :data="paginatedClubs" class="club-table" style="margin-top: 20px; width: 100%;">
          <el-table-column prop="name" label="社团名称" :min-width="250"></el-table-column>
          <el-table-column prop="date" label="成立时间" :min-width="200">
            <template v-slot="scope">
              <span>{{ formatDate(scope.row.date) }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="author" label="社长" :min-width="200"></el-table-column>
          <el-table-column label="操作" :min-width="150">
            <template v-slot="scope">
              <el-button type="danger" @click="confirmDelete(scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <div v-else class="no-data">无合适社团</div>
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
import { ElMessage, ElMessageBox } from 'element-plus';
import { format } from 'date-fns';

export default {
  name: 'ClubManagement',
  data() {
    return {
      searchQuery: '',
      clubs: [],
      currentPage: 1,
      pageSize: 10,
      selectedCategory: 'all',
      userId: localStorage.getItem('userId'),
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
        const response = await axios.get('http://localhost:8088/api/clubs', { withCredentials: true });
        this.clubs = response.data;
      } catch (error) {
        console.error('Error fetching clubs:', error);
      }
    },
    handleSearch() {
      this.currentPage = 1;
    },
    handlePageChange(page) {
      this.currentPage = page;
    },
    handleCategorySelect(key) {
      this.selectedCategory = key;
      this.currentPage = 1;
    },
    formatDate(dateString) {
      return format(new Date(dateString), 'yyyy-MM-dd');
    },
    confirmDelete(club) {
      ElMessageBox.confirm('你确定要删除该社团吗？', '确认', {
        confirmButtonText: '删除',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          this.deleteClub(club.id);
        })
        .catch(() => {
          ElMessage({
            type: 'info',
            message: '已取消删除',
          });
        });
    },
    async deleteClub(clubId) {
      try {
        await axios.delete(`http://localhost:8088/api/clubs/${clubId}`, { withCredentials: true });
        this.clubs = this.clubs.filter(club => club.id !== clubId);
        ElMessage({
          type: 'success',
          message: '社团已删除',
        });
      } catch (error) {
        console.error('Error deleting club:', error);
        ElMessage({
          type: 'error',
          message: `删除失败: ${error.response ? error.response.data : error.message}`,
        });
      }
    },
  },
  async mounted() {
    await this.fetchClubs();
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

.club-table {
  width: 100%; 
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
